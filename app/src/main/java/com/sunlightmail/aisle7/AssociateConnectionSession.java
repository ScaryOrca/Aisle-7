package com.sunlightmail.aisle7;

/**
 * Connect to the Staples Associate Connection website.
 * @author Matthew Warner
 * @version 1.0
 *
 */
public class AssociateConnectionSession {
    private String userId;
    private String userPassword;

    public AssociateConnectionSession(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public boolean login() throws LoginErrorException{

    }

}
