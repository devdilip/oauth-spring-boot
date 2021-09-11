package com.authentication.oauth.controller;

import com.authentication.oauth.model.UserRequest;
import com.authentication.oauth.model.UserResponse;
import com.authentication.oauth.model.validation.group.EmailGroup;
import com.authentication.oauth.model.validation.group.MobileGroup;
import com.authentication.oauth.model.validation.group.UserRequestGroup;
import com.authentication.oauth.service.IUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.authentication.oauth.common.constants.RouteConstant.APP_VERSION;
import static com.authentication.oauth.common.constants.RouteConstant.USER_BASE_ROUTE;

@RestController
@RequestMapping(APP_VERSION + USER_BASE_ROUTE)
@SecurityRequirement(name = USER_BASE_ROUTE)
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@Validated(EmailGroup.class) @ModelAttribute UserRequest userRequest){
        return new ResponseEntity<>(userService.getUserByEmail(userRequest.getEmail()), HttpStatus.OK);
    }

    @GetMapping("/mobile/{mobile}")
    public ResponseEntity<UserResponse> getUserByMobile(@Validated(MobileGroup.class) @ModelAttribute UserRequest userRequest){
        return new ResponseEntity<>(userService.getUserByMobile(userRequest.getMobile()), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UserResponse> saveUser(@Validated(UserRequestGroup.class)  @RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.saveUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

}
