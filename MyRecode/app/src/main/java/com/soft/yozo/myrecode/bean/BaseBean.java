package com.soft.yozo.myrecode.bean;

import android.content.ContentValues;
import android.database.Cursor;

import com.soft.yozo.myrecode.db.TableColumn;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;

public abstract class BaseBean<T>  implements Serializable
{
    @TableColumn(type = TableColumn.Types.INTEGER, isPrimary = true)
    public int _ID;


    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public abstract ContentValues beanToValues();

    public ContentValues toValues()
    {
        ContentValues values = new ContentValues();
        try
        {
            Class<?> c = getClass();
            Field[] fields = c.getFields();
            for(Field f: fields)
            {
                f.setAccessible(true);
                final TableColumn tableColumnAnnotation = f.getAnnotation(TableColumn.class);
                if(tableColumnAnnotation != null && !f.getName().equals("_ID"))
                {
                    if(tableColumnAnnotation.type() == TableColumn.Types.INTEGER )
                    {
                        values.put(f.getName(),f.getInt(this));
                    }else if(tableColumnAnnotation.type() == TableColumn.Types.BLOB)
                    {
                        values.put(f.getName(), (byte[])f.get(this));
                    }else if(tableColumnAnnotation.type() == TableColumn.Types.TEXT)
                    {
                        values.put(f.getName(),f.get(this).toString());
                    }else
                    {
                        values.put(f.getName(), f.get(this).toString());
                    }
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return values;
    }

    public abstract T cursorToBean(Cursor cursor);
    /**
     * 将json对象转化位Bean实例
     * @param jsonObject
     * @return
     */
    public abstract T parseJSON(JSONObject jsonObject);

    /**
     * 将Bean实例转化为json对象
     * @return
     */
    public abstract JSONObject toJSON();



}
