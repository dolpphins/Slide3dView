package com.mcb.slide3dview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;

public class MainActivity extends Activity implements View.OnClickListener {
    private View iv_sloop_left;
    private View iv_sloop_center;
    private View iv_sloop_right;

    private int screenWidth;
    private int screenHeight;
    private int viewWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        iv_sloop_left = findViewById(R.id.iv_sloop_left);
        iv_sloop_center = findViewById(R.id.iv_sloop_center);
        iv_sloop_right = findViewById(R.id.iv_sloop_right);

        iv_sloop_right.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                rotate(iv_sloop_left, 0, 30).start();

                rotate(iv_sloop_right, iv_sloop_right.getWidth(), -30).start();

            }
        });

        iv_sloop_left.setOnClickListener(this);
        iv_sloop_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        System.out.println("click " + view.getId());

        int id = view.getId();
        switch (id) {
            case R.id.iv_sloop_left:
                ValueAnimator translateLeft = translate(iv_sloop_left, (screenWidth - viewWidth) / 2);
                ValueAnimator rotationLeft = rotate(iv_sloop_left, 0, 0);

                translateLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {

                        System.out.println("fraction:" + valueAnimator.getAnimatedFraction());
                        System.out.println("current play time:" + valueAnimator.getCurrentPlayTime());
                    }
                });

                ValueAnimator translateCenter = translate(iv_sloop_center, (screenWidth - viewWidth) / 2);
                ValueAnimator rotationCenter = rotate(iv_sloop_center, iv_sloop_center.getWidth(), -30);

                ValueAnimator rotationRight = rotate(iv_sloop_right, iv_sloop_right.getWidth(), -90);

                startAnimators(translateLeft, rotationLeft, translateCenter, rotationCenter, rotationRight);



                break;
            case R.id.iv_sloop_right:

                break;
            default:
        }
    }



    /*********************************************************************************************
     *                                                                                           *
     *                              动画工具方法                                                 *
     *                                                                                           *
     *********************************************************************************************/

    //X方向上移动
    private static ValueAnimator translate(View v, float distance) {
        ValueAnimator animator = ObjectAnimator.ofFloat(v, "translationX", distance);
        animator.setDuration(1500);
        return animator;
    }

    //垂直方向上旋转
    private static ValueAnimator rotate(View v, float pivotX, float degree) {
        ValueAnimator animator = ObjectAnimator.ofFloat(v, "rotationY", degree);
        v.setPivotX(pivotX);
        animator.setDuration(1500);
        return animator;
    }

    //同时开始动画
    private static void startAnimators(Animator...animators) {
        if(animators == null) {
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animators);
        animatorSet.start();
    }


    private void init() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        viewWidth = dip2px(this, 200);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
