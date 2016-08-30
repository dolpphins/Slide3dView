package com.mcb.slide3dview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;

public class MainActivity extends Activity {
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

                applyRotation(iv_sloop_left, 0, 45);
                applyRotation(iv_sloop_right, 0, -45);
            }
        });
    }

    private void applyRotation(View v, float start, float end) {
        float centerX = v.getWidth() / 2.0f;
        float centerY = v.getHeight() / 2.0f;

        if(v == iv_sloop_left) {
            centerX = 0;
        } else if(v == iv_sloop_right) {
            centerX *= 2;
        }

        final Rotate3dAnimation rotation = new Rotate3dAnimation(this, start, end, centerX, centerY, 1.0f, true);
        rotation.setDuration(1500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());

        v.startAnimation(rotation);
    }

}
