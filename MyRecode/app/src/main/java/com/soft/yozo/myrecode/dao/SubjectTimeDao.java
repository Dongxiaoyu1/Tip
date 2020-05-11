package com.soft.yozo.myrecode.dao;

import android.database.Cursor;

import com.soft.yozo.myrecode.bean.Subject;
import com.soft.yozo.myrecode.bean.SubjectTime;

import java.util.ArrayList;
import java.util.List;

public class SubjectTimeDao extends BaseDao
{
    public SubjectTimeDao()
    {
        super();
    }

    /**
     * 添加提醒
     * @param subjectTime
     * @return
     */
    public long addSubjectTime(SubjectTime subjectTime)
    {
        return mSQLiteDatabase.insertOrThrow("subjectTime",null,subjectTime.beanToValues());
    }

    /**
     * 得到所有的提醒
     * @return
     */
    public List<SubjectTime> getAllSubjectTime()
    {
        List<SubjectTime> subjects = new ArrayList<>();
        String sql = "select * from subjectTime;";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql,null);
        while (cursor.moveToNext())
        {
            SubjectTime subject = new SubjectTime();
            subject.cursorToBean(cursor);
            subjects.add(subject);
        }
        return subjects;
    }

    /**
     * 得到本周所有的提醒
     * @return
     */
    public List<SubjectTime> getCurWeekSubjectTime()
    {
        List<SubjectTime> subjects = new ArrayList<>();
        String sql = "select * from subjectTime where type = ?";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql,new String[]{"0"});
        while (cursor.moveToNext())
        {
            SubjectTime subject = new SubjectTime();
            subject.cursorToBean(cursor);
            subjects.add(subject);
        }
        return subjects;
    }

    /**
     * 得到今天所有的提醒
     * @return
     */
    public List<SubjectTime> getCurWeekSubjectTime(String date)
    {
        List<SubjectTime> subjects = new ArrayList<>();
        String sql = "select * from subjectTime where date = ?";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql,new String[]{date});
        while (cursor.moveToNext())
        {
            SubjectTime subject = new SubjectTime();
            subject.cursorToBean(cursor);
            subjects.add(subject);
        }
        return subjects;
    }

    /**
     * 根据日期得到课表
     * @param date
     * @return
     */
    public List<SubjectTime> getSubjectTimeByDate(String date)
    {
        List<SubjectTime> subjectTimes = new ArrayList<>();
        String sql = "select * from subjectTime where date = ?";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql,new String[]{date});
        while (cursor.moveToNext())
        {
            SubjectTime subject = new SubjectTime();
            subject.cursorToBean(cursor);
            subjectTimes.add(subject);
        }
        return subjectTimes;
    }



}
