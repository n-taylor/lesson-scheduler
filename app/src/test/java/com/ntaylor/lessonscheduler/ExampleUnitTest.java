package com.ntaylor.lessonscheduler;

import com.ntaylor.lessonscheduler.util.SimpleDate;

import org.junit.Test;

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
        SimpleDate dayMonthYear = new SimpleDate(17, 5, 2018);
        SimpleDate  dayMonth = new SimpleDate(17, 5);
        SimpleDate today = new SimpleDate();
        SimpleDate extendedMonth = new SimpleDate(17, 17, 2018);
        assertEquals("May 17, 2018", dayMonthYear.toString());
        assertEquals("May 17", dayMonth.toString());
        assertEquals("May 17, 2018", today.toString());
        assertEquals("May 17, 2019", extendedMonth.toString());
    }

    @Test
    public void leapYearDate(){
        SimpleDate date = new SimpleDate(28, 2, 2020);
        date.addOneWeek();
        assertEquals("March 6, 2020", date.toString());
    }
}