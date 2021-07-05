package com.restful.app.api.services.extension.feign;

import com.restful.app.extension_entity.Post;

import java.util.List;

public interface FeignPostService {

    List<Post> getPosts();

    Post getPostById(Long id);
}
