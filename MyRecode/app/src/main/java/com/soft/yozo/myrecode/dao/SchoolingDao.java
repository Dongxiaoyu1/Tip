package com.soft.yozo.myrecode.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.soft.yozo.myrecode.bean.Schooling;

import java.util.ArrayList;
import java.util.List;

public class SchoolingDao extends BaseDao
{
    //添加一个缴费记录
    public long addSchooling(Schooling schooling)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("subid",schooling.getSubid());
        contentValues.put("sid",schooling.getSid());
        contentValues.put("scounts",schooling.getScounts());
        contentValues.put("sfee", schooling.getSfee());
        contentValues.put("sdate",schooling.getSdate());
        return mSQLiteDatabase.insert("school",null, contentValues);
    }

    /**
     *
     * @param subId
     * @return
     */
    public List<Schooling> getSchoolingBySubId(int subId)
    {
        List<Schooling> schools = new ArrayList<>();
        String sql = "select * from school where subid = ?";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql,new String[]{subId+""});
        while (cursor.moveToNext())
        {
            Schooling schooling = new Schooling();
            schooling.cursorToBean(cursor);
            schools.add(schooling);
        }
        return schools;
    }

    /**
     *
     *
     * @return
     */
    public List<Schooling> getSchooling()
    {
        List<Schooling> schools = new ArrayList<>();
        String sql = "select * from school";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql,null);
        while (cursor.moveToNext())
        {
            Schooling schooling = new Schooling();
            schooling.cursorToBean(cursor);
            schools.add(schooling);
        }
        return schools;
    }
}
