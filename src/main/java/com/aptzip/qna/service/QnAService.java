package com.aptzip.qna.service;

import com.aptzip.qna.constant.Category;
import com.aptzip.qna.dto.request.QnARegistRequest;
import com.aptzip.qna.dto.response.QnAListResponse;
import com.aptzip.qna.entity.QnA;
import com.aptzip.qna.repository.QnARepository;
import com.aptzip.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QnAService {

    private final QnARepository qnARepository;

    @Transactional
    public String qnaSave(User user, QnARegistRequest qnARegistRequest) {
        QnA qna = qnARegistRequest.toEntity(user);
        return qnARepository.save(qna).getQnaUuid();
    }

    public List<QnAListResponse> getQnAList() {
        List<QnA> results = qnARepository.findAll();

        return results.stream()
                .map(result -> new QnAListResponse(
                        (String) result.getQnaUuid(),
                        (Category) result.getCategory(),
                        (String) result.getTitle(),
                        (LocalDateTime) result.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

}
