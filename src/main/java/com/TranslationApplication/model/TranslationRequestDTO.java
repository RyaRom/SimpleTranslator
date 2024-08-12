package com.TranslationApplication.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "Translation request", description = "Translation request")
public class TranslationRequestDTO {
    @Schema(description = "text to translate", example = "hello world")
    private String text;
    @Schema(description = "source language", example = "en")
    private String source;
    @Schema(description = "target language", example = "ru")
    private String target;
}
