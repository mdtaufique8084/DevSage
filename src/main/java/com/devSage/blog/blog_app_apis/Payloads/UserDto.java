package com.devSage.blog.blog_app_apis.Payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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
    private Set<RoleDto> roles = new HashSet<>();


    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password=password;
    }

}
