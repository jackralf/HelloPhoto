package com.jackralf.hellophoto;

import android.content.Context;
import android.opengl.GLES20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

/**
 * Created by admin on 2018/2/6.
 */

public class HPUtil {
    static private HPUtil mInstance;
    private Context mContext;

    static public HPUtil getInstance() {
        if(mInstance == null) {
            mInstance = new HPUtil();
        }
        return mInstance;
    }

    public void init(Context context) {
        mContext = context;
    }

    public int createProgram(String vert, String frag) {
        int vertexShader = HPUtil.getInstance().loadShader(GLES20.GL_VERTEX_SHADER, vert);
        int fragmentShader = HPUtil.getInstance().loadShader(GLES20.GL_FRAGMENT_SHADER, frag);

        int program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);

        return program;
    }

    public int loadShader(int type, String shaderName) {
        int shader = GLES20.glCreateShader(type);

        String shaderCode = "";
        int resId = getResId(shaderName, R.raw.class);
        InputStream inputStream = mContext.getResources().openRawResource(resId);
        if(inputStream != null)
        {
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            shaderCode = sb.toString();
        }

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
