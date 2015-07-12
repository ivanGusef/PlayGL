package com.ivangusef.android.playgl.triangle;

import android.support.annotation.NonNull;

import com.ivangusef.android.playgl.gl.ShapeRenderer;

/**
 * Created by Иван on 05.07.2015.
 */
public final class TriangleRenderer extends ShapeRenderer<Triangle> {

    @NonNull
    @Override
    protected Triangle onCreateShape() {
        return new Triangle();
    }
}
