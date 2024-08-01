package com.TranslationApplication.controller;

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

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping("/translate")
    public ResponseEntity<?> translate(HttpServletRequest request, @NonNull @RequestBody String text, @NonNull @RequestBody String source, @NonNull @RequestBody String target){
        String translated = translationService.translate(text, source, target);
        return ResponseEntity.ok(request.getRemoteAddr());
    }
    @GetMapping("/myip")
    public ResponseEntity<?> getIp(HttpServletRequest request){
        return ResponseEntity.ok(request.getRemoteAddr() + " - IP    " + request.getRemoteUser()+ "  - User");
    }
}

