package com.kujason.springbootmall.impl;

import com.kujason.springbootmall.dao.UserDao;
import com.kujason.springbootmall.dto.UserRegisterRequest;
import com.kujason.springbootmall.model.User;
import com.kujason.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }
}
