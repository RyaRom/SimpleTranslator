package com.TranslationApplication.service;

import com.TranslationApplication.model.TranslationRequestDTO;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class TranslationService {
    private final RestTemplate restTemplate;
    private final String url = "https://translate.fedilab.app/translate";

    public TranslationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String translateAsync(String text, String source, String target){
        List<CompletableFuture<String>> futures = Arrays.stream(text.split("\\s+"))
                .map(word->translate(word, source, target)).toList();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        return futures.stream().map(f-> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Async translation error   " +e);
            }
        }).collect(Collectors.joining(" "));
    }

    @SneakyThrows
    public String translateRequest(TranslationRequestDTO translationRequest, boolean async){
        String text = translationRequest.getText();
        String source = translationRequest.getSource();
        String target = translationRequest.getTarget();
        if (async) return translateAsync(text,source,target);
        else return translate(text, source, target).get();
    }

    @Async("taskExecutor")
    public CompletableFuture<String> translate(String text, String source, String target){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> request= new HashMap<>();
        request.put("q", text);
        request.put("source", source);
        request.put("target", target);

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(request, httpHeaders);
        Map<String, String> response = restTemplate.postForObject(url, httpEntity,Map.class);
        return CompletableFuture.completedFuture(response.get("translatedText"));
    }
}
