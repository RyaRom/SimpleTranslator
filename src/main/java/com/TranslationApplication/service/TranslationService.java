package com.TranslationApplication.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TranslationService {
    private final RestTemplate restTemplate;
    private final String url = "https://translate.fedilab.app/translate";

    public TranslationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String translate(String text, String source, String target){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> request= new HashMap<>();
        request.put("q", text);
        request.put("source", source);
        request.put("target", target);

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(request, httpHeaders);
        Map<String, String> response = restTemplate.postForObject(url, httpEntity,Map.class);
        return response.get("translatedText");
    }
}
