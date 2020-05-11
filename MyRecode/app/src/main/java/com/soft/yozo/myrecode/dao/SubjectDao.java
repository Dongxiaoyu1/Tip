package com.soft.yozo.myrecode.dao;

import android.database.Cursor;

import com.soft.yozo.myrecode.bean.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectDao extends BaseDao
{
    public SubjectDao()
    {
        super();
    }

    /**
     * 添加一个科目
     * @param subject
     * @return
     */
    public long addSubject(Subject subject)
    {
//        mSQLiteDatabase.insertOrThrow()
        return mSQLiteDatabase.insertOrThrow("subject",null,subject.beanToValues());
    }

    /**
     * 得到所有的课题
     * @return
     */
    public List<Subject> getAllSubject()
    {
        List<Subject> subjects = new ArrayList<>();
        String sql = "select * from subject;";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql,null);
        while (cursor.moveToNext())
        {
            Subject subject = new Subject();
            subject.cursorToBean(cursor);
            subjects.add(subject);
        }
        return subjects;
    }

    /**
     * 得到所有的课题
     * @return
     */
    public Subject getSubjectById(int subId)
    {
        Subject subject = null;
        String sql = "select * from subject where _ID=? ";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql,new String[]{subId+""});

        if (cursor.moveToNext())
        {
            subject = new Subject();
            subject.cursorToBean(cursor);
        }
        return subject;
    }
}
