package com.authentication.oauth.service;

import com.authentication.oauth.common.constants.AppConstants;
import com.authentication.oauth.entity.User;
import com.authentication.oauth.mapper.ResponseFormatter;
import com.authentication.oauth.model.UserDetailResponse;
import com.authentication.oauth.model.UserRequest;
import com.authentication.oauth.model.UserResponse;
import com.authentication.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResponseFormatter responseFormatter;

    @Override
    public UserResponse getUserById(Integer userId){
        return userRepository.findById(userId)
                .map(user -> {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setUserDetailResponse(new UserDetailResponse(user));
                    return responseFormatter.getSuccessResponse(userResponse, AppConstants.SUCCESS);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setUserDetailResponse(new UserDetailResponse(user));
                    return responseFormatter.getSuccessResponse(userResponse, AppConstants.SUCCESS);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public UserResponse getUserByMobile(String mobile) {
        return userRepository.findByMobile(mobile)
                .map(user -> {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setUserDetailResponse(new UserDetailResponse(user));
                    return responseFormatter.getSuccessResponse(userResponse, AppConstants.SUCCESS);
                })
                .orElseThrow(EntityNotFoundException::new);
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
        userResponse.setUserDetailResponse(new UserDetailResponse(user));
        return responseFormatter.getSuccessResponse(userResponse, AppConstants.SUCCESS_CREATED);
    }
}
