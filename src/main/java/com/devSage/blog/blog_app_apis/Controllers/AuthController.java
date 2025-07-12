package com.devSage.blog.blog_app_apis.Controllers;

import com.devSage.blog.blog_app_apis.Entity.User;
import com.devSage.blog.blog_app_apis.Exceptions.ApiException;
import com.devSage.blog.blog_app_apis.Payloads.JwtAuthRequest;
import com.devSage.blog.blog_app_apis.Payloads.JwtAuthResponse;
import com.devSage.blog.blog_app_apis.Payloads.UserDto;
import com.devSage.blog.blog_app_apis.Repositories.UserRepository;
import com.devSage.blog.blog_app_apis.Security.JwtTokenHelper;
import com.devSage.blog.blog_app_apis.Services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        this.authenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);

        User user = this.userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        response.setUser(this.mapper.map(user, UserDto.class));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);

        try {

            this.authenticationManager.authenticate(authenticationToken);

        } catch (BadCredentialsException e) {
            System.out.println("Invalid Detials !!");
            throw new ApiException("Invalid username or password !!");
        }

    }

    // register new user api

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        UserDto registeredUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
    }

    // get loggedin user data


    @GetMapping("/current-user/")
    public ResponseEntity<UserDto> getUser(Principal principal) {
        User user = this.userRepo.findByEmail(principal.getName()).get();
        return new ResponseEntity<UserDto>(this.mapper.map(user, UserDto.class), HttpStatus.OK);
    }

}
