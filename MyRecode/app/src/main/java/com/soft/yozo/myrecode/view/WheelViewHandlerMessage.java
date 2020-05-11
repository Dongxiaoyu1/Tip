package com.soft.yozo.myrecode.view;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

public class WheelViewHandlerMessage extends Handler
{
    public static final int WHAT_SMOOTH_SCROLL = 0x10000;
    public static final int WHAT_ITEM_CLICKED = 0x10001;
    private HorWheelView mHorWheelView;
    public WheelViewHandlerMessage(HorWheelView wheelView)
    {
        this.mHorWheelView = wheelView;
    }
    @Override
    public void handleMessage(@NonNull Message msg)
    {
        switch (msg.what)
        {
            case WHAT_ITEM_CLICKED:
            mHorWheelView.onItemSelected();
                break;
            case WHAT_SMOOTH_SCROLL:

                break;
        }

    }
}
