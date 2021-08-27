package com.authentication.oauth.exception;

import com.authentication.oauth.common.constants.AppConstants;
import com.authentication.oauth.model.AppResponse;
import com.authentication.oauth.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AppResponse> handleUnauthorizedException(AccessDeniedException exception){
        log.error("Unauthorized: {}", exception.getMessage());
        AppResponse appResponse = new AppResponse();
        appResponse.setStatus(null);
        appResponse.setError(new ErrorResponse(Integer.valueOf(AppConstants.ERROR_CODE_UNAUTHORIZED), AppConstants.ERROR_MSG_UNAUTHORIZED));
        return new ResponseEntity<>(appResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<AppResponse> handleDatabaseException(PersistenceException exception) {
        log.error("PersistenceException: {}", exception.getMessage());
        AppResponse appResponse = new AppResponse();
        appResponse.setStatus(null);
        appResponse.setError(new ErrorResponse(Integer.valueOf(AppConstants.ERROR_CODE_PERSISTENCE), AppConstants.ERROR_MSG_PERSISTENCE));
        return new ResponseEntity<>(appResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("handleMethodArgumentNotValid: {}", ex.getBindingResult().getAllErrors());
        log.info("handleMethodArgumentNotValid: {}", status);

        AppResponse appResponse = new AppResponse();
        appResponse.setStatus(null);
        appResponse.setError(new ErrorResponse(Integer.valueOf(AppConstants.ERROR_CODE_INVALID_METHOD_ARGUMENT), AppConstants.ERROR_MSG_INVALID_METHOD_ARGUMENT));
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }
}
