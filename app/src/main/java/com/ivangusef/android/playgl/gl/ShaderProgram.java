package com.ivangusef.android.playgl.gl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.support.annotation.NonNull;

import com.ivangusef.android.playgl.util.GLUtil;

import java.nio.FloatBuffer;

import static android.opengl.GLES30.GL_CLAMP_TO_EDGE;
import static android.opengl.GLES30.GL_FLOAT;
import static android.opengl.GLES30.GL_FRAGMENT_SHADER;
import static android.opengl.GLES30.GL_NEAREST;
import static android.opengl.GLES30.GL_TEXTURE0;
import static android.opengl.GLES30.GL_TEXTURE_2D;
import static android.opengl.GLES30.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES30.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES30.GL_TEXTURE_WRAP_S;
import static android.opengl.GLES30.GL_TEXTURE_WRAP_T;
import static android.opengl.GLES30.GL_VERTEX_SHADER;
import static android.opengl.GLES30.glActiveTexture;
import static android.opengl.GLES30.glAttachShader;
import static android.opengl.GLES30.glBindTexture;
import static android.opengl.GLES30.glCompileShader;
import static android.opengl.GLES30.glCreateProgram;
import static android.opengl.GLES30.glCreateShader;
import static android.opengl.GLES30.glEnableVertexAttribArray;
import static android.opengl.GLES30.glGenTextures;
import static android.opengl.GLES30.glGetAttribLocation;
import static android.opengl.GLES30.glGetUniformLocation;
import static android.opengl.GLES30.glLinkProgram;
import static android.opengl.GLES30.glShaderSource;
import static android.opengl.GLES30.glTexParameterf;
import static android.opengl.GLES30.glTexParameteri;
import static android.opengl.GLES30.glUniform1i;
import static android.opengl.GLES30.glUniform3f;
import static android.opengl.GLES30.glUniformMatrix4fv;
import static android.opengl.GLES30.glUseProgram;
import static android.opengl.GLES30.glVertexAttribPointer;

/**
 * Created by Иван on 05.07.2015.
 */
public class ShaderProgram {

    public static int loadTexture(final Context context, final int resourceId) {
        final int[] textureHandle = new int[1];

        glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            // Read in the resource
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

            // Bind to the texture in OpenGL
            glBindTexture(GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        }

        if (textureHandle[0] == 0) {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[0];
    }

    private int mProgramHandle;

    public ShaderProgram(@NonNull final String vertexShaderCode, @NonNull final String fragmentShaderCode) {
        createProgram(vertexShaderCode, fragmentShaderCode);
    }

    private void createProgram(@NonNull final String vertexShaderCode, @NonNull final String fragmentShaderCode) {
        final int vertexShaderHandle = loadShader(vertexShaderCode, GL_VERTEX_SHADER);
        final int fragmentShaderHandle = loadShader(fragmentShaderCode, GL_FRAGMENT_SHADER);

        mProgramHandle = glCreateProgram();
        glAttachShader(mProgramHandle, vertexShaderHandle);
        glAttachShader(mProgramHandle, fragmentShaderHandle);
        glLinkProgram(mProgramHandle);
    }

    private int loadShader(@NonNull final String source, final int type) {
        final int shaderHandle = glCreateShader(type);
        glShaderSource(shaderHandle, source);
        glCompileShader(shaderHandle);
        return shaderHandle;
    }

    public void linkVertexBuffer(@NonNull final FloatBuffer vertexBuffer, final int stride) {
        int aPositionHandle = glGetAttribLocation(mProgramHandle, "aPosition");
        GLUtil.checkError();
        glEnableVertexAttribArray(aPositionHandle);
        glVertexAttribPointer(aPositionHandle, 3, GL_FLOAT, false, stride, vertexBuffer);
    }

    public void linkNormalBuffer(@NonNull final FloatBuffer normalBuffer, final int stride) {
        final int aNormalHandle = glGetAttribLocation(mProgramHandle, "aNormal");
        GLUtil.checkError();
        glEnableVertexAttribArray(aNormalHandle);
        glVertexAttribPointer(aNormalHandle, 3, GL_FLOAT, false, stride, normalBuffer);
    }

    public void linkColorBuffer(@NonNull final FloatBuffer colorBuffer, final int stride) {
        final int aColorHandle = glGetAttribLocation(mProgramHandle, "aColor");
        GLUtil.checkError();
        glEnableVertexAttribArray(aColorHandle);
        glVertexAttribPointer(aColorHandle, 4, GL_FLOAT, false, stride, colorBuffer);
    }

    public void linkTexture(@NonNull final FloatBuffer texBuffer, final int stride) {
        final int aTexCoordinateHandle = glGetAttribLocation(mProgramHandle, "aTexCoordinate");
        GLUtil.checkError();
        glEnableVertexAttribArray(aTexCoordinateHandle);
        glVertexAttribPointer(aTexCoordinateHandle, 2, GL_FLOAT, false, stride, texBuffer);
    }

    public void linkModelViewProjectionMatrix(@NonNull final float[] mvpMatrix) {
        final int uMVPMatrixHandle = glGetUniformLocation(mProgramHandle, "uMVPMatrix");
        GLUtil.checkError();
        glUniformMatrix4fv(uMVPMatrixHandle, 1, false, mvpMatrix, 0);
    }

    public void linkModelViewMatrix(@NonNull final float[] modelViewMatrix) {
        final int uMVMatrixHandle = glGetUniformLocation(mProgramHandle, "uMVMatrix");
        GLUtil.checkError();
        glUniformMatrix4fv(uMVMatrixHandle, 1, false, modelViewMatrix, 0);
    }

    public void linkCamera(final float xCamera, final float yCamera, final float zCamera) {
        final int uCameraHandle = glGetUniformLocation(mProgramHandle, "uCamera");
        GLUtil.checkError();
        glUniform3f(uCameraHandle, xCamera, yCamera, zCamera);
    }

    public void linkLightSource(final float xLightPosition, final float yLightPosition, final float zLightPosition) {
        final int uLightPositionHandle = glGetUniformLocation(mProgramHandle, "uLightPosition");
        GLUtil.checkError();
        glUniform3f(uLightPositionHandle, xLightPosition, yLightPosition, zLightPosition);
    }

    public void linkTextureSampler(final int textureHandle) {
        final int uTexture = glGetUniformLocation(mProgramHandle, "uTex");
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureHandle);
        glUniform1i(uTexture, 0);
    }

    public void useProgram() {
        glUseProgram(mProgramHandle);
    }
}
