package com.springboot.blog.blogrestapi.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
public class PostDTO {
    private long id;

//    cannot be null or empty, and at least 2 chars
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

//    cannot be null or empty, and at least 5 chars
    @NotEmpty
    @Size(min = 5, message = "Post description should have at least 5 characters")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDTO> comments;

}
