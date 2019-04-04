package com.cskj.crawar.service;

import com.cskj.crawar.entity.ssq.User;
import com.cskj.crawar.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findById(String id) {
        return userMapper.findById(id);
    }

    public int addUser(String name, String age) {
        return userMapper.addUser(name, age);
    }

    public void updataById(String id, String name) {
        userMapper.updataById(id, name);
    }

    public void deleteById(String id) {
        userMapper.deleteById(id);
    }

    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }
}