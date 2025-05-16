package com.aptzip.qna.service;

import com.aptzip.qna.constant.Category;
import com.aptzip.qna.dto.request.QnARegistRequest;
import com.aptzip.qna.dto.response.DetailResponse;
import com.aptzip.qna.dto.response.QnAAnswerResponse;
import com.aptzip.qna.dto.response.QnAListResponse;
import com.aptzip.qna.entity.Answer;
import com.aptzip.qna.entity.QnA;
import com.aptzip.qna.repository.AnswerRepository;
import com.aptzip.qna.repository.QnARepository;
import com.aptzip.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QnAService {

    private final QnARepository qnARepository;
    private final AnswerRepository answerRepository;

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

    public DetailResponse qnaDetail(String qnaUuid) {
        QnA qnA =  qnARepository.findById(qnaUuid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected qna"));

        return DetailResponse
                .builder()
                .category(qnA.getCategory())
                .title(qnA.getTitle())
                .content(qnA.getContent())
                .createdAt(qnA.getCreatedAt())
                .profileUrl(qnA.getUser().getProfileUrl())
                .nickname(qnA.getUser().getNickname())
                .build();
    }

    public List<QnAAnswerResponse> qnaAnswerList(String qnaUuid) {
        Optional<Answer> answer = answerRepository.findById(qnaUuid);

        return answer.stream()
                .map(result -> new QnAAnswerResponse(
                        (String) result.getQnaAnsUuid(),
                        (String) result.getContent(),
                        (String) result.getUser().getProfileUrl(),
                        (String) result.getUser().getNickname(),
                        (LocalDateTime) result.getCreatedAt()
                )).collect(Collectors.toList());
    }

}
