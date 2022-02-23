package com.bezkoder.spring.jpa.postgresql.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ApiDto {
    private Long id;
    private String apiMethod;
    private String responseTime;
    private String timestamp;
}
