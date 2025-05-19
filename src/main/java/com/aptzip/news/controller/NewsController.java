package com.aptzip.news.controller;

import com.aptzip.news.dto.request.NewsRequest;
import com.aptzip.news.dto.response.NewsResponse;
import com.aptzip.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/list")
    public ResponseEntity<List<NewsResponse>> getNewsList(@ModelAttribute NewsRequest newsRequest) {
        List<NewsResponse> newsList = newsService.searchNews(newsRequest);
        return ResponseEntity.ok(newsList);
    }
}
