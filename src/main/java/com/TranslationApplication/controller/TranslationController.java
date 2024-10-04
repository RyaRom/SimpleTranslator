package com.TranslationApplication.controller;

import com.TranslationApplication.model.TranslationRequestDTO;
import com.TranslationApplication.service.RequestService;
import com.TranslationApplication.service.TranslationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Tag(name = "Translator")
public class TranslationController {
    private final TranslationService translationService;

    private final RequestService requestService;

    @Operation(summary = "translate", description = "Translate text", parameters = {
            @Parameter(name = "correctTranslation", description = "Determine the translation method: `false` for translating by words, `true` for translating by chunks.")
    })
    @PostMapping("/translate")
    public ResponseEntity<?> translate(HttpServletRequest request, @NonNull @RequestBody TranslationRequestDTO translationRequest, @RequestParam(defaultValue = "true") Boolean correctTranslation) {
        String translated = translationService.translateRequest(translationRequest, correctTranslation);
        requestService.saveLog(request.getRemoteAddr(), translationRequest.getText(), translated);
        return ResponseEntity.ok(translated);
    }

    @Operation(summary = "logs", description = "Get translation logs")
    @GetMapping("/logs")
    public ResponseEntity<?> getLogs() {
        return ResponseEntity.ok(requestService.getLogs());
    }
}

