package com.sunlightmail.aisle7;

import java.util.ArrayList;

/**
 * Created by matthew on 8/29/17.
 */

public class WorkCalendar {
    private ArrayList<WorkDay> calendar;

    public WorkCalendar() {

    }

    public void addDate(WorkDay date) {
        // Assume dates are added in order.
        calendar.add(date);
    }
}
