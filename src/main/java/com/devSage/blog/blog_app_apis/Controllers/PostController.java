package com.devSage.blog.blog_app_apis.Controllers;

import com.devSage.blog.blog_app_apis.Config.AppConstants;
import com.devSage.blog.blog_app_apis.Exceptions.ApiResponse;
import com.devSage.blog.blog_app_apis.Payloads.PostDto;
import com.devSage.blog.blog_app_apis.Payloads.PostResponse;
import com.devSage.blog.blog_app_apis.Services.FileService;
import com.devSage.blog.blog_app_apis.Services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    // Create post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId) {
        PostDto createdPost = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // Update post (text only)
    @PutMapping("/updatePost/{postId}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
        PostDto updated = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Update post with image (combined)
    @PutMapping(value = "/updatePostWithImage/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDto> updatePostWithImage(
            @Valid
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @PathVariable Integer postId) throws IOException {

        PostDto existing = postService.getPostById(postId);
        existing.setTitle(title);
        existing.setContent(content);

        if (image != null && !image.isEmpty()) {
            String fileName = fileService.uploadImage(path, image);
            existing.setImageName(fileName);
        }

        PostDto updated = postService.updatePost(existing, postId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Get posts by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
        List<PostDto> posts = postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Get posts by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
        List<PostDto> posts = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Get all posts with pagination and sorting
    @GetMapping("/getAllPosts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        PostResponse response = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get post by ID
    @GetMapping("/getPostById/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto post = postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Delete post
    @DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully !!", true), HttpStatus.OK);
    }

    // Search post by title
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<?> searchPostByTitle(@PathVariable String keywords) {
        List<PostDto> result = postService.SearchPosts(keywords);
        if (result.isEmpty()) {
            return new ResponseEntity<>("No posts found with the given title", HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Upload image only
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImageOnly(
            @Valid
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId) throws IOException {

        PostDto postDto = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updated = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Serve image directly
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

    // Download image with stream
    @GetMapping(value = "/post/image/download/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) {
        try (InputStream resource = fileService.getResource(path, imageName);
             OutputStream out = response.getOutputStream()) {

            response.setContentType(MediaType.IMAGE_JPEG_VALUE);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = resource.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }

            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
