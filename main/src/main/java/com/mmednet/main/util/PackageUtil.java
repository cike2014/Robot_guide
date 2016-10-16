package com.mmednet.main.util;

import android.os.Bundle;

import com.mmednet.main.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpha on 2016/9/23.
 */
public class PackageUtil {

    static Map<Integer, MyPackage> packageMap;

    static {
        packageMap=new HashMap<Integer, MyPackage>();
        packageMap.put(R.id.iv_daozhen, new MyPackage(R.id.iv_daozhen, "com.mmednet.robot_guide",null, null));
        packageMap.put(R.id.iv_pinggu, new MyPackage(R.id.iv_pinggu, "com.mmednet.autodiagnose", null,null));
        packageMap.put(R.id.iv_xueya,new MyPackage(R.id.iv_xueya,"com.unisrobot.jtjkzx2","com.unisrobot.jtjkzx2.BloodPressureActivity",null));
        packageMap.put(R.id.iv_tizhong,new MyPackage(R.id.iv_tizhong,"com.unisrobot.jtjkzx2","com.unisrobot.jtjkzx2.WeightActivity",null));
        packageMap.put(R.id.iv_tiwen,new MyPackage(R.id.iv_tiwen,"com.unisrobot.jtjkzx2","com.unisrobot.jtjkzx2.TiWenActivity",null));
        packageMap.put(R.id.iv_xuetang,new MyPackage(R.id.iv_xuetang,"com.unisrobot.jtjkzx2","com.unisrobot.jtjkzx2.SugarActivity",null));
    }

    public static MyPackage getPackage(Integer imageId) {
        return packageMap.get(imageId);
    }

    public static void setArgs(Integer imageId, Bundle args) {
        getPackage(imageId).setArgs(args);
    }


    public static class MyPackage {
        private int imageId;
        private String packageName;
        private String activityName;
        private Bundle args;

        public MyPackage(int imageId, String packageName,String activityName, Bundle args) {
            this.imageId=imageId;
            this.packageName=packageName;
            this.activityName =activityName;
            this.args=args;
        }

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId=imageId;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName=packageName;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName=activityName;
        }

        public Bundle getArgs() {
            return args;
        }

        public void setArgs(Bundle args) {
            this.args=args;
        }

        @Override
        public String toString() {
            return "MyPackage{" +
                    "imageId=" + imageId +
                    ", packageName='" + packageName + '\'' +
                    ", activityName='" + activityName + '\'' +
                    ", args=" + args +
                    '}';
        }
    }


}
