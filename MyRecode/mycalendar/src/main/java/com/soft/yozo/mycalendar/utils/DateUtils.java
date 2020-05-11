package com.soft.yozo.mycalendar.utils;

import java.util.ArrayList;
import java.util.Calendar;

public class DateUtils
{
    /**
     * 得到当月的天数。第一个为星期
     * @param year
     * @param month
     * @return
     */
    public static ArrayList<Integer> getDays(int year, int month)
    {
        ArrayList<Integer> days = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        days.add(week);
        calendar.roll(Calendar.DATE, -1);
        int day = calendar.get(Calendar.DATE);
        for(int i = 1;i <= day;i++)
        {
            days.add(i);
        }
        return days;
    }
}
