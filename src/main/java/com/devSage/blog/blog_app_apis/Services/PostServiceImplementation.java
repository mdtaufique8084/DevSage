package com.devSage.blog.blog_app_apis.Services;

import com.devSage.blog.blog_app_apis.Entity.Category;
import com.devSage.blog.blog_app_apis.Entity.Post;
import com.devSage.blog.blog_app_apis.Entity.User;
import com.devSage.blog.blog_app_apis.Exceptions.ResourceNotFoundException;
import com.devSage.blog.blog_app_apis.Payloads.PostDto;
import com.devSage.blog.blog_app_apis.Payloads.PostResponse;
import com.devSage.blog.blog_app_apis.Repositories.CategoryRepository;
import com.devSage.blog.blog_app_apis.Repositories.PostRepository;
import com.devSage.blog.blog_app_apis.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class PostServiceImplementation implements  PostService{

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        User user=userRepository.findById(userId).
                orElseThrow(()->new ResourceNotFoundException("User","User Id ",userId));

        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","category Id ",categoryId));
        Post post=modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost=postRepository.save(post);
        return modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post"," post id ",postId));
        post.setPostTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost=postRepository.save(post);
        return modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post"," post id ",postId));
        postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize) {
        Pageable p= PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePosts=postRepository.findAll(p);
        List<Post> allPosts=pagePosts.getContent();
        List<PostDto> postDtos=allPosts.stream().map((post -> modelMapper.map(post,PostDto.class))).toList();
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post"," post id ",postId));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","category Id ",categoryId));
        List<Post> posts=postRepository.findByCategory(category);
        List<PostDto> postDtos=posts.stream().map((post -> modelMapper.map(post,PostDto.class))).toList();
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User"," user Id ",userId));
        List<Post> posts=postRepository.findByUser(user);
        List<PostDto> postDtos=posts.stream().map((post -> modelMapper.map(post,PostDto.class))).toList();
        return postDtos;
    }

    @Override
    public List<PostDto> SearchPosts(String keyword) {
        return List.of();
    }
}
