package com.soft.yozo.myrecode.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.soft.yozo.myrecode.R;

public abstract  class BasepopupWindows extends PopupWindow
{
    protected Context mContext;
    protected FrameLayout mContainer;
    private View top;
    private View bottom;
    private TextView mTitle;
    private View mView;
    public  BasepopupWindows(Context context)
    {
        mContext = context;
        initView();
        initData();
        setContentView();

        setTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.blue)));
    }
    private void initView()
    {
        mView = LayoutInflater.from(mContext).inflate(R.layout.popup_base, null);
        mContainer = mView.findViewById(R.id.popup_base_container);
        top = mView.findViewById(R.id.popup_base_top);
        bottom = mView.findViewById(R.id.popup_base_bottom);
        mTitle = mView.findViewById(R.id.popup_base_title);
        setContentView(mView);
    }
    private void initData()
    {
        if(isShowTitle())
        {
            top.setVisibility(View.VISIBLE);
            mTitle.setText(getTitle());
        }else
        {
            top.setVisibility(View.INVISIBLE);
        }
        if(isShowBottom())
        {
            bottom.setVisibility(View.VISIBLE);
        }else
        {
            bottom.setVisibility(View.INVISIBLE);
        }
    }

    abstract void setContentView();
    abstract boolean isShowTitle();
    abstract boolean isShowBottom();
    abstract String getTitle();
    protected void showAsDrop(View view)
    {
        this.showAsDrop(view);
    }
}
