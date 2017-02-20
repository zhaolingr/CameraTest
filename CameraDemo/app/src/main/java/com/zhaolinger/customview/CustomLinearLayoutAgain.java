/*
 * Copyright 2016 Fang Lemei.
 * Author: Zhao Linger.
 * DESTCRIBETION: Add content.
 */
package com.zhaolinger.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by 赵灵儿 on 2017/2/9.
 */
public class CustomLinearLayoutAgain extends LinearLayout{

    private Scroller mscroller;

    public CustomLinearLayoutAgain(Context context) {
        this(context,null);
    }

    public CustomLinearLayoutAgain(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomLinearLayoutAgain(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mscroller = new Scroller(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //Log.i("test","super.dispatchTouchEvent(ev)=="+super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //Log.i("test","super.onInterceptTouchEvent(ev)=="+super.onInterceptTouchEvent(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.i("test","super.onTouchEvent(ev)=="+super.onTouchEvent(event));
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("test","MotionEvent.ACTION_DOWN==");
                return false;
            case MotionEvent.ACTION_UP:
                Log.i("test","MotionEvent.ACTION_UP==");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("test","MotionEvent.ACTION_MOVE==");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
