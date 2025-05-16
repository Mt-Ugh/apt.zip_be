package com.aptzip.qna.dto.request;

import com.aptzip.qna.constant.Category;
import com.aptzip.qna.entity.QnA;
import com.aptzip.user.entity.User;

public record QnARegistRequest(
        Category category,
        String title,
        String content) {


    public QnA toEntity(User user) {
        return QnA.builder()
                .user(user)
                .title(title)
                .content(content)
                .category(category)
                .build();
    }
}
