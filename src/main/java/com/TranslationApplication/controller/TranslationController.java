package com.TranslationApplication.controller;

import com.TranslationApplication.model.TranslationRequestDTO;
import com.TranslationApplication.service.RequestService;
import com.TranslationApplication.service.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;


@RestController

public class TranslationController {
    private final TranslationService translationService;
    private final RequestService requestService;

    public TranslationController(TranslationService translationService, RequestService requestService) {
        this.translationService = translationService;
        this.requestService = requestService;
    }

    @PostMapping("/translate")
    public ResponseEntity<?> translate(HttpServletRequest request, @NonNull @RequestBody TranslationRequestDTO translationRequest, @RequestParam(defaultValue = "false") Boolean correctTranslation){
        String translated= translationService.translateRequest(translationRequest, correctTranslation);
        requestService.saveLog(request.getRemoteAddr(), translationRequest.getText(), translated);
        return ResponseEntity.ok(translated);
    }
    @GetMapping("/logs")
    public ResponseEntity<?> getLogs(){
        return ResponseEntity.ok(requestService.getLogs());
    }
}

