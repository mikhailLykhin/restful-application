package com.restful.app.rest.controllers.extension.feign_controller;

import com.restful.app.api.services.extension.feign.FeignPostService;
import com.restful.app.extension_entity.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class PostController {

    private final FeignPostService feignPostService;

    public PostController(FeignPostService feignPostService) {
        this.feignPostService = feignPostService;
    }


    @GetMapping("/posts")
    public List<Post> getPosts() {
        return this.feignPostService.getPosts();
    }

    @GetMapping("/posts/{id}")
    public Post getPost(@PathVariable("id") long id) {
        return this.feignPostService.getPostById(id);
    }
}
