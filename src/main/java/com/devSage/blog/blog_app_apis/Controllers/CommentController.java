package com.devSage.blog.blog_app_apis.Controllers;

import com.devSage.blog.blog_app_apis.Exceptions.ApiResponse;
import com.devSage.blog.blog_app_apis.Payloads.CommentDto;
import com.devSage.blog.blog_app_apis.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId)
    {
        CommentDto createdComment=commentService.creatComment(commentDto,postId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment Deleted Successfully",true),HttpStatus.OK);
    }
}
