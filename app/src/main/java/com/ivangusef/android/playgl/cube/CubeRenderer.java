package com.ivangusef.android.playgl.cube;

import android.support.annotation.NonNull;

import com.ivangusef.android.playgl.gl.ShapeRenderer;

/**
 * Created by Иван on 11.07.2015.
 */
public final class CubeRenderer extends ShapeRenderer<Cube> {

    @NonNull
    @Override
    protected Cube onCreateShape() {
        return new Cube();
    }
}
