package com.TranslationApplication.model;

import lombok.Data;

@Data
public class TranslationRequestDTO {
    private String text;
    private String source;
    private String target;
}
