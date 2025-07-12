package com.devSage.blog.blog_app_apis.Payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;

    private UserDto user;
}
