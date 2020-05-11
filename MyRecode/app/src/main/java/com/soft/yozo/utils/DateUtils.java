package com.soft.yozo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateUtils
{
    /**
     * 得到当前星期的日期
     * @return
     */
    public static List<Date> getWeeks()
    {
        List<Date> weeks = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int dayofWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if(dayofWeek == 1)
        {
            dayofWeek = 8;
        }
        calendar.add(Calendar.DATE,calendar.getFirstDayOfWeek() - dayofWeek);
        weeks.add(calendar.getTime());
        calendar.add(Calendar.DATE,1);
        weeks.add(calendar.getTime());
        calendar.add(Calendar.DATE,1);
        weeks.add(calendar.getTime());
        calendar.add(Calendar.DATE,1);
        weeks.add(calendar.getTime());
        calendar.add(Calendar.DATE,1);
        weeks.add(calendar.getTime());
        calendar.add(Calendar.DATE,1);
        weeks.add(calendar.getTime());
        calendar.add(Calendar.DATE,1);
        weeks.add(calendar.getTime());
        return weeks;
    }

    public static List<String> getDay(List<Date> dates)
    {
        List<String> days = new ArrayList<>();
        for(Date date:dates)
        {
            DateFormat dateFormat = new SimpleDateFormat("MM-dd");
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            String result = calendar.get(Calendar.MONTH)+1+"-"+calendar.get(Calendar.DAY_OF_MONTH);
            days.add(dateFormat.format(date));

        }
        return days;
    }

    public static Map<Integer,String> getDayAndWeek(List<Date> dates)
    {
        Map<Integer, String> values = new HashMap<>();
        int i =0;
        for(Date date:dates)
        {
            DateFormat dateFormat = new SimpleDateFormat("MM-dd");
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            String result = calendar.get(Calendar.MONTH)+1+"-"+calendar.get(Calendar.DAY_OF_MONTH);
            i++;
            values.put(i,dateFormat.format(date));

        }
        return values;
    }


    /**
     * 得到当前的年月日
     * @return
     */
    public static String getCurrentDate()
    {
        String result="";
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        result=year+"-"+month+"-"+day;
        return result;

    }

    public static int getCurDay()
    {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day;
//        String week ="星期一";
//        if(day == 1)
//        {
//            week = "星期日";
//        }else if(day == 2)
//        {
//            week = "星期一";
//        }else if(day == 3)
//        {
//            week = "星期二";
//
//        }else if(day == 4)
//        {
//            week = "星期三";
//
//        }else if(day == 5)
//        {
//            week = "星期四";
//
//        }else if(day == 6)
//        {
//            week = "星期五";
//
//        }else if(day == 7)
//        {
//            week = "星期六";
//
//        }
//        return week;
    }

    /**
     * 得到当前的年月日
     * @return
     */
    public static String getCurrentDate(String date)
    {
        String result="";
        Date d = new Date();
        long time = Long.valueOf(date);
        d.setTime(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        result=year+"-"+month+"-"+day;
        return result;

    }

    /**
     * 得到当前的年月日
     * @return
     */
    public static String getDate(Calendar calendar)
    {
        String result="";
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String m = month+"";
        String d1 = day +"";
        if(month <= 9)
        {
            m= "0"+month;
        }
        if(day <=9 )
        {
            d1 = "0"+day;
        }
        result=year+"-"+m+"-"+d1;
        return result;

    }

    /**
     * 得到当前的年月日
     * @return
     */
    public static String getCurrentDate(Calendar calendar)
    {
        String result="";
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int w = calendar.get(Calendar.DAY_OF_WEEK);
        String week ="星期一";
        if(w == 1)
        {
            week = "星期日";
        }else if(w == 2)
        {
            week = "星期一";
        }else if(w == 3)
        {
            week = "星期二";

        }else if(w == 4)
        {
            week = "星期三";

        }else if(w == 5)
        {
            week = "星期四";

        }else if(w == 6)
        {
            week = "星期五";

        }else if(w == 7)
        {
            week = "星期六";

        }
        result=year+"-"+month+"-"+day+"   "+week;
        return result;

    }


}
