package com.sunlightmail.aisle7;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

/**
 * Connect to the Staples Associate Connection website.
 * @author Matthew Warner
 * @version 1.0
 *
 */
public class AssociateConnectionSession {
    private static final String LOGIN_URL = "https://associateconnection.staples.com/psp/psext/?cmd=login";
    private String userId;
    private String userPassword;
    private Map<String, String> cookies;
    private boolean isLoggedIn;

    public AssociateConnectionSession() {
        isLoggedIn = false;
    }

    /**
     * Login to Associate Connection.
     * @param userId Employee ID.
     * @param userPassword Employee password for accessing Associate Connection.
     * @throws LoginErrorException
     */
    public void login(String userId, String userPassword) throws LoginErrorException {
        try {
            // Doesn't make sense to set these in constructor if they potentially need to be updated.
            this.userId = userId;
            this.userPassword = userPassword;

            Connection.Response loginPage = Jsoup.connect(LOGIN_URL).data("timezoneOffset", "300")
                                            .data("ptmode", "f")
                                            .data("ptlangcd", "ENG")
                                            .data("ptinstalledlang", "GER,UKE,DAN,CFR,ENG,FRA,POR,ZHT,JPN,ZHS,RUS,POL,THA,ITA,KOR,SVE,ESP,DUT,FIN")
                                            .data("userid", userId)
                                            .data("pwd", userPassword)
                                            .data("ptlangsel", "ENG").method(Connection.Method.POST).execute();
            cookies = loginPage.cookies();

            // Verify login was successful.
            if(cookies.get("PS_TOKEN").equals("")) {
                isLoggedIn = false;
                throw new LoginErrorException("Employee ID/Password is wrong.");
            }
            isLoggedIn = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getEmployeeName() {
        return "Hola";
    }

}
