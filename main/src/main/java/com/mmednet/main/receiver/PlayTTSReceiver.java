package com.mmednet.main.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.mmednet.main.socket.MsgSendUtils;
import com.mmednet.main.socket.MsgType;

/**
 * 由各个应用向主应用发送广播，该广播收到后，发送TTS广播
 *
 * @author zdb
 *         created by 2016/10/9 12:21
 */
public class PlayTTSReceiver extends BroadcastReceiver {

    private static final String TAG = PlayTTSReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras=intent.getExtras();
        String word=extras.getString("word");
        Log.d(TAG,"收到广播："+word);
        if (!TextUtils.isEmpty(word))
            MsgSendUtils.sendStringMsg(MsgType.SEND_MSGTYPE_PLAY_TTS, word);

    }
}
