package com.devSage.blog.blog_app_apis.Repositories;

import com.devSage.blog.blog_app_apis.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

}
