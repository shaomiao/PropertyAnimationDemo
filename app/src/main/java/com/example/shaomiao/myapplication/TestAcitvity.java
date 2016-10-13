package com.example.shaomiao.myapplication;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by shaomiao on 2016-8-29.
 */
public class TestAcitvity extends Activity implements View.OnClickListener {
    private LinearLayout login_div;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        login_div = (LinearLayout) findViewById(R.id.login_div);
        Intent i=getIntent();
        //String data= i.getStringExtra("data");
        Bundle bundle=i.getBundleExtra("data");
        TextView textView= (TextView) findViewById(R.id.textView);
        //textView.setText(String.format("name%s,age%d",bundle.getString("name"),bundle.getInt("age")));


        //gin_div.setOnClickListener(this);
		
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_div:
                Log.e("旋转","sss");
                ObjectAnimator anim= ObjectAnimator
                        .ofFloat(v, "rotationY", 0.0F, 360.0F)
                        .setDuration(1000);
                anim.start();
                break;
        }
    }
}
