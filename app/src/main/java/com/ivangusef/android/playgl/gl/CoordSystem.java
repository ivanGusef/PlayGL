package com.ivangusef.android.playgl.gl;

import android.support.annotation.NonNull;

import java.nio.FloatBuffer;

import static android.opengl.GLES30.GL_LINES;
import static android.opengl.GLES30.glDrawArrays;

/**
 * Created by Иван on 12.07.2015.
 */
public final class CoordSystem extends Shape {
    private static final float[] COORDS = {
            0.0f, 0.0f, 0.0f,
            10.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 10.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 10.0f
    };
    private static final float[] COLORS = {
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f
    };

    private final FloatBuffer mVertexBuffer;
    private final FloatBuffer mColorBuffer;

    public CoordSystem() {
        mVertexBuffer = prepareBuffer(COORDS);
        mColorBuffer = prepareBuffer(COLORS);
    }

    @Override
    public void draw(@NonNull final Env env) {
        super.draw(env);

        final ShaderProgram program = getProgram();
        program.linkVertexBuffer(mVertexBuffer, VERTEX_STRIDE);
        program.linkColorBuffer(mColorBuffer, COLOR_STRIDE);
        program.linkModelViewProjectionMatrix(env.getMVPMatrix());
        glDrawArrays(GL_LINES, 0, COORDS.length / COORDS_PER_VERTEX);
    }

    @Override
    public String createVertexShaderSource() {
        return "#version 300 es\n" +
                "in vec3 aPosition;\n" +
                "in vec4 aColor;\n" +
                "out vec4 vColor;\n" +
                "uniform mat4 uMVPMatrix;\n" +
                "void main() {\n" +
                "   gl_Position = uMVPMatrix * vec4(aPosition, 1.0f);\n" +
                "   vColor = aColor;\n" +
                "}";
    }

    @Override
    public String createFragmentShaderSource() {
        return "#version 300 es\n" +
                "precision highp float;\n" +
                "in vec4 vColor;\n" +
                "out vec4 fragmentColor;\n" +
                "void main() {\n" +
                "  fragmentColor = vColor;\n" +
                "}";
    }
}
