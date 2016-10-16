package com.mmednet.main.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Tcp发送文件线程
 * 
 * @author ccf
 * 
 *         2012/2/28
 */
public class FileSendThread extends Thread {
    private final static String TAG = "FileSendThread";
    private String mFileName;
    private ServerSocket mServerSocket;
    private Socket mClientSocket;

    public FileSendThread(String fileName) {
        this.mFileName = fileName;
        try {
            mServerSocket = new ServerSocket(IpMsgConst.FILE_PORT);
            Logger.d(TAG, "ServerSocket construct ok!");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e(TAG, "监听tcp端口失败");
        }
    }

    @Override
    public void run() {
        BufferedOutputStream bos = null;
       // BufferedInputStream bis = null;
        BufferedInputStream fbis = null;
        try {
            Logger.d(TAG, "mFileName"+mFileName);
            File sendFile = new File(mFileName); // 要发送的文件
            if (!sendFile.exists()) {
                Logger.d(TAG, "file " + sendFile + " not exist!");
                return;
            }
            Logger.d(TAG, "before filesend mServerSocket.accept---");
            mClientSocket = mServerSocket.accept();
            Logger.d(TAG, "与IP为"
                    + mClientSocket.getInetAddress().getHostAddress()
                    + "的用户建立TCP连接");
            bos = new BufferedOutputStream(mClientSocket.getOutputStream());
          //  bis = new BufferedInputStream(mClientSocket.getInputStream());
            fbis = new BufferedInputStream(new FileInputStream(sendFile));
            int rlen = 0;
            byte[] readBuffer = new byte[1024];
            while ((rlen = fbis.read(readBuffer)) != -1) {
                bos.write(readBuffer, 0, rlen);
            }
            bos.flush();
            Logger.d(TAG, "文件发送成功");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e(TAG, "发生IO错误");
        } finally {
//            if (bis != null) {
//                try {
//                    bis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                bis = null;
//            }
            if (fbis != null) {
                try {
                    fbis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fbis = null;
            }

            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bos = null;
            }
            if (mClientSocket != null) {
                try {
                    mClientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mClientSocket = null;
            }
            if (mServerSocket != null) {
                try {
                    mServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mServerSocket = null;
            }
        }
    }
}
