package com.soft.yozo.myrecode.dao;

import android.database.sqlite.SQLiteDatabase;

import com.soft.yozo.myrecode.db.RecoderDBFactory;

public class BaseDao
{
    protected SQLiteDatabase mSQLiteDatabase;
    protected RecoderDBFactory mRecoderDBFactory;

    public BaseDao()
    {
        mRecoderDBFactory = RecoderDBFactory.getInstance();
        mSQLiteDatabase = mRecoderDBFactory.open();
    }
}
