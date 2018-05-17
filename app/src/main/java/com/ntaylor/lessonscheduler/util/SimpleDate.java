package com.ntaylor.lessonscheduler.util;

import java.util.Calendar;

/**
 * Defines dates in a more simple way.
 *
 * Created by Nathan Taylor on 5/15/2018.
 */

public class SimpleDate {

    private int date;
    private int month;
    private int year;

    private boolean noYear = false;

    private int[] daysPerMonth = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public SimpleDate(){
        Calendar cal = Calendar.getInstance();
        this.date = cal.get(Calendar.DAY_OF_MONTH);
        this.month = cal.get(Calendar.MONTH) + 1; // Convert from 0-based month to 1-based month
        this.year = cal.get(Calendar.YEAR);
    }

    public SimpleDate(int date, int month, int year){
        this.date = date;
        this.month = month;
        this.year = year;
        adjustDate();
    }

    public SimpleDate (int date, int month){
        this.date = date;
        this.month = month;
        this.year = -1;
        adjustDate();
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
        return (getMonthString(month) + " " + date ) + ((year == -1) ? "" : ", " + year);
    }

    public SimpleDate addOneWeek(){
        date += 7;
        adjustDate();
        return this;
    }

    private void adjustDate(){
        boolean modified = false;

        // first, account for february: leap year or not?
        daysPerMonth[1] = ((year - 2000) % 4 == 0) ? 29 : 28;

        // then adjust the month. Increment the year if necessary.
        int newMonth = month % 12;
        if (newMonth != 0 && newMonth != month){
            if (year != -1) {
                year += (month - newMonth) / 12;
            }
            month = newMonth;
            modified = true;
        }

        // next, adjust the date. Increment the month if necessary.
        int newDate = date % daysPerMonth[month-1];
        if (newDate != 0 && newDate != date){ // if it's not the last day of the month and the date had to be modified to stay inside
            month += (date - newDate) / daysPerMonth[month-1];
            date = newDate;
            modified = true;
        }

        // if things had to be modified, run adjustDate again to make sure that drastic changes in dates will update the month correctly.
        if (modified)
            adjustDate();
    }

    /**
     * Returns the day of the month, generally 1-31 or 1-30
     * @return
     */
    public int getDate () { return date; }
}
