package com.mmednet.main.socket;

import android.os.Environment;

import java.io.File;

public class ConstUtils {

    public static final String PATH_SD_ROOT = Environment
            .getExternalStorageDirectory().toString();

    /**
     * 此路径由服务器指定，客户端不能更改为其他值.
     */
    public static final String PATH_UPDATE_APK = PATH_SD_ROOT + File.separator
            + "apk/";

    /**
     * 此文件名字由服务器指定，客户端不能更改为其他值.
     */
    public static final String PATH_VERSION = PATH_SD_ROOT + File.separator
            + "ai/version.txt";

    /**
     * 判断是否需要看开机更新
     */
    public static final boolean START_UPDATE = true;

    // 网络设置
    public static final String Update_Tip = "更新提示";
    public static final String Update_Tip_Msg = "有新版本，您是否现在更新？";
    public static final String Update_NET_ERROR_TIP = "温馨提示";
    public static final String Update_NET_ERROR_MSG = "未能连接到网络，请检查网络后，再使用";
    public static final String UPDATE_LEFT_BTN_TEXT = "残忍离开";
    public static final String UPDATE_RIGHT_BTN_TEXT = "设置WIFI";

    // public static final String APP_DOWNLOAD_URL_PARENT =
    // "http://192.168.2.130:8080/uurobot_u5_pad/";

    public static final String APP_DOWNLOAD_URL_PARENT = "http://weixin.uurobots.com/uurobot_u5_pad/";

    /**
     * getAllversion的访问路径
     */
    public static final String GET_ALL_VERSION_URL = APP_DOWNLOAD_URL_PARENT
            + "getAllVersion";

    public static final String ZIP_APP_PATH = PATH_SD_ROOT + "/sys_update_zip/";

    // public static final String U05_PACKAGE = "com.unisrobot.uurobot_u5";
    /**
     * getUpdateAppsZip的访问路径
     */
    public static final String GET_UPDATE_APPS_ZIP_URL = APP_DOWNLOAD_URL_PARENT
            + "getUpdateAppsZip";

    public static final int SET_NETWORK_TIMEOUT = 500;
    public static final int SET_NETWORKERROR_CONNET_COUNT = 0;
    public static final int GET_CHECKUPDATE_TO_WAKEUP = 7;
    public static final long DELAY_MILLIS_TO_WAKEUP = 1500;

    // public static String ssid = "\"ROBOT_D2-003\"";
    // public static String ssidName = "ROBOT_D2-003";
    // public static String host = "192.168.12.12";
    // public static int port = 2810;

    public static final String ACTION_PHOTO_RECEIVED = "action_photo_receiver";// 接收照片通知
    public static final String ACTION_REGISTER_RESULT = "action_register_result";// 注册结果通知
    public static final String ACTION_DELETE_RESULT = "action_delete_result";// 删除结果通知
    public static final String ACTION_MOVE_RESULT = "action_move_result";// 移动结果通知
    public static final String ACTION_RECOGNITION_RESULT = "action_recognition_result";// 识别结果通知
    public static final String ACTION_START_REGISTER = "action_start_register";// 开启注册通知
    public static final String ACTION_PHOTO_REGISTER_RESULT = "action_photo_register_result";// 发送图片结果通知
    public static final String ACTION_OTHERS_REGISTER_RESULT = "action_others_register_result";// 第三方注册结果通知

    // 从哪个界面跳转至注册界面 0：人脸注册 1：图片注册
    public static final int FROM_FACE_REGISTER = 0;
    public static final int FROM_PIC_REGISTER = 1;

}
