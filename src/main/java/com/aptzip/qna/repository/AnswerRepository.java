package com.aptzip.qna.repository;

import com.aptzip.qna.entity.Answer;
import com.aptzip.qna.entity.QnA;
import com.aptzip.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, String> {
    List<Answer> findByQna(QnA qna);

    List<Answer> findByUser(User user);

    int deleteByQnaAnsUuid(String qnaAnsUuid);
}
