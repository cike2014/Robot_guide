package com.mmednet.main.socket;

public class RegisteResult {
	private int msgType;
	private String fileName;	
	private String result;
	private String reason;
	private boolean isInsert;
	
	public boolean isInsert() {
		return isInsert;
	}
	public void setInsert(boolean isInsert) {
		this.isInsert = isInsert;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public int getMsgType() {
		return msgType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
}
