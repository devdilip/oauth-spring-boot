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

    public static final String ERROR_CODE_UNAUTHORIZED = "111";
    public static final String ERROR_MSG_UNAUTHORIZED = "You are not authorized!";

    public static final String ERROR_CODE_PERSISTENCE = "112";
    public static final String ERROR_MSG_PERSISTENCE = "PERSISTENCE!";


    public static final String ERROR_CODE_INVALID_METHOD_ARGUMENT = "113";
    public static final String ERROR_MSG_INVALID_METHOD_ARGUMENT = "INVALID_METHOD_ARGUMENT!";

    public static final String APP_PATH_IDENTIFIER = "/app/**";
    public static final String ACTUATOR_PATH_IDENTIFIER = "/actuator/**";
    public static final String APP_ACTUATOR_PATH_IDENTIFIER = "/app/actuator/**";
    public static final String ALL_PATH_IDENTIFIER = "/**";
    public static final String SECURITY_EXCLUDED_PATHS = "\"/dist/**\", \"/static/js/**\", \"/app/**\" ";

    public static final String AUTHORITIES_ROLE_USER = "ROLE_USER";

}
