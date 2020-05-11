package com.soft.yozo.myrecode.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TimeUtils;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.soft.yozo.myrecode.view.listener.OnItemListener;
import com.soft.yozo.utils.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 用来显示一周的水平视图
 */
public class HorWheelView extends View
{
    private enum TYPE
    {
        CLICKED,MOVED;
    }
    private static final int ITME_COUNTS = 5;
    private Context mContext;
    private List<String> days;
    private List<String> dates;
    private int measureWidth;
    private int measuerHeight;
    private int mItemWidth;

    private Paint mCenterPaint;
    private Paint mOuterPaint;
    private Paint mTextPaint;
    private int mLineSpace;
    private float mFristX;
    private float mFristY;
    private int mRadio;
    private int initPosition;
    private int mPerCurPosition;
    private int mSelectItem;


    private float mTotalScrollX;
    private float mPreX;

    private ScheduledExecutorService mScheduledExecutorService;
    private ScheduledFuture<?> mFuture;
    private Handler mHandler;
    private int mFirstLine;
    private int mSecondLine;
    private int mTextWeekWidth;
    private int mTextDateWidth;
    private int mTextWeekHeight;

    private Map<Integer, String> mDatas;

    private OnItemListener mOnItemListener;

    public HorWheelView(Context context)
    {
        super(context);
        this.mContext = context;
    }

    public HorWheelView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initAttrs(context,attrs);
    }

    public HorWheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initAttrs(context,attrs);
    }

    public HorWheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context,attrs);
    }

    private void initAttrs(Context context, AttributeSet set)
    {
        this.mContext = context;

        mLineSpace = 10;

        initPaint();
        initData();

    }

    private void initPaint()
    {
        mCenterPaint  = new Paint();
        mOuterPaint = new Paint();
        mTextPaint = new Paint();
        mCenterPaint.setColor(0xFFFF9966);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(35);
        mOuterPaint.setColor(0xFFFF9966);
    }

    private void initData()
    {
        mDatas = DateUtils.getDayAndWeek(DateUtils.getWeeks());
        days = new ArrayList<>();
        days.add("星期一");
        days.add("星期二");
        days.add("星期三");
        days.add("星期四");
        days.add("星期五");
        days.add("星期六");
        days.add("星期日");
        dates = new ArrayList<>();
        dates.add(mDatas.get(1));
        dates.add(mDatas.get(2));
        dates.add(mDatas.get(3));
        dates.add(mDatas.get(4));
        dates.add(mDatas.get(5));
        dates.add(mDatas.get(6));
        dates.add(mDatas.get(7));
        DateUtils.getCurDay();
        int week = DateUtils.getCurDay();
        if(week == 1)
        {
            initPosition = 6;
        }else
        {
            initPosition = week - 1 ;
        }

        Rect rect = new Rect();
        mCenterPaint.getTextBounds("星期一",0,3,rect);
        mTextWeekWidth = rect.width();
        mTextWeekHeight = rect.height();
        Rect rect1 = new Rect();
        mOuterPaint.getTextBounds("00-00",0,5,rect1);
        mTextDateWidth = rect1.width();
        mPerCurPosition = initPosition;
        mSelectItem = initPosition;

        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mHandler = new WheelViewHandlerMessage(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        remeasure();
    }

    private void remeasure()
    {
        measureWidth = getMeasuredWidth();
        measuerHeight = getMeasuredHeight();
        mItemWidth = measureWidth / ITME_COUNTS ;
//        if(measuerHeight < mItemWidth)
//        {
            measuerHeight = mItemWidth+ 10;
//        }

        setMeasuredDimension(measureWidth,measuerHeight);
    }

    public void smoothScroll()
    {
        int offset = (int)((mTotalScrollX % mItemWidth + mItemWidth)%mItemWidth);
        if(offset > mItemWidth /2.0f)
        {
            offset = (int)(mItemWidth - (float)offset);
        }else
        {
            offset = -offset;
        }
        mFuture = mScheduledExecutorService.scheduleAtFixedRate(new SmoothScorll(this,offset),0, 10, TimeUnit.MILLISECONDS);
    }

    public void cancelFuture()
    {
        if(mFuture != null && !mFuture.isCancelled())
        {
            mFuture.cancel(true);
            mFuture = null;
        }
    }

    public void setTotalScrollX(float scrollX)
    {
        this.mTotalScrollX = scrollX;
        invalidate();
    }

    public float getTotalScrollX()
    {
        return mTotalScrollX;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int actionId = event.getActionMasked();
        long downTime = 0;
        switch (actionId)
        {
            case MotionEvent.ACTION_DOWN:
                mPreX = event.getRawX();
                downTime = event.getDownTime();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = mPreX - event.getRawX();
//                mTotalScrollX += dx;
//                mPreX = event.getRawX();
                break;
            case MotionEvent.ACTION_UP:
                if(event.getEventTime() - downTime > 120)
                {
                    int index = (int)event.getRawX() / mItemWidth;
                    Log.i("TAG","time==="+(event.getEventTime() - downTime)+"index=="+index);
                    int offsetX = (2 - index)*mItemWidth;
                    cancelFuture();
                    mFuture = mScheduledExecutorService.scheduleAtFixedRate(new SmoothScorll(this,-offsetX),0, 20, TimeUnit.MILLISECONDS);

                }else
                {
//                    smoothScroll();
                }

                break;
        }
        if(actionId != MotionEvent.ACTION_DOWN)
        {
            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRadio = (mItemWidth - mLineSpace*2)/2;
        int  change = (int)(mTotalScrollX / mItemWidth);
        Log.i("TAG","mTotalScrollX="+mTotalScrollX);
        mPerCurPosition = initPosition + change % days.size();
        if(mPerCurPosition < 0)
        {
            mPerCurPosition = mPerCurPosition + days.size();
        }
        if(mPerCurPosition >= days.size())
        {
            mPerCurPosition = mPerCurPosition - days.size();
        }
        Log.i("TAG","initPosition="+mPerCurPosition);
        float itemWidthOffset = mTotalScrollX % mItemWidth;
        int counts = -1;

        mFristY = mRadio + mLineSpace;
        while(counts < ITME_COUNTS+1)
        {
            int index = mPerCurPosition - (ITME_COUNTS/2 -counts);
            Log.i("TAG","index="+index);
            if(index < 0)
            {
                index = index + days.size();
            }
            if(index >= days.size())
            {
                index = index - days.size();
            }

            mFristX = (mRadio + mLineSpace) + mItemWidth*counts - itemWidthOffset;
            mFirstLine = measureWidth / 2 - mItemWidth/2;
            mSecondLine = measureWidth /2 + mItemWidth/2;
            if(mFristX > mFirstLine && mFristX < mSecondLine)
            {
                mSelectItem = index;
                canvas.save();
                canvas.translate(mFristX,0);
                canvas.scale(1.1f,1.1f);
                canvas.drawCircle(0,mFristY,mRadio,mCenterPaint);
                canvas.drawText(days.get(index),-mTextWeekWidth-mLineSpace,mFristY,mTextPaint);
                canvas.drawText(dates.get(index),-mTextDateWidth-mLineSpace,mFristY+mRadio/2,mTextPaint);
                canvas.restore();
            }else
            {
                canvas.save();
                canvas.translate(mFristX,0);
                canvas.drawCircle(0,mFristY,mRadio,mOuterPaint);
                canvas.drawText(days.get(index),-mTextWeekWidth-mLineSpace,mFristY,mTextPaint);
                canvas.drawText(dates.get(index),-mTextDateWidth-mLineSpace,mFristY+mRadio/2,mTextPaint);
                canvas.restore();
            }

//            canvas.drawCircle(mFristX,mFristY,mRadio,mCenterPaint);
//            canvas.drawText(days.get(index),mFristX,mFristY,mOuterPaint);
            counts++;
        }
        mFristX = 0;
    }

    public void setmOnItemListener(OnItemListener onItemListener)
    {
        this.mOnItemListener = onItemListener;
    }

    public final void onItemSelected()
    {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mOnItemListener != null)
                {
                    mOnItemListener.onItemSelectedListener(DateUtils.getWeeks().get(mSelectItem -1).getTime());
                }
            }
        },0);
    }


    @Override
    public Handler getHandler() {
        return super.getHandler();
    }

}
