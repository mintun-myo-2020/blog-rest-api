package com.springboot.blog.blogrestapi.controller;

import com.springboot.blog.blogrestapi.DTO.CommentDTO;
import com.springboot.blog.blogrestapi.model.Comment;
import com.springboot.blog.blogrestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(
            @PathVariable(value = "postId") Long postId,
            @Valid @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable(value = "postId") long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public CommentDTO getCommentById(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "id") long id
    ) {
        return commentService.getCommentById(postId, id);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateCommentById(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "id") long id,
            @Valid @RequestBody CommentDTO updatedComment
    ) {
        return new ResponseEntity<CommentDTO>(commentService.updateComment(postId, id, updatedComment), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteCommentById(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "id") long id
    ) {
        return new ResponseEntity<String>("Comment successfully deleted", HttpStatus.OK);
    }

}
