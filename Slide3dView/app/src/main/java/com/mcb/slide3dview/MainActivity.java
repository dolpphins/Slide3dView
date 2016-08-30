package com.mcb.slide3dview;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

public class MainActivity extends Activity implements View.OnClickListener {
    private View iv_sloop_left;
    private View iv_sloop_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_sloop_left = findViewById(R.id.iv_sloop_left);
        iv_sloop_right = findViewById(R.id.iv_sloop_right);

        iv_sloop_right.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                ObjectAnimator animatorLeft = ObjectAnimator.ofFloat(iv_sloop_left, "rotationY", 0, 30f);
                iv_sloop_left.setPivotX(0);
                animatorLeft.setDuration(1500);
                animatorLeft.start();

                ObjectAnimator animatorRight = ObjectAnimator.ofFloat(iv_sloop_right, "rotationY", 0, -30f);
                iv_sloop_right.setPivotX(580);
                animatorRight.setDuration(1500);
                animatorRight.start();
            }
        });

        iv_sloop_left.setOnClickListener(this);
        iv_sloop_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        System.out.println("click " + view.getId());
    }
}
