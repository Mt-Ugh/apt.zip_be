package com.aptzip.news.service;

import com.aptzip.news.dto.request.NewsRequest;
import com.aptzip.news.dto.response.NewsResponse;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NewsService {

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    private final ThumbnailService thumbnailService;

    public List<NewsResponse> searchNews(NewsRequest newsRequest) {
        String url = UriComponentsBuilder
                .fromHttpUrl("https://openapi.naver.com/v1/search/news.json")
                .queryParam("query", newsRequest.query())
                .queryParam("display", newsRequest.display())
                .queryParam("start", newsRequest.start())
                .queryParam("sort", "sim")
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        List<Map<String, String>> items = (List<Map<String, String>>) response.getBody().get("items");

        List<NewsResponse> result = new ArrayList<>();
        for (Map<String, String> item : items) {
            String title = Jsoup.parse(item.get("title")).text();
            String link = item.get("link");
            String description = Jsoup.parse(item.get("description")).text();
            String pubDate = item.get("pubDate");
            String thumbnail = thumbnailService.extractOgImage(link);

            result.add(new NewsResponse(title, link, description, pubDate, thumbnail));
        }

        return result;
    }
}