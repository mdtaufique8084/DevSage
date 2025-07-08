package com.devSage.blog.blog_app_apis.Controllers;

import com.devSage.blog.blog_app_apis.ApiResponse;
import com.devSage.blog.blog_app_apis.Payloads.UserDto;
import com.devSage.blog.blog_app_apis.Services.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // post user -postmapping
    @PostMapping("/addUsers")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto=userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }
    // put user-put mapping
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId){
        UserDto updateUser=this.userService.updateUser(userDto,userId);
        return new ResponseEntity<>(updateUser,HttpStatus.OK);
    }
    // delete User -deleteUser
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
    }
    // Get user - getMapping
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUssers(){
        return new ResponseEntity<>(this.userService.getAllUsers(),HttpStatus.OK);
    }
    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId){
        UserDto userDto=userService.getUserById(userId);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
}
