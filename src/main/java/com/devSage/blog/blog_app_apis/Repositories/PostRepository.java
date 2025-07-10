package com.devSage.blog.blog_app_apis.Repositories;

import com.devSage.blog.blog_app_apis.Entity.Category;
import com.devSage.blog.blog_app_apis.Entity.Post;
import com.devSage.blog.blog_app_apis.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByPostTitleContaining(String postTitle);

//    @Query("SELECT p FROM Post p WHERE LOWER(p.postTitle) LIKE LOWER(CONCAT('%', :title, '%'))")
//    List<Post> searchByTitle(@Param("title") String title);
}
