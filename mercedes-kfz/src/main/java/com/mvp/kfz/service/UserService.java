package com.mvp.kfz.service;

import com.mvp.kfz.data.LoginInput;
import com.mvp.kfz.data.entity.Users;

public interface UserService {
    Long addUser(Users users);

    Users getUserDetails(LoginInput loginInput);

    Users getUserById(Long userId);
}
