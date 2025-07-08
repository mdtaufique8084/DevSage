package com.devSage.blog.blog_app_apis.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    @NotEmpty
    @Size(min=4,message = "UserName must br min of 4 character")
    private String name;
    @NotEmpty
    @Email(message = "Email address is not valid")
    private String email;
    @NotEmpty
    @Size(min=3,max = 10,message = "Password must be between 3 to 10 character")
    private String password;
    @NotEmpty
    private String about;
}
