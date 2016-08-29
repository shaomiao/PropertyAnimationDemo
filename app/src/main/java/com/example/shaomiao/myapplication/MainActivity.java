package com.example.shaomiao.myapplication;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import junit.framework.Test;

import java.util.logging.Logger;

public class MainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv=(ImageView) findViewById(R.id.image);
        iv.setOnClickListener(this);
    }

    public void onClickAnimation(View view) {
        //属性动画
//        ObjectAnimator anim=ObjectAnimator
//                .ofFloat(view, "rotationY", 0.0F, 360.0F)
//                .setDuration(1000);
//        anim.start();
        Intent intent=new Intent(MainActivity.this,TestAcitvity.class);  //方法1
        startActivity(intent);
        //安卓组合动画
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
//        {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation)
//            {
//                float cVal = (Float) animation.getAnimatedValue();
//                view.setAlpha(cVal);
//                view.setScaleX(cVal);
//                view.setScaleY(cVal);
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this, TestAcitvity.class);
        startActivity(intent);
    }
}


