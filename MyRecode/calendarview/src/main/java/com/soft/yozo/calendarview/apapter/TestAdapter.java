package com.soft.yozo.calendarview.apapter;


import java.util.ArrayList;

public class TestAdapter implements CalendarAdapter<String>
{
    private ArrayList<String> mDatas;
    public TestAdapter()
    {
        mDatas = new ArrayList<>();
        mDatas.add("01");
        mDatas.add("02");
        mDatas.add("03");
        mDatas.add("04");
        mDatas.add("05");
        mDatas.add("06");
        mDatas.add("07");

    }

    @Override
    public int getItemCounts()
    {
        return mDatas.size();
    }

    @Override
    public String getItem(int index)
    {
        return mDatas.get(index);
    }

}
