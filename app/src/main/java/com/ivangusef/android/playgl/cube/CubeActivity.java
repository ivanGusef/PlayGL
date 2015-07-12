package com.ivangusef.android.playgl.cube;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ivangusef.android.playgl.RendererActivity;

/**
 * Created by Иван on 11.07.2015.
 */
public final class CubeActivity extends RendererActivity {

    @NonNull
    public static Intent makeLaunchIntent(@NonNull final Context context) {
        return new Intent(context, CubeActivity.class);
    }

    @Nullable
    @Override
    protected GLSurfaceView.Renderer onCreateRenderer(@Nullable final Bundle savedInstanceState) {
        return null;
    }

    @Nullable
    @Override
    protected GLSurfaceView onCreateSurfaceView(@Nullable final Bundle savedInstanceState) {
        return new CubeSurfaceView(this);
    }
}
