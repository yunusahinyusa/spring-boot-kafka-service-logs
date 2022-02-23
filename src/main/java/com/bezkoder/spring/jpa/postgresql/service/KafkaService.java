package com.bezkoder.spring.jpa.postgresql.service;

import org.springframework.stereotype.Service;

@Service
public interface KafkaService {
    public void sendKafkaMessageProducer(String lastLine);
    public void listenMessageConsumer();
}
