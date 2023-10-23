package com.kujason.springbootmall.dao;

import com.kujason.springbootmall.dto.UserRegisterRequest;
import com.kujason.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
