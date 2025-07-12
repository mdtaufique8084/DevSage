package com.devSage.blog.blog_app_apis.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Role {

    @Id
    private Integer id;

    private String name;

}