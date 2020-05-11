package com.soft.yozo.myrecode.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.soft.yozo.myrecode.R;

public class BaseActivity extends AppCompatActivity
{
    protected TextView mMore;
    protected View mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(this,0xFFFF9966);
    }

    protected void initActionBar()
    {
//        setStatusBarColor(this,0xFFFF9966);
//        ActionBar bar = getSupportActionBar();
//        bar.setDisplayShowCustomEnabled(true);
//        mView = LayoutInflater.from(this).inflate(R.layout.action_bar_view,null);
//        mMore = mView.findViewById(R.id.action_bar_more);
//        bar.setCustomView(mView);
//        bar.setBackgroundDrawable(new ColorDrawable(0xFFFF9966));
    }

    protected void setMore(String text, View.OnClickListener onClickListener)
    {
//        mMore.setText(text);
//        mMore.setOnClickListener(onClickListener);
    }

    public static void setStatusBarColor(Activity activity, int colorId)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0xFFFF9966);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
        }
    }
}
