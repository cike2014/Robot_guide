package com.mmednet.main.socket;

/**
 * IP 相关的常量
 * 
 * @author xiaowei
 *
 */
public class IpMsgConst {

	/**
	 * 通过串口通讯时的IP和 Port
	 */
	public static final String SerialPort_Host = "192.168.12.12";
	public static final int SerialPort_Port = 2810;

	/**
	 * 通过wifi热点通信时 的IP和Port
	 */
	public static final String SOCKET_HOST = "192.168.22.131";//"192.168.11.90";//"192.168.12.1";
	public static final int SOCKET_PORT = 19255;

	/**
	 * 传输文件的端口
	 */
   public static final int FILE_PORT = 19256;

	/**
	 * 和人脸识别app通讯的IP和端口
	 */
	public static final String FACE_SOCKET_HOST = "192.168.1.153";
    public static final int FACE_SOCKET_PORT = 19258;
    
    /**
     * 传输文件时的端口好
     */
    public static final int FACE_FILE_PORT = 19259;
}
