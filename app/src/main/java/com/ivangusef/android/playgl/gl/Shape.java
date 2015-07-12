package com.ivangusef.android.playgl.gl;

import android.support.annotation.NonNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Иван on 11.07.2015.
 */
public abstract class Shape {
    protected int BYTES_PER_FLOAT   = 4;
    protected int BYTES_PER_SHORT   = 2;
    protected int COORDS_PER_VERTEX = 3;
    protected int COORDS_PER_NORMAL = 3;
    protected int COORDS_PER_COLOR  = 4;

    protected int VERTEX_STRIDE = COORDS_PER_VERTEX * BYTES_PER_FLOAT;
    protected int COLOR_STRIDE  = COORDS_PER_COLOR * BYTES_PER_FLOAT;
    protected int NORMAL_STRIDE = COORDS_PER_NORMAL * BYTES_PER_FLOAT;

    private final ShaderProgram mProgram;

    protected Shape() {
        mProgram = new ShaderProgram(createVertexShaderSource(), createFragmentShaderSource());
    }

    protected ShaderProgram getProgram() {
        return mProgram;
    }

    @NonNull
    protected final FloatBuffer prepareBuffer(@NonNull final float[] data) {
        final ByteBuffer bb = ByteBuffer.allocateDirect(data.length * BYTES_PER_FLOAT);
        bb.order(ByteOrder.nativeOrder());

        final FloatBuffer buffer = bb.asFloatBuffer();
        buffer.put(data);
        buffer.position(0);
        return buffer;
    }

    @NonNull
    protected final ShortBuffer prepareBuffer(@NonNull final short[] data) {
        final ByteBuffer bb = ByteBuffer.allocateDirect(data.length * BYTES_PER_SHORT);
        bb.order(ByteOrder.nativeOrder());

        final ShortBuffer buffer = bb.asShortBuffer();
        buffer.put(data);
        buffer.position(0);
        return buffer;
    }

    public void draw(@NonNull final Env env) {
        mProgram.useProgram();
    }

    public abstract String createVertexShaderSource();

    public abstract String createFragmentShaderSource();
}
