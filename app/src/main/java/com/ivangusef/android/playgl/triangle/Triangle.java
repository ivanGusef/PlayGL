package com.ivangusef.android.playgl.triangle;

import android.support.annotation.NonNull;

import com.ivangusef.android.playgl.gl.Env;
import com.ivangusef.android.playgl.gl.ShaderProgram;
import com.ivangusef.android.playgl.gl.Shape;

import java.nio.FloatBuffer;

import static android.opengl.GLES30.GL_TRIANGLES;
import static android.opengl.GLES30.glDrawArrays;

/**
 * Created by Иван on 05.07.2015.
 */
public final class Triangle extends Shape {

    private static final float[] COORDS = {
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };
    private static final float[] COLORS = {
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f
    };

    private final FloatBuffer mVertexBuffer;
    private final FloatBuffer mColorBuffer;

    public Triangle() {
        mVertexBuffer = prepareBuffer(COORDS);
        mColorBuffer = prepareBuffer(COLORS);
    }

    @Override
    public void draw(@NonNull final Env env) {
        super.draw(env);
        final ShaderProgram program = getProgram();
        program.linkVertexBuffer(mVertexBuffer, VERTEX_STRIDE);
        program.linkColorBuffer(mColorBuffer, COLOR_STRIDE);
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }

    @Override
    public String createVertexShaderSource() {
        return "#version 300 es\n" +
                "in vec3 aPosition;\n" +
                "in vec4 aColor;\n" +
                "out vec4 vColor;\n" +
                "void main() {\n" +
                "   gl_Position = vec4(aPosition, 1.0f);\n" +
                "   vColor = aColor;\n" +
                "}";
    }

    @Override
    public String createFragmentShaderSource() {
        return "#version 300 es\n" +
                "precision highp float;\n" +
                "in vec4 vColor;" +
                "out vec4 fragmentColor;\n" +
                "void main() {\n" +
                "  fragmentColor = vColor;\n" +
                "}";
    }
}
