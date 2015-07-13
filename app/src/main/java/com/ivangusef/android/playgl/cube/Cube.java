package com.ivangusef.android.playgl.cube;

import android.support.annotation.NonNull;

import com.ivangusef.android.playgl.gl.Env;
import com.ivangusef.android.playgl.gl.ShaderProgram;
import com.ivangusef.android.playgl.gl.Shape;

import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES30.GL_TRIANGLES;
import static android.opengl.GLES30.glDrawArrays;

/**
 * Created by Иван on 11.07.2015.
 */
public final class Cube extends Shape {

    private static final float[] COORDS     = {
            // Front face
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,

            // Right face
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,

            // Back face
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,

            // Left face
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,

            // Top face
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,

            // Bottom face
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
    };
    private static final float[] NORMALS    = {
            // Front face
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,

            // Right face
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            // Back face
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,

            // Left face
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,

            // Top face
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,

            // Bottom face
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f
    };
    private static final float[] COLORS     = {
            // Front face (red)
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            // Right face (green)
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,

            // Back face (blue)
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,

            // Left face (yellow)
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            // Top face (cyan)
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,

            // Bottom face (magenta)
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f
    };
    private static final float[] TEX_COORDS = {
            // Front face
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,

            // Right face
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,

            // Back face
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,

            // Left face
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,

            // Top face
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,

            // Bottom face
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    private final FloatBuffer mVertexBuffer;
    private final FloatBuffer mNormalBuffer;
    private final FloatBuffer mColorBuffer;
    //private final FloatBuffer mTexBuffer;

    private final int mTextureHandle;

    public Cube(final int textureHandle) {
        mVertexBuffer = prepareBuffer(COORDS);
        mNormalBuffer = prepareBuffer(NORMALS);
        mColorBuffer = prepareBuffer(COLORS);
        //mTexBuffer = prepareBuffer(TEX_COORDS);

        mTextureHandle = textureHandle;
        if (mTextureHandle != -1) {
            glGenerateMipmap(GL_TEXTURE_2D);
        }
    }

    @Override
    public void draw(@NonNull final Env env) {
        super.draw(env);

        final ShaderProgram program = getProgram();
        //program.linkTextureSampler(mTextureHandle);
        program.linkModelViewProjectionMatrix(env.getMVPMatrix());
        program.linkModelViewMatrix(env.getMVMatrix());
        program.linkLightSource(env.getLightX(), env.getLightY(), env.getLightZ());

        program.linkVertexBuffer(mVertexBuffer, VERTEX_STRIDE);
        program.linkNormalBuffer(mNormalBuffer, NORMAL_STRIDE);
        //program.linkTexture(mTexBuffer, TEXTURE_STRIDE);
        program.linkColorBuffer(mColorBuffer, COLOR_STRIDE);
        glDrawArrays(GL_TRIANGLES, 0, COORDS.length / COORDS_PER_VERTEX);
    }

    @Override
    public String createVertexShaderSource() {
        return "#version 300 es\n" +
                "in vec3 aPosition;\n" +
                "in vec3 aNormal;\n" +
                "in vec4 aColor;\n" +
                "out vec3 vPosition;\n" +
                "out vec3 vNormal;\n" +
                "out vec4 vColor;\n" +
                "uniform mat4 uMVPMatrix;\n" +
                "uniform mat4 uMVMatrix;\n" +
                "void main() {\n" +
                "   vPosition = vec3(uMVMatrix * vec4(aPosition, 0.0));\n" +
                "   vNormal = vec3(uMVMatrix * vec4(aNormal, 0.0));\n" +
                "   vColor = aColor;\n" +
                "   gl_Position = uMVPMatrix * vec4(aPosition, 1.0);\n" +
                "}";
    }

    @Override
    public String createFragmentShaderSource() {
        return "#version 300 es\n" +
                "precision highp float;\n" +
                "in vec3 vPosition;\n" +
                "in vec3 vNormal;\n" +
                "in vec4 vColor;\n" +
                "out vec4 fragmentColor;\n" +
                "uniform vec3 uLightPosition;\n" +
                "void main() {\n" +
                "   float distance = length(uLightPosition - vPosition);\n" +
                "   vec3 lightVector = normalize(uLightPosition - vPosition);\n" +
                "   float diffuse = max(dot(vNormal, lightVector), 0.1);\n" +
                "   diffuse = diffuse * (1.0 / (1.0 + (0.1 * distance * distance)));\n" +
                "   fragmentColor = vColor * diffuse;\n" +
                "}";
    }
}
