package com.nicoletti.rinharouter.service.impl;

import com.nicoletti.rinharouter.service.api.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestServiceImpl implements RestService {

    private final static Logger logger = LoggerFactory.getLogger(RestServiceImpl.class);
    private final RestTemplate restTemplate;

    public RestServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <RESPONSE, REQUEST> boolean post(String url, REQUEST request, Class<RESPONSE> responseType) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<REQUEST> requestHttpEntity = new HttpEntity<>(request, headers);

            ResponseEntity<RESPONSE> response = restTemplate.postForEntity(
                    url,
                    requestHttpEntity,
                    responseType
            );

            logger.debug("Status code: {}", response.getStatusCodeValue());
            logger.debug("Resposta: {}", response.getBody());
            logger.debug("Headers: {}", response.getHeaders());

        } catch (Exception e) {
            logger.error("Error while making POST request to {}: {}", url, e.getMessage());
            return false;
        }

        return true;
    }

}
