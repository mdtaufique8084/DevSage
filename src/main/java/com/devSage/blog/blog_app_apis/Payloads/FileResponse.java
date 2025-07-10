package com.devSage.blog.blog_app_apis.Payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileResponse {
    private String fileName;
    private String message;
}
