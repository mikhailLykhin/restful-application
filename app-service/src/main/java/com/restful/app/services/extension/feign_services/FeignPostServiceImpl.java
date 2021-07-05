package com.restful.app.services.extension.feign_services;

import com.restful.app.api.services.extension.feign.FeignPostService;
import com.restful.app.extension_entity.Post;
import com.restful.app.services.extension.feign_client.FeignPostClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeignPostServiceImpl implements FeignPostService {


    private final FeignPostClient feignPostClient;

    public FeignPostServiceImpl(FeignPostClient feignPostClient) {
        this.feignPostClient = feignPostClient;
    }

    @Override
    public List<Post> getPosts() {
       return this.feignPostClient.getPosts();
    }

    @Override
    public Post getPostById(Long postId) {
      return this.feignPostClient.getPostById(postId);
    }
}
