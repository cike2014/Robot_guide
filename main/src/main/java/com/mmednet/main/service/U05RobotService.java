package com.mmednet.main.service;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.text.TextUtils;
import android.util.Log;

import com.mmednet.main.socket.MsgSendUtils;
import com.mmednet.main.socket.MsgType;
import com.mmednet.main.socket.U05RobotManger;

public class U05RobotService extends Service {

    private static final int MESSAGE_CODE_PLAY = 0;

    private static final int MESSAGE_CODE_START_RECOGNIZER = MESSAGE_CODE_PLAY
            + 1;

    private static final int MESSAGE_CODE_STOP_RECOGNIZER = MESSAGE_CODE_PLAY
            + 2;

    private static final int MESSAGE_CODE_PLAY_TTS = MESSAGE_CODE_PLAY + 3;

    private String TAG = "U05RobotService";

    protected boolean completd = false;

    @Override
    public void onCreate() {
        super.onCreate();

        // IntentFilter filter = new IntentFilter();
        // filter.addAction(ReceiverSocketAction.PLAY_SOUND_END);
        // LocalBroadcastManager.getInstance(this).registerReceiver(a, filter);

    }

    @Override
    public IBinder onBind(Intent arg0) {
        return new Messenger(handler).getBinder();
    }

    /**
     * handler 用来跨进程信息的处理.
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_CODE_PLAY: {
                    System.out.println(TAG + " 服务端收到的信息:  msg.what " + msg.what
                            + " +msg.obj " + msg.obj.toString());
                    Bundle b2 = (Bundle) msg.obj;
                    String path = b2.getString("path");
                    completd = false;
                    playSound(path);
                    String action = b2.getInt("actionID") + "";
                    if (!TextUtils.isEmpty(action)) {
                        sendAction(action);
                    }
                    String eye = b2.getInt("eyeID") + "";
                    if (!TextUtils.isEmpty(eye)) {
                        sendEyeMotion(eye);
                    }

                    // while(completd==false)
                    // {
                    // try {
                    // Thread.currentThread().sleep(20);
                    // } catch (InterruptedException e1) {
                    // e1.printStackTrace();
                    // }
                    //
                    // }
                    break;
                }

                case MESSAGE_CODE_START_RECOGNIZER: {
                    U05RobotManger.getInstance().startVoiceRecognition();
                    break;
                }

                case MESSAGE_CODE_STOP_RECOGNIZER: {
                    U05RobotManger.getInstance().stopVoiceRecognition();
                    break;
                }

                case MESSAGE_CODE_PLAY_TTS: {
                    Bundle b2 = (Bundle) msg.obj;
                    String text = b2.getString("text");
                    MsgSendUtils.sendStringMsg(MsgType.SEND_MSGTYPE_PLAY_TTS,
                            text);
                    break;
                }
                default:
                    break;
            }
        }

    };

    BroadcastReceiver a = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            // String path = intent.getStringExtra(IntentKey.Sound_Path);
            Log.e("sound", "PLAY_SOUND_END");
            completd = true;

            Message reply = Message.obtain(null, 0);
            System.out.println(TAG + " 服务端回复信息给客户端 ");
            Bundle b = new Bundle();
            b.putString("state", "播放完毕");
            reply.obj = b;
            // try {
            // replyTo.send(reply);
            // } catch (RemoteException e) {
            // e.printStackTrace();
            // }

        }

    };

    private void sendAction(String action) {
        System.out.println("sendAction " + action);
        MsgSendUtils.sendStringMsg(MsgType.SEND_ACTION, action);
    }

    private void sendEyeMotion(String action) {
        System.out.println("sendEyeMotion " + action);
        MsgSendUtils.sendStringMsg(MsgType.SEND_EYE_MOTION, action);
    }

    private void playSound(String path) {
        // if(true)
        // {
        System.out.println(TAG + "playSound  " + path);
        // return ;
        // }

        MsgSendUtils.sendStringMsg(MsgType.SEND_MSGTYPE_PLAY_SOUND, path);
        // Intent intent = new Intent(ReceiverAPPAction.START_PLAY_SOUND);
        // intent.putExtra(IntentKey.SOUND_PATH, path);
        // intent.putExtra(IntentKey.COMPLETED_DO, IntentContent.NONE);
        // intent.putExtra(IntentKey.MSG_TYPE, MsgType.SEND_MSGTYPE_PLAY_SOUND);
        // sendBroadcast(intent);

    }

}