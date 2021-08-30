package com.authentication.oauth.exception;

import com.authentication.oauth.common.constants.AppConstants;
import com.authentication.oauth.mapper.ResponseFormatter;
import com.authentication.oauth.model.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Objects;

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
        log.error("handleMethodArgumentNotValid :: Error");
        AppResponse appResponse = responseFormatter.getFailureResponse(AppConstants.ERROR_400_INVALID_ARGUMENT, ex.getBindingResult().getAllErrors());
        return new ResponseEntity<>(appResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleHttpRequestMethodNotSupported: {}", ex.getMethod());
        AppResponse appResponse = responseFormatter.getFailureResponse(AppConstants.ERROR_405_METHOD_NOT_ALLOWED, List.of(ex.getMessage()));
        return new ResponseEntity<>(appResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleHttpMessageNotReadable: {}", ex.getMessage());
        AppResponse appResponse = responseFormatter.getFailureResponse(AppConstants.ERROR_400_INVALID_ARGUMENT, List.of(Objects.requireNonNull(ex.getMessage())));
        return new ResponseEntity<>(appResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * EntityNotFoundException
     * @param exception
     * @return
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AppResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        log.error("EntityNotFoundException: {}", exception.getMessage());
        AppResponse appResponse = responseFormatter.getFailureResponse(AppConstants.ERROR_404_NOT_FOUND, null);
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    /**
     * Exception
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppResponse> handleException(Exception exception) {
        log.error("Exception: {}", exception.getMessage());
        AppResponse appResponse = responseFormatter.getFailureResponse(AppConstants.ERROR_500_INTERNAL_SERVER_ERROR, null);
        return new ResponseEntity<>(appResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
