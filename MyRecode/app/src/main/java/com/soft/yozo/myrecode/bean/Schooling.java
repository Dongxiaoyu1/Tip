package com.soft.yozo.myrecode.bean;

import android.content.ContentValues;
import android.database.Cursor;

import com.soft.yozo.myrecode.db.Table;
import com.soft.yozo.myrecode.db.TableColumn;

import org.json.JSONObject;

/**
 * 课程缴费记录
 */
@Table(name="school")
public class Schooling extends BaseBean<Schooling>
{
    @TableColumn(type = TableColumn.Types.INTEGER)
    public int subid;//课目名id
    @TableColumn(type = TableColumn.Types.INTEGER)
    public int sid;//学生名id
    @TableColumn(type = TableColumn.Types.INTEGER)
    public int scounts;//课程次数
    @TableColumn(type = TableColumn.Types.INTEGER)
    public int sfee;//课程费用
    @TableColumn(type = TableColumn.Types.TEXT)
    public String sdate;//缴费日期

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getScounts() {
        return scounts;
    }

    public void setScounts(int scounts) {
        this.scounts = scounts;
    }

    public int getSfee() {
        return sfee;
    }

    public void setSfee(int sfee) {
        this.sfee = sfee;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    @Override
    public ContentValues beanToValues() {
        return null;
    }

    @Override
    public Schooling cursorToBean(Cursor cursor) {
        this.subid = cursor.getInt(cursor.getColumnIndex("subid"));
        this.sid = cursor.getInt(cursor.getColumnIndex("sid"));
        this.scounts = cursor.getInt(cursor.getColumnIndex("scounts"));
        this.sdate = cursor.getString(cursor.getColumnIndex("sdate"));
        this.sfee = cursor.getInt(cursor.getColumnIndex("sfee"));
        return this;
    }

    @Override
    public Schooling parseJSON(JSONObject jsonObject)
    {
        return null;
    }

    @Override
    public JSONObject toJSON()
    {
        return null;
    }
}
