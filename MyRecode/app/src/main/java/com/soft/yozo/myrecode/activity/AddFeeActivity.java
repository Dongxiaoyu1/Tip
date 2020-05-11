package com.soft.yozo.myrecode.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.soft.yozo.myrecode.R;
import com.soft.yozo.myrecode.bean.Schooling;
import com.soft.yozo.myrecode.bean.Subject;
import com.soft.yozo.myrecode.dao.SchoolingDao;
import com.soft.yozo.myrecode.dao.SubjectDao;
import com.soft.yozo.myrecode.popup.DatePickerPopup;
import com.soft.yozo.myrecode.popup.SubjectPop;
import com.soft.yozo.utils.DateUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddFeeActivity extends BaseActivity implements View.OnClickListener
{
    private TextView name;
    private EditText fee;
    private EditText counts;
    private TextView date;
    private Button cancel;
    private Button ok;

    private SubjectDao subjectDao;
    private List<Subject> subjects;
    private long mydate;
    private SchoolingDao schoolingDao;
    private Subject mCurSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fee);
        initActionBar();
        initView();
        initdata();
    }

    private void initView()
    {
        name = findViewById(R.id.add_fee_cursor_name_et);
        fee = findViewById(R.id.add_fee_fee_name_et);
        counts = findViewById(R.id.add_fee_count_name_et);
        date = findViewById(R.id.add_fee_date_et);

        cancel = findViewById(R.id.button_cancel);
        ok = findViewById(R.id.button_ok);
    }

    private void initdata()
    {
        mydate = Calendar.getInstance().getTime().getTime();
        schoolingDao = new SchoolingDao();
        subjectDao = new SubjectDao();
        subjects = subjectDao.getAllSubject();
        if(subjects.size() > 0)
        {
            mCurSubject = subjects.get(0);
            name.setText(mCurSubject.subName);
        }
        date.setText(DateUtils.getCurrentDate());
        name.setOnClickListener(this);
        date.setOnClickListener(this);
        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if(id == R.id.button_ok)
        {
            //添加一条记录
            String subName = name.getText().toString();
            String subFee = fee.getText().toString();
            String subCounts = counts.getText().toString();
            Schooling schooling = new Schooling();
            schooling.sfee = Integer.valueOf(subFee);
            schooling.sdate = mydate+"";
            schooling.scounts = Integer.valueOf(subCounts);
            schooling.subid = mCurSubject._ID;
            schooling.sid = 1;
            schoolingDao.addSchooling(schooling);
            this.finish();

        }else if(id == R.id.button_cancel)
        {
            //将所有数据清空
            this.finish();

        }else if(id == R.id.add_fee_cursor_name_et)
        {
            SubjectPop pop = new SubjectPop(this,subjects);
            pop.setSubjectCallback(new SubjectPop.SubjectCallback(){

                @Override
                public void onSubjectCallback(Subject subject) {
                    mCurSubject = subject;
                    name.setText(mCurSubject.subName);
                }
            });
            pop.setHeight(300);
            pop.setWidth(200);
            pop.showAsDropDown(name);
        }else if(id == R.id.add_fee_date_et)
        {
            DatePickerPopup datePickerPopup = new DatePickerPopup(this);
            DisplayMetrics outMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
            datePickerPopup.setHeight(outMetrics.heightPixels*2/3);
            datePickerPopup.setWidth(outMetrics.widthPixels*2/3);
            datePickerPopup.setmChangeCallback(new DatePickerPopup.ChangeCallback() {
                @Override
                public void onCallBack(int year, int monthOfYear, int dayOfMonth) {
                    String result = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR,year);
                    calendar.set(Calendar.MONTH,monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    mydate = calendar.getTime().getTime();
                    date.setText(result);
                }
            });
            datePickerPopup.showAtLocation(date, Gravity.CENTER,0,0);
        }

    }
}
