package com.authentication.oauth.service;

import com.authentication.oauth.model.UserResponse;

public interface IUserService {

    UserResponse getUserById(Integer userId);

}