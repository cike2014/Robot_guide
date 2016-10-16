package com.mmednet.main.socket;

import com.alibaba.fastjson.JSON;


/**
 * 平板发送消息给3288 ----通过串口和socket在这里切换
 * 
 * @author xiaowei
 *
 */
public class MsgSendUtils {

    public static final String TAG = "MsgSendUtils";

    /**
     * 发送String类型的数据
     * 
     * @param msgType
     * @param msgData
     */
    public static void sendStringMsg(int msgType, String msgData) {
        if (Config.USE_SOCKET) {
            StringMsgBean bean = new StringMsgBean();
            bean.setMsgType(msgType); // 兼容旧的方式
            bean.setMsgData(msgData);
            String sendBuf = JSON.toJSONString(bean);
            SocketManager.getInstance().sendStringMsgToServer(sendBuf);
        }
    }


   

}
