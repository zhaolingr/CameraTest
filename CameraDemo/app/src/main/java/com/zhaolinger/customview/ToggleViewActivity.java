package com.zhaolinger.customview;

import android.app.Activity;
import android.os.Bundle;

import com.zhaolinger.R;

public class ToggleViewActivity extends Activity {

    private ToggleView toggleView;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggleview);

//        toggleView = (ToggleView) findViewById(R.id.toggleView);
//        toggleView.setSwitchBackgroundResource(R.drawable.switch_background);
//        toggleView.setSlideButtonResource(R.drawable.slide_button);
//        toggleView.setSwitchState(true);
//
        // 设置开关更新监听
//        toggleView.setOnSwitchStateUpdateListener(new ToggleView.OnSwitchStateUpdateListener(){
//
//			@Override
//			public void onStateUpdate(boolean state) {
//				Toast.makeText(getApplicationContext(), "state: " + state, Toast.LENGTH_SHORT).show();
//			}
//
//        });
    }

//	@Override
//	protected void onResume() {
//		super.onResume();
//	}
//    
}
