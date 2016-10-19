package com.mmednet.main.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.mmednet.main.bean.Account;
import com.mmednet.main.db.actual.AccountDao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用工具类
 *
 * @author zdb
 *         created by 2016/9/22 10:17
 */
public class CommonUtil {

    private static final String TAG = CommonUtil.class.getSimpleName();

    /**
     * 判断当前应用是否显示在最前面
     * @param context
     * @return true:后台;false：前台
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i(TAG, appProcess.processName+"后台");
                    return true;
                }else{
                    Log.i(TAG, appProcess.processName+"前台");
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 获得用户登录状态
     *
     * @return true:登录；false:未登录
     */
    public static boolean getLoginSte(Context context) {
        return TextUtils.isEmpty(SettingUtils.getSharedPreferences(context, Constant.USER_ID, "")) ? false : true;
    }

    /**
     * 根据用户ID查询用户信息
     * @param context
     * @param userId
     * @return
     */
    public static Account getAccount(Context context, String userId){
        AccountDao accountDao = new AccountDao(context);
        return accountDao.queryObject("id",userId);
    }


    /**
     * 跳转到别的应用
     *
     * @param packageName ： 包名
     * @param context
     */
    public static void redirect(String packageName,String activityName, Bundle args, Context context) {
        Intent intent=null;
        if(TextUtils.isEmpty(activityName)){
            PackageManager packageManager=context.getPackageManager();
            intent=packageManager.getLaunchIntentForPackage(packageName);
        }else{
            intent = new Intent();
            intent.setComponent(new ComponentName(packageName,activityName));
            intent.setAction(Intent.ACTION_VIEW);
        }
        if (args!=null) intent.putExtras(args);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static boolean existPackage(Context context, String packageName) {
        List<PackageInfo> packageInfos=getAllApps(context);
        for (PackageInfo packageInfo : packageInfos) {
            if (packageInfo.packageName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获得手机内应用的包名，返回一个List集合
     **/
    public static List<PackageInfo> getAllApps(Context context) {
        List<PackageInfo> apps=new ArrayList<PackageInfo>();
        PackageManager packageManager=context.getPackageManager();
        List<PackageInfo> paklist=packageManager.getInstalledPackages(0);
        for (int i=0; i < paklist.size(); i++) {
            PackageInfo pak=(PackageInfo) paklist.get(i);
            //判断是否为非系统预装的应用  (大于0为系统预装应用，小于等于0为非系统应用)
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                apps.add(pak);
            }
        }
        return apps;
    }


    public static Bitmap getBitmap(String fileName){
        String path = Constant.ALBUM_PATH+fileName;
        File file = new File(path);
        if(file.exists()){
            return BitmapFactory.decodeFile(path);
        }else{
            return null;
        }

    }

    public static void saveBitmapToSD(Bitmap bitmap,String fileName){

        String ALBUM_PATH = Constant.ALBUM_PATH;
        File dirFile = new File(Constant.ALBUM_PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }

        File myCaptureFile = new File(ALBUM_PATH + fileName);
        BufferedOutputStream bos =null;
        try {
            bos=new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        try {
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
