package com.devSage.blog.blog_app_apis.Payloads;

import com.devSage.blog.blog_app_apis.Entity.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private Integer postId;

    @NotBlank(message = "Post title must not be blank")
    @Size(min = 4, max = 100, message = "Title must be between 4 and 100 characters")
    private String title;

    @NotBlank(message = "Content must not be blank")
    @Size(min = 10, message = "Content must be at least 10 characters long")
    private String content;

    private String imageName;

    private Date addedDate;

    private UserDto user; // rec se bachne ke liye dto use hua h

    private CategoryDto category;

    private Set<CommentDto> comments=new HashSet<>();

}
