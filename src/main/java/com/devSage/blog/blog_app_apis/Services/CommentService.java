package com.devSage.blog.blog_app_apis.Services;

import com.devSage.blog.blog_app_apis.Payloads.CommentDto;

public interface CommentService {
    CommentDto creatComment(CommentDto commentDto,Integer postId);
    void deleteComment(Integer commentId);
}
