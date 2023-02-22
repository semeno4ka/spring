package com.cydeo.controller;

import com.cydeo.dto.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@RequestMapping("/cydeo")
public class Consume_RestTemplate {

    private final RestTemplate restTemplate;
    private final String URI = "https://jsonplaceholder.typicode.com/users";// The uri we will consume

    public Consume_RestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<User[]> readAllUsers() {
        return restTemplate.getForEntity(URI, User[].class);//accepts array, that's how template works
        // it is already returns response entity, that is why no need to write ResponseEntity.
    }

    @GetMapping("{id}")
    public Object readUSer(@PathVariable("id") Integer id) {
        // No need for DTO in getForObject, take whatever is there already
        String URL = URI + "/{id}";//need to show certain user
        return restTemplate.getForObject(URL, Object.class, id);
    }

    @GetMapping("/test")
    public ResponseEntity<Object> consumePostFromDummyApi() {
        HttpHeaders headers = new HttpHeaders();// in order to pass api-id to header we set the HEADER
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("app-id", "6298ebfecd0551211fce37a6");
        HttpEntity<String> entity = new HttpEntity<>(headers);// Exchange the header below
        return restTemplate.exchange("https://dummyapi.io/data/v1/user?limit=10", HttpMethod.GET, entity, Object.class);
    }


}
