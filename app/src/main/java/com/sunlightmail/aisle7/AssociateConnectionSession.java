package com.sunlightmail.aisle7;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Connect to the Staples Associate Connection website.
 * @author Matthew Warner
 * @version 1.0
 *
 */
public class AssociateConnectionSession {
    private static final String LOGIN_URL = "https://associateconnection.staples.com/psp/psext/?cmd=login";
    private Map<String, String> siteMap;
    private String userId;
    private String userPassword;
    private Map<String, String> cookies;
    private boolean isLoggedIn;
    //private String employeeName;

    public AssociateConnectionSession() {
        isLoggedIn = false;
        siteMap = new HashMap<>();

        // As more features are added, more URLs will need to be added. This will make managing many
        // URLs much easier.
        siteMap.put("login", "https://associateconnection.staples.com/psp/psext/?cmd=login");
        siteMap.put("workSchedule", "https://associateconnection.staples.com/psc/psext/EMPLOYEE/HRMS/c/ROLE_EMPLOYEE.SCH_EE_SCHEDULE.GBL");
        siteMap.put("payCheck", "FILL_ME_IN");
    }

    /**
     * Login to Associate Connection.
     * @param userId Employee ID.
     * @param userPassword Employee password for accessing Associate Connection.
     * @throws LoginErrorException
     */
    public void login(String userId, String userPassword) throws LoginErrorException {
        try {
            // Doesn't make sense to set these in constructor if they potentially need to be updated
            // i.e., wrong login information.
            this.userId = userId;
            this.userPassword = userPassword;

            Connection.Response loginPage = Jsoup.connect(siteMap.get("login")).data("timezoneOffset", "300")
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

    /**
     * Generate WorkCalendar using information scraped from Associate Connection.
     */
    private void buildCalendar() throws LoginErrorException {
        if(!isLoggedIn) {
            throw new LoginErrorException("Not logged into Associate Connection.");
        }


    }

    /*public WorkCalendar getMonthView() {
        return null;
    }*/

    /**
     * Get employee schedule for set number of days.
     * @param range Number of days, including today, to get.
     * @return Schedule for set number of days.
     */
    /*public WorkCalendar getRangeView(int range) {
        return null;
    }*/
}
