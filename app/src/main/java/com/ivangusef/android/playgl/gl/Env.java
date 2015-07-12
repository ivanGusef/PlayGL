package com.ivangusef.android.playgl.gl;

import android.opengl.Matrix;

/**
 * Created by Иван on 11.07.2015.
 */
public final class Env {

    private final float[] mMVPMatrix = new float[16];

    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix       = new float[16];
    private final float[] mModelMatrix      = new float[16];

    private final float[] mRotateMatrix            = new float[16];
    private final float[] mAccumulatedRotateMatrix = new float[16];

    private final float[] mTempMatrix = new float[16];

    private float mCameraX;
    private float mCameraY;
    private float mCameraZ;

    private float mLightX;
    private float mLightY;
    private float mLightZ;

    public float[] getMVPMatrix() {
        return mMVPMatrix;
    }

    public float[] getMVMatrix() {
        Matrix.multiplyMM(mTempMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        return mTempMatrix;
    }

    public float[] getProjectionMatrix() {
        return mProjectionMatrix;
    }

    public float[] getViewMatrix() {
        return mViewMatrix;
    }

    public float[] getModelMatrix() {
        return mModelMatrix;
    }

    public float[] getRotateMatrix() {
        return mRotateMatrix;
    }

    public float[] getAccumulatedRotateMatrix() {
        return mAccumulatedRotateMatrix;
    }

    public float[] getTempMatrix() {
        return mTempMatrix;
    }

    public float getCameraX() {
        return mCameraX;
    }

    public float getCameraY() {
        return mCameraY;
    }

    public float getCameraZ() {
        return mCameraZ;
    }

    public float getLightX() {
        return mLightX;
    }

    public float getLightY() {
        return mLightY;
    }

    public float getLightZ() {
        return mLightZ;
    }

    public void setCameraX(final float cameraX) {
        mCameraX = cameraX;
    }

    public void setCameraY(final float cameraY) {
        mCameraY = cameraY;
    }

    public void setCameraZ(final float cameraZ) {
        mCameraZ = cameraZ;
    }

    public void setLightX(final float lightX) {
        mLightX = lightX;
    }

    public void setLightY(final float lightY) {
        mLightY = lightY;
    }

    public void setLightZ(final float lightZ) {
        mLightZ = lightZ;
    }
}
