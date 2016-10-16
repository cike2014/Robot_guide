package com.mmednet.main.socket;

/**
 * 通过socket发送过来的Stringbean
 * 
 * @author xiaowei
 *
 */
public class StringMsgBean {

	/**
	 * 消息类型，详见MsgType，，和串口保持一致
	 */
	private int msgType;

	/**
	 * 数据类型---byte or String
	 */
//	private byte dataType=MsgType.DATATYPE_STRING;

	/**
	 * 数据
	 */
	private String msgData;

	public StringMsgBean(int msgType,  String msgData) {
		super();
		this.msgType = msgType;
//		this.dataType = dataType;
		this.msgData = msgData;
	}

	public StringMsgBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

//	public byte getDataType() {
//		return dataType;
//	}
//
//	public void setDataType(byte dataType) {
//		this.dataType = dataType;
//	}

	public String getMsgData() {
		return msgData;
	}

	public void setMsgData(String msgData) {
		this.msgData = msgData;
	}

	@Override
	public String toString() {
		return "StringMsgBean [msgType=" + msgType + ", msgData=" + msgData + "]";
	}
	
}
