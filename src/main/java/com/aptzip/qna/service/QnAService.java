package com.aptzip.qna.service;

import com.aptzip.qna.constant.Category;
import com.aptzip.qna.dto.request.AnswerRequest;
import com.aptzip.qna.dto.request.QnARegistRequest;
import com.aptzip.qna.dto.response.DetailResponse;
import com.aptzip.qna.dto.response.QnAAnswerResponse;
import com.aptzip.qna.dto.response.QnAListResponse;
import com.aptzip.qna.dto.response.RegistResponse;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QnAService {

    private final QnARepository qnARepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public RegistResponse qnaSave(User user, QnARegistRequest qnARegistRequest) {
        QnA qna = qnARegistRequest.toEntity(user);
        String qnaUuid =  qnARepository.save(qna).getQnaUuid();
        return new RegistResponse(qnaUuid);
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

    public DetailResponse qnaDetail(User user, String qnaUuid) {
        QnA qnA =  qnARepository.findById(qnaUuid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected qna"));

        Integer isMine = qnA.getUser().getUserUuid().equals(user.getUserUuid()) ? 1: 0;

        return DetailResponse
                .builder()
                .category(qnA.getCategory())
                .title(qnA.getTitle())
                .content(qnA.getContent())
                .createdAt(qnA.getCreatedAt())
                .profileUrl(qnA.getUser().getProfileUrl())
                .nickname(qnA.getUser().getNickname())
                .isMineQnA(isMine)
                .build();
    }

    public List<QnAAnswerResponse> qnaAnswerList(User user, String qnaUuid) {
        QnA qnA = qnARepository.findById(qnaUuid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected qna"));
        List<Answer> answers = answerRepository.findByQna(qnA);

        return answers.stream()
                .map(result -> new QnAAnswerResponse(
                        result.getQnaAnsUuid(),
                        result.getAnsContent(),
                        result.getUser().getProfileUrl(),
                        result.getUser().getNickname(),
                        result.getCreatedAt(),
                        result.getUser().getUserUuid().equals(user.getUserUuid()) ? 1 : 0
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteQnA(User user, String qnaUuid) {
        List<QnAAnswerResponse> qnaList =  qnaAnswerList(user, qnaUuid);
        for (QnAAnswerResponse qnaAnswerResponse : qnaList) {
            deleteAnswer(qnaAnswerResponse.qnaAnsUuid());
        }

        int deletedCount = qnARepository.deleteByQnaUuidAndUser(qnaUuid, user);
        if (deletedCount == 0) {
            throw new SecurityException("삭제 권한이 없거나 QnA가 없습니다.");
        }
    }

    @Transactional
    public String answerSave(User user, String qnaUuid, AnswerRequest answerRequest) {
        QnA qnA = qnARepository.findById(qnaUuid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected qna"));

        Answer answer = Answer.builder()
                .ansContent(answerRequest.ansContent())
                .user(user)
                .qna(qnA)
                .build();

        return answerRepository.save(answer).getQnaAnsUuid();
    }

    @Transactional
    public void deleteAnswer(String qnaAnsUuid) {
        int deletedCount = answerRepository.deleteByQnaAnsUuid(qnaAnsUuid);
        if (deletedCount == 0) {
            throw new SecurityException("삭제 권한이 없거나 Answer가 없습니다.");
        }
    }

    public List<QnAListResponse> getUserQnAList(User user) {
        List<QnA> results = qnARepository.findByUser(user);

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
