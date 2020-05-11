package com.soft.yozo.calendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.soft.yozo.calendarview.apapter.CalendarAdapter;

public class WheelView extends View
{
    private Context mContext;
    private Paint mPaintCenter; //绘制中间一行数据
    private Paint mPaintSide; //绘制两边的数据
    private Paint mPaintDivide;
    private int mTextHeight;
    private int mItemHeight; //每行的高度
    private int mItemWidth; //每行的宽度
    private int mVisibleHeigh;
    private int mLineSpace;
    private final static int VISIBLE_COUNTS = 5;
    private float mFirstLineY;
    private float mSecondLineY;

    private int mCenterColor;
    private int mSideColor;
    private float mTextSize;

    private int widthMeasureSpec;
    private int measureWidth;
    private int measureHeight;

    private int mSelectedIndex;
    private int mPerCurrentIndex;
    private int mInitIndex;

    private CalendarAdapter mAdapter;

    private int mTotalScrollY; //当前滚动的位置
    private float mPerDownY;

    public WheelView(Context context)
    {
        super(context);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        mContext = context;
        mCenterColor = Color.BLUE;
        mSideColor = Color.BLACK;
        mTextSize = context.getResources().getDimension(R.dimen.calendar_text_size);
        TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.calendar_style);

        mPaintCenter = new Paint();
        mPaintCenter.setColor(mCenterColor);
        mPaintCenter.setTextSize(mTextSize);
        mPaintSide = new Paint();
        mPaintSide.setColor(mSideColor);
        mPaintSide.setTextSize(mTextSize);

        mPaintDivide = new Paint();
        mPaintDivide.setColor(Color.RED);

        mInitIndex = 0;
        mSelectedIndex = 0;
        mPerCurrentIndex = 0;
        mLineSpace = 10;
    }

    public void setmAdapter(CalendarAdapter adapter)
    {
        this.mAdapter = adapter;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        this.widthMeasureSpec = widthMeasureSpec;
        reMeasure();
        setMeasuredDimension(measureWidth,measureHeight);
    }

    private void reMeasure()
    {
        Rect bounds = new Rect();
        mPaintCenter.getTextBounds("01",0,1,bounds);
        mTextHeight = bounds.height();
        mItemHeight = bounds.height() + mLineSpace * 2;
        mVisibleHeigh = mItemHeight * VISIBLE_COUNTS;
        measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        measureHeight = mVisibleHeigh;

        mFirstLineY = (mVisibleHeigh - mItemHeight) / 2;
        mSecondLineY = (mVisibleHeigh + mItemHeight) /2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int id = event.getAction();
        switch (id)
        {
            case MotionEvent.ACTION_DOWN:
                mPerDownY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getRawY();
                float dx = mPerDownY - moveY;
                Log.i("TAG","dx===="+dx);
                mTotalScrollY += dx;
                mPerDownY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if(mAdapter == null)
            return;

        mFirstLineY = mItemHeight * 2;
        mSecondLineY = mItemHeight * 3;
        int size = mAdapter.getItemCounts();
        int index = mTotalScrollY / mItemHeight ;
        if(index < 0 )
        {
            index = size + index%size;
        }
        if(index >= size)
        {
            index = index % size;
        }
        mPerCurrentIndex = index + mInitIndex;
        if(mPerCurrentIndex > size)
        {
            mPerCurrentIndex = mPerCurrentIndex - size;
        }
        if(mPerCurrentIndex < 0)
        {
            mPerCurrentIndex = size + mPerCurrentIndex;
        }

        Log.i("TAG","mScrollY / mItemHeight = index===="+mTotalScrollY +"/"+mItemHeight+"="+index);
        Log.i("TAG","mPerCurrentIndex"+mPerCurrentIndex);



        int count = 0;
        while(count < VISIBLE_COUNTS)
        {

            int position = mPerCurrentIndex - VISIBLE_COUNTS / 2 + count;
            if(position >= size)
            {
                position = position - size;
            }
            if(position < 0)
            {
                position = size + position;
            }
            int translate = mItemHeight * count;
            //绘制中间的值
            canvas.save();
            if(translate > mFirstLineY && translate < mSecondLineY)
            {
                mSelectedIndex = position;
            }
            canvas.translate(0,translate);
            canvas.drawText((String)mAdapter.getItem(position),0,mTextHeight + mLineSpace,mPaintCenter);
            canvas.restore();
            count++;
        }

        //绘制中间的线
        canvas.save();
        canvas.drawLine(0,mFirstLineY,measureWidth,mFirstLineY,mPaintDivide);
        canvas.drawLine(0,mSecondLineY,measureWidth,mSecondLineY,mPaintDivide);
        canvas.restore();



    }
}
