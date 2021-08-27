package com.authentication.oauth.service;

import com.authentication.oauth.common.constants.AppConstants;
import com.authentication.oauth.entity.User;
import com.authentication.oauth.model.AppResponse;
import com.authentication.oauth.model.StatusResponse;
import com.authentication.oauth.model.UserDetailResponse;
import com.authentication.oauth.model.UserResponse;
import com.authentication.oauth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public UserResponse getUserById(Integer userId){
        log.info("Inside the getUserById :: UserService");
        UserResponse userResponse = new UserResponse();

        try {
            Optional<User> userOptional =  userRepository.findById(userId);
            if(userOptional.isPresent()){
                User user = userOptional.get();
                UserDetailResponse userDetailResponse = new UserDetailResponse();
                userDetailResponse.setName(user.getFirstName() + " " + user.getLastName());
                userDetailResponse.setEmail(user.getEmail());
                userResponse.setUserDetailResponse(userDetailResponse);
                userResponse.setStatus(new StatusResponse(Integer.parseInt(AppConstants.SUCCESS_CODE), AppConstants.SUCCESS_MSG));
                log.info("User details found!");
            }else {
                log.info("User details not found!");
                userResponse.setStatus(new StatusResponse(Integer.parseInt(AppConstants.ERROR_CODE_NO_RECORD_FOUND), AppConstants.ERROR_MSG_NO_RECORD_FOUND));
            }
        }catch (Exception e){
            log.error("Exception occurred while retrieving user details - ", e);
        }
        log.info("userResponse - {}", userResponse);
        return userResponse;
    }
}
