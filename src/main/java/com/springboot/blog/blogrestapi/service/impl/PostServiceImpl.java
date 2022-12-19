package com.springboot.blog.blogrestapi.service.impl;

import com.springboot.blog.blogrestapi.DTO.PostDTO;
import com.springboot.blog.blogrestapi.DTO.PostResponse;
import com.springboot.blog.blogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.blogrestapi.model.Post;
import com.springboot.blog.blogrestapi.repository.PostRepository;
import com.springboot.blog.blogrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    PostRepository postRepository;
    ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    private Post DtoToPost(PostDTO postDTO) {
        Post post = mapper.map(postDTO, Post.class);
//        Post post = new Post();
//        post.setContent(postDTO.getContent());
//        post.setTitle(postDTO.getTitle());
//        post.setDescription(postDTO.getDescription());
        return post;
    }

    private PostDTO PostToDto(Post savedPost) {
        PostDTO responseDTO = mapper.map(savedPost, PostDTO.class);
//        PostDTO responseDTO = new PostDTO();
//        responseDTO.setId(savedPost.getId());
//        responseDTO.setContent(savedPost.getContent());
//        responseDTO.setDescription(savedPost.getDescription());
//        responseDTO.setTitle(savedPost.getTitle());
        return responseDTO;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
//        convert postDTO to Post object
        Post post = DtoToPost(postDTO);

//        save Post object to repo
        Post savedPost = postRepository.save(post);

//        convert Post object to postDTO for response
        PostDTO responseDTO = PostToDto(savedPost);

        return responseDTO;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, Boolean asc) {

        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

//         get content for page object
        List<Post> listOfPosts = posts.getContent();
        List<PostDTO> content = listOfPosts.stream().map(post -> PostToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return PostToDto(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setDescription(postDTO.getDescription());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());

        postRepository.save(post);
        return PostToDto(post);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
}
