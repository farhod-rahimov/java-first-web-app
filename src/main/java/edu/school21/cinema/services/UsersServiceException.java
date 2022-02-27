package edu.school21.cinema.services;

public class UsersServiceException extends RuntimeException {

    public UsersServiceException(UsersServiceExceptionEnum e) {
        super(getExceptionMsg(e));
    }

    private static String getExceptionMsg(UsersServiceExceptionEnum e) {
        switch (e) {
            case WRONG_INPUT_DATA:
                return  "Error. A new user cannot be created, because some of input data is not complete.\n" +
                        "Notice following restrictions:\n" +
                        "- password should be minimum 8 characters in length and contain at least one character in UPPER case and ine in LOWER case\n" +
                        "- firstname, lastname should be maximum 70 characters in length, phone number - 20, password - 80";
            case USER_SAVE_ERROR:
                return "Error. A new user cannot be created. Please try again";
            case JDBC_NOT_FOUND:
                return "Error. Driver \"org.postgresql.Driver\" not found";
            case AUTH_INFO_SAVE_ERROR:
                return "Error. Cannot save user authentication info";
            case IMAGE_SAVE_ERROR:
                return "Error. Cannot save image";
            case IMAGE_SEND_ERROR:
                return "Error. Cannot send image";
        }
        return "Error. Something went wrong while executing the operation. Please check your input data and try again";
    }
}
