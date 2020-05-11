package com.soft.yozo.calendarview.apapter;

public interface CalendarAdapter<T>
{
    int getItemCounts();
    T getItem(int position);

}
