package com.springboot.blog.blogrestapi.DTO;

import com.springboot.blog.blogrestapi.model.Post;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Data
public class CommentDTO {

    private long id;

    @NotEmpty
    @Min(value = 2, message = "Name requires at least 2 characters")
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Min(value = 5, message = "Comment requirest 5 characters")
    private String body;
}
