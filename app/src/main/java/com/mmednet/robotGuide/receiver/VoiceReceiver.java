package com.mmednet.robotGuide.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.mmednet.robotGuide.MainActivity;
import com.mmednet.robotGuide.RobotApplication;
import com.mmednet.robotGuide.util.CommonUtils;
import com.mmednet.robotGuide.util.Constant;
import com.mmednet.robotGuide.util.DicUtil;

/**
 * 声音广播接收者
 *
 * @author zdb
 *         created by 2016/10/8 18:12
 */
public class VoiceReceiver extends BroadcastReceiver {

    private static final String TAG=VoiceReceiver.class.getSimpleName();

    private final static String res="是,是的,否,有点,没有,有,有一点,结束,完成,提交";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "VoiceReceiver:" + RobotApplication.getInstance().getTop() + ";result=" + intent.getExtras().get("result"));

        if (!CommonUtils.isBackground(context)) {
            String result=(String) intent.getExtras().get("result");
            if (RobotApplication.getInstance().getTop() == Constant.TOP_INDEX) {
                String keyWord=DicUtil.getKeyWord(result);
                if (!TextUtils.isEmpty(keyWord)) {
                    Intent myIntent=new Intent(context, MainActivity.class);
                    myIntent.putExtra("keyWord", keyWord);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(myIntent);
                } else {
                    //没有匹配，重新识别
                    CommonUtils.beginRecognize(context);
                }
            } else {
                if (res.indexOf(result) > -1) {
                    Intent i=new Intent(Constant.SEND_TO_ACTIVITY);
                    i.putExtra("result", result);
                    context.sendBroadcast(i);
                } else {
                    //没有匹配，重新识别
                    CommonUtils.beginRecognize(context);
                }
            }

        }
    }
}
