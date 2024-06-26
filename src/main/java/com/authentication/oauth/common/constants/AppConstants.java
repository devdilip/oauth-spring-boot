package com.authentication.oauth.common.constants;

public class AppConstants {

    public static final String ACTUATOR_PATH_IDENTIFIER = "/actuator/**";
    public static final String SECURITY_EXCLUDED_PATHS = "\"/dist/**\", \"/static/js/**\", \"/app/**\" ";
    public static final String SWAGGER_PATHS = "/swagger-ui/**";
    public static final String OPEN_API_PATHS = "/oauth-openapi/**";

    public static final String AUTHORITIES_ROLE_USER = "ROLE_USER";

    public static final String ERROR_401_UNAUTHORIZED = "401_UNAUTHORIZED";
    public static final String ERROR_500_INTERNAL_SERVER_ERROR = "500_INTERNAL_SERVER_ERROR";
    public static final String ERROR_404_NOT_FOUND = "NOT_FOUND";
    public static final String ERROR_400_BAD_REQUEST = "ERROR_400_BAD_REQUEST";
    public static final String ERROR_405_METHOD_NOT_ALLOWED = "405_METHOD_NOT_ALLOWED";
    public static final String ERROR_DATABASE = "ERROR_DATABASE";

    public static final String SUCCESS_CREATED = "SUCCESS_CREATED";
    public static final String SUCCESS = "SUCCESS";

    public static final String MISSING_REQUIRED_PROPERTIES = "Missing required input properties";
    public static final String INVALID_PROPERTIES = "Invalid properties";

    // Regex
    public static final String NAME_REGEX = "^([a-z]+[,.]?[ ]?|[a-z]+['-]?)+$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    public static final String MOBILE_REGEX = "^[0-9]+";

}
