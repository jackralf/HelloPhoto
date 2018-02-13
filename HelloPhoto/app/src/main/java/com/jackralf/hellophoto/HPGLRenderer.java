package com.jackralf.hellophoto;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by admin on 2018/2/5.
 */

public class HPGLRenderer implements GLSurfaceView.Renderer {

    public static final String TAG = HPGLRenderer.class.getSimpleName();
    private Triangle mTriangle;
    private Lines mLines;
    private int mWidth, mHeight;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        mTriangle = new Triangle();
        mLines = new Lines();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG, "width:" + width + ", height:" + height);
        mWidth = width;
        mHeight = height;
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        double time = System.currentTimeMillis() / 1000.0;

//        float[] color = {0.0f, (float) Math.cos(time), 0.0f, 1.0f};
//        mTriangle.setColor(color);
//        mTriangle.draw();
        mLines.draw();
    }

    public void addLinePoint(int id, float x, float y) {
        mLines.addPoint(id, x / mWidth, y / mHeight);
    }

    public void clear() {
        mLines.clear();
    }
}
