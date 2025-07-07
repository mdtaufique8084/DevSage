package com.devSage.blog.blog_app_apis.Payloads;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Data
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String about;
}
