package com.soft.yozo.myrecode.bean;

import android.content.ContentValues;
import android.database.Cursor;

import com.soft.yozo.myrecode.db.Table;
import com.soft.yozo.myrecode.db.TableColumn;

import org.json.JSONObject;

/**
 * 学生表
 */
@Table(name="student")
public class Student extends BaseBean<Student>
{
    @TableColumn(type = TableColumn.Types.TEXT)
    public String sname;

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Override
    public ContentValues beanToValues() {
        return super.toValues();
    }

    @Override
    public Student cursorToBean(Cursor cursor) {
        return null;
    }

    @Override
    public Student parseJSON(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }
}
