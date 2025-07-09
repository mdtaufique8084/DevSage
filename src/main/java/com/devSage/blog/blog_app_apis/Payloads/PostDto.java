package com.devSage.blog.blog_app_apis.Payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private UserDto user; // to avoid recursion we not used User but used UserDto
    private CategoryDto category;
}
