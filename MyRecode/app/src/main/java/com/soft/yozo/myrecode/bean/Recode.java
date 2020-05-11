package com.soft.yozo.myrecode.bean;

import android.content.ContentValues;
import android.database.Cursor;

import com.soft.yozo.myrecode.db.Table;
import com.soft.yozo.myrecode.db.TableColumn;

import org.json.JSONObject;

/**
 * 打卡记录表
 */
@Table(name="recode")
public class Recode extends BaseBean<Recode>
{
    @TableColumn(type = TableColumn.Types.INTEGER)
    public int subId;
    @TableColumn(type = TableColumn.Types.INTEGER)
    public int sid;
    @TableColumn(type = TableColumn.Types.INTEGER)
    public long sDate;

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public long getsDate() {
        return sDate;
    }

    public void setsDate(long sDate) {
        this.sDate = sDate;
    }

    @Override
    public ContentValues beanToValues() {
        return null;
    }

    @Override
    public Recode cursorToBean(Cursor cursor) {
        return null;
    }

    @Override
    public Recode parseJSON(JSONObject jsonObject)
    {
        return null;
    }

    @Override
    public JSONObject toJSON()
    {
        return null;
    }
}
