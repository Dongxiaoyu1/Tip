package com.soft.yozo.myrecode.bean;

import android.content.ContentValues;
import android.database.Cursor;

import com.soft.yozo.myrecode.db.Table;
import com.soft.yozo.myrecode.db.TableColumn;

import org.json.JSONObject;

@Table(name="subjectTime")
public class SubjectTime extends BaseBean<SubjectTime>
{
    @TableColumn(type = TableColumn.Types.INTEGER)
    public int subId;
    @TableColumn(type = TableColumn.Types.INTEGER)
    public int type;
    @TableColumn(type = TableColumn.Types.TEXT)
    public String startDate;
    @TableColumn(type = TableColumn.Types.TEXT)
    public String endDate;
    @TableColumn(type = TableColumn.Types.TEXT)
    public String date;
    @TableColumn(type = TableColumn.Types.TEXT)
    public String week;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public ContentValues beanToValues() {
        return super.toValues();
    }

    @Override
    public SubjectTime cursorToBean(Cursor cursor) {
        this._ID = cursor.getInt(cursor.getColumnIndex("_ID"));
        this.type = cursor.getInt(cursor.getColumnIndex("type"));
        this.startDate = cursor.getString(cursor.getColumnIndex("startDate"));
        this.endDate = cursor.getString(cursor.getColumnIndex("endDate"));
        this.date = cursor.getString(cursor.getColumnIndex("date"));
        this.week = cursor.getString(cursor.getColumnIndex("week"));
        this.subId = cursor.getInt(cursor.getColumnIndex("subId"));
        return this;
    }

    @Override
    public SubjectTime parseJSON(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }
}
