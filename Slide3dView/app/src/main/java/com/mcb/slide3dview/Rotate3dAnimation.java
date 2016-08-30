/**
 * @Title: Rotate3dAnimation.java
 * @Package com.sloop.animation
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015年3月10日 上午11:20:10
 * @version V1.0
 */

package com.mcb.slide3dview;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.ContextThemeWrapper;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3dAnimation extends Animation {

	private final float mFromDegrees;
	private final float mToDegrees;
	private final float mCenterX;
	private final float mCenterY;
	private final float mDepthZ;
	private final boolean mReverse;
	private Camera mCamera;
	ContextThemeWrapper context;
	float scale = 1;

	public Rotate3dAnimation(ContextThemeWrapper context, float fromDegrees, float toDegrees, float centerX, float centerY, float depthZ, boolean reverse) {
		this.context = context;
		mFromDegrees = fromDegrees;
		mToDegrees = toDegrees;
		mCenterX = centerX;
		mCenterY = centerY;
		mDepthZ = depthZ;
		mReverse = reverse;
		scale = context.getResources().getDisplayMetrics().density;
	}

	@Override
	public void initialize(int width, int height, int parentWidth, int parentHeight) {
		
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera = new Camera();
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		final float fromDegrees = mFromDegrees;
		float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

		
		final float centerX = mCenterX;
		final float centerY = mCenterY;
		final Camera camera = mCamera;

		final Matrix matrix = t.getMatrix();

		camera.save();
		if (mReverse) {
			camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
		} else {
			camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
		}
		camera.rotateY(degrees);
		camera.getMatrix(matrix);
		camera.restore();

		float[] mValues = {0,0,0,0,0,0,0,0,0};
		matrix.getValues(mValues);
		mValues[6] = mValues[6]/scale;
		matrix.setValues(mValues);

		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
	}
}
