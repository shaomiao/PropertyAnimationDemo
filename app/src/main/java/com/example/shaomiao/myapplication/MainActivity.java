package com.example.shaomiao.myapplication;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.media.Image;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import junit.framework.Test;

import java.net.URI;
import java.util.logging.Logger;

public class MainActivity extends Activity implements View.OnClickListener {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
        imageView= (ImageView) findViewById(R.id.image);
        rl = (RelativeLayout) findViewById(R.id.rl);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                addAnimal(imageView);
                break;
        }
    }
    private RelativeLayout rl;
    private PathMeasure mPathMeasure;
    private float[] mCurrentPosition = new float[2];
    private void addAnimal(ImageView iv){
        final ImageView goods = new ImageView(MainActivity.this);
        goods.setImageDrawable(iv.getDrawable());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(30, 30);
        rl.addView(goods, params);
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(0,0);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo(300,500 , 0, 1000);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        mPathMeasure = new PathMeasure(path, false);
        //★★★属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(400);
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // ★★★★★获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });
//      五、 开始执行动画
        valueAnimator.start();
    }
    private void addCart(ImageView iv) {
//      一、创造出执行动画的主题---imageview
        //代码new一个imageview，图片资源是上面的imageview的图片
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
//        final ImageView goods = new ImageView(ProductDetailActivity.this);
//        goods.setImageDrawable(iv.getDrawable());
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(30, 30);
//        rl.addView(goods, params);
//
////        二、计算动画开始/结束点的坐标的准备工作
//        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
//        int[] parentLocation = new int[2];
//        rl.getLocationInWindow(parentLocation);
//        Log.e("addCart:0 ", "x" + parentLocation[0] + "y" + parentLocation[1]);
//        //得到商品图片的坐标（用于计算动画开始的坐标）
//        int startLoc[] = new int[2];
//        iv.getLocationInWindow(startLoc);
//        Log.e("addCart: ", "x" + startLoc[0] + "y" + startLoc[1]);
//
//        //得到购物车图片的坐标(用于计算动画结束后的坐标)
//        int endLoc[] = new int[2];
//        findViewById(R.id.tool_bar_cart).getLocationInWindow(endLoc);
//        Log.e("addCart1: ", "x" + endLoc[0] + "y" + endLoc[1]);
//
//
////        三、正式开始计算动画开始/结束的坐标
//        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
//        float startX = startLoc[0] - parentLocation[0] + iv.getWidth() / 4;
//        float startY = startLoc[1] - parentLocation[1] + iv.getHeight() / 4;
//
//        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
//        float toX = endLoc[0] - parentLocation[0] + findViewById(R.id.tool_bar_cart).getWidth() / 5;
//        float toY = endLoc[1] - parentLocation[1];
//
////        四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
//        //开始绘制贝塞尔曲线
//        Path path = new Path();
//        //移动到起始点（贝塞尔曲线的起点）
//        path.moveTo(startX, startY);
//        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
//        path.quadTo((startX + toX) / 3, startY, toX, toY);
//        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
//        // 如果是true，path会形成一个闭环
//        mPathMeasure = new PathMeasure(path, false);
//
//        //★★★属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
//        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
//        valueAnimator.setDuration(400);
//        // 匀速线性插值器
//        valueAnimator.setInterpolator(new LinearInterpolator());
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                // 当插值计算进行时，获取中间的每个值，
//                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
//                float value = (Float) animation.getAnimatedValue();
//                // ★★★★★获取当前点坐标封装到mCurrentPosition
//                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
//                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
//                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
//                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
//                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
//                goods.setTranslationX(mCurrentPosition[0]);
//                goods.setTranslationY(mCurrentPosition[1]);
//            }
//        });
////      五、 开始执行动画
//        valueAnimator.start();
//
////      六、动画结束后的处理
//        valueAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            //当动画结束后：
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                // 购物车的数量加1
////                i++;
////                count.setText(String.valueOf(i));
////                // 把移动的图片imageview从父布局里移除
//                rl.removeView(goods);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
    }

    public void onClickAnimation(View view) {
        //属性动画
//        ObjectAnimator anim=ObjectAnimator
//                .ofFloat(view, "rotationY", 0.0F, 360.0F)
//                .setDuration(10000);
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



}


