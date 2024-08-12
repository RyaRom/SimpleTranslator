package com.TranslationApplication.service;

import com.TranslationApplication.model.TranslationRequestDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TranslationService {
    private final AsyncApiService asyncApiService;
    @Value("${translation.chunk.size}")
    private Integer chunkSize;

    public TranslationService(AsyncApiService asyncApiService) {
        this.asyncApiService = asyncApiService;
    }

    public String translateByChunks(String text, String source, String target) {
        List<String> chunks = Arrays.stream(text.split("\\s+")).toList();
        List<List<String>> partitions = new LinkedList<>();
        for (int i = 0; i < chunks.size(); i += chunkSize) {
            partitions.add(chunks.subList(i, Math.min(i + chunkSize, chunks.size())));
        }
        List<CompletableFuture<String>> futures = partitions.stream()
                .map(word -> asyncApiService.translate(String.join(" ", word), source, target)).toList();
        return getAsyncResults(futures);
    }

    public String translateByWords(String text, String source, String target) {
        List<CompletableFuture<String>> futures = Arrays.stream(text.split("\\s+"))
                .map(word -> asyncApiService.translate(word, source, target)).toList();
        return getAsyncResults(futures);
    }

    private String getAsyncResults(List<CompletableFuture<String>> futures) {
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        return futures.stream().map(f -> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("Error in async operations  ", e);
                return "Error occurred while translating this chunk";
            }
        }).collect(Collectors.joining(" "));
    }

    @SneakyThrows
    public String translateRequest(TranslationRequestDTO translationRequest, boolean correctTranslation) {
        String text = translationRequest.getText();
        String source = translationRequest.getSource();
        String target = translationRequest.getTarget();
        log.info("translation attempt; Text={}; {} -> {}; Is correct={}", text, source, target, correctTranslation);
        if (correctTranslation) return translateByChunks(text, source, target);
        else return translateByWords(text, source, target);
    }
}
