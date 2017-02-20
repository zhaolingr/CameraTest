package com.zhaolinger.camerademo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhaolinger on 2017/2/19.
 * 自定义recyclerview的适配器
 * 触摸item中的图片时 有拖拽效果
 */
public class CustomRecyclerviewAdapter extends RecyclerView.Adapter<CustomRecyclerviewAdapter.MyContentviewHolder> {

    private List<CustomRecyclerviewDataBean> datas;
    private OnItemClickListener mOnItemClickListener;
    private OnItemTouchListener mOnItemTouchListener;

    public void updateAdapter(List<CustomRecyclerviewDataBean> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setmOnItemTouchListener(OnItemTouchListener mOnItemTouchListener) {
        this.mOnItemTouchListener = mOnItemTouchListener;
    }

    @Override
    public MyContentviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyContentviewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyContentviewHolder holder, int position) {

        holder.mName.setText(datas.get(position).getName());
        holder.mAge.setText(datas.get(position).getAge()+"");

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class MyContentviewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mName)
        TextView mName;
        @BindView(R.id.mAge)
        TextView mAge;
        @BindView(R.id.mImageview)
        ImageView mImageview;
        public MyContentviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //item 的点击事件
                    if(null != mOnItemClickListener){
                     mOnItemClickListener.setOnItemClickCallback(getAdapterPosition());
                    }
                }
            });
//            itemView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    if(null != mOnItemTouchListener){
//                        mOnItemTouchListener.setOnItemTouchCallback(MyContentviewHolder.this);
//                    }
//                    return false;
//                }
//            });
        }
    }

    /**
     * item点击事件回调接口
     */
    public interface OnItemClickListener{
        void setOnItemClickCallback(int position);
    }

    /**
     * item触摸事件回调接口
     */
    public interface  OnItemTouchListener{
        void setOnItemTouchCallback(MyContentviewHolder myContentviewHolder);
    }


}
