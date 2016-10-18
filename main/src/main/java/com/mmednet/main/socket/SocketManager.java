package com.mmednet.main.socket;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

import com.alibaba.fastjson.JSON;
import com.mmednet.main.util.Constant;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * socket通信管理
 * 
 * @ClassName: SocketManager
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liuyusheng
 * @date 2016年9月27日 上午9:38:23
 *
 */
public class SocketManager {
    private static final String TAG = "SocketManager";
    private static SocketManager instance;
    private Context mContext;
    private Socket mSocket;
    private SocketReadThread mReadThread;
    private String mdstname;
    public long touchTime = 0;// 记录操作时间（包括触控、声控）
    private int mdstport;
    public String LOCK = "LOCK";
    private static final long HEART_BEAT_RATE = 3 * 1000;
    private String[][] mCmdArrays = { { "1", "com.uurobot.receive" },
            { "2", "com.uurobot.recognize" }, { "3", "com.uurobot.start" },
            { "4", "com.uurobot.stop" }, { "5", "com.uurobot.MyViewPager1" },
            { "6", "com.uurobot.KnowAboutUUActivity" },
            { "7", "com.uurobot.FunctionUUActivity" },
            { "8", "com.uurobot.SceneActivity" },
            { "9", "com.uurobot.AIActivity" },
            { "10", "com.uurobot.PersonalityActivity" },
            { "11", "com.uurobot.InteractionActivity" },
            { "12", "com.uurobot.IdentifyActivity" },
            { "13", "com.uurobot.PositionActivity" },
            { "14", "com.uurobot.IdentifyActivity" },
            { "15", "com.uurobot.IndustryActivity" },
            { "16", "com.uurobot.VoicecontrolActivity" },
            { "17", "com.uurobot.SensorActivity" },
            { "25", "com.uurobot.viewpagerEnd" }, { "26", "com.uurobot.back" },
            { "27", "com.uurobot.xi" }, { "28", "com.uurobot.nu" },
            { "29", "com.uurobot.ai" }, { "30", "com.uurobot.si" },
            { "31", "com.uurobot.kong" }, { "32", "com.uurobot.jing" },
            { "33", "com.uurobot.normal" } };

    private SocketManager() {
        mSocket = new Socket();
    }

    public static SocketManager getInstance() {
        if (instance == null) {
            instance = new SocketManager();
        }
        return instance;
    }

    /**
     * 初始化
     * 
     * @author liuyusheng
     */
    public void init(Context context) {
        this.mContext = context;
        connectToServer();
    }


    /**
     * 通过wifi热点通信时 的IP和Port
     */
    // public static final String SOCKET_HOST = "192.168.22.188";//
    // "192.168.11.90";//"192.168.12.1";



    private void connectToServer() {
        Thread aa = new Thread(new Runnable() {
            public void run() {
                try {
                    mSocket.connect(new InetSocketAddress(Constant.SOCKET_HOST,
                            Constant.SOCKET_PORT));
                    mReadThread = new SocketReadThread();
                    mReadThread.start();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        aa.start();
        try {
            aa.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /** 断开连接 */
    private void releaseLastSocket() {
        try {
            if (null != mSocket) {
                if (!mSocket.isClosed()) {
                    mSocket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动activity
     * 
     * @param cls
     */
    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * SerialPortThread和SocketThread解析后的所有消息在这里处理
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Logger.v(TAG, "msg.what" + msg.what);
            switch (msg.what) {
                case MsgType.RECEIVER_MSG_SWITCH_ACTIVITY:
                    synchronized (LOCK) {
                        touchTime = System.currentTimeMillis();
                    }
                    int command = (Integer) msg.obj;
                    for (int j = 0, length = mCmdArrays.length; j < length; j++) {
                        String temp = mCmdArrays[j][0];
                        int localCmd = Integer.parseInt(temp, 10);
                        if (localCmd == command) {
                            Logger.v(TAG, "switch_activity or emotion = "
                                    + mCmdArrays[j][1]);
                            // LocalBroadcastManager
                            // .getInstance(VoiceCtrService.this)
                            // .sendBroadcast(new Intent(mCmdArrays[j][1]) );
                            sendBroadcast(mCmdArrays[j][1]);
                            break;
                        }
                    }
                    break;
                case MsgType.RECEIVER_MSG_PLAY_SOUND_END:
                    // LocalBroadcastManager
                    // .getInstance(VoiceCtrService.this)
                    // .sendBroadcast(new
                    // Intent(ReceiverSocketAction.PLAY_SOUND_END)
                    // );
                    sendBroadcast(ReceiverSocketAction.PLAY_SOUND_END);
                    break;

                case MsgType.RECEIVER_MSG_VOICE_RECOGNIZER_RESULT:
//                    synchronized (MainApplication.LOCK) {
//                        ((MainApplication) mContext.getApplicationContext()).touchTime = System
//                                .currentTimeMillis();
//                    }

                    String result = (String) msg.obj;
                    if (result != null && !result.isEmpty()) {
                        Intent intent = new Intent(
                                ReceiverSocketAction.MSG_VOICE_RECOGNIZER_RESULT);
                        intent.putExtra(IntentKey.VOICE_RESULT, result);
                        // LocalBroadcastManager
                        // .getInstance(context)
                        // .sendBroadcast(intent);
                        mContext.sendBroadcast(intent);

                    }
                    break;
//                case MsgType.RECEIVER_MSG_UPDATE_FIREWARE:
////                    startActivity(ShowUpdateActivity.class);
//                    break;

                case MsgType.RECEIVER_MSG_WAKE_UP:
                    LocalBroadcastManager
                            .getInstance(mContext)
                            .sendBroadcast(
                                    new Intent(
                                            ReceiverSocketAction.MSG_VOICE_RECOGNIZER_WAKEUP));
                    break;
                case MsgType.RECEIVER_MSG_SUSPEND:
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(
                            new Intent(
                                    ReceiverSocketAction.RECEIVER_MSG_SUSPEND));
                    break;
                case MsgType.RECEIVER_MSG_START_RECODER:
                    LocalBroadcastManager
                            .getInstance(mContext)
                            .sendBroadcast(
                                    new Intent(
                                            ReceiverSocketAction.RECEIVER_MSG_START_RECODER));
                    break;
                case MsgType.RECEIVER_MSG_START_RECOGNIZER:
                    LocalBroadcastManager
                            .getInstance(mContext)
                            .sendBroadcast(
                                    new Intent(
                                            ReceiverSocketAction.RECEIVER_MSG_START_RECOGNIZER));
                    break;
                case MsgType.RECEIVER_FACE_REGONGNIZE_RESULT:
                    String name = (String) msg.obj;// 人脸识别返回结果
                    Intent intent = new Intent(
                            ReceiverSocketAction.FACE_RECOGNIZER_RESULT);
                    intent.putExtra("face_recognize_result", name);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(
                            intent);
                    break;
                case MsgType.SEND_HEARTBEAT:
                    intent = new Intent(ReceiverSocketAction.MSG_HEARTBEAT);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(
                            intent);
                    break;
            }
        };
    };

    /**
     * 发送广播
     * 
     * @param action
     */
    private void sendBroadcast(String action) {
        mContext.sendBroadcast(new Intent(action));
    }


    public class SocketReadThread extends Thread {

        private static final String TAG = "SocketThread";
        private volatile boolean mStopThread = false;

        public void release() {
            mStopThread = true;
            releaseLastSocket();
        }

        public SocketReadThread() {
        }

        @Override
        public void run() {
             DataInputStream mInputStream = null;
            try {
                mInputStream = new DataInputStream(mSocket.getInputStream());
                while (!mStopThread ) {
                    String resultStr = mInputStream.readUTF();
                    handleStringMsg(resultStr);
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) { 
                e.printStackTrace();
            } finally {
                try {
                    mSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (mInputStream != null) {
                    try {
                        mInputStream.close();
                        mInputStream = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        

        /**
         * 处理String 消息
         * 
         * @param data
         */
        private void handleStringMsg(String data) {
            if (data != null) {
                try {
                    StringMsgBean strMsgBean = JSON.parseObject(data,
                            StringMsgBean.class);
                    if (strMsgBean != null) {
//                        Logger.d(TAG,
//                                "StringMsgBean===" + strMsgBean.toString());
                        int msgType = strMsgBean.getMsgType();
                        String msgData = strMsgBean.getMsgData();
                        switch (msgType) {
                            case MsgType.RECEIVER_MSG_SWITCH_ACTIVITY:
                                        int command = Integer.parseInt(msgData);
//                                        Logger.d(TAG, "command==" + command);
                                        mHandler.obtainMessage(
                                                MsgType.RECEIVER_MSG_SWITCH_ACTIVITY,
                                                command).sendToTarget();
                                break;
                            case MsgType.RECEIVER_MSG_PLAY_SOUND_END:
                                mHandler.obtainMessage(
                                        MsgType.RECEIVER_MSG_PLAY_SOUND_END)
                                        .sendToTarget();
                                break;

                            case MsgType.RECEIVER_MSG_VOICE_RECOGNIZER_RESULT:
                                mHandler.obtainMessage(
                                        MsgType.RECEIVER_MSG_VOICE_RECOGNIZER_RESULT,
                                        msgData).sendToTarget();
                                break;

                            case MsgType.RECEIVER_MSG_UPDATE_FIREWARE:// 收到更新固件的消息
                                mHandler.obtainMessage(
                                        MsgType.RECEIVER_MSG_UPDATE_FIREWARE)
                                        .sendToTarget();
                                break;
                            case MsgType.RECEIVER_MSG_WAKE_UP:
                                mHandler.obtainMessage(
                                        MsgType.RECEIVER_MSG_WAKE_UP)
                                        .sendToTarget();
                                break;
                            case MsgType.RECEIVER_MSG_SUSPEND:
                                mHandler.obtainMessage(
                                        MsgType.RECEIVER_MSG_SUSPEND)
                                        .sendToTarget();
                                break;
                            case MsgType.RECEIVER_MSG_START_RECODER:
                                mHandler.obtainMessage(
                                        MsgType.RECEIVER_MSG_START_RECODER)
                                        .sendToTarget();
                                break;
                            case MsgType.RECEIVER_MSG_START_RECOGNIZER:
                                mHandler.obtainMessage(
                                        MsgType.RECEIVER_MSG_START_RECOGNIZER)
                                        .sendToTarget();
                                break;
                            case MsgType.RECEIVER_FACE_REGONGNIZE_RESULT:
                                mHandler.obtainMessage(
                                        MsgType.RECEIVER_FACE_REGONGNIZE_RESULT)
                                        .sendToTarget();
                                break;
                            case MsgType.SEND_HEARTBEAT:
                                mHandler.obtainMessage(MsgType.SEND_HEARTBEAT)
                                        .sendToTarget();
                                break;

                        }
                    } else {
//                        Logger.d(TAG, "stringMsgBean parse is null！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean sendStringMsgToServer(String msg) {
        if (null == mSocket||msg==null||msg.isEmpty()) {
            return false;
        }
        Socket soc = mSocket;
        try {
            if (!soc.isClosed() && !soc.isOutputShutdown()) {
                DataOutputStream os = new DataOutputStream(
                        soc.getOutputStream());
                os.writeUTF(msg);
                os.flush();
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
