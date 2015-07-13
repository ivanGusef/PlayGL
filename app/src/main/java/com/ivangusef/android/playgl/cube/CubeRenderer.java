package com.ivangusef.android.playgl.cube;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ivangusef.android.playgl.gl.ShapeRenderer;

/**
 * Created by Иван on 11.07.2015.
 */
public final class CubeRenderer extends ShapeRenderer<Cube> {

    public CubeRenderer(@NonNull final Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected Cube onCreateShape() {
        return new Cube(/*ShaderProgram.loadTexture(getContext(), R.drawable.ic_russian_post)*/-1);
    }
}
