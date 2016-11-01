package com.example.shaomiao.myapplication;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity implements View.OnClickListener {
    ImageView imageView;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                startActivity(new Intent(this,ShoppingCartAnimationActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this,AnimationPopupMenuActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(this,AnimationTestActivity.class));
                break;
            case R.id.btn4:
                startActivity(new Intent(this,AnimationListenEventActivity.class));
                break;
            case R.id.btn5:
                startActivity(new Intent(this, ValueAnimationTestActivity.class));
                break;
        }
    }





}


