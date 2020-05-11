package com.soft.yozo.myrecode.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soft.yozo.myrecode.R;

public class Item4TextView extends LinearLayout
{
    private Context mContext;
    private TextView[] mTvs;
    private View mView;

    private int mNamesId;



    public Item4TextView(Context context)
    {
        super(context);
    }

    public Item4TextView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs)
    {
        mContext = context;
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = layoutInflater.inflate(R.layout.view_4_item,this,true);

        int namesId = -1;
        boolean isBold = false;
        mTvs = new TextView[4];
        mTvs[0] = mView.findViewById(R.id.view_4_item_tv1);
        mTvs[1] = mView.findViewById(R.id.view_4_item_tv2);
        mTvs[2] = mView.findViewById(R.id.view_4_item_tv3);
        mTvs[3] = mView.findViewById(R.id.view_4_item_tv4);

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,R.styleable.Item4TextView);
        final int count = typedArray.getIndexCount();
        for(int i=0;i<count; i++)
        {
            final int attr = typedArray.getIndex(i);
            if(attr == R.styleable.Item4TextView_texts)
            {
                namesId = typedArray.getResourceId(attr,-1);
            }
            if(attr == R.styleable.Item4TextView_isTitle)
            {
                isBold = typedArray.getBoolean(attr,false);
                if(isBold)
                {
                    mTvs[0].getPaint().setFakeBoldText(true);
                    mTvs[1].getPaint().setFakeBoldText(true);
                    mTvs[2].getPaint().setFakeBoldText(true);
                    mTvs[3].getPaint().setFakeBoldText(true);
                }else
                {
                    mTvs[0].getPaint().setFakeBoldText(false);
                    mTvs[1].getPaint().setFakeBoldText(false);
                    mTvs[2].getPaint().setFakeBoldText(false);
                    mTvs[3].getPaint().setFakeBoldText(false);
                }

            }
        }
        mNamesId = namesId;
        setContentTTextView();
    }

    private void setContentTTextView()
    {
        if(mNamesId != -1)
        {
            CharSequence[] titles = mContext.getResources().getTextArray(mNamesId);
            mTvs[0].setText(titles[0]);
            mTvs[1].setText(titles[1]);
            mTvs[2].setText(titles[2]);
            mTvs[3].setText(titles[3]);
        }
    }

    public void setTexts(String[] values)
    {
        mTvs[0].setText(values[0]);
        mTvs[1].setText(values[1]);
        mTvs[2].setText(values[2]);
        mTvs[3].setText(values[3]);
    }

}
