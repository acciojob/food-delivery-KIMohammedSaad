package com.driver.service.impl;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto user) throws Exception {
        UserEntity userEntity=userRepository.findByEmail(user.getEmail());
        if(userEntity!=null){
            throw new Exception("Record already exists!");
        }
        UserEntity userEntity1= UserEntity.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
        userRepository.save(userEntity1);
        return user;
    }
    @Override
    public UserDto getUser(String email) throws Exception {
        UserEntity userEntity=userRepository.findByEmail(email);
        if(userEntity==null){
            throw new Exception(email);
        }
        UserDto userDto= UserDto.builder()
                .id(userEntity.getId())
                .email(email)
                .userId(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName()).build();
        return userDto;
    }


    @Override
    public UserDto getUserByUserId(String userId) throws Exception {

        UserEntity userEntity=userRepository.findByUserId(userId);
        if(userEntity==null){
            throw new Exception(userId);
        }
        UserDto userDto= UserDto.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .userId(userId)
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName()).build();
        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) throws Exception {
        UserEntity userEntity=userRepository.findByUserId(userId);
        if(userEntity==null){
            throw new Exception(userId);
        }
        UserEntity userEntity1= UserEntity.builder()
                .id(user.getId())
                .userId(userId)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
        userRepository.save(userEntity1);
        return user;
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        UserEntity userEntity=userRepository.findByUserId(userId);
        if(userEntity==null){
            throw new Exception(userId);
        }
        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserDto> userDtos=new ArrayList<>();
        List<UserEntity>userEntities= (List<UserEntity>) userRepository.findAll();
        for(UserEntity userEntity:userEntities){
            UserDto userDto= UserDto.builder()
                    .id(userEntity.getId())
                    .userId(userEntity.getUserId())
                    .firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName())
                    .email(userEntity.getEmail()).build();
            userDtos.add(userDto);
        }
        return userDtos;
    }
}