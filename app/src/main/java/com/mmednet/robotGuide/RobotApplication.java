package com.mmednet.robotGuide;

import android.app.Application;

/**
 * Created by alpha on 2016/9/2.
 */
public class RobotApplication extends Application {

    private int top;

    private static RobotApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top=top;
    }

    public static RobotApplication getInstance(){
        return instance;
    }
}
