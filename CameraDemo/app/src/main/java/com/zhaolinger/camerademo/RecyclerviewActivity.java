package com.zhaolinger.camerademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerviewActivity extends AppCompatActivity {

    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;
    private CustomRecyclerviewAdapter mCustomRecyclerviewAdapter;
    private List<CustomRecyclerviewDataBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); //默认
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mCustomRecyclerviewAdapter = new CustomRecyclerviewAdapter();
        mRecyclerview.setAdapter(mCustomRecyclerviewAdapter);

        //模拟数据
        for(int i=0;i<5;i++){
            list.add(new CustomRecyclerviewDataBean("小明名",16));
            list.add(new CustomRecyclerviewDataBean("小花",19));
        }
        mCustomRecyclerviewAdapter.updateAdapter(list);


        //设置item监听
        CustomRecyclerviewAdapter.OnItemClickListener onItemClickListener = new CustomRecyclerviewAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickCallback(int position) {
                Toast.makeText(RecyclerviewActivity.this, "点击的position=="+position, Toast.LENGTH_SHORT).show();
            }
        };
        mCustomRecyclerviewAdapter.setmOnItemClickListener(onItemClickListener);

        itemTouch();

    }

    /**
     * item的拖拽滑动
     * 由ItemTouchHelper实现
     */
    private void itemTouch() {

        RecyclerviewItemTouchCallback mRecyclerviewItemTouchCallback = new RecyclerviewItemTouchCallback();
        final ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mRecyclerviewItemTouchCallback);

        mItemTouchHelper.attachToRecyclerView(mRecyclerview);

        mRecyclerviewItemTouchCallback.setmOnTouchDragListener(new RecyclerviewItemTouchCallback.OnTouchDragListener() {
            @Override
            public boolean setOnTouchDragCallback(int oldPosition, int newPosition) {
                if(list != null){
                    //交换数据的位置
                    Collections.swap(list,oldPosition,newPosition);
                    //交换item的位置
                    mCustomRecyclerviewAdapter.notifyItemMoved(oldPosition,newPosition);
                    return true;
                }
                return false;
            }
        });
        mRecyclerviewItemTouchCallback.setmOnTouchSwipeListener(new RecyclerviewItemTouchCallback.OnTouchSwipeListener() {
            @Override
            public void setOnTouchSwipeCallback(int positon) {
                if(list != null){
                    list.remove(positon);
                    mCustomRecyclerviewAdapter.notifyItemRemoved(positon);
                }
            }
        });

//        //touch图片也能实现拖拽
//        mCustomRecyclerviewAdapter.setmOnItemTouchListener(new CustomRecyclerviewAdapter.OnItemTouchListener() {
//            @Override
//            public void setOnItemTouchCallback(CustomRecyclerviewAdapter.MyContentviewHolder myContentviewHolder) {
//                mItemTouchHelper.startDrag(myContentviewHolder);
//            }
//        });

    }
}
