package com.springboot.blog.blogrestapi.repository;

import com.springboot.blog.blogrestapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
