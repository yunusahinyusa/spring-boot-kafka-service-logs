package com.bezkoder.spring.jpa.postgresql.service.impl;

import com.bezkoder.spring.jpa.postgresql.entity.ApiS;
import com.bezkoder.spring.jpa.postgresql.repository.ApisRepository;
import com.bezkoder.spring.jpa.postgresql.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiServiceImpl implements ApiService {
    @Autowired
    private ApisRepository apiRepository;

    public ApiServiceImpl(ApisRepository apisRepository) {
        this.apiRepository = apisRepository;
    }

    @Override
    public void saveApiS(String methodType,String responseTime, String timeStamp) {

        ApiS apiS = new ApiS();
        apiS.setApiMethod(methodType);
        apiS.setResponseTime(responseTime);
        apiS.setTimestamp(timeStamp);
        apiRepository.save(apiS);

    }
}
