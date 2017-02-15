package com.zhaolinger.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by zhaolinger on 16/11/6.
 */
public class CustomLinearLayout extends LinearLayout{

    private Scroller scroller;
    //全局的 手指所在的最新位置
    private int newX;
    private int newY;
    //全局的 控件最初的位置
    private int viewX;
    private int viewY;

    public CustomLinearLayout(Context context) {
    this(context, null, 0);
}

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }

    /**
     *每个事件监听都return true 表明要处理该事件
     * scrollBy、scrollTo实现view的滑动 只能实现内容的滑动
     * scrollBy(-x,-y) 滑到相对view当前位置x,y处
     * scrollTo(x,y) 滑到相对view初始位置x,y处
     * startScroll(startX,startY,dx,dy,duration) 滑动的起点 滑动的距离 整个滑动过程完成所需要的时间
     * invalidate(); //重绘 这里会间接调用computeScroll（）
     * getScrollX() view初始位置的水平方向的滚动量
     * scroller.getCurrX()
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //每次进来都获取手指的位置
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                //记录控件的最初位置
//                viewX = getScrollX();
//                viewY = getScrollY();

                //通过startscroll实现滑动后 需要停止滑动
                if(!scroller.isFinished()){
                   scroller.abortAnimation();
                }

                //更新手指的位置
                newX = x;
                newY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                //计算上一秒和此时手指所在位置的距离 即滚动的距离
                int dx = x - newX;
                int dy = y - newY;
                //此时手指的位置仍是x,y 更新手指的位置
                newX = x;
                newY = y;
                //调用scrollBy 实现滚动（上下滚动 即X不变 －dx值为0；左右滚动同理 -dy为0）
                scrollBy(-dx,-dy);

                return true;
            case MotionEvent.ACTION_UP:
                //调用scrollTo 回到最初位置（只是拖动的话 就什么都不操作）
                //scrollTo(viewX,viewY);
                Log.i("test","scroller.getCurrX()=="+scroller.getCurrX()+"    scroller.getCurrY()=="+scroller.getCurrY());
                Log.i("test","getScrollX=="+getScrollX()+"    getScrollY=="+getScrollY());
                //调用startScroll 设置回弹的时间 在down中结合finish和abort使用
                scroller.startScroll(getScrollX(),getScrollY(),-getScrollX(),-getScrollY(),500); //view的内容滚动的偏移量 起点 要滑动回去的距离 总共需要的时长
                invalidate(); //这里会间接调用computeScroll（）
                return true;
        }


        return super.onTouchEvent(event);
    }


    /**
     * scroller.computeScrollOffset()返回true 说明还没有回到终点位置
     * 通过scroller.getCurrX(),scroller.getCurrY()获取最新位置
     * postInvalidate()  //重绘 这里会间接调用computeScroll（）
     */
    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset()){
            Log.i("test","scroller.getCurrX()=="+scroller.getCurrX()+"    scroller.getCurrY()=="+scroller.getCurrY());
            scrollTo(scroller.getCurrX(),scroller.getCurrY()); //滑动到最新位置
            postInvalidate(); //再次调用该方法 实现缓慢滑动效果
        }
        super.computeScroll();
    }
}
