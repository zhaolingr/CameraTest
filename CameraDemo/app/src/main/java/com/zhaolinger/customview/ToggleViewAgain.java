/*
 * Copyright 2016 Fang Lemei.
 * Author: Zhao Linger.
 * DESTCRIBETION: Add content.
 */
package com.zhaolinger.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zhaolinger.R;

/**
 * Created by 赵灵儿 on 2017/2/13.
 * 自定义一个开关效果的view
 * view样式 ： 背景 滑块 开关状态
 */
public class ToggleViewAgain extends View{

    private Bitmap switchBackgroundBitmap; //背景图片
    private Bitmap slideBitmap;//滑块图片
    private boolean switchState; //开关状态
    private Paint paint = new Paint();
    private float currentX; //手指当前X坐标

    /**
     * 使用代码创建控件时  执行该构造
     * ToggleViewAgain tv = new ToggleViewAgain(this);
     * @param context
     */
    public ToggleViewAgain(Context context) {
        this(context,null);
        Log.i("test","一个参数构造");
    }

    /**
     * 在xml中使用自定义控件属性时 执行该构造
     * xmlns:custom="http://schemas.android.com/apk/res-auto"
     * custom:xxx=""
     * @param context
     * @param attrs  自定义属性声明文件
     */
    public ToggleViewAgain(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        Log.i("test","两个参数构造");

    }

    /**
     * 在xml中使用了自定义控件属性 并且指定了样式 执行该构造
     * @param context
     * @param attrs
     * @param defStyleAttr 默认的样式
     */
    public ToggleViewAgain(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i("test","三个参数构造");
        //获取布局中自定义属性值
        TypedArray typedArray =  context.getTheme().obtainStyledAttributes(attrs, R.styleable.ToggleView,defStyleAttr,0);
        int switchBackgroundResoucre = typedArray.getResourceId(R.styleable.ToggleView_switch_background,-1);
        int slidResource = typedArray.getResourceId(R.styleable.ToggleView_slide_button,-1);
        switchState = typedArray.getBoolean(R.styleable.ToggleView_switch_state,true);
        typedArray.recycle();

        //不直接设置背景 而是将图片资源转换成bitmap 通过canvas绘制 扩展性更好
        //setBackgroundResource(switchBackgroundResoucre);
        setSwitchBackgroundResource(switchBackgroundResoucre);
        setSlideResource(slidResource);

    }

    //将背景图片资源转换成bitmap
    private void setSwitchBackgroundResource(int switchBackgroundResource) {
        switchBackgroundBitmap = BitmapFactory.decodeResource(getResources(),switchBackgroundResource);
    }

    //将滑块图片资源转换成bitmap
    private void setSlideResource(int slideResource) {
        slideBitmap = BitmapFactory.decodeResource(getResources(),slideResource);
    }

    /**
     * view宽高的测量 ：对于普通View，其MeasureSpec由父容器的MeasureSpec和自身的LayoutParams来共同决定。
     * MeasureSpec 和 LayoutParams 相对应，如下：
     *   EXACTLY  -->  match_parent和具体的数值 ： 精确模式，View的大小就是指定的这个值
     *   AT_MOST  -->  wrap_content ： 最大模式，父容器指定了一个可用大小，View的大小不能大于这个值
     *   UNSPECIFIED 一般用于系统内部多次Measure的情况，不关注。
     *   测量出来的结果，需要重绘的情况有：
     *   父容器是EXACTLY模式 View自身的LayoutParams是wrap_content 这样View测量出来的大小就是父容器的大小 和自身真正的大小不一致
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //这里设置为背景图的宽高
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(switchBackgroundBitmap.getWidth(),slideBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //1、绘制背景
        canvas.drawBitmap(switchBackgroundBitmap,0,0,paint);

        //2、绘制滑块 根据手指移动的位置
        int left = switchBackgroundBitmap.getWidth() - slideBitmap.getWidth();
        if(isTouched){ //滑动 具体绘制
            if(currentX<slideBitmap.getWidth()/2.0f){
                canvas.drawBitmap(slideBitmap,0,0,paint);
            } else if(currentX>left+slideBitmap.getWidth()/2.0f){
                canvas.drawBitmap(slideBitmap,left,0,paint);
            }else{
                canvas.drawBitmap(slideBitmap,currentX-slideBitmap.getWidth()/2.0f,0,paint);
            }
        }else{ //如果没有滑动或者松手 绘制 初始时
            if(currentX<slideBitmap.getWidth()){ //一定靠左边
                canvas.drawBitmap(slideBitmap,0,0,paint);
                switchState = true; //开
            }else if(currentX>left){ //一定靠右边
                canvas.drawBitmap(slideBitmap,left,0,paint);
                switchState = false; //关
            }
        }

    }

    private boolean isTouched = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isTouched = true;
                currentX = event.getX();
                Log.i("test","currentx down=="+currentX);
                break;
            case MotionEvent.ACTION_MOVE:

                currentX = event.getX();
                Log.i("test","currentx ACTION_MOVE=="+currentX);
                break;
            case MotionEvent.ACTION_UP:
                isTouched = false;
                currentX = event.getX();
                Log.i("test","currentx ACTION_UP=="+currentX);
                break;
            default:
                break;
        }
        invalidate(); //重绘 onDraw(Canvas canvas)
        return true;
    }
}
