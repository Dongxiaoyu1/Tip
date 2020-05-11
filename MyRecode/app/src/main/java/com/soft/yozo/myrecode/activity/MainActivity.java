package com.soft.yozo.myrecode.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.soft.yozo.myrecode.R;
import com.soft.yozo.myrecode.fragment.NameFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private ViewPager mViewPager;
    private List<Fragment> mItemViews;
    private List<String> mTabs;
    private RecodeFragmentAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView()
    {
        mViewPager = findViewById(R.id.my_recode_content);
    }

    private void initData()
    {
        mItemViews = new ArrayList<>();
        for(int i=0;i<4;i++)
        {
            NameFragment fragment = new NameFragment();
            mItemViews.add(fragment);
        }


        mAdapter = new RecodeFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

    }

    private class RecodeFragmentAdapter extends FragmentPagerAdapter
    {

        public RecodeFragmentAdapter(FragmentManager fm) {
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

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "TAB";
        }
    }
}
