package com.TranslationApplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.time.Duration;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class Config {
    @Value("${translation.threadPoolSize}")
    private Integer maxThreadPoolSize;
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(600))
                .setReadTimeout(Duration.ofSeconds(600))
                .build();
    }
    @Bean(name = "threadPoolForWords")
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(maxThreadPoolSize);
        taskExecutor.setMaxPoolSize(maxThreadPoolSize);
        taskExecutor.setQueueCapacity(500);
        taskExecutor.setThreadNamePrefix("Thread_for_word_");
        taskExecutor.initialize();
        return taskExecutor;
    }
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/request_logs");
        dataSource.setUsername("postgres");
        dataSource.setPassword("password");
        return dataSource;
    }
}
