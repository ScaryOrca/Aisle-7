package com.sunlightmail.aisle7;

/**
 * Created by matthew on 8/29/17.
 */

public class WorkDay {
    private String date;
    private String time;
    private String weekDay;

    public WorkDay(String date, String time, String weekDay) {
        this.date = date;
        this.time = time;
        this.weekDay = weekDay;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getWeekDay() {
        return weekDay;
    }
}
