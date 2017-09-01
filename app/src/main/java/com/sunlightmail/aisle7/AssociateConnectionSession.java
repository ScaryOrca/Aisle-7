package com.sunlightmail.aisle7;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
    private Map<String, String> siteMap;
    private String userId;
    private String userPassword;
    private Map<String, String> cookies;
    private boolean isLoggedIn;
    //private String employeeName;
    private WorkCalendar currentMonth;

    public AssociateConnectionSession() {
        isLoggedIn = false;
        siteMap = new HashMap<>();

        // Should probably be static final, no reason to set here.
        // As more features are added, more URLs will need to be added. This will make managing many
        // URLs much easier.
        siteMap.put("login", "https://associateconnection.staples.com/psp/psext/?cmd=login");
        // Fun Fact: You can append "?EMPLID=xxxxxxx" to view the schedule of any employee.
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

            // timezoneOffset is set to 300, but may need to be adjusted.
            Connection.Response loginPage = Jsoup.connect(siteMap.get("login")).data("timezoneOffset", "300")
                                            .data("ptmode", "f")
                                            .data("ptlangcd", "ENG")
                                            .data("ptinstalledlang", "GER,UKE,DAN,CFR,ENG,FRA,POR,ZHT,JPN,ZHS,RUS,POL,THA,ITA,KOR,SVE,ESP,DUT,FIN")
                                            .data("userid", userId)
                                            .data("pwd", userPassword)
                                            .data("ptlangsel", "ENG").method(Connection.Method.POST).execute();

            // Cookies are stored for later use.
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
     * Rebuild session from cookies. Useful if you already have cookies, and don't need to login
     * again.
     * @param cookies Cookies to use when rebuilding session.
     */
    public void resumeSession(Map<String, String> cookies) {
        this.cookies = cookies;
        isLoggedIn = true;
        siteMap = new HashMap<>();

        // As more features are added, more URLs will need to be added. This will make managing many
        // URLs much easier.
        siteMap.put("login", "https://associateconnection.staples.com/psp/psext/?cmd=login");
        // Fun Fact: You can append "?EMPLID=xxxxxxx" to view the schedule of any employee.
        siteMap.put("workSchedule", "https://associateconnection.staples.com/psc/psext/EMPLOYEE/HRMS/c/ROLE_EMPLOYEE.SCH_EE_SCHEDULE.GBL");
        siteMap.put("payCheck", "FILL_ME_IN");
        //getMonthView();
    }

    /**
     * Generate WorkCalendar using information scraped from Associate Connection.
     */
    private void buildCalendar() throws LoginErrorException {
        if(!isLoggedIn) {
            throw new LoginErrorException("Not logged into Associate Connection.");
        }

        try {
            currentMonth = new WorkCalendar();
            Document scrapedPage = Jsoup.connect(siteMap.get("workSchedule")).cookies(cookies).get();
            Log.d("!!!!!", "Made it here.");
            Elements scrapedDates = scrapedPage.getElementsByClass("PSGROUPBOX");

            // This is needed because the calendar legend at the bottom of the page is part of the
            // class.
            scrapedDates.remove(scrapedDates.size() - 1);
            for(Element element: scrapedDates) {
                String sDate = element.select(".PACALENDARDAYNUM").first().text();
                String sTime = element.select(".PAADDITIONALINSTRUCTIONS").text().replaceAll("\\s","");
                sTime = sTime.replace("-", " - ");
                if(sTime.isEmpty()) {
                    sTime = "No Data";
                }
                WorkDay newDate = new WorkDay(sDate, sTime, "Monday");
                Log.d("!!!!!", "Made it here.");
                currentMonth.addDate(newDate);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 31; i++) {
            Log.d("!!!!!", currentMonth.getCalendar().get(i).getTime());
        }
    }

    public WorkCalendar getMonthView() {
        if(currentMonth == null) {
            try {
                buildCalendar();
            } catch (LoginErrorException e) {
                e.printStackTrace();
            }
        }
        return currentMonth;
    }

    /**
     * Get employee schedule for set number of days.
     * @param range Number of days, including today, to get.
     * @return Schedule for set number of days.
     */
    /*public WorkCalendar getRangeView(int range) {
        return null;
    }*/

    /**
     * Get current session cookies. Useful if you need to rebuild object, such as when passing to
     * another activity.
     * @return Current session cookies.
     */
    public Map<String, String> getCookies() {
        return cookies;
    }
}
