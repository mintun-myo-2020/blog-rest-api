package com.springboot.blog.blogrestapi.service;

import com.springboot.blog.blogrestapi.DTO.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(long postId, CommentDTO commentDTO);

    List<CommentDTO> getCommentsByPostId(long postId);

    CommentDTO getCommentById(long postId, long id);

    CommentDTO updateComment(long postId, long id, CommentDTO updatedComment);

    void deleteComment(long postId, long id);
}
