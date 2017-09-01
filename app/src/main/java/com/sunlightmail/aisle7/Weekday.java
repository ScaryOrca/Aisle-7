package com.sunlightmail.aisle7;

/**
 * Created by matthew on 9/1/17.
 */

public enum Weekday {
    SUN,
    MON,
    TUE,
    WED,
    THU,
    FRI,
    SAT {
        @Override
        public Weekday next() {
            return values()[0];
        }
    };

    public Weekday next() {
        return values()[ordinal() + 1];
    }
}

