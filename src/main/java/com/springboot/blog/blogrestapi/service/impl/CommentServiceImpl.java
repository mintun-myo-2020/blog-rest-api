package com.springboot.blog.blogrestapi.service.impl;

import com.springboot.blog.blogrestapi.DTO.CommentDTO;
import com.springboot.blog.blogrestapi.exception.BlogAPIException;
import com.springboot.blog.blogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.blogrestapi.model.Comment;
import com.springboot.blog.blogrestapi.model.Post;
import com.springboot.blog.blogrestapi.repository.CommentRepository;
import com.springboot.blog.blogrestapi.repository.PostRepository;
import com.springboot.blog.blogrestapi.service.CommentService;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    //    DTO to comment
    public Comment DtoToComment(CommentDTO commentDTO) {
        Comment comment = mapper.map(commentDTO, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setBody(commentDTO.getBody());
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());

        return comment;
    }

    //    comment to DTO
    public CommentDTO commentToDto(Comment comment) {

        CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setEmail(comment.getEmail());
//        commentDTO.setName(comment.getName());
//        commentDTO.setBody(comment.getBody());
//        commentDTO.setId(comment.getId());

        return commentDTO;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = DtoToComment(commentDTO);
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);

        return commentToDto(savedComment);

    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        return commentRepository.findByPostId(postId).stream().map(comment -> commentToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(long postId, long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );


        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", id)
        );

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return commentToDto(comment);
    }

    @Override
    public CommentDTO updateComment(long postId, long id, CommentDTO updatedComment) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", id)
        );

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setBody(updatedComment.getBody());
        comment.setName(updatedComment.getName());
        comment.setEmail(updatedComment.getEmail());

        Comment savedUpdatedComment = commentRepository.save(comment);

        return commentToDto(comment);

    }

    @Override
    public void deleteComment(long postId, long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", id)
        );

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentRepository.deleteById(id);

    }


}
