package com.devSage.blog.blog_app_apis.Exceptions;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);

    }

    public ApiException() {
        super();

    }

}
