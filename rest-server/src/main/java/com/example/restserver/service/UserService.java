package com.example.restserver.service;

import com.example.restserver.mapper.UserMapper;
import com.example.restserver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(String username){
        User userByUserName = userMapper.findUserByUserName(username);
        return userByUserName;
    }
}
