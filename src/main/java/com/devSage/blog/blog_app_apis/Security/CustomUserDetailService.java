package com.devSage.blog.blog_app_apis.Security;

import com.devSage.blog.blog_app_apis.Entity.User;
import com.devSage.blog.blog_app_apis.Exceptions.ResourceNotFoundException;
import com.devSage.blog.blog_app_apis.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(username)
                .orElseThrow(()->new ResourceNotFoundException("User "," with email : "+username,0));
        return user;
    }
}
