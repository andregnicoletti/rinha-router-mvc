package com.nicoletti.rinharouter.service.impl;

import com.nicoletti.rinharouter.service.api.RestService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestServiceImpl implements RestService {

    private final RestTemplate restTemplate;

    public RestServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <RESPONSE, REQUEST> RESPONSE post(String url, REQUEST request, Class<RESPONSE> responseType) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<REQUEST> requestHttpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<RESPONSE> response = restTemplate.postForEntity(
                url,
                requestHttpEntity,
                responseType
        );

        // Log (só como exemplo; use logger em prod)
        System.out.println("Status code: " + response.getStatusCodeValue());
        System.out.println("Resposta: " + response.getBody());
        System.out.println("Headers: " + response.getHeaders());

        return response.getBody();

    }

}
