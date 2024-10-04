package com.TranslationApplication.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RequestLogDTO {
    private String ip;

    private String input;

    private String output;

    private Timestamp date;
}
