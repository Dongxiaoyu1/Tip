package com.soft.yozo.myrecode.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.soft.yozo.myrecode.bean.Recode;
import com.soft.yozo.myrecode.bean.Subject;

import java.util.ArrayList;
import java.util.List;

public class RecodeDao extends BaseDao
{
    /**
     * 添加一条记录
     * @param recode
     * @return
     */
    public long addRecode(Recode recode)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sid",recode.getSid());
        contentValues.put("subId",recode.getSubId());
        contentValues.put("sDate",recode.getsDate());
        return mSQLiteDatabase.insert("recode",null, contentValues);
    }

    /**
     * 得到所有的记录
     * @return
     */
    public List<Recode> getAllRecode()
    {
        List<Recode> recodes = new ArrayList<>();
        String sql = "select * from recode";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql,null);
        while (cursor.moveToNext())
        {
            Recode recode = new Recode();
            recode.cursorToBean(cursor);
            recodes.add(recode);
        }
        return recodes;
    }

    /**
     * 得到所有的记录
     * @return
     */
    public List<Recode> getAllRecodeBySubId(int subId)
    {
        List<Recode> recodes = new ArrayList<>();
        String sql = "select * from recode where subId=?";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql,new String[]{subId+""});
        while (cursor.moveToNext())
        {
            Recode recode = new Recode();
            recode.cursorToBean(cursor);
            recodes.add(recode);
        }
        return recodes;
    }


}
