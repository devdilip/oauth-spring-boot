package com.authentication.oauth.mapper;


import com.authentication.oauth.model.StatusResponse;
import com.authentication.oauth.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

import static com.authentication.oauth.common.constants.AppConstants.*;

@Component
public class ResponseFormatter {

    @Autowired
    private MessageSource messageSource;

    public <T> UserResponse getFailureResponse(String errorCode, List<T> errors){

        UserResponse userResponse = new UserResponse();

        switch (errorCode){

            case ERROR_401:
                userResponse.setStatus(new StatusResponse(
                        Integer.parseInt(messageSource.getMessage("response.code.unauthorized", null, Locale.getDefault())),
                        messageSource.getMessage("response.message.unauthorized", null, Locale.getDefault())
                ));
                break;
            case ERROR_500:
                userResponse.setStatus(new StatusResponse(
                        Integer.parseInt(messageSource.getMessage("response.code.internalServerError", null, Locale.getDefault())),
                        messageSource.getMessage("response.message.internalServerError", null, Locale.getDefault())
                ));
                break;
            case ERROR_DATABASE:
                userResponse.setStatus(new StatusResponse(
                        Integer.parseInt(messageSource.getMessage("response.code.failure", null, Locale.getDefault())),
                        messageSource.getMessage("response.message.failure", null, Locale.getDefault())
                ));
                break;
            default:
                break;
        }
        return userResponse;
    }
}
