package com.devSage.blog.blog_app_apis.Repositories;

import com.devSage.blog.blog_app_apis.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
