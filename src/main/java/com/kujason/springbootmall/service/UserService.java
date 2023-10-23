package com.kujason.springbootmall.service;

import com.kujason.springbootmall.dto.UserRegisterRequest;
import com.kujason.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register (UserRegisterRequest userRegisterRequest);


}
