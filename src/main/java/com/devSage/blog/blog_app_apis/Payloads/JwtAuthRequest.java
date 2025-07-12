package com.devSage.blog.blog_app_apis.Payloads;

import lombok.Data;

@Data

public class JwtAuthRequest {

    private String email;

    private String password;

}
