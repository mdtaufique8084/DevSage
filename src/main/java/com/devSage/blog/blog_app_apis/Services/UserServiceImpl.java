package com.devSage.blog.blog_app_apis.Services;

import com.devSage.blog.blog_app_apis.Config.AppConstants;
import com.devSage.blog.blog_app_apis.Entity.Role;
import com.devSage.blog.blog_app_apis.Entity.User;
import com.devSage.blog.blog_app_apis.Exceptions.ResourceNotFoundException;
import com.devSage.blog.blog_app_apis.Payloads.UserDto;
import com.devSage.blog.blog_app_apis.Repositories.RoleRepository;
import com.devSage.blog.blog_app_apis.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository; // proxy class of userRepo interface made the object of proxy class

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);

        // encoded the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // roles
        Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();

        user.getRoles().add(role);

        User newUser = this.userRepository.save(user);

        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user=this.dtoToEntity(userDto);
        User savedUser=userRepository.save(user);
        return this.entityToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User"," Id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser=userRepository.save(user);
        return entityToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User"," Id",userId));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users =userRepository.findAll();
        List<UserDto> userDtos=users.stream().
                map(user->entityToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User"," Id",userId));
        userRepository.delete(user);
    }


    private User dtoToEntity(UserDto userDto){
        User user=modelMapper.map(userDto,User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;
    }

    private UserDto entityToDto(User user){
        UserDto userDto=modelMapper.map(user,UserDto.class);
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return userDto;
    }
}
