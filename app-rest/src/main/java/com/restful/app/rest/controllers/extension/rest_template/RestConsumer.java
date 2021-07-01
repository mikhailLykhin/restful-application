package com.restful.app.rest.controllers.extension.rest_template;

import com.restful.app.extension_entity.Post;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
@RequestMapping("/rest")
public class RestConsumer {

    RestTemplate restTemplate;

    private final String POST_API = "https://jsonplaceholder.typicode.com/posts";

    public RestConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/posts")
    public Post[] getProductList() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(POST_API, HttpMethod.GET, entity, Post[].class).getBody();
    }

    @PostMapping( "/posts")
    public String createPost(@RequestBody Post post) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Post> entity = new HttpEntity<Post>(post, httpHeaders);
        return restTemplate.exchange(POST_API, HttpMethod.POST, entity, String.class).getBody();

    }

    @GetMapping("/posts/{id}")
    public Post getProduct(@PathVariable("id") int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/posts/" + id, HttpMethod.GET, entity, Post.class).getBody();
    }

    @PutMapping( "/posts/{id}")
    public String updatePost(@PathVariable("id") int id, @RequestBody Post post) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Post> entity = new HttpEntity<>(post, httpHeaders);
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/posts/" + id, HttpMethod.PUT, entity, String.class).getBody();
    }

    @DeleteMapping( "/posts/{id}")
    public String deletePost(@PathVariable("id") int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/posts/" + id, HttpMethod.DELETE, entity, String.class).getBody();
    }

}