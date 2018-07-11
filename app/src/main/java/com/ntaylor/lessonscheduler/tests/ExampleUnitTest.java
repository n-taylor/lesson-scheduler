package com.ntaylor.lessonscheduler.tests;

import com.ntaylor.lessonscheduler.util.SimpleDate;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Calendar;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void formatSimpleDate() {
        SimpleDate dayMonthYear = new SimpleDate(2018, 5, 17);
        SimpleDate  dayMonth = new SimpleDate(5, 17);
        SimpleDate today = new SimpleDate();
        SimpleDate extendedMonth = new SimpleDate(2018, 17, 17);
        Assert.assertEquals("May 17, 2018", dayMonthYear.toString());
        Assert.assertEquals("May 17", dayMonth.toString());
        Assert.assertEquals("July " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + ", 2018", today.toString()); // PROBLEM CHILD
        Assert.assertEquals("May 17, 2019", extendedMonth.toString());
    }

    @Test
    public void leapYearDate(){
        SimpleDate date = new SimpleDate(2020, 2, 28);
        date = date.addOneWeek();
        Assert.assertEquals("March 6, 2020", date.toString());
    }

    @Test
    public void parseDate(){
        int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        SimpleDate date = new SimpleDate();
        Assert.assertEquals("2018/07/" + today, date.serializeDate());
        Assert.assertEquals(date.toString(), SimpleDate.deserializeDate("2018/07/" + today, true).toString());
        Assert.assertEquals((new SimpleDate(5, 17)).toString(), SimpleDate.deserializeDate("05/17", false).toString());
    }

    @Test
    public void nextSunday(){
        SimpleDate today = new SimpleDate(2018, 7, 30);
        SimpleDate sunday = today.upcomingSunday();
        Assert.assertEquals("2018/08/05", sunday.serializeDate());
    }
}