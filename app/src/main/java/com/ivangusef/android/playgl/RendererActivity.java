package com.ivangusef.android.playgl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;

import com.ivangusef.android.playgl.util.GLUtil;

/**
 * Created by Иван on 11.07.2015.
 */
public abstract class RendererActivity extends AppCompatActivity {

    private GLSurfaceView mSurfaceView;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!GLUtil.isGL3Supported(this)) {
            finish();
            return;
        }

        final GLSurfaceView surfaceView = onCreateSurfaceView(savedInstanceState);
        if (surfaceView != null) {
            mSurfaceView = surfaceView;
        } else {
            mSurfaceView = new GLSurfaceView(this);
            mSurfaceView.setEGLContextClientVersion(GLUtil.VERSION_INT_GLES30);
            mSurfaceView.setRenderer(onCreateRenderer(savedInstanceState));
        }
        setContentView(mSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        mSurfaceView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mSurfaceView = null;
        super.onDestroy();
    }

    protected final GLSurfaceView getSurfaceView() {
        return mSurfaceView;
    }

    @Nullable
    protected GLSurfaceView onCreateSurfaceView(@Nullable final Bundle savedInstanceState) {
        return null;
    }

    @Nullable
    protected abstract GLSurfaceView.Renderer onCreateRenderer(@Nullable final Bundle savedInstanceState);
}
