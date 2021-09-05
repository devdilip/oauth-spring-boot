package com.authentication.oauth.controller;

import com.authentication.oauth.model.UserRequest;
import com.authentication.oauth.model.UserResponse;
import com.authentication.oauth.service.IUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.authentication.oauth.common.constants.RouteConstant.APP_VERSION;
import static com.authentication.oauth.common.constants.RouteConstant.USER_BASE_ROUTE;

@RestController
@RequestMapping(APP_VERSION + USER_BASE_ROUTE)
@SecurityRequirement(name = USER_BASE_ROUTE)
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId")  Integer userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable("email")  String email){
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/mobile/{mobile}")
    public ResponseEntity<UserResponse> getUserByMobile(@PathVariable("mobile")  String mobile){
        return new ResponseEntity<>(userService.getUserByMobile(mobile), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UserResponse> saveUser(@Valid @RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.saveUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

}
