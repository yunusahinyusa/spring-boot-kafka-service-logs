package com.bezkoder.spring.jpa.postgresql.service;

import org.springframework.stereotype.Service;

@Service
public interface ApiService {
    public void saveApiS(String methodType,String responseTime,String timeStamp);
}
