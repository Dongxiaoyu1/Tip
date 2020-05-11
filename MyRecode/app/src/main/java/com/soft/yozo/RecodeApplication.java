package com.soft.yozo;

import android.app.Application;

import com.soft.yozo.myrecode.bean.Recode;
import com.soft.yozo.myrecode.bean.Schooling;
import com.soft.yozo.myrecode.bean.Student;
import com.soft.yozo.myrecode.bean.Subject;
import com.soft.yozo.myrecode.bean.SubjectTime;
import com.soft.yozo.myrecode.db.DBConfig;
import com.soft.yozo.myrecode.db.RecoderDBFactory;


public class RecodeApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        Class<Student> s = Student.class;
        DBConfig config = new DBConfig.Builder().setName("recode")
                .setVersion(1)
                .addTable(Student.class)
                .addTable(Recode.class)
                .addTable(Subject.class)
                .addTable(Schooling.class)
                .addTable(SubjectTime.class)
                .build();
        RecoderDBFactory.init(this,config);
    }
}
