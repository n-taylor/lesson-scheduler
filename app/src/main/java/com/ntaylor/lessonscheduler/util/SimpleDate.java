package com.ntaylor.lessonscheduler.util;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

/**
 * A wrapper class of Calendar that defines dates in a more simple way.
 *
 * Created by Nathan Taylor on 5/15/2018.
 */

public class SimpleDate {

    private boolean noYear = false;
    private Calendar calendar;

    public SimpleDate(){
        calendar = Calendar.getInstance();
    }

    public SimpleDate(int year, int month, int date){
        calendar = Calendar.getInstance();
        calendar.set(year, month-1, date);
    }

    public SimpleDate (int month, int date){
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
        return new SimpleDate(getYear(), getMonth(), getDate()+7);
    }

    /**
     * Returns the day of the month, generally 1-31 or 1-30
     */
    public int getDate () { return calendar.get(Calendar.DAY_OF_MONTH); }

    /**
     * Returns the month of the year, 1-12
     */
    public int getMonth() { return calendar.get(Calendar.MONTH) + 1; }

    /**
     * Returns the year
     */
    public int getYear() { return calendar.get(Calendar.YEAR); }

    /**
     * Formats the date in YYYY/MM/DD or MM/DD format for use with databases.
     */
    public String serializeDate(){
        String day = String.format(Locale.US, "%02d", getDate());
        String month = String.format(Locale.US, "%02d", getMonth());
        if (!noYear) {
            String year = String.format(Locale.US, "%04d", getYear());
            return year + "/" + month + "/" + day;
        }
        else
            return month + "/" + day;
    }

    /**
     * Given a string date in the format YYYY/MM/DD (with year) or MM/DD (not with year),
     * returns a SimpleDate with the corresponding values.
     */
    public static SimpleDate deserializeDate(String date, boolean withYear){
        String[] units = date.split("/");
        if (withYear)
            return new SimpleDate(Integer.parseInt(units[0]), Integer.parseInt(units[1]), Integer.parseInt(units[2]));
        else
            return new SimpleDate(Integer.parseInt(units[0]), Integer.parseInt(units[1]));
    }

    /**
     * If the current date is a Sunday, returns the current date. Otherwise, returns the SimpleDate of
     * the next date that is a Sunday.
     */
    public SimpleDate upcomingSunday(){
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        // If today is Sunday, return a SimpleDate with today's info
        if (weekday == Calendar.SUNDAY){
            return new SimpleDate();
        }
        else {
            // Add on the necessary days to get the next Sunday
            int offset = 8 - weekday;
            return new SimpleDate(getYear(), getMonth(), getDate()+offset);
        }
    }
}
