package com.example.shaomiao.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ShoppingCartAnimationActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    private int screenWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart_animation);
        findViewById(R.id.btn).setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.image);
        rl = (RelativeLayout) findViewById(R.id.rl);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density1 = dm.density;
        int width3 = dm.widthPixels;
        int height3 = dm.heightPixels;
        screenWidth = dm.widthPixels;

    }
    private RelativeLayout rl;
    private PathMeasure mPathMeasure;
    private float[] mCurrentPosition = new float[2];

    private void addAnimal(Activity activity, ImageView iv, int end_position_x, int end_position_y, int screenwidth) {
        final ImageView goods = new ImageView(activity);
        goods.setImageDrawable(iv.getDrawable());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        rl.addView(goods, params);
        Path path = new Path();
        /**
         * 根据
         */
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        rl.getLocationInWindow(parentLocation);
        Log.e("addCart:0 ", "x" + parentLocation[0] + "y" + parentLocation[1]);
        //得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        iv.getLocationInWindow(startLoc);
        Log.e("addCart: ", "x" + startLoc[0] + "y" + startLoc[1]);
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startLoc[0] - 30, startLoc[1] - 30);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo(300, 500, 1080, 1000);
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
       // 六、动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            //当动画结束后：
            @Override
            public void onAnimationEnd(Animator animation) {
                // 购物车的数量加1
//                i++;
//                count.setText(String.valueOf(i));
//                // 把移动的图片imageview从父布局里移除
                rl.removeView(goods);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    /**
     * 添加购物车添加动画
     * @param rl 添加的视图
     * @param activity 添加的activity
     * @param iv 复制的图片id
     * @param end 移动的终点坐标
     * @param start 启始的坐标
     * @param position1 控制点
     * @param position2 控制点
     * @param dm 获取屏幕参数的
     */
    private void addAnimal(final ViewGroup rl, Activity activity, int iv, int[] end, int[] start, int[] position1, int[] position2, DisplayMetrics dm){
        final ImageView goods = new ImageView(activity);
        goods.setImageResource(iv);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(20*dm.density), (int)(20*dm.density));
        rl.addView(goods, params);
        Path path = new Path();

        /**
         * 根据
         */
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        rl.getLocationInWindow(parentLocation);
        Log.e("addCart:0 ", "x" + parentLocation[0] + "y" + parentLocation[1]);
        //得到商品图片的坐标（用于计算动画开始的坐标）
//        int startLoc[] = new int[2];
//        iv.getLocationInWindow(startLoc);
        //Log.e("addCart: ", "x" + startLoc[0] + "y" + startLoc[1]);
        //移动到起始点（贝塞尔曲线的起点）

        path.moveTo(start[0],start[1]);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.cubicTo(position1[0],position1[1],position2[0],position2[1] , end[0], end[1]);
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
        //动画结束后释放
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rl.removeView(goods);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                addAnimal(ShoppingCartAnimationActivity.this,imageView,0,1000,1080);
                break;
        }
    }
}
