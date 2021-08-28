package com.authentication.oauth.exception;

import com.authentication.oauth.common.constants.AppConstants;
import com.authentication.oauth.mapper.ResponseFormatter;
import com.authentication.oauth.model.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;
import java.util.List;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ResponseFormatter responseFormatter;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AppResponse> handleUnauthorizedException(AccessDeniedException exception){
        log.error("Unauthorized: {}", exception.getMessage());
        AppResponse appResponse = responseFormatter.getFailureResponse(HttpStatus.UNAUTHORIZED.toString(), null);
        return new ResponseEntity<>(appResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<AppResponse> handleDatabaseException(PersistenceException exception) {
        log.error("PersistenceException: {}", exception.getMessage());
        AppResponse appResponse = responseFormatter.getFailureResponse(AppConstants.ERROR_DATABASE, null);
        return new ResponseEntity<>(appResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("handleMethodArgumentNotValid :: Error");
        AppResponse appResponse = responseFormatter.getFailureResponse(AppConstants.ERROR_400_INVALID_ARGUMENT, ex.getBindingResult().getAllErrors());
        return new ResponseEntity<>(appResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("handleHttpRequestMethodNotSupported: {}", ex.getMethod());
        AppResponse appResponse = responseFormatter.getFailureResponse(AppConstants.ERROR_405_METHOD_NOT_ALLOWED, List.of(ex.getMessage()));
        return new ResponseEntity<>(appResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
