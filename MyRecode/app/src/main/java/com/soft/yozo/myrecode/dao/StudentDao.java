package com.soft.yozo.myrecode.dao;

import android.content.ContentValues;
import com.soft.yozo.myrecode.bean.Student;

public class StudentDao extends BaseDao
{

    public StudentDao()
    {
        super();
    }

    public long addStudent(Student stu)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sname",stu.getSname());
        return mSQLiteDatabase.insert("student",null, contentValues);
    }

}
