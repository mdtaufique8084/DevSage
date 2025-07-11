package com.devSage.blog.blog_app_apis.Services;

import com.devSage.blog.blog_app_apis.Entity.Comment;
import com.devSage.blog.blog_app_apis.Entity.Post;
import com.devSage.blog.blog_app_apis.Exceptions.ResourceNotFoundException;
import com.devSage.blog.blog_app_apis.Payloads.CommentDto;
import com.devSage.blog.blog_app_apis.Repositories.CommentRepository;
import com.devSage.blog.blog_app_apis.Repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentImplementation implements CommentService{

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto creatComment(CommentDto commentDto, Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post"," post id ",postId));
        Comment comment=modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        Comment savedComment=commentRepository.save(comment);
        return modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Post"," post id ",commentId));
        commentRepository.delete(comment);
    }
}
