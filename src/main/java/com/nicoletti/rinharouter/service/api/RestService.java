package com.nicoletti.rinharouter.service.api;

public interface RestService {

    public <RESPONSE, REQUEST> RESPONSE post(String url, REQUEST request, Class<RESPONSE> responseType);

}
