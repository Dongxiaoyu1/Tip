package com.soft.yozo.myrecode.bean;

import android.content.ContentValues;
import android.database.Cursor;

import com.soft.yozo.myrecode.db.Table;
import com.soft.yozo.myrecode.db.TableColumn;

import org.json.JSONObject;

/**
 * 课程表
 */
@Table(name="subject")
public class Subject extends BaseBean<Subject>
{
    @TableColumn(type = TableColumn.Types.TEXT)
    public String subName;
    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    @Override
    public ContentValues beanToValues() {
        return super.toValues();
    }

    @Override
    public Subject cursorToBean(Cursor cursor)
    {
        this._ID = cursor.getInt(cursor.getColumnIndex("_ID"));
        this.subName = cursor.getString(cursor.getColumnIndex("subName"));
        return this;
    }

    @Override
    public Subject parseJSON(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }
}
