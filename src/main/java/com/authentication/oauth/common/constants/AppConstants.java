package com.authentication.oauth.common.constants;

public class AppConstants {

    public static final String SUCCESS_CODE = "100";
    public static final String SUCCESS_MSG = "Success with result!";

    public static final String ERROR_CODE_NO_RECORD_FOUND = "101";
    public static final String ERROR_MSG_NO_RECORD_FOUND = "No record found!";

    public static final String ERROR_CODE_INVALID_REQUEST = "102";
    public static final String ERROR_MSG_INVALID_REQUEST = "Invalid request!";

    public static final String ERROR_CODE_FAILURE = "103";
    public static final String ERROR_MSG_FAILURE = "Failure!";

    public static final String INVALID_USER_ID = "104";
    public static final String INVALID_USER_ID_MSG = "Invalid user id!";

    public static final String ERROR_MSG_UNAUTHORIZED = "You are not authorized!";

    public static final String APP_PATH_IDENTIFIER = "/app/**";
    public static final String ACTUATOR_PATH_IDENTIFIER = "/actuator/**";
    public static final String APP_ACTUATOR_PATH_IDENTIFIER = "/app/actuator/**";
    public static final String ALL_PATH_IDENTIFIER = "/**";
    public static final String SECURITY_EXCLUDED_PATHS = "\"/dist/**\", \"/static/js/**\", \"/app/**\" ";

    public static final String AUTHORITIES_ROLE_USER = "ROLE_USER";

    public static final String ERROR_401_UNAUTHORIZED = "401 UNAUTHORIZED";
    public static final String ERROR_500_INTERNAL_SERVER_ERROR = "500 INTERNAL_SERVER_ERROR";
    public static final String ERROR_400 = "Bad Request";
    public static final String ERROR_404_NOT_FOUND = "Not Found!";
    public static final String ERROR_400_INVALID_ARGUMENT = "400 INVALID_ARGUMENT";
    public static final String ERROR_405_METHOD_NOT_ALLOWED = "405 METHOD_NOT_ALLOWED";
    public static final String ERROR_DATABASE = "Database Error";

}
