package com.mmednet.main.util;

import android.os.Environment;

/**
 * Created by alpha on 2016/9/22.
 */
public class Constant {

    public static String USER_ID="user_id";
    public static final String OTHER_FLAG="com.mmednet.interprocess";

    public final static String ALBUM_PATH
            =Environment.getExternalStorageDirectory() + "/mmednet_robot/";


    public static final  String SOCKET_HOST = "192.168.12.1";
    public static final int SOCKET_PORT = 19255;
}
