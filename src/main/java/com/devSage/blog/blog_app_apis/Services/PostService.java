package com.devSage.blog.blog_app_apis.Services;

import com.devSage.blog.blog_app_apis.Entity.Post;
import com.devSage.blog.blog_app_apis.Payloads.PostDto;
import com.devSage.blog.blog_app_apis.Payloads.PostResponse;

import java.util.List;

public interface PostService {
    // create a post
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    // update a post
    PostDto updatePost(PostDto postDto,Integer postId);
    // delete a Post
    void deletePost(Integer postId);
    // get All post
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
    // get post by Id
    PostDto getPostById(Integer postId);
    // get post by category
    List<PostDto> getPostsByCategory(Integer categoryId);
    // get post by user
    List<PostDto> getPostsByUser(Integer userId);
    // search post by keyword
    List<PostDto> SearchPosts(String keyword);
}
