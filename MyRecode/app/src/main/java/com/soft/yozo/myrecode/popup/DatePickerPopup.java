package com.soft.yozo.myrecode.popup;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.soft.yozo.myrecode.R;

public class DatePickerPopup extends BasepopupWindows
{
    private View mView;
    private DatePicker mDatePicker;
    private ChangeCallback mChangeCallback;
    public DatePickerPopup(Context context)
    {
        super(context);
    }

    @Override
    void setContentView()
    {
        mView = LayoutInflater.from(mContext).inflate(R.layout.popup_datepicker,null);
        mContainer.addView(mView);
        mDatePicker = mView.findViewById(R.id.popup_date_picker);
        mDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener()
        {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                mChangeCallback.onCallBack(year,monthOfYear,dayOfMonth);
            }
        });
    }

    public void setmChangeCallback(ChangeCallback changeCallback)
    {
        this.mChangeCallback = changeCallback;
    }

    @Override
    boolean isShowTitle() {
        return false;
    }

    @Override
    boolean isShowBottom() {
        return true;
    }

    @Override
    String getTitle() {
        return null;
    }

    public interface ChangeCallback{
        void onCallBack(int year, int monthOfYear, int dayOfMonth);
    }
}
