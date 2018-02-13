package com.jackralf.hellophoto;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by admin on 2018/2/6.
 */

public class Triangle {
    private FloatBuffer vertexBuffer;

    final int COORDS_PER_VERTEX = 3;
    float mTriangleCoords[] = {
        0.0f,  1.0f, 0.0f, // top
        -1.0f, -1.0f, 0.0f, // bottom left
        1.0f, -0.98f, 0.0f  // bottom right
    };

    float mColor[] = {
        0.63671875f, 0.76953125f, 0.22265625f, 1.0f
    };

    private final int vertexCount = mTriangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    private final int mProgram;
    private int mPositionHandle;
    private int mColorHandle;

    public Triangle() {

        ByteBuffer bb = ByteBuffer.allocateDirect(mTriangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());

        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(mTriangleCoords);
        vertexBuffer.position(0);

        mProgram = HPUtil.getInstance().createProgram("vertex", "fragment");
    }

    public void setColor(float color[]) {
        mColor = color;
    }

    public void setCoords(float coords[]) {
        mTriangleCoords = coords;
        vertexBuffer.clear();
        vertexBuffer.put(mTriangleCoords);
        vertexBuffer.position(0);
    }

    public void draw() {
        GLES20.glUseProgram(mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, mColor, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
