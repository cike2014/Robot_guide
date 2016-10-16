package com.mmednet.main.socket;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 人脸识别socket通讯管理器
 * 
 * @author ruandan
 *
 */
public class FaceRecManager {

    private static final String TAG = "FaceRecManager";

    private Socket mSocket = null;
    // private OutputStream outPutStream = null;
    DataOutputStream outPutStream;
    private DataInputStream inPutStream = null;
    private Context mContext = null;
    // private Handler mHandler;
    private volatile boolean mStopThread = true;

    private FaceRecManager() {
        new SocketThread().start();
    }

    private static FaceRecManager faceRecManager;

    public static FaceRecManager getInstance() {

        if (faceRecManager == null)
            faceRecManager = new FaceRecManager();
        return faceRecManager;

    }

    public void startSocketThread(Context context/* , Handler handler */) {
        mContext = context;

    }

    public void stopSocketThread() {
        mStopThread = false;
    }

    /**
     * 发送String 类型的数据
     * 
     * @param msgType
     * @param msgData
     */
    public void sendStringMsg(int msgType, String msgData) {
        if (msgData == null || msgData.isEmpty()) {
            return;
        }
        if (Config.USE_SERIALPORT) {
//            SerialPortMsgUtils.sendStringMsg(msgType, msgData);

        } else if (Config.USE_SOCKET) {
            StringMsgBean bean = new StringMsgBean();
            bean.setMsgType(msgType); // 兼容旧的方式
            String temp = new String(msgData);
            bean.setMsgData(temp);
            String sendStr = JSON.toJSONString(bean);
            try {
                sendString(sendStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送文件
     */
    public void senFileMsg(String filePath, FileMsgBean bean) {
        if (Config.USE_SOCKET) {
            if (bean != null) {
                // String sendStr = JSON.toJSONString(bean);
                // try {
                // sendString(sendStr);
                // Logger.d(TAG, "senFileMsg success!");
                // new FileSendThread(filePath).start();
                // } catch (IOException e) {
                // e.printStackTrace();
                // Logger.e(TAG, "senFileMsg error!");
                // }

                String sendStr = JSON.toJSONString(bean);
                sendStringMsg(MsgType.FILE_TYPE, sendStr);
                new FileSendThread(filePath).start();

            } else {
                Logger.e(TAG, "fileMsgBean is null");
            }
        } else {
            Logger.e(TAG, "USE_SOCKET=false,cannot send file!");
        }
    }

    /**
     * 对发送的信息加同步保护
     * 
     * @throws IOException
     */
    private synchronized boolean sendString(String str) throws IOException {
        if (outPutStream != null) {
            outPutStream.writeUTF(str);
            outPutStream.flush();
            Logger.d(TAG, "sendString success!");
            return true;
        } else {
            Logger.e(TAG, "mOut is null  ,mOut is null  ");
            return false;
        }
    }

    /**
     * 通过socket和3288通信
     * 
     *
     */
    public class SocketThread extends Thread {
        private static final String TAG = "FaceSocketThread";
        private int scoketConnectTimes = 1;

        public SocketThread() {
            Logger.d(TAG, " SocketThread constructor!");

        }

        @Override
        public void run() {
            super.run();
            try {
                mSocket = new Socket(IpMsgConst.FACE_SOCKET_HOST,
                        IpMsgConst.FACE_SOCKET_PORT);
                outPutStream = new DataOutputStream(mSocket.getOutputStream());
                inPutStream = new DataInputStream(mSocket.getInputStream());
                // byte[] readBuf = new byte[512];
                Logger.d(TAG, "SocketThread running!");
                while (mStopThread || !interrupted()) {
                    Logger.d(TAG, "befor read---------!");
                    String resultStr = null;
                    try {
                        resultStr = inPutStream.readUTF();
                        // len = inPutStream.read(readBuf);// 一旦服务端出现问题，len=-1
                    } catch (IOException e) {
                        try {
                            sleep(scoketConnectTimes * 1000);
                            // if (!mSocket.isConnected()) {
                            scoketConnectTimes = scoketConnectTimes + 2;
                            mSocket = new Socket(IpMsgConst.FACE_SOCKET_HOST,
                                    IpMsgConst.FACE_SOCKET_PORT);
                            outPutStream = new DataOutputStream(
                                    mSocket.getOutputStream());
                            inPutStream = new DataInputStream(
                                    mSocket.getInputStream());
                            // }
                        } catch (IOException e1) {
                            e.printStackTrace();
                            Logger.e(TAG, "IOException--" + e.toString());
                        } catch (Exception e2) {
                            Logger.d(TAG, "mSocket= disconnected");
                        }
                    }
                    if (resultStr != null && !resultStr.isEmpty()) {
                        // String resultStr = new String(readBuf, 0, len);
                        Logger.d(TAG, "result=" + resultStr);
                        handleStringMsg(mSocket.getInetAddress().toString(),
                                resultStr);
                    } else {
                        try {
                            sleep(scoketConnectTimes * 1000);
                            scoketConnectTimes = scoketConnectTimes + 2;
                            mSocket = new Socket(IpMsgConst.FACE_SOCKET_HOST,
                                    IpMsgConst.FACE_SOCKET_PORT);
                            outPutStream = new DataOutputStream(
                                    mSocket.getOutputStream());
                            inPutStream = new DataInputStream(
                                    mSocket.getInputStream());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                            Logger.e(TAG, "IOException--" + e1.toString());
                        } catch (Exception e2) {
                            Logger.d(TAG, "mSocket= disconnected");
                        }
                        // Logger.d(TAG, "inPutStream.read--len=" + len);
                    }
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
                Logger.e(TAG, "UnknownHostException--" + e.toString());
            } catch (IOException e) { // 当server端断掉后就会出现异常，怎么重连？
                e.printStackTrace();
                Logger.e(TAG, "IOException--" + e.toString());

            } finally {
                if (mSocket != null) {
                    try {
                        mSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outPutStream != null) {
                    try {
                        outPutStream.close();
                        outPutStream = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inPutStream != null) {
                    try {
                        inPutStream.close();
                        inPutStream = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 处理文件信息
         * 
         * @param data
         */
        private void handleFileMsg(String data) {
            if (data != null) {
                try {
                    FileMsgBean fileMsgBean = JSON.parseObject(data,
                            FileMsgBean.class);
                    if (fileMsgBean != null) {
                        new FileReceiveThread(mContext,
                                IpMsgConst.FACE_SOCKET_HOST, fileMsgBean)
                                        .start();
                        Logger.d(TAG,
                                "FileMsgBean===" + fileMsgBean.toString());
                    } else {
                        Logger.d(TAG, "FileMsgBean parse is null！");
                    }
                } catch (Exception e) {
                    Logger.d(TAG, "FileMsgBean parse error！");
                }
            }
        }

        /**
         * 处理文件类型的消息
         * 
         * @param data
         */
        private void handFileMsg(String senderIp, String data) {
            try {
                FileMsgBean fileMsgBean = JSON.parseObject(data,
                        FileMsgBean.class);
                if (fileMsgBean != null) {
                    Logger.e(TAG, "fileMsgBean=" + fileMsgBean.toString());
                    new FileReceiveThread(mContext, senderIp, fileMsgBean)
                            .start();
                } else {
                    Logger.d(TAG, "FileMsgBean parse is null！");
                }
            } catch (Exception e) {
                Logger.e(TAG, "FileMsgBean parse error！");
            }
        }

        /**
         * 处理Strng 消息
         * 
         * @param data
         */
        private void handleStringMsg(String ip, String data) {
            if (data != null) {
                try {
                    StringMsgBean strMsgBean = JSON.parseObject(data,
                            StringMsgBean.class);
                    if (strMsgBean != null) {
                        Logger.d(TAG,
                                "StringMsgBean===" + strMsgBean.toString());
                        int msgType = strMsgBean.getMsgType();
                        String msgData = strMsgBean.getMsgData();
                        Intent intent = new Intent();

                        switch (msgType) {
                            case MsgType.FILE_TYPE:
                                handFileMsg(ip, msgData);
                                break;
                            case MsgType.RECEIVER_FACE_RECOGNIZE_RESULT:// 识别结果
                                Logger.i(TAG, "识别结果--》" + msgData);
                                intent.putExtra("result", msgData);
                                intent.setAction(
                                        ConstUtils.ACTION_RECOGNITION_RESULT);
                                mContext.sendBroadcast(intent);

                                break;
                            case MsgType.RECEIVER_REGISTE_RESULT:// 注册结果
                                Logger.i(TAG, "注册结果" + msgData);
                                intent.putExtra("result", msgData);
                                RegisteResult result = JSON.parseObject(msgData,
                                        RegisteResult.class);
                                if (result
                                        .getMsgType() == MsgType.RECEIVER_PHOTO_RESULT) {// 发送图片结果
                                    intent.setAction(
                                            ConstUtils.ACTION_PHOTO_REGISTER_RESULT);
                                } else {// 发送音频，普通注册信息（TTS/随机语音/音频）结果
                                    intent.setAction(
                                            ConstUtils.ACTION_REGISTER_RESULT);
                                }
                                mContext.sendBroadcast(intent);
                                break;
                            case MsgType.RECEIVER_DELETE_RESULT:// 删除结果

                                Logger.i(TAG, "删除结果" + msgData);
                                intent.putExtra("result", msgData);
                                intent.setAction(
                                        ConstUtils.ACTION_DELETE_RESULT);
                                mContext.sendBroadcast(intent);
                                break;

                            case MsgType.RECEIVER_START_REGISTER:// 启动人脸注册

                                Logger.i(TAG, "启动人脸注册" + msgData);
                                intent.setAction(
                                        ConstUtils.ACTION_START_REGISTER);
                                break;

                            case MsgType.RECEIVER_OTHERS_REGISTER_RESULT:// 第三方注册结果
                                Logger.i(TAG, "第三方注册结果" + msgData);
                                intent.putExtra("result", msgData);
                                intent.setAction(
                                        ReceiverSocketAction.FACE_RECOGNIZER_OTHERS_RESULT);
                                mContext.sendBroadcast(intent);
                                break;
                            case MsgType.RECEIVER_OTHERS_RECONGNIZE_RESULT:// 第三方识别结果
                                Logger.i(TAG, "第三方识别结果" + msgData);
                                intent.putExtra("result", msgData);
                                intent.setAction(
                                        ReceiverSocketAction.FACE_RECOGNIZER_RESULT);
                                mContext.sendBroadcast(intent);
                                break;
                        }
                    } else {
                        Logger.d(TAG, "stringMsgBean parse is null！");
                    }
                } catch (Exception e) {
                    Logger.d(TAG, "stringMsgBean parse error！");
                }
            }
        }

    }

}
