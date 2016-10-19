package com.mmednet.main.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mmednet.main.socket.MsgSendUtils;
import com.mmednet.main.socket.MsgType;

/**
 * Created by alpha on 2016/10/18.
 */
public class RecognizeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MsgSendUtils.sendStringMsg(MsgType.SEND_MSGTYPE_PLAY_TTS,"无法识别，请重复&&1");
//        MsgSendUtils.sendStringMsg(MsgType.SEND_START_RECOGNIZER, "OK");
    }

}
