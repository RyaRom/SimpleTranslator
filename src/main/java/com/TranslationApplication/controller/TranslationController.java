package com.TranslationApplication.controller;

import com.TranslationApplication.model.TranslationRequestDTO;
import com.TranslationApplication.service.RequestService;
import com.TranslationApplication.service.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class TranslationController {
    private final TranslationService translationService;
    private final RequestService requestService;

    public TranslationController(TranslationService translationService, RequestService requestService) {
        this.translationService = translationService;
        this.requestService = requestService;
    }

    @PostMapping("/translate")
    public ResponseEntity<?> translate(HttpServletRequest request, @NonNull @RequestBody TranslationRequestDTO translationRequest){
        String text = translationRequest.getText();
        String source = translationRequest.getSource();
        String target = translationRequest.getTarget();
        String translated;

        try {
            translated = translationService.translate(text, source, target);
        } catch (Exception e) {
            translated = "Failed to translate";
            requestService.saveLog(request.getRemoteAddr(), text, translated);
            return ResponseEntity.badRequest().build();
        }
        requestService.saveLog(request.getRemoteAddr(), text, translated);
        return ResponseEntity.ok(translated);
    }
    @GetMapping("/logs")
    public ResponseEntity<?> getLogs(){
        return ResponseEntity.ok(requestService.getLogs());
    }
}

