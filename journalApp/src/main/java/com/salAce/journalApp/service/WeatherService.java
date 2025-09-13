package com.salAce.journalApp.service;

import com.salAce.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate ;
    // it is a class in spring boot which process the request and provide us the response

    private static final String  apiKey = "ef2f7500dfeb1ba3b07890f4b005bff8";
    private static final String  api = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    public WeatherResponse getWeather(String  city ) {

        final String Api =  api.replace("CITY" , city).replace("API_KEY" , apiKey) ;
        ResponseEntity<WeatherResponse> weatherResponse =   restTemplate.exchange(Api , HttpMethod.GET,null, WeatherResponse.class) ;
//        here null is for body, that means we are passing nothing. after that is response (json to pojo)

       WeatherResponse body = weatherResponse.getBody();
       return body;



    }
}
