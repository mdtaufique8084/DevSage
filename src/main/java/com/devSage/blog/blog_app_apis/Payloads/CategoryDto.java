package com.devSage.blog.blog_app_apis.Payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;
    @NotEmpty
    @Size(min = 3,message = "minimum 3 characters are required")
    private String categoryTitle;
    @NotEmpty
    @Size(min = 3,message = "minimum 3 characters are required")
    private String categoryDescription;
}
