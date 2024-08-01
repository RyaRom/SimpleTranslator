package com.TranslationApplication.service;

import com.TranslationApplication.model.RequestDTO;
import com.TranslationApplication.repository.RequestRepo;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class RequestService {
    private final RequestRepo requestRepo;

    public RequestService(RequestRepo requestRepo) {
        this.requestRepo = requestRepo;
    }
    public void saveLog(String ip, String input, String output){
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setIp(ip);
        requestDTO.setInput(input);
        requestDTO.setOutput(output);
        requestDTO.setDate(currentDateTime());
        requestRepo.save(requestDTO);
    }
    public List<RequestDTO> getLogs(){
        return requestRepo.findAll();
    }
    private Timestamp currentDateTime(){
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        return Timestamp.from(zonedDateTime.toInstant());
    }
}
