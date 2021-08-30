package com.authentication.oauth.controller;

import com.authentication.oauth.common.constants.AppConstants;
import com.authentication.oauth.model.ErrorResponse;
import com.authentication.oauth.model.StatusResponse;
import com.authentication.oauth.model.UserRequest;
import com.authentication.oauth.model.UserResponse;
import com.authentication.oauth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.authentication.oauth.common.constants.RouteConstant.APP_VERSION;
import static com.authentication.oauth.common.constants.RouteConstant.USER_BASE_ROUTE;

@RestController
@Slf4j
@RequestMapping(APP_VERSION + USER_BASE_ROUTE)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") String userId){

        log.debug("Executing :: getUserById :: START");
        log.info("Fetching user for {}", userId);

        UserResponse userResponse = new UserResponse();
        String invalidateRequestMgs = validateRequest(userId);
        if(StringUtils.isEmpty(invalidateRequestMgs)){
            int usrId = Integer.parseInt(userId);
            userResponse = userService.getUserById(usrId);
            log.info("userResponse  = {}", userResponse.toString());
        }else {
            userResponse.setStatus(new StatusResponse(Integer.valueOf(AppConstants.ERROR_CODE_INVALID_REQUEST), AppConstants.ERROR_MSG_INVALID_REQUEST));
            userResponse.setError(new ErrorResponse(Integer.valueOf(invalidateRequestMgs.split("~")[0]), invalidateRequestMgs.split("~")[1]));
        }
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UserResponse> saveUser(@Valid @RequestBody UserRequest user){
        UserResponse userResponse = new UserResponse();
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    private String validateRequest(String userId) {
        log.info("Inside validateRequest for {}", userId);
        String invalidateRequestMgs = StringUtils.EMPTY;

        if(StringUtils.isBlank(userId) || !StringUtils.isNumeric(userId) || userId.equalsIgnoreCase((NumberUtils.INTEGER_ZERO.toString()))){
            invalidateRequestMgs = AppConstants.INVALID_USER_ID + "~" + AppConstants.INVALID_USER_ID_MSG;
        }
        return invalidateRequestMgs;
    }
}
