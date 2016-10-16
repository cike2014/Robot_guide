package com.mmednet.main.socket;

/**
 * 发送的数据包
 * 
 * @author xiaowei
 *
 */
public class ProtocolBean {

	/**
	 * 数据包的类型---文件类型 or str类型
	 */
	private String protocolType;

	/**
	 * 消息内容----根据不同的消息类型，创建不同的msgBean
	 */
	private String protocolData;

	public ProtocolBean(String protocolType, String protocolData) {
		super();
		this.protocolType = protocolType;
		this.protocolData = protocolData;
	}

	public ProtocolBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public String getProtocolData() {
		return protocolData;
	}

	public void setProtocolData(String protocolData) {
		this.protocolData = protocolData;
	}

	@Override
	public String toString() {
		return "ProtocolBean [protocolType=" + protocolType + ", protocolData=" + protocolData + "]";
	}

}
