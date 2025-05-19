package com.aptzip.news.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ThumbnailService {

    @Cacheable(value = "newsThumbnail", key = "#url")
    public String extractOgImage(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Element meta = doc.selectFirst("meta[property=og:image]");
            return meta != null ? meta.attr("content") : null;
        } catch (Exception e) {
            return null;
        }
    }
}
