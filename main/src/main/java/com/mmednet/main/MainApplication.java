package com.mmednet.main;

import android.app.Application;

import com.mmednet.main.socket.FaceRecManager;

/**
 * Created by alpha on 2016/9/22.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FaceRecManager.getInstance();
    }
}
