package com.TranslationApplication.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class RequestDTO {
    private String ip;
    private String input;
    private String output;
    private Timestamp date;
}
