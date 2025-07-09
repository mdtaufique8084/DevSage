package com.devSage.blog.blog_app_apis.Repositories;

import com.devSage.blog.blog_app_apis.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
