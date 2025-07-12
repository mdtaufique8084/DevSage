package com.devSage.blog.blog_app_apis.Services;

import com.devSage.blog.blog_app_apis.Payloads.UserDto;

import java.util.List;

// This is used for data transfer but user entity is used for direct database querrying,we can expose Dto to api but
// not directly the Entity
public interface UserService {
    UserDto registerNewUser(UserDto user);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto,Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
}
