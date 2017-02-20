package com.zhaolinger.camerademo;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by zhaolinger on 2017/2/19.
 * recycelerview的itemtouch回调
 */
public class RecyclerviewItemTouchCallback extends ItemTouchHelper.Callback {

    private OnTouchDragListener mOnTouchDragListener;
    private OnTouchSwipeListener mOnTouchSwipeListener;

    public void setmOnTouchDragListener(OnTouchDragListener mOnTouchDragListener) {
        this.mOnTouchDragListener = mOnTouchDragListener;
    }

    public void setmOnTouchSwipeListener(OnTouchSwipeListener mOnTouchSwipeListener) {
        this.mOnTouchSwipeListener = mOnTouchSwipeListener;
    }

    /**
     * 长按是否可以拖拽
     * @return true 可以 false 不可以
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * 是否可以滑动
     * @return true 可以 false 不可以
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * 定义拖拽和滑动的方向
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int dragFlag = 0;
        int swipeFlag = 0;
        if(layoutManager instanceof GridLayoutManager){
            //可以拖拽(上下左右) 不能滑动(0)
            dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipeFlag = 0;
            return makeMovementFlags(dragFlag,swipeFlag);
        }else if(layoutManager instanceof LinearLayoutManager){

            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            //两个方向
            if(orientation == ((LinearLayoutManager) layoutManager).HORIZONTAL){
                //水平方向上 可以拖拽（左右） 可以滑动（上下）
                dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                swipeFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                return makeMovementFlags(dragFlag,swipeFlag);
            }else if(orientation == ((LinearLayoutManager) layoutManager).VERTICAL){
                //垂直方向上 可以拖拽（上下） 可以滑动（左右）
                swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                return makeMovementFlags(dragFlag,swipeFlag);
            }

        }

        return makeMovementFlags(dragFlag,swipeFlag);
    }

    /**
     * item被拖拽的时候会调用
     * @param recyclerView
     * @param oldViewHolder
     * @param targetViewHolder
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder oldViewHolder, RecyclerView.ViewHolder targetViewHolder) {
        //将拖拽的两个位置回调出去
        if(null != mOnTouchDragListener){
            return mOnTouchDragListener.setOnTouchDragCallback(oldViewHolder.getAdapterPosition(),targetViewHolder.getAdapterPosition());
        }
        return false;
    }

    /**
     * item被滑动的时候会调用
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(null != mOnTouchSwipeListener){
            mOnTouchSwipeListener.setOnTouchSwipeCallback(viewHolder.getAdapterPosition());
        }
    }

    /**
     * item 拖拽回调接口
     */
    public interface OnTouchDragListener{
        boolean setOnTouchDragCallback(int oldPosition,int newPosition);
    }

    /**
     * item 滑动回调接口
     */
    public interface OnTouchSwipeListener{
        void setOnTouchSwipeCallback(int positon);
    }


}
