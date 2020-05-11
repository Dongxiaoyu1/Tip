package com.soft.yozo.myrecode.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class MyRelativeLayout extends RelativeLayout {

    private Scroller scroller;
    public MyRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    private void init(Context context)
    {
        scroller = new Scroller(context);
    }

    public void onScroll(int dx)
    {
        if(this.getScaleX() != 0)
        {
            scroller.startScroll(this.getScrollX(),0,dx,0);
            invalidate();
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset())
        {
            this.scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }
}
