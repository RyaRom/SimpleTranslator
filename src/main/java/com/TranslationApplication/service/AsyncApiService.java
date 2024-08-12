package com.TranslationApplication.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AsyncApiService {
    private final RestTemplate restTemplate;

    @Value("${translation.api}")
    private String url;

    public AsyncApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async("threadPoolForWords")
    public CompletableFuture<String> translate(String text, String source, String target) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> request = new HashMap<>();
        request.put("q", text);
        request.put("source", source);
        request.put("target", target);

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(request, httpHeaders);
        Map<String, String> response = restTemplate.postForObject(url, httpEntity, Map.class);
        String translated = response.get("translatedText");

        if (response.containsKey("error")) log.error("Error in external API {}", response.get("error"));

        log.info("The word {} is translated in thread {} into result {}; lang: {} -> {}", text, Thread.currentThread(), translated, source, target);

        return CompletableFuture.completedFuture(translated);
    }
}
