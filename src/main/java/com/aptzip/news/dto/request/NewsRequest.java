package com.aptzip.news.dto.request;

public record NewsRequest(
        String query,
        String display,
        String start) {
}
