package com.soft.yozo.myrecode.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.soft.yozo.myrecode.R;
import com.soft.yozo.myrecode.fragment.CurrentFragment;
import com.soft.yozo.myrecode.fragment.HistoryFragment;
import com.soft.yozo.myrecode.fragment.RecodeFragment;
import com.soft.yozo.myrecode.fragment.SubjectFragment;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends BaseActivity
{
    private TabLayout mTabLayout;
    private List<Fragment> mItemViews;
    private ViewPager mViewPager;
    private MyAdapter mMyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
//        initActionBar();
//        setMore("添加提醒", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FirstActivity.this,AddTipActivity.class);
//                FirstActivity.this.startActivity(intent);
//
//            }
//        });
        initView();
        initData();
    }

    private void initView()
    {
        mTabLayout = findViewById(R.id.first_tab);
        mViewPager = findViewById(R.id.first_viewpager);
    }

    private void initData()
    {
        mTabLayout.addTab(mTabLayout.newTab().setText("日程"));
        mTabLayout.addTab(mTabLayout.newTab().setText("打卡"));
        mTabLayout.addTab(mTabLayout.newTab().setText("课程"));
        mTabLayout.addTab(mTabLayout.newTab().setText("记录"));

        mItemViews = new ArrayList<>();
        CurrentFragment currentFragment = new CurrentFragment();
        RecodeFragment recodeFragment = new RecodeFragment();
        SubjectFragment subjectFragment = new SubjectFragment();
        HistoryFragment historyFragment = new HistoryFragment();
        mItemViews.add(currentFragment);
        mItemViews.add(recodeFragment);
        mItemViews.add(subjectFragment);
        mItemViews.add(historyFragment);

        mMyAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMyAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private class MyAdapter extends FragmentPagerAdapter
    {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mItemViews.get(position);
        }

        @Override
        public int getCount() {
            return mItemViews.size();
        }
    }
}
