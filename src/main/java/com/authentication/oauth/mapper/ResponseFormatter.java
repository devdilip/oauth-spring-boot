package com.authentication.oauth.mapper;


import com.authentication.oauth.model.AppResponse;
import com.authentication.oauth.model.ErrorResponse;
import com.authentication.oauth.model.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.authentication.oauth.common.constants.AppConstants.*;

@Component
public class ResponseFormatter {

    @Autowired
    private MessageSource messageSource;

    public <T> AppResponse getFailureResponse(String errorCode, List<T> errors){

        AppResponse appResponse = new AppResponse();

        switch (errorCode){

            case ERROR_401_UNAUTHORIZED:
                appResponse.setStatus(new StatusResponse(
                        Integer.parseInt(messageSource.getMessage("response.code.unauthorized", null, Locale.getDefault())),
                        messageSource.getMessage("response.message.unauthorized", null, Locale.getDefault())
                ));
                break;

            case ERROR_500_INTERNAL_SERVER_ERROR:
                appResponse.setStatus(new StatusResponse(
                        Integer.parseInt(messageSource.getMessage("response.code.internalServerError", null, Locale.getDefault())),
                        messageSource.getMessage("response.message.internalServerError", null, Locale.getDefault())
                ));
                break;

            case ERROR_DATABASE:
                appResponse.setStatus(new StatusResponse(
                        Integer.parseInt(messageSource.getMessage("response.code.failure", null, Locale.getDefault())),
                        messageSource.getMessage("response.message.failure", null, Locale.getDefault())
                ));
                break;

            case ERROR_400_INVALID_ARGUMENT:
                Integer invalidRequestCode = Integer.parseInt(messageSource.getMessage("response.code.invalidRequest", null, Locale.getDefault()));
                appResponse.setStatus(new StatusResponse(invalidRequestCode,
                        messageSource.getMessage("response.message.invalidRequest", null, Locale.getDefault())
                ));
                setErrorInResponse(appResponse, errors, invalidRequestCode);
                break;

            case ERROR_405_METHOD_NOT_ALLOWED:
                Integer methodNotAllowedCode = Integer.parseInt(messageSource.getMessage("response.code.methodNotAllowed", null, Locale.getDefault()));
                appResponse.setStatus(new StatusResponse(methodNotAllowedCode,
                        messageSource.getMessage("response.message.methodNotAllowed", null, Locale.getDefault())
                ));
                setErrorInResponse(appResponse, errors, methodNotAllowedCode);
                break;

            default:
                break;
        }
        return appResponse;
    }

    static <T> void setErrorInResponse(AppResponse appResponse, List<T> errors, Integer errorCode){
        if (errors != null && errors.get(0) instanceof String) {
            appResponse.setError(new ErrorResponse(errorCode,
                    errors.stream().map(Objects::toString).collect(Collectors.joining(", "))));
        }
        if (errors != null && errors.get(0) instanceof ObjectError) {
            appResponse.setError(new ErrorResponse(errorCode,
                    errors.stream().map(e -> ((ObjectError)e).getDefaultMessage()).collect(Collectors.joining(", "))));
        }
    }
}
