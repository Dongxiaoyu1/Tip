package com.soft.yozo.myrecode.view;

import com.contrarywind.timer.MessageHandler;

import java.util.TimerTask;

public class SmoothScorll extends TimerTask
{
    private HorWheelView mHorWheelView;
    private float mOffset;
    private float mRealOffset;
    private float mRealTotalOffset;
    public SmoothScorll(HorWheelView horWheelView, float offset)
    {
        this.mHorWheelView = horWheelView;
        this.mOffset = offset;
        this.mRealTotalOffset = Integer.MAX_VALUE;
        this.mRealOffset = 0;
    }
    @Override
    public void run()
    {
        if(mRealTotalOffset == Integer.MAX_VALUE)
        {
            mRealTotalOffset = mOffset;
        }

        this.mRealOffset = ((float)mOffset * 0.1f);

        if (Math.abs(mRealTotalOffset) <= 1) {
            mHorWheelView.cancelFuture();
//            wheelView.getHandler().sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
        }else
        {
            mHorWheelView.setTotalScrollX(mHorWheelView.getTotalScrollX() + mRealOffset);
            mRealTotalOffset = mRealTotalOffset - mRealOffset;
        }

    }
}
