package com.nicoletti.rinharouter.service.api;

public interface RestService {

    public <RESPONSE, REQUEST> boolean post(String url, REQUEST request, Class<RESPONSE> responseType);

}
