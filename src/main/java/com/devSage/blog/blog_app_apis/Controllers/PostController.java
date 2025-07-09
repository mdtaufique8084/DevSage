package com.devSage.blog.blog_app_apis.Controllers;

import com.devSage.blog.blog_app_apis.Exceptions.ApiResponse;
import com.devSage.blog.blog_app_apis.Payloads.PostDto;
import com.devSage.blog.blog_app_apis.Payloads.PostResponse;
import com.devSage.blog.blog_app_apis.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    // create post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable
                                              Integer userId,@PathVariable Integer categoryId){
        PostDto createdPost=postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // update Post

    @PutMapping("/updatePost/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto postDto1=postService.updatePost(postDto,postId);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }

    // get post by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        List<PostDto> postDtos=postService.getPostsByUser(userId);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    // get post by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> postDtos=postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    // getAll posts

    @GetMapping("/getAllPosts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "7",required = false) Integer pageSize
    ){
        PostResponse postResponse=postService.getAllPost(pageNumber,pageSize);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    // get post by id

    @GetMapping("/getPostById/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto=postService.getPostById(postId);
        return  new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    // deletePost
    @DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId){
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully !!",true),HttpStatus.OK);
    }
}
