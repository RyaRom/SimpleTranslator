package com.TranslationApplication.repository;

import com.TranslationApplication.model.RequestLogDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RequestRepo {
    private final JdbcTemplate jdbcTemplate;

    public RequestRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RequestLogDTO> findAll() {
        return jdbcTemplate.query("SELECT * FROM USER_LOG", new Mapper());
    }

    public void save(RequestLogDTO requestLogDTO) {
        jdbcTemplate.update("INSERT INTO USER_LOG (ip, input, output, date) VALUES ( ?,?,?,? )", requestLogDTO.getIp(), requestLogDTO.getInput(), requestLogDTO.getOutput(), requestLogDTO.getDate());
    }

    private static class Mapper implements RowMapper<RequestLogDTO> {

        @Override
        public RequestLogDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            RequestLogDTO request = new RequestLogDTO();
            request.setIp(rs.getString("ip"));
            request.setInput(rs.getString("input"));
            request.setOutput(rs.getString("output"));
            request.setDate(rs.getTimestamp("date"));
            return request;
        }
    }
}
