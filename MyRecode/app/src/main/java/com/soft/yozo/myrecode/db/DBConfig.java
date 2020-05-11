package com.soft.yozo.myrecode.db;

import com.soft.yozo.myrecode.bean.BaseBean;

import java.util.ArrayList;

public class DBConfig
{
    final ArrayList<Class<? extends BaseBean<?>>> tableList;
    final String dbName;
    final int dbVersion;
    final ArrayList<String> tableNameList;

    private DBConfig(final Builder builder)
    {
        tableList = builder.tableList;
        dbName = builder.dbName;
        dbVersion = builder.dbVersion;
        tableNameList = new ArrayList<>();
        for(Class<? extends  BaseBean<?>> c: tableList)
        {
            String name=  TableUtil.getTableName(c);
            tableNameList.add(name);
        }
    }


    public static class Builder
    {
        private ArrayList<Class<? extends BaseBean<?>>> tableList;

        private String dbName;

        private int dbVersion;

        public Builder()
        {
            tableList = new ArrayList<>();
        }

        public Builder setName(String name)
        {
            dbName = name;
            return this;
        }

        public Builder setVersion(int version)
        {
            dbVersion = version;
            return this;
        }

        public Builder addTable(Class<? extends BaseBean<?>> table)
        {
            tableList.add(table);
            return this;
        }

        public DBConfig build()
        {
            return new DBConfig(this);
        }
    }
}





























