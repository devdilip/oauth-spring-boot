package com.authentication.oauth.service;

import com.authentication.oauth.entity.User;
import com.authentication.oauth.model.StatusResponse;
import com.authentication.oauth.model.UserDetailResponse;
import com.authentication.oauth.model.UserRequest;
import com.authentication.oauth.model.UserResponse;
import com.authentication.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

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
            userResponse.setStatus(new StatusResponse(
                    Integer.parseInt(messageSource.getMessage("response.code.success", null, Locale.getDefault())),
                    messageSource.getMessage("response.message.success", null, Locale.getDefault())
            ));
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
            userResponse.setStatus(new StatusResponse(
                    Integer.parseInt(messageSource.getMessage("response.code.recordCreated", null, Locale.getDefault())),
                    messageSource.getMessage("response.message.recordCreated", null, Locale.getDefault())
            ));
        }else {
            userResponse.setStatus(new StatusResponse(
                    Integer.parseInt(messageSource.getMessage("response.code.createUserError", null, Locale.getDefault())),
                    messageSource.getMessage("response.message.createUserError", null, Locale.getDefault())
            ));
        }
        return userResponse;
    }
}
