package com.devSage.blog.blog_app_apis.Repositories;

import com.devSage.blog.blog_app_apis.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
