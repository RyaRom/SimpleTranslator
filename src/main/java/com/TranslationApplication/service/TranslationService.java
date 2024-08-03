package com.TranslationApplication.service;

import com.TranslationApplication.model.TranslationRequestDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TranslationService {
    private final AsyncApiService asyncApiService;

    public TranslationService(AsyncApiService asyncApiService) {
        this.asyncApiService = asyncApiService;
    }

    public String translateAsync(String text, String source, String target){
        List<CompletableFuture<String>> futures = Arrays.stream(text.split("\\s+"))
                .map(word->asyncApiService.translate(word, source, target)).toList();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        return futures.stream().map(f-> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("Error in async operations  ", e);
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining(" "));
    }

    @SneakyThrows
    public String translateRequest(TranslationRequestDTO translationRequest, boolean async){
        String text = translationRequest.getText();
        String source = translationRequest.getSource();
        String target = translationRequest.getTarget();
        if (async) return translateAsync(text,source,target);
        else return asyncApiService.translate(text, source, target).get();
    }
}
