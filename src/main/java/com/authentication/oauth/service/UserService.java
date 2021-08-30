package com.authentication.oauth.service;

import com.authentication.oauth.common.constants.AppConstants;
import com.authentication.oauth.entity.User;
import com.authentication.oauth.model.StatusResponse;
import com.authentication.oauth.model.UserDetailResponse;
import com.authentication.oauth.model.UserResponse;
import com.authentication.oauth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse getUserById(Integer userId){
        log.info("Inside the getUserById :: UserService");
        UserResponse userResponse = new UserResponse();
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
            throw new EntityNotFoundException();
        }
        log.info("userResponse - {}", userResponse);
        return userResponse;
    }
}
