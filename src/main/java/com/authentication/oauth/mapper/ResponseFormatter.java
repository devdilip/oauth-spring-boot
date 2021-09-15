package com.authentication.oauth.mapper;


import com.authentication.oauth.model.AppResponse;
import com.authentication.oauth.model.ErrorResponse;
import com.authentication.oauth.model.StatusResponse;
import com.authentication.oauth.model.UserResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.*;
import java.util.stream.Collectors;

import static com.authentication.oauth.common.constants.AppConstants.*;

@Component
@Slf4j
public class ResponseFormatter {

    @Autowired
    private MessageSource messageSource;

    public UserResponse getSuccessResponse(UserResponse userResponse, String successCode){

        switch (successCode){
            case SUCCESS:
                userResponse.setStatus(new StatusResponse(
                        Integer.parseInt(messageSource.getMessage("response.code.success", null, Locale.getDefault())),
                        messageSource.getMessage("response.message.success", null, Locale.getDefault())
                ));
                break;
            case SUCCESS_CREATED:
                userResponse.setStatus(new StatusResponse(
                        Integer.parseInt(messageSource.getMessage("response.code.recordCreated", null, Locale.getDefault())),
                        messageSource.getMessage("response.message.recordCreated", null, Locale.getDefault())
                ));
                break;

            default:
                break;
        }
        return userResponse;
    }

    public <T> AppResponse getFailureResponse(String errorCode, List<T> errors){

        AppResponse appResponse = new AppResponse();
        log.error("getFailureResponse :: ErrorCode: {}", errorCode);

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
                setErrorInResponse(appResponse, errors, Integer.parseInt(messageSource.getMessage("response.code.failure", null, Locale.getDefault())));
                break;

            case ERROR_DATABASE:
                appResponse.setStatus(new StatusResponse(
                        Integer.parseInt(messageSource.getMessage("response.code.failure", null, Locale.getDefault())),
                        messageSource.getMessage("response.message.failure", null, Locale.getDefault())
                ));
                setErrorInResponse(appResponse, errors, Integer.parseInt(messageSource.getMessage("response.code.dbError", null, Locale.getDefault())));
                break;

            case ERROR_400_BAD_REQUEST:
                Integer invalidRequestCode = Integer.parseInt(messageSource.getMessage("response.code.invalidRequest", null, Locale.getDefault()));
                String invalidRequestMessage = messageSource.getMessage("response.message.invalidRequest", null, Locale.getDefault());
                appResponse.setStatus(new StatusResponse(invalidRequestCode, invalidRequestMessage));
                setErrorsInResponse(appResponse, errors);
                break;

            case ERROR_404_NOT_FOUND:
                Integer noRecordCode = Integer.parseInt(messageSource.getMessage("response.code.noRecord", null, Locale.getDefault()));
                String noRecordMessage = messageSource.getMessage("response.message.noRecord", null, Locale.getDefault());
                appResponse.setStatus(new StatusResponse(noRecordCode, noRecordMessage));
                break;

            case ERROR_405_METHOD_NOT_ALLOWED:
                Integer methodNotAllowedCode = Integer.parseInt(messageSource.getMessage("response.code.methodNotAllowed", null, Locale.getDefault()));
                String methodNotAllowedMessage = messageSource.getMessage("response.message.methodNotAllowed", null, Locale.getDefault());
                appResponse.setStatus(new StatusResponse(methodNotAllowedCode, methodNotAllowedMessage));
                setErrorInResponse(appResponse, errors, methodNotAllowedCode);
                break;

            default:
                break;
        }
        return appResponse;
    }
    static <T> void setErrorsInResponse(AppResponse appResponse, List<T> errors){
        if(errors != null){
            if(errors.get(0) instanceof ObjectError){
                List<ErrorResponse> invalidErrors = new ArrayList<>();
                Set<String> missingInput = new LinkedHashSet<>();
                Set<String> invalidProperties = new LinkedHashSet<>();

                errors.forEach(error -> {
                    String[] errorCodeAndMessage = ((DefaultMessageSourceResolvable) error).getDefaultMessage().split("##");
                    if(errorCodeAndMessage.length == 1){
                        if(MISSING_REQUIRED_PROPERTIES.equals(errorCodeAndMessage[0]))
                            missingInput.add(((FieldError) error).getField());
                        if(INVALID_PROPERTIES.equals(errorCodeAndMessage[0]))
                            invalidProperties.add(((FieldError) error).getField());
                    }else {
                        invalidErrors.add(new ErrorResponse(
                                errorCodeAndMessage.length == 2 ? Integer.parseInt((errorCodeAndMessage[1])) : null,
                                errorCodeAndMessage[0]));
                    }
                });
                invalidProperties.removeAll(missingInput);
                List<String> errorFormat = new ArrayList<>();

                if(!missingInput.isEmpty())
                    errorFormat.add(MISSING_REQUIRED_PROPERTIES + missingInput);
                if(!invalidProperties.isEmpty())
                    errorFormat.add(INVALID_PROPERTIES + invalidProperties);
                if (!errorFormat.isEmpty())
                    appResponse.setErrors(errorFormat);

            }else if (errors.get(0) instanceof ErrorResponse){
                appResponse.setErrors(errors);
            }else if (errors.get(0) instanceof JsonMappingException.Reference){
                List<String> invalidProperties = new ArrayList<>();
                errors.forEach(error -> {
                    JsonMappingException.Reference reference = ((JsonMappingException.Reference) error);
                    invalidProperties.add(reference.getFieldName());
                });
                if (!invalidProperties.isEmpty())
                    appResponse.setErrors(Arrays.asList(INVALID_PROPERTIES + invalidProperties));
            }else if (errors.get(0) instanceof String){
                appResponse.setErrors(errors);
            }
        }
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
