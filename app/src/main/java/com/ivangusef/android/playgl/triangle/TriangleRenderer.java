package com.ivangusef.android.playgl.triangle;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ivangusef.android.playgl.gl.ShapeRenderer;

/**
 * Created by Иван on 05.07.2015.
 */
public final class TriangleRenderer extends ShapeRenderer<Triangle> {

    public TriangleRenderer(@NonNull final Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected Triangle onCreateShape() {
        return new Triangle();
    }
}
