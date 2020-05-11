package com.soft.yozo.myrecode.view;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soft.yozo.myrecode.adapter.MyAdapter;

public class OnItemTouch implements RecyclerView.OnItemTouchListener {

    private VelocityTracker velocityTracker = VelocityTracker.obtain();

    private MyAdapter.MyViewHolder curHolder;
    private MyAdapter.MyViewHolder oldHolder;
    private boolean flag = true;
    private int state = 0;
    private int MAX_WIDTH;
    private int x;
    private  boolean dealEvent = true;
    private final int MAX_VELOCITY = 100;

    public OnItemTouch(Context context)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        MAX_WIDTH = metrics.widthPixels / 5;
    }
    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        switch (e.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                View view = rv.findChildViewUnder(e.getX(),e.getY());
                if(view != null)
                {
                    curHolder = (MyAdapter.MyViewHolder) rv.getChildViewHolder(view);
                    curHolder.itemView.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            onItemClick();
                        }
                    });
                }else
                {
                    curHolder = null;
                }
                if(state == 1 && oldHolder != null && curHolder != oldHolder)
                {
                    closeItem();
                    dealEvent = false;
                    break;
                }
                x = (int)e.getX();
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                if(flag)
                {
                    velocityTracker.addMovement(e);
                    velocityTracker.computeCurrentVelocity(1000);
                    flag = false;
                    if(Math.abs(velocityTracker.getYVelocity()) < Math.abs(velocityTracker.getXVelocity()) ||
                            (state ==1 && curHolder != null && curHolder == oldHolder))
                    {
                        return true;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                reset();
                break;
            }
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e)
    {
        switch (e.getAction())
        {
            case MotionEvent.ACTION_MOVE:
            {
                if(null != curHolder && curHolder.root.getScrollX() >= 0 && dealEvent)
                {
                    onScroll(e);
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                if(dealEvent)
                {
                    if(curHolder != null)
                    {
                        oldHolder = curHolder;
                    }

                    if(velocityTracker.getXVelocity() > MAX_VELOCITY)
                    {
                        closeItem();
                    }else if(curHolder.root.getScrollX() > MAX_WIDTH /4)
                    {
                        openItem();
                    }else
                    {
                        closeItem();
                    }
                }
                reset();
                break;
            }
        }
        x = (int)e.getX();

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private void closeItem()
    {
        state = 0;
        oldHolder.root.onScroll(-oldHolder.root.getScrollX());
    }

    private void openItem()
    {
        state = 1;
        int startX = oldHolder.root.getScrollX() > MAX_WIDTH ? MAX_WIDTH: oldHolder.root.getScrollX();
        int dx = startX < MAX_WIDTH ? MAX_WIDTH - startX : 0;
        oldHolder.root.onScroll(dx);
    }

    private void onScroll(MotionEvent e)
    {
        int dx = (int)(e.getX() - x);
        if(curHolder.root.getScrollX() <= MAX_WIDTH && dx < 0)
        {
            if(curHolder.root.getScrollX() - dx > MAX_WIDTH)
            {
                dx = curHolder.root.getScrollX() - MAX_WIDTH;
                state = 1;
            }

            if(curHolder.root.getScrollX() == 0 && dx > 0 )
            {
                dx = 0;
            }

            curHolder.root.scrollBy(-dx, 0);
        }

        if(dx > 0 && curHolder.root.getScrollX() > 0)
        {
            if(curHolder.root.getScrollX() - dx < 0)
            {
                dx = curHolder.root.getScrollX();
                state = 0;
            }
            curHolder.root.scrollBy( -dx , 0);
        }
    }

    private  void reset()
    {
        flag = true;
        dealEvent = true;
        velocityTracker.clear();
    }

    private  void onItemClick()
    {
        if(null != oldHolder && 1 == state)
        {
            closeItem();
        }
    }
}
