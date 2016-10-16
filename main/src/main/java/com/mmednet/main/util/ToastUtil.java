package com.mmednet.main.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by alpha on 2016/9/5.
 */
public class ToastUtil {

    public static void showMsg(Context context,String msg){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }

    public static void showMsg(Context context,int resourceId){
        String msg=context.getResources().getString(resourceId);
        showMsg(context,msg);
    }
}
