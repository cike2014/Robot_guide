package com.mmednet.main.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;


import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

/**
 * 接收文件线程类
 */
public class FileReceiveThread extends Thread {
    private final static String TAG = "FileReceiveThread";
    private String senderIp;
    private Socket socket;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    private BufferedOutputStream fbos;
    private String fileName;// 接受文件的文件名
    private long fileSize; // 接收文件的大小
    private String dirPath;// 文件所在文件夹的路径
    private int msgType;
    private Context mContext;
    private String mQuestion;
    // private Handler mHandler;
    
    /**
     * 
     * @param senderIp
     * @param fileName
     * @param question
     * @param fileSize
     */
    public FileReceiveThread(Context context/*, Handler handler*/, String senderIp,
            FileMsgBean fileMsgBean) {
        Log.i(TAG, "FileReceiveThread  收到图片");
        this.senderIp = senderIp.substring(1);
        this.mContext = context;
       // this.mHandler = handler;
        parseParams(fileMsgBean);
    }

    /**
     * 
     * @param fileMsgBean
     */
    private void parseParams(FileMsgBean fileMsgBean) {
        this.fileSize = fileMsgBean.getFileSize();
        msgType = fileMsgBean.getCustomType();
        String name = fileMsgBean.getFileName();
        mQuestion = fileMsgBean.getQuestion();
        String temp = ".mp3";
        if (name != null) {
            if (name.endsWith("mp3")) {
                temp = ".mp3";
            } else if (name.endsWith("wav")) {
                temp = ".wav";
            } else if (name.endsWith("jpg")) {
                temp = ".jpg";
            }
        }
        switch (msgType) {
            case MsgType.RECEIVER_PHOTO_FILE:
                fileName = mQuestion + temp;
//                this.dirPath = com.unisrobot.uurobot_u5.utils.ConstUtils.PHOTO_PATH;
                break;
           
        }
        File fileDir = new File(dirPath);
        if (!fileDir.exists()) { // 若不存在
            fileDir.mkdirs();
        }
        Log.d(TAG, "fileName=" + fileName.toString());
        Log.d(TAG, "fileDir=" + fileDir.toString());
        Log.d(TAG, "senderIp=" + senderIp);
    }

    @Override
    public void run() {
        try {
            Log.i(TAG, "FileReceiveThread  解析图片");
            socket = new Socket(senderIp, IpMsgConst.FILE_PORT);
            Log.d(TAG,
                    "connect socket ok!,socket=" + socket.getInetAddress());
            bos = new BufferedOutputStream(socket.getOutputStream());
            File receiveFile = new File(dirPath + fileName);
            Log.d(TAG, "receiveFile=" + receiveFile.toString());
            if (!receiveFile.exists()) {
                receiveFile.createNewFile();
            }
            fbos = new BufferedOutputStream(new FileOutputStream(receiveFile));
            bis = new BufferedInputStream(socket.getInputStream());
            int len = 0;
            long receiveSize = 0; // 已接收文件大小
            int temp = 0;
            byte[] readBuffer = new byte[1024];
            while ((len = bis.read(readBuffer)) != -1) {
                fbos.write(readBuffer, 0, len);
                fbos.flush();
                receiveSize += len; // 已接收文件大小
                int sendedPer = (int) (receiveSize * 100 / fileSize); // 接收百分比
                if (temp != sendedPer) { // 每增加一个百分比，发送一个message
                    temp = sendedPer;
                }
            }
            if (receiveSize == fileSize) {
                Log.d(TAG, "file :" + receiveFile + " receiver success！");
                saveInfo();
            } else {
                Log.d(TAG, "receiveSize=" + receiveSize + " ....fileSize="
                        + fileSize);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(TAG, "....系统不支持GBK编码");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Log.e(TAG, "远程IP地址错误");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "文件创建失败");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "发生IO错误");
        } finally { // 处理
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bos = null;
            }
            if (fbos != null) {
                try {
                    fbos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fbos = null;
            }

            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bis = null;
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }

    /**
     * 一旦文件接收成功，将question和 fileName
     * 绑定存起来--这里调用可能在子线程，需注意(这里只保存数据，在VoiceService更新数据)
     */
    private void saveInfo() {
        if (msgType == MsgType.RECEIVER_PHOTO_FILE) {
            Log.i(TAG, "发送广播到TakePhotoActivity");
            Intent intent = new Intent();  
            intent.setAction(ConstUtils.ACTION_PHOTO_RECEIVED);  
            mContext.sendBroadcast(intent); 
        }
    }

}
