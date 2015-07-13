package com.ivangusef.android.playgl.cube;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.ivangusef.android.playgl.util.GLUtil;

/**
 * Created by Иван on 12.07.2015.
 */
public final class CubeSurfaceView extends GLSurfaceView {

    private final float mDensity;

    private CubeRenderer mRenderer;
    private float        mPreviousX, mPreviousY;

    public CubeSurfaceView(@NonNull final Context context) {
        this(context, null);
    }

    public CubeSurfaceView(@NonNull final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(GLUtil.VERSION_INT_GLES30);
        setRenderer(new CubeRenderer(context));
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        mDensity = context.getResources().getDisplayMetrics().density;
    }

    @Override
    public void setRenderer(@NonNull final Renderer renderer) {
        super.setRenderer(renderer);
        if (renderer instanceof CubeRenderer) {
            mRenderer = (CubeRenderer) renderer;
        } else {
            throw new IllegalArgumentException("Unsupported renderer has been passed: " + renderer.getClass().getName());
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull final MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (mRenderer != null) {
                final float deltaX = (x - mPreviousX) / mDensity / 2f;
                final float deltaY = (y - mPreviousY) / mDensity / 2f;

                mRenderer.deltaX(deltaX);
                mRenderer.deltaY(deltaY);
                requestRender();
            }
        }

        mPreviousX = x;
        mPreviousY = y;

        return true;
    }
}
