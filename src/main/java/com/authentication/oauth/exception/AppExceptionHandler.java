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
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Objects;

import static com.authentication.oauth.common.constants.AppConstants.*;


@Slf4j
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ResponseFormatter responseFormatter;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AppResponse> handleUnauthorizedException(AccessDeniedException exception){
        log.error("handleUnauthorizedException :: Unauthorized: {}", exception.getMessage());
        AppResponse appResponse = responseFormatter.getFailureResponse(ERROR_401_UNAUTHORIZED, null);
        return new ResponseEntity<>(appResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<AppResponse> handleDatabaseException(PersistenceException exception) {
        log.error("PersistenceException: {}", exception.getMessage());
        AppResponse appResponse = responseFormatter.getFailureResponse(ERROR_DATABASE, List.of(exception.getMessage()));
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleMethodArgumentNotValid :: Error");
        AppResponse appResponse = responseFormatter.getFailureResponse(ERROR_400_BAD_REQUEST, ex.getBindingResult().getAllErrors());
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleHttpRequestMethodNotSupported: {}", ex.getMethod());
        AppResponse appResponse = responseFormatter.getFailureResponse(ERROR_405_METHOD_NOT_ALLOWED, List.of(ex.getMessage()));
        return new ResponseEntity<>(appResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleHttpMessageNotReadable: {}", ex.getMessage());
        AppResponse appResponse = responseFormatter.getFailureResponse(ERROR_400_BAD_REQUEST, List.of(Objects.requireNonNull(ex.getMessage())));
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    /**
     * MethodArgumentTypeMismatchException
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("handleMethodArgumentTypeMismatchException :: MethodArgumentTypeMismatchException: {}", ex.getMessage());
        String argumentName = ex.getName();
        String requiredType = Objects.requireNonNull(ex.getRequiredType()).getSimpleName();
        Object currentValue = ex.getValue();
        String exceptionMessage = String.format("'%s' should be a valid '%s' not '%s'", argumentName, requiredType, currentValue);
        AppResponse appResponse = responseFormatter.getFailureResponse(AppConstants.ERROR_400_BAD_REQUEST, List.of(Objects.requireNonNull(exceptionMessage)));
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    /**
     * EntityNotFoundException
     * @param exception
     * @return
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AppResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        log.error("handleEntityNotFoundException :: EntityNotFoundException: {}", exception.getMessage());
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
        log.error("handleException :: Exception: {}", exception.getMessage());
        AppResponse appResponse = responseFormatter.getFailureResponse(AppConstants.ERROR_500_INTERNAL_SERVER_ERROR, List.of(exception.getMessage()));
        return new ResponseEntity<>(appResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * For catching validation fail exception for all model class
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleBindException: {}", ex.getMessage());
        AppResponse appResponse = responseFormatter.getFailureResponse(AppConstants.ERROR_400_BAD_REQUEST, ex.getBindingResult().getAllErrors());
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    /**
     * For validating invalid url or 404 URL not found
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleNoHandlerFoundException: {}", ex.getMessage());
        String errorMessage = "Invalid request URL"+List.of(ex.getRequestURL(), ex.getHttpMethod());
        AppResponse appResponse = responseFormatter.getFailureResponse(ERROR_400_BAD_REQUEST, List.of(errorMessage));
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }
}
