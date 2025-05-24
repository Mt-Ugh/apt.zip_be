package com.aptzip.user.service;

import com.aptzip.common.service.MinioService;
import com.aptzip.interestArea.repositiory.InterestRepository;
import com.aptzip.interestDeal.respository.InterestSaleRepositiory;
import com.aptzip.qna.entity.QnA;
import com.aptzip.qna.repository.AnswerRepository;
import com.aptzip.qna.repository.QnARepository;
import com.aptzip.review.repository.ReviewRepository;
import com.aptzip.user.dto.request.AddUserRequest;
import com.aptzip.user.dto.request.UpdateUserRequest;
import com.aptzip.user.dto.response.UserDetailResponse;
import com.aptzip.user.entity.User;
import com.aptzip.user.repository.UserRepository;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final QnARepository qnARepository;
    private final AnswerRepository answerRepository;
    private final ReviewRepository reviewRepository;
    private final InterestRepository interestAreaRepository;
    private final InterestSaleRepositiory interestSaleRepositiory;
    private final MinioService minioService;

    @Value("${minio.bucket}")
    private String bucketName;

    @Value("${minio.url}")
    private String minioUrl;


    public String save(AddUserRequest addUserRequest) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = addUserRequest.toEntity();
        user.encodePassword(encoder);

        return userRepository.save(user).getUserUuid();
    }

    public UserDetailResponse findByUuid(String userUuid) {
        User user = userRepository.findById(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));

        return new UserDetailResponse(
                user.getName(),
                user.getNickname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getProfileUrl()
        );
    }

    @Transactional
    public void updateUser(String userUuid, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));

        String encodedPassword = null;
        if (updateUserRequest.password() != null && !updateUserRequest.password().isBlank()) {
            encodedPassword = passwordEncoder.encode(updateUserRequest.password());
        }

        user.update(
                updateUserRequest.nickname(),
                updateUserRequest.email(),
                encodedPassword
        );
    }

    @Transactional
    public void updateProfileUrl(String userUuid, MultipartFile profileUrl) {
        try {
            User user = userRepository.findById(userUuid)
                    .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));

            String objectName = "profile_" + userUuid + "_" + System.currentTimeMillis() + "." + getExtension(profileUrl.getOriginalFilename());
            String imageUrl = minioService.uploadFile(profileUrl, objectName);
            if (user.getProfileUrl() != null) {
                String oldObjectName = extractObjectNameFromUrl(user.getProfileUrl());
                minioService.deleteFile(oldObjectName);
            }
            user.updateProfileUrl(imageUrl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload profile image", e);
        }
    }

    @Transactional
    public void withdrawUser(String userUuid) {
        User user = userRepository.findById(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
        QnA qna = qnARepository.findQnaUuidByUser(user);

        answerRepository.deleteByQna(qna);
        qnARepository.deleteByUser(user);
        reviewRepository.deleteByUser(user);
        interestAreaRepository.deleteByUser(user);
        interestSaleRepositiory.deleteByUser(user);
        userRepository.delete(user);
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    private String extractObjectNameFromUrl(String url) {
        int idx = url.indexOf(bucketName);
        if (idx == -1) return null;
        return url.substring(idx + bucketName.length() + 1);
    }
}
