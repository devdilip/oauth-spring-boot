package com.authentication.oauth.service;

import com.authentication.oauth.common.constants.AppConstants;
import com.authentication.oauth.entity.User;
import com.authentication.oauth.model.*;
import com.authentication.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse getUserById(Integer userId){
        UserResponse userResponse = new UserResponse();
        Optional<User> userOptional =  userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            UserDetailResponse userDetailResponse = new UserDetailResponse();
            userDetailResponse.setName(user.getFirstName() + " " + user.getLastName());
            userDetailResponse.setEmail(user.getEmail());
            userDetailResponse.setMobile(user.getMobile());
            userResponse.setUserDetailResponse(userDetailResponse);
            userResponse.setStatus(new StatusResponse(Integer.parseInt(AppConstants.SUCCESS_CODE), AppConstants.SUCCESS_MSG));
        }else {
            throw new EntityNotFoundException();
        }
        return userResponse;
    }

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        UserResponse userResponse = new UserResponse();
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setMobile(userRequest.getMobile());
        user = userRepository.save(user);
        if(user.getId() != null){
            UserDetailResponse userDetailResponse = new UserDetailResponse();
            userDetailResponse.setName(user.getFirstName() + " " + user.getLastName());
            userDetailResponse.setEmail(user.getEmail());
            userDetailResponse.setMobile(user.getMobile());
            userResponse.setUserDetailResponse(userDetailResponse);
            userResponse.setStatus(new StatusResponse(Integer.parseInt(AppConstants.SUCCESS_CODE_CREATE), AppConstants.SUCCESS_MSG_CREATE));
        }else {
            userResponse.setStatus(new StatusResponse(Integer.parseInt(AppConstants.ERROR_CREATE_USER_ID), AppConstants.ERROR_CREATE_USER_ID_MSG));
        }
        return userResponse;
    }
}
