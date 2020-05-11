package com.soft.yozo.myrecode.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.soft.yozo.myrecode.R;

public class WheelView extends View {

    private final static int VISIBLE_ITEM_COUNTS = 5;
    private  static String[] test=new String[]{"01","02","03","04","05","06","07","08","09","00"};
    private String testText;
    private Context mContext;
    //字体宽度
    private int mMaxTextWidth;
    //字体高度
    private int mMaxTextHeight;

    //wheel view 的宽高
    private int measureWidth;
    private int measureHeight;
    private int itemHeight;



    //绘制中间字体
    private Paint mCenterPaint;
    //绘制两边字体
    private Paint mOuterPaint;
    //绘制分割线
    private Paint mIndicatorPaint;

    //字体颜色
    private int mCenterColor;
    private int mOuterColor;
    private int mIndicatorColor;

    //分割线宽度
    private int mIndicatorWidth;

    //非中间数据开始绘制点
    private int mDrawOuterStartY;
    //中间数据绘制点
    private int mDrawCenterStartY;
    // 条目间距倍数
    private float lineSpacingMultiplier = 1.6F;

    private int initPosition = 0;

    //滚动相关
    //当前滚动总高度y值
    private float totalScrollY;
    private float previousY;


    public WheelView(Context context)
    {
        super(context);
        init(context, null);
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

    public WheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        reMeasure();
    }

    private void reMeasure()
    {
        Rect rect = new Rect();
        //得到字体的宽高
        mOuterPaint.getTextBounds(test[0],0,testText.length(),rect);
        mMaxTextWidth = rect.width();
        mMaxTextHeight = rect.height();

        itemHeight =(int) (mMaxTextHeight * lineSpacingMultiplier);
        measureHeight = itemHeight * VISIBLE_ITEM_COUNTS;
        setMeasuredDimension(getMeasuredWidth(),measureHeight);

    }

    /**
     * 初始化数据
     */
    private void init(Context context, AttributeSet attrs)
    {
        testText = "09";
        this.mContext = context;
        if(attrs != null)
        {
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.custom_wheel);
            final int count = typedArray.getIndexCount();
            for(int i=0;i<count;i++)
            {
                int resId = typedArray.getIndex(i);
                if(resId == R.styleable.custom_wheel_center_text_color)
                {
                    mCenterColor = typedArray.getColor(resId,0xFFFF00);
                }else if(resId == R.styleable.custom_wheel_outer_text_color)
                {
                    mOuterColor = typedArray.getColor(resId,0x00FFFF);
                }else if(resId == R.styleable.custom_wheel_indicator_color)
                {
                    mIndicatorColor = typedArray.getColor(resId,0x00FF00);
                }else if(resId == R.styleable.custom_wheel_indicator_width)
                {
                    mIndicatorWidth = typedArray.getInt(resId,2);
                }
            }

            mCenterPaint = new Paint();
            mOuterPaint = new Paint();
            mOuterPaint.setColor(Color.RED);
            mOuterPaint.setTextSize(40);

            mIndicatorPaint = new Paint();

            totalScrollY = 0;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int actionId = event.getAction();
        switch (actionId)
        {
            case MotionEvent.ACTION_DOWN:
                previousY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = previousY - event.getRawY();
                totalScrollY += dy;
                previousY = event.getRawY();
                break;
        }
        invalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        int change = (int)(totalScrollY / itemHeight); //当前第一行数据
        float itemHeightOffset = (totalScrollY % itemHeight);
//        change = change % 10;
        Log.i("TAG",totalScrollY+"/"+itemHeight + "="+change);
        initPosition = initPosition + change;
        initPosition = initPosition % 10;
        Log.i("TAG","initPosition"+"="+initPosition);
        if(initPosition >= test.length)
        {
            initPosition = initPosition - test.length;
        }
        if(initPosition < 0)
        {
            initPosition = initPosition + test.length;
        }
        //绘制5行数据
        int j = 0;
        for(int i=-2;i< 3;i++)
        {
            int curIndex = initPosition +i;
            if(curIndex >= test.length)
            {
                curIndex = curIndex - test.length;
            }
            if(curIndex < 0)
            {
                curIndex = curIndex + test.length;
            }
            float offsetY = (itemHeight - mMaxTextHeight)/2+ itemHeight *j + itemHeightOffset;
            canvas.save();
            canvas.translate(0,offsetY);
//            mDrawOuterStartY = (mMaxTextHeight + (itemHeight - mMaxTextHeight)/2) + itemHeight*j;
            canvas.drawText(test[curIndex],0,mMaxTextHeight,mOuterPaint);
            j++;
            canvas.restore();
        }

    }
}
