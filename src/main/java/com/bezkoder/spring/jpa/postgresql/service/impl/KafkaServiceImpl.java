package com.bezkoder.spring.jpa.postgresql.service.impl;

import com.bezkoder.spring.jpa.postgresql.entity.ApiS;
import com.bezkoder.spring.jpa.postgresql.repository.ApisRepository;
import com.bezkoder.spring.jpa.postgresql.service.ApiService;
import com.bezkoder.spring.jpa.postgresql.service.KafkaService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Service
public class KafkaServiceImpl implements KafkaService {
    @Value("${config.server}")
    private String configServer;
    @Value("${key.serializer}")
    private String keySerializer;
    @Value("${value.serializer}")
    private String valueSerializer;
    @Value("${key.deserializer}")
    private String keyDeserializer;
    @Value("${group.id}")
    private String groupId;
    @Value("${client.id}")
    private String clientId;

    @Autowired
    private ApiService apiService;

    private String topicName = "apiLogs";
    private List<String> logList;
    private String recordList;
    private ApiS apiS;

    @Override
    public void sendKafkaMessageProducer(String lastLine) {
        Properties configPro = new Properties();
        configPro.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,configServer);
        configPro.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,keySerializer);
        configPro.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,valueSerializer);

        Producer producer = new KafkaProducer<String,String>(configPro);
        ProducerRecord<String,String> rec = new ProducerRecord<String,String>(topicName,lastLine);
        producer.send(rec);
        producer.close();
        listenMessageConsumer();
    }

    @Override
    public void listenMessageConsumer(){
        Properties configPro = new Properties();
        configPro.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,configServer);
        configPro.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,keyDeserializer);
        configPro.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,keyDeserializer);
        configPro.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        configPro.put(ConsumerConfig.CLIENT_ID_CONFIG,clientId);

        KafkaConsumer<String,String> kafkaConsumer;
        kafkaConsumer = new KafkaConsumer<String, String>(configPro);

        kafkaConsumer.subscribe(Arrays.asList(topicName));
        try {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    recordList = record.value();
                    logList = Arrays.asList(recordList.split(","));
                    apiService.saveApiS(logList.get(0),logList.get(1),logList.get(2));
                }
        }
        catch (Exception e){
            System.out.println(e);
        }
        finally {
            kafkaConsumer.close();
        }

    }
}
