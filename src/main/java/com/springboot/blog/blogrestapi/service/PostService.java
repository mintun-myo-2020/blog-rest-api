package com.springboot.blog.blogrestapi.service;

import com.springboot.blog.blogrestapi.DTO.PostDTO;
import com.springboot.blog.blogrestapi.DTO.PostResponse;
import com.springboot.blog.blogrestapi.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, Boolean asc);

    PostDTO getPostById(Long id);

    PostDTO updatePost(PostDTO postDTO, Long id);

    void deletePostById(Long id);
}
