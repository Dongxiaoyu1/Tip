package com.soft.yozo.myrecode.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.soft.yozo.myrecode.R;
import com.soft.yozo.myrecode.bean.Subject;
import com.soft.yozo.myrecode.bean.SubjectTime;
import com.soft.yozo.myrecode.dao.SubjectDao;
import com.soft.yozo.myrecode.dao.SubjectTimeDao;
import com.soft.yozo.myrecode.popup.DatePickerPopup;
import com.soft.yozo.myrecode.popup.SubjectPop;
import com.soft.yozo.utils.DateUtils;

import java.util.Calendar;
import java.util.List;

public class AddTipActivity extends BaseActivity implements View.OnClickListener
{

    private SubjectDao subjectDao;
    private List<Subject> subjects;
    private Subject mCurSubject;
    private int type = 1;
    private EditText mSubject;
    private EditText mStart;
    private EditText mEnd;
    private EditText mDate;
    private TextView mAdd;


    private SubjectTimeDao mSubjectTimeDao;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tip);
//        initActionBar();
        initView();
        initData();
    }

    private void initView()
    {
        mSubject = findViewById(R.id.add_cursor);
        mStart = findViewById(R.id.add_start);
        mEnd = findViewById(R.id.add_end);
        mDate = findViewById(R.id.add_date);
        mAdd = findViewById(R.id.tip_add);
        mAdd.setOnClickListener(this);
        mSubject.setOnClickListener(this);
    }

    private void initData()
    {
        mSubjectTimeDao = new SubjectTimeDao();
        subjectDao = new SubjectDao();
        subjects = subjectDao.getAllSubject();
        if(subjects.size() > 0)
        {
            mCurSubject = subjects.get(0);
        }

    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if(id == R.id.tip_add)
        {
            SubjectTime subjectTime = new SubjectTime();
            subjectTime.setSubId(mCurSubject._ID);
            subjectTime.setType(type);
            subjectTime.setStartDate(mStart.getText().toString());
            subjectTime.setEndDate(mEnd.getText().toString());
            if(type == 0)
            {
                subjectTime.setDate("nothing");
//                    subjectTime.setWeek(mEditTime.getText().toString());
            }else
            {
                subjectTime.setWeek("nothing");
                    subjectTime.setDate(mDate.getText().toString());
            }
            mSubjectTimeDao.addSubjectTime(subjectTime);
            AddTipActivity.this.finish();
        }else if(id == R.id.add_cursor)
        {
            SubjectPop pop = new SubjectPop(this,subjects);
            pop.setSubjectCallback(new SubjectPop.SubjectCallback(){

                @Override
                public void onSubjectCallback(Subject subject) {
                    mCurSubject = subject;
                    mSubject.setText(mCurSubject.subName);
                }
            });
            pop.setHeight(300);
            pop.setWidth(200);
            pop.showAsDropDown(mSubject);
        }

    }
}
