package com.sunlightmail.aisle7;

/**
 * Created by matthew on 8/31/17.
 */

public class ListDate {
    private String weekDay;
    private String time;
    private String date;

    public ListDate(String weekDay, String time, String date) {
        this.weekDay = weekDay;
        this.time = time;
        this.date = date;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }
}
