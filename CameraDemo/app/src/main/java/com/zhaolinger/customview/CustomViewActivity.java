package com.zhaolinger.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhaolinger.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomViewActivity extends AppCompatActivity {

    @BindView(R.id.childTextView)
    TextView childTextView;
    @BindView(R.id.childButton)
    Button childButton;
    @BindView(R.id.customLinearLayout)
    CustomLinearLayoutAgain customLinearLayout;
    @BindView(R.id.activity_custom_view)
    RelativeLayout activityCustomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //Log.i("test", "activity super.dispatchTouchEvent(ev)==" + super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.i("test","super.onTouchEvent(ev)=="+super.onTouchEvent(event));
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("test","activity MotionEvent.ACTION_DOWN==");
                return false;
            //break;
            case MotionEvent.ACTION_UP:
                Log.i("test","activity MotionEvent.ACTION_UP==");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("test","activity MotionEvent.ACTION_MOVE==");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

}
