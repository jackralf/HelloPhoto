package com.jackralf.hellophoto;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;

/**
 * Created by admin on 2018/2/6.
 */

public class Lines {
    public static final String TAG = Lines.class.getSimpleName();
    private HashMap<Integer, FloatBuffer> mLineMap;
    private HashMap<Integer, Integer> mLinePointCountMap;
    private int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mCount = 0;

    float mColor[] = {
            0.0f, 0.0f, 0.0f, 1.0f
    };

    public Lines() {
        mLineMap = new HashMap<>();
        mLinePointCountMap = new HashMap<>();
        mProgram = HPUtil.getInstance().createProgram("vertex", "fragment");
    }

    public void draw() {
        GLES20.glUseProgram(mProgram);

        for(Integer id : mLineMap.keySet()) {
            FloatBuffer buf = mLineMap.get(id);
            int count = mLinePointCountMap.get(id);
            Log.d(TAG, "id:" + id + ", count:" + count);

            mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
            GLES20.glEnableVertexAttribArray(mPositionHandle);
            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
            GLES20.glUniform4fv(mColorHandle, 1, mColor, 0);
            GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 3 * 4, buf);
            GLES20.glLineWidth(50);
            GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, count);
            GLES20.glDisableVertexAttribArray(mPositionHandle);
        }
    }

    public void addPoint(int id, float x, float y) {
        x = (float) (2.0 * x - 1);
        y = (float) (-2.0 * y + 1);
        Log.d(TAG, "id:" + id + " ,x:" + x + ",y:" + y);
        FloatBuffer buf;
        if(mLineMap.containsKey(id)){
            buf = mLineMap.get(id);

        } else {
            ByteBuffer bb = ByteBuffer.allocateDirect(1024 * 1024 * 4);
            bb.order(ByteOrder.nativeOrder());

            buf = bb.asFloatBuffer();
            buf.position(0);
            mLineMap.put(id, buf);
            mLinePointCountMap.put(id, 0);
        }

        int count = mLinePointCountMap.get(id);
        mLinePointCountMap.put(id, count + 1);
        float coords[] = {x, y, 0.0f};
        buf.position(count * 3);
        buf.put(coords);
        buf.position(0);
    }

    public void clear() {
        for(Integer id : mLineMap.keySet()) {
            FloatBuffer buf = mLineMap.get(id);
            buf.clear();
        }
        mLineMap.clear();
        mLinePointCountMap.clear();
    }
}
