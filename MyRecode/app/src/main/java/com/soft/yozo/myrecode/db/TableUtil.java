package com.soft.yozo.myrecode.db;

import android.text.TextUtils;

import com.soft.yozo.myrecode.bean.BaseBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableUtil
{
    public static String getTableName(Class<? extends BaseBean<?>> c)
    {
        String name = null;
        Table tableNameAnnotation = c.getAnnotation(Table.class);
        if(tableNameAnnotation != null)
        {
            name = tableNameAnnotation.name();
        }
        if(TextUtils.isEmpty(name))
        {
            name = c.getSimpleName();
        }

        return name;
    }

    public final static List<String> getCreateStatments(Class<? extends BaseBean<?>> c)
    {
        final List<String> createStatments = new ArrayList<String>();
        final List<String> indexStatments = new ArrayList<String>();
        final StringBuilder builder = new StringBuilder();
        final String tableName = getTableName(c);
        builder.append("CREATE TABLE ");
        builder.append(tableName);
        builder.append(" (");
        int columnNum = 0;
        for(final Field f : c.getFields())
        {
            f.setAccessible(true);
            final TableColumn tableColumnAnnotation = f.getAnnotation(TableColumn.class);
            if(tableColumnAnnotation != null)
            {
                columnNum++;
                String columnName = f.getName();
                builder.append(columnName);
                builder.append(" ");
                if(tableColumnAnnotation.type() == TableColumn.Types.INTEGER)
                {
                    builder.append(" INTEGER");
                }else if(tableColumnAnnotation.type() == TableColumn.Types.BLOB)
                {
                    builder.append(" BLOB");
                }else if(tableColumnAnnotation.type() == TableColumn.Types.TEXT)
                {
                    builder.append(" TEXT");
                }else
                {
                    builder.append(" DATETIME");
                }

                if(tableColumnAnnotation.isPrimary())
                {
                    builder.append(" PRIMARY KEY");
                }else
                {
                    if(tableColumnAnnotation.isNotNull())
                    {
                        builder.append(" NOT NULL");
                    }
                    if(tableColumnAnnotation.isUnique())
                    {
                        builder.append(" UNIQUE");
                    }
                }
                if(tableColumnAnnotation.isIndex())
                {
                    indexStatments.add("CREATE INDEX idx_" + columnName + "_"+tableName+" ON "+tableName +"("+columnName+");");
                }
                builder.append(", ");
            }
        }
        builder.setLength(builder.length() - 2);
        builder.append(");");
        if(columnNum > 0)
        {
            createStatments.add(builder.toString());
            createStatments.addAll(indexStatments);
        }

        return createStatments;
    }
}

























