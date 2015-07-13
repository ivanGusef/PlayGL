package com.ivangusef.android.playgl.gl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.support.annotation.NonNull;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES30.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES30.GL_CULL_FACE;
import static android.opengl.GLES30.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES30.GL_DEPTH_TEST;
import static android.opengl.GLES30.glClear;
import static android.opengl.GLES30.glClearColor;
import static android.opengl.GLES30.glEnable;
import static android.opengl.GLES30.glViewport;

/**
 * Created by Иван on 11.07.2015.
 */
public abstract class ShapeRenderer<S extends Shape> implements GLSurfaceView.Renderer {

    private final Context mContext;
    private final Env     mEnv;

    private S mShape;

    private volatile float mDeltaX, mDeltaY;

    public ShapeRenderer(@NonNull final Context context) {
        mContext = context;
        mEnv = new Env();
    }

    public void deltaX(final float deltaX) {
        mDeltaX += deltaX;
    }

    public void deltaY(final float deltaY) {
        mDeltaY += deltaY;
    }

    public Context getContext() {
        return mContext;
    }

    public Env getEnv() {
        return mEnv;
    }

    public S getShape() {
        return mShape;
    }

    @Override
    public final void onSurfaceCreated(final GL10 gl, final EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        // Use culling to remove back faces.
        glEnable(GL_CULL_FACE);
        // Enable depth testing
        glEnable(GL_DEPTH_TEST);
        // Position the eye in front of the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 4.0f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = 0.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;
        Matrix.setLookAtM(mEnv.getViewMatrix(), 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        Matrix.setIdentityM(mEnv.getModelMatrix(), 0);
        Matrix.setIdentityM(mEnv.getAccumulatedRotateMatrix(), 0);

        mEnv.setCameraX(eyeX);
        mEnv.setCameraY(eyeY);
        mEnv.setCameraZ(eyeZ);

        mEnv.setLightX(eyeX);
        mEnv.setLightY(eyeY);
        mEnv.setLightZ(eyeZ);

        mShape = onCreateShape();
    }

    @Override
    public final void onSurfaceChanged(final GL10 gl, final int width, final int height) {
        glViewport(0, 0, width, height);

        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;

        Matrix.frustumM(mEnv.getProjectionMatrix(), 0, left, right, bottom, top, near, far);
    }

    @Override
    public final void onDrawFrame(final GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        Matrix.setIdentityM(mEnv.getModelMatrix(), 0);
        Matrix.setRotateM(mEnv.getRotateMatrix(), 0, mDeltaX, 0.0f, 1.0f, 0.0f);
        Matrix.rotateM(mEnv.getRotateMatrix(), 0, mDeltaY, 1.0f, 0.0f, 0.0f);
        mDeltaX = 0.0f;
        mDeltaY = 0.0f;

        Matrix.multiplyMM(mEnv.getTempMatrix(), 0, mEnv.getRotateMatrix(), 0, mEnv.getAccumulatedRotateMatrix(), 0);
        System.arraycopy(mEnv.getTempMatrix(), 0, mEnv.getAccumulatedRotateMatrix(), 0, 16);

        Matrix.multiplyMM(mEnv.getTempMatrix(), 0, mEnv.getModelMatrix(), 0, mEnv.getAccumulatedRotateMatrix(), 0);
        System.arraycopy(mEnv.getTempMatrix(), 0, mEnv.getModelMatrix(), 0, 16);

        Matrix.multiplyMM(mEnv.getMVPMatrix(), 0, mEnv.getViewMatrix(), 0, mEnv.getModelMatrix(), 0);
        Matrix.multiplyMM(mEnv.getTempMatrix(), 0, mEnv.getProjectionMatrix(), 0, mEnv.getMVPMatrix(), 0);
        System.arraycopy(mEnv.getTempMatrix(), 0, mEnv.getMVPMatrix(), 0, 16);

        mShape.draw(mEnv);
    }

    @NonNull
    protected abstract S onCreateShape();
}
