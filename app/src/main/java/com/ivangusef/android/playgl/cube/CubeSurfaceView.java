package com.ivangusef.android.playgl.cube;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.ivangusef.android.playgl.util.GLUtil;

/**
 * Created by Иван on 12.07.2015.
 */
public final class CubeSurfaceView extends GLSurfaceView implements GestureDetector.OnGestureListener {

    private final float           mDensity;
    private final GestureDetector mGestureDetector;

    private CubeRenderer mRenderer;

    public CubeSurfaceView(@NonNull final Context context) {
        this(context, null);
    }

    public CubeSurfaceView(@NonNull final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(GLUtil.VERSION_INT_GLES30);
        setRenderer(new CubeRenderer(context));
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        mDensity = context.getResources().getDisplayMetrics().density;
        mGestureDetector = new GestureDetector(getResources(), this);
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
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public void onTranslate(final float dx, final float dy) {
        updateRenderer(dx, dy, 0);
    }

    @Override
    public void onRotate(final float dz) {
        updateRenderer(0, 0, dz);
    }

    private void updateRenderer(final float dx, final float dy, final float dz) {
        if (mRenderer != null) {
            mRenderer.deltaX(dx);
            mRenderer.deltaY(dy);
            mRenderer.deltaZ(dz);
            requestRender();
        }
    }
}

class GestureDetector {

    private static final int INVALID_POINTER_ID = -1;

    interface OnGestureListener {
        void onTranslate(final float dx, final float dy);

        void onRotate(final float dz);
    }

    private final OnGestureListener mListener;

    private final float mDensity;

    private float startX, startY;
    private float ptrX1, ptrY1, ptrX2, ptrY2;
    private int ptrID1, ptrID2;

    GestureDetector(@NonNull final Resources res, @NonNull final OnGestureListener listener) {
        mListener = listener;
        mDensity = res.getDisplayMetrics().density;
        ptrID1 = INVALID_POINTER_ID;
        ptrID2 = INVALID_POINTER_ID;
    }

    public boolean onTouchEvent(@NonNull final MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                ptrID1 = event.getPointerId(event.getActionIndex());
                startX = event.getX(event.findPointerIndex(ptrID1));
                startY = event.getY(event.findPointerIndex(ptrID1));
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (ptrID2 != INVALID_POINTER_ID) {
                    return false;
                }
                ptrID2 = event.getPointerId(event.getActionIndex());
                ptrX1 = event.getX(event.findPointerIndex(ptrID1));
                ptrY1 = event.getY(event.findPointerIndex(ptrID1));
                ptrX2 = event.getX(event.findPointerIndex(ptrID2));
                ptrY2 = event.getY(event.findPointerIndex(ptrID2));
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerId = event.getPointerId(event.getActionIndex());
                if (pointerId != ptrID1 && pointerId != ptrID2) {
                    return false;
                }
                if (ptrID1 != INVALID_POINTER_ID) {
                    if (ptrID2 != INVALID_POINTER_ID) {
                        final float ptr1X, ptr1Y, ptr2X, ptr2Y;
                        ptr1X = event.getX(event.findPointerIndex(ptrID1));
                        ptr1Y = event.getY(event.findPointerIndex(ptrID1));
                        ptr2X = event.getX(event.findPointerIndex(ptrID2));
                        ptr2Y = event.getY(event.findPointerIndex(ptrID2));
                        mListener.onRotate(angleBetweenLines(ptrX2, ptrY2, ptrX1, ptrY1, ptr2X, ptr2Y, ptr1X, ptr1Y));

                        ptrX1 = ptr1X;
                        ptrY1 = ptr1Y;
                        ptrX2 = ptr2X;
                        ptrY2 = ptr2Y;
                    } else {
                        final float ptrX, ptrY;
                        ptrX = event.getX();
                        ptrY = event.getY();
                        mListener.onTranslate(translation(startX, ptrX), translation(startY, ptrY));
                        startX = ptrX;
                        startY = ptrY;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                ptrID1 = INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                pointerId = event.getPointerId(event.getActionIndex());
                if (pointerId == ptrID1) {
                    startX = event.getX(event.findPointerIndex(ptrID2));
                    startY = event.getY(event.findPointerIndex(ptrID2));
                    ptrID1 = ptrID2;
                } else if (pointerId == ptrID2) {
                    startX = event.getX(event.findPointerIndex(ptrID1));
                    startY = event.getY(event.findPointerIndex(ptrID1));
                } else {
                    return false;
                }
                ptrID2 = INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_CANCEL:
                ptrID1 = INVALID_POINTER_ID;
                ptrID2 = INVALID_POINTER_ID;
                break;
        }
        return true;
    }

    private float angleBetweenLines(final float fX, final float fY, final float sX, final float sY, final float nfX, final float nfY,
                                    final float nsX, final float nsY) {
        float angle1 = (float) Math.atan2((fY - sY), (fX - sX));
        float angle2 = (float) Math.atan2((nfY - nsY), (nfX - nsX));

        float angle = ((float) Math.toDegrees(angle1 - angle2)) % 360;
        if (angle < -180.f) angle += 360.0f;
        if (angle > 180.f) angle -= 360.0f;
        return angle;
    }

    private float translation(final float start, final float end) {
        return (end - start) / mDensity / 2f;
    }
}
