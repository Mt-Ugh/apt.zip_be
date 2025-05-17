package com.aptzip.qna.repository;

import com.aptzip.qna.entity.QnA;
import com.aptzip.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnARepository extends JpaRepository<QnA, String> {

    int deleteByQnaUuidAndUser(String qnaUuid, User user);

    List<QnA> findByUser(User user);
}
