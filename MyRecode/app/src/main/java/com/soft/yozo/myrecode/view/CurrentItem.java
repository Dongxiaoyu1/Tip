package com.soft.yozo.myrecode.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.soft.yozo.myrecode.R;

public class CurrentItem extends LinearLayout
{
    private Context mContext;
    private View rootView;
    private TextView mStartTime;
    private TextView mEndTime;
    private TextView mContent;


    public CurrentItem(Context context)
    {
        super(context);
        initView(context,null);
    }

    public CurrentItem(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attributeSet)
    {
        mContext = context;
        rootView = LayoutInflater.from(mContext).inflate(R.layout.item_current_layout,null);
        this.addView(rootView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));

        mStartTime = rootView.findViewById(R.id.current_start_time);
        mEndTime = rootView.findViewById(R.id.current_end_time);
        mContent = rootView.findViewById(R.id.current_content);
    }

    public void setTime(String start, String end)
    {
        mStartTime.setText(start);
        mEndTime.setText(end);
    }

    public void setContent(String content)
    {
        mContent.setText(content);
    }


}
