package com.soft.yozo.myrecode.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.soft.yozo.mycalendar.view.CalendarView;
import com.soft.yozo.myrecode.R;

import java.util.Calendar;

public class TestActivity extends AppCompatActivity
{
    private CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        calendarView = findViewById(R.id.calendar_view);
        calendarView.addItemClick(new CalendarView.CalendarItemClick() {
            @Override
            public void onItemClick(Calendar calendar) {
                Toast.makeText(TestActivity.this,calendar.get(Calendar.YEAR)+"年"+calendar.get(Calendar.MONTH)+"月"+ calendar.get(Calendar.DATE)+"日",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
