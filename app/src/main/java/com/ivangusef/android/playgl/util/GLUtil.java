package com.ivangusef.android.playgl.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import static android.opengl.GLES20.*;

/**
 * Created by Иван on 05.07.2015.
 */
public final class GLUtil {

    public static final int VERSION_GLES20 = 0x20000;
    public static final int VERSION_GLES30 = 0x30000;

    public static final int VERSION_INT_GLES20 = 2;
    public static final int VERSION_INT_GLES30 = 3;

    public static boolean isGL2Supported(@NonNull final Context context) {
        return getGLESVersion(context) >= VERSION_GLES20;
    }

    public static boolean isGL3Supported(@NonNull final Context context) {
        return getGLESVersion(context) >= VERSION_GLES30;
    }

    public static void checkError() {
        while (glGetError() != GL_NO_ERROR) {
            Log.w("GLError", "Code: " + glGetError());
        }
    }

    private static int getGLESVersion(@NonNull final Context context) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        return configurationInfo.reqGlEsVersion;
    }

    private GLUtil() {
    }
}
