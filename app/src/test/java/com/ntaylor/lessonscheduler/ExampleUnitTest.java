package com.ntaylor.lessonscheduler;

import com.ntaylor.lessonscheduler.util.SimpleDate;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void formatSimpleDate() {
        SimpleDate dayMonthYear = new SimpleDate(2018, 5, 17);
        SimpleDate  dayMonth = new SimpleDate(5, 17);
        SimpleDate today = new SimpleDate();
        SimpleDate extendedMonth = new SimpleDate(2018, 17, 17);
        assertEquals("May 17, 2018", dayMonthYear.toString());
        assertEquals("May 17", dayMonth.toString());
        assertEquals("May " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + ", 2018", today.toString());
        assertEquals("May 17, 2019", extendedMonth.toString());
    }

    @Test
    public void leapYearDate(){
        SimpleDate date = new SimpleDate(2020, 2, 28);
        date = date.addOneWeek();
        assertEquals("March 6, 2020", date.toString());
    }

    @Test
    public void parseDate(){
        int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        SimpleDate date = new SimpleDate();
        assertEquals("2018/05/" + today, date.serializeDate());
        assertEquals(date.toString(), SimpleDate.deserializeDate("2018/05/" + today, true).toString());
        assertEquals((new SimpleDate(5, 17)).toString(), SimpleDate.deserializeDate("05/17", false).toString());
    }
}