package com.ntaylor.lessonscheduler.util;

import java.util.Calendar;

/**
 * Defines dates in a more simple way.
 *
 * Created by Nathan Taylor on 5/15/2018.
 */

public class SimpleDate {

    private boolean noYear = false;
    private Calendar calendar;

    public SimpleDate(){
        calendar = Calendar.getInstance();
    }

    public SimpleDate(int date, int month, int year){
        calendar = Calendar.getInstance();
        calendar.set(year, month-1, date);
    }

    public SimpleDate (int date, int month){
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, date);
        calendar.set(Calendar.MONTH, month-1);
        noYear = true;
    }

    /**
     * Gets the name of a given month (e.g. 3 -> March)
     * @param month The numerical value of a month (1-12)
     * @return null if month > 12 or month < 1
     */
    private String getMonthString(int month){
        switch(month){
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return null;
        }
    }

    /**
     * Gets the abbreviated name of a given month (e.g. 3 -> Mar)
     * @param month The numerical value of a month (1-12)
     * @return null if month > 12 or month < 1
     */
    private String getMonthAbbreviation(int month){
        return (month <= 12 && month >= 1) ? getMonthString(month).substring(0, 3) : null;
    }

    @Override
    public String toString(){
        return (getMonthString(getMonth()) + " " + getDate() ) + ((noYear) ? "" : ", " + getYear());
    }

    public SimpleDate addOneWeek(){
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        return this;
    }

    /**
     * Returns the day of the month, generally 1-31 or 1-30
     * @return
     */
    public int getDate () { return calendar.get(Calendar.DAY_OF_MONTH); }

    public int getMonth() { return calendar.get(Calendar.MONTH) + 1; }

    public int getYear() { return calendar.get(Calendar.YEAR); }
}
