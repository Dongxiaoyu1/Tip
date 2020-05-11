package com.soft.yozo.myrecode.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.soft.yozo.myrecode.bean.BaseBean;

public class RecoderDBFactory
{
    private static final String TAG = RecoderDBFactory.class.getSimpleName();

    private DBConfig mConfig;
    private SQLiteDatabase mSQLiteDB;
    private static RecoderDBFactory instance;
    private RecodeOpenHelper mRecodeOpenHelper;
    private final Context mContext;

    private RecoderDBFactory(Context context)
    {
        mContext = context;
    }

    public static void init(Context context, DBConfig dbConfig)
    {
        if(instance == null)
        {
            instance = new RecoderDBFactory(context.getApplicationContext());
            instance.setDBConfig(dbConfig);
        }
    }

    public static RecoderDBFactory getInstance()
    {
        return instance;
    }

    public void setDBConfig(DBConfig dbConfig)
    {
        mConfig = dbConfig;
    }

    public DBConfig getDBConfig()
    {
        return mConfig;
    }

    public SQLiteDatabase open()
    {
        if(mSQLiteDB == null)
        {
            mRecodeOpenHelper = new RecodeOpenHelper(mContext,mConfig.dbName,null,mConfig.dbVersion);
            mSQLiteDB = mRecodeOpenHelper.getWritableDatabase();
        }

        return mSQLiteDB;
    }

    public void close()
    {
        if(mRecodeOpenHelper != null)
        {
            mRecodeOpenHelper.close();
        }
    }

    public void beginTransaction()
    {
        if(mSQLiteDB == null)
        {
            mSQLiteDB.beginTransaction();
        }
    }

    public void endTransaction()
    {
        if(mSQLiteDB == null && mSQLiteDB.inTransaction())
        {
            mSQLiteDB.endTransaction();
        }
    }

    public void setTransactionSuccessful()
    {
        if(mSQLiteDB == null)
        {
            mSQLiteDB.setTransactionSuccessful();
        }
    }


    private final class RecodeOpenHelper extends SQLiteOpenHelper
    {

        public RecodeOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            for(Class<? extends BaseBean<?>> table : mConfig.tableList )
            {
                try {
                    for(String statment : TableUtil.getCreateStatments(table))
                    {
                        Log.d(TAG, statment);
                        db.execSQL(statment);
                    }

                }catch (Throwable e)
                {
                    Log.e(TAG,"Can't create table" + table.getSimpleName());
                }
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG, "onUpgrade: " + oldVersion + ">>"+newVersion);
            for(Class<? extends BaseBean<?>> table : mConfig.tableList)
            {
                try {
                    db.execSQL("DROP TABLE IF EXISTS " + TableUtil.getTableName(table));
                }catch (Throwable e)
                {
                    Log.e(TAG, "Can't create table "+ table.getSimpleName());
                }
            }
            onCreate(db);

        }

        public void cleanTable(String tableName, int maxSize, int batchSize)
        {
            Cursor cursor = mSQLiteDB.rawQuery("select count(_id) from " + tableName, null);
            if(cursor.getCount() != 0 && cursor.moveToFirst() && !cursor.isAfterLast())
            {
                if(cursor.getInt(0) >= maxSize)
                {
                    int deleteSize = maxSize - batchSize;
                    mSQLiteDB.execSQL("delete from " + tableName +" where _id in ("+ "select _id from "+tableName
                    + " order by _id " +" limit "+ deleteSize + " )");
                }
            }
            cursor.close();
        }
    }
}
