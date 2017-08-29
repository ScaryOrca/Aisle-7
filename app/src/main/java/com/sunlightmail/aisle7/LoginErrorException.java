package com.sunlightmail.aisle7;

/**
 * Exception for login errors to Associate Connection
 * @author Matthew Warner
 * @version 1.0
 */

public class LoginErrorException extends Exception {

    public LoginErrorException(String message) {
        super(message);
    }
}
