package com.springboot.blog.blogrestapi.controller;

import com.springboot.blog.blogrestapi.DTO.PostDTO;
import com.springboot.blog.blogrestapi.DTO.PostResponse;
import com.springboot.blog.blogrestapi.service.PostService;
import com.springboot.blog.blogrestapi.utils.UrlConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = UrlConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = UrlConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = UrlConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "asc", defaultValue = UrlConstants.DEFAULT_ASC, required = false) Boolean asc
    ) {

        return postService.getAllPosts(pageNo, pageSize, sortBy, asc);
    }

    @GetMapping("/{id}")
    public PostDTO getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePostById(@Valid @PathVariable Long id, @RequestBody PostDTO postDTO) {
        PostDTO postResponse = postService.updatePost(postDTO, id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        postService.deletePostById(id);
        ResponseEntity<String> response = new ResponseEntity<>(String.format("%s successfully deleted", id),
                HttpStatus.OK);
        return response;
    }
}
