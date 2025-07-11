package com.devSage.blog.blog_app_apis.Payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Integer commentId;
    @NotBlank(message = "Comment content must not be empty")
    private String content;
}
