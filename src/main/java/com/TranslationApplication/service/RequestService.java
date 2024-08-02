package com.TranslationApplication.service;

import com.TranslationApplication.model.RequestLogDTO;
import com.TranslationApplication.repository.RequestRepo;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class RequestService {
    private final RequestRepo requestRepo;

    public RequestService(RequestRepo requestRepo) {
        this.requestRepo = requestRepo;
    }
    public void saveLog(String ip, String input, String output){
        RequestLogDTO requestLogDTO = new RequestLogDTO();
        requestLogDTO.setIp(ip);
        requestLogDTO.setInput(input);
        requestLogDTO.setOutput(output);
        requestLogDTO.setDate(currentDateTime());
        requestRepo.save(requestLogDTO);
    }
    public List<RequestLogDTO> getLogs(){
        return requestRepo.findAll();
    }
    private Timestamp currentDateTime(){
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        return Timestamp.from(zonedDateTime.toInstant());
    }
}
