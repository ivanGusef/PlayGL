package com.ivangusef.android.playgl.triangle;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ivangusef.android.playgl.RendererActivity;
import com.ivangusef.android.playgl.util.GLUtil;

/**
 * Created by Иван on 05.07.2015.
 */
public final class TriangleActivity extends RendererActivity {

    @NonNull
    public static Intent makeLaunchIntent(@NonNull final Context context) {
        return new Intent(context, TriangleActivity.class);
    }

    @Nullable
    @Override
    protected GLSurfaceView.Renderer onCreateRenderer(@Nullable final Bundle savedInstanceState) {
        return new TriangleRenderer();
    }
}
