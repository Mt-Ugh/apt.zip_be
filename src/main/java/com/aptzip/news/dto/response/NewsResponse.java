package com.aptzip.news.dto.response;

public record NewsResponse(
        String title,
        String link,
        String description,
        String pubDate,
        String thumbnail) {
}
