package com.mmednet.main.socket;

/**
 * 3288 发送过来的消息转换后通过广播发送出去
 * 
 * @author xiaowei
 *
 */
public class ReceiverSocketAction {
	/**
	 * 基类action
	 */
	private static final String BASEACITION = "com.unisrobot.uurobot_u5.";

	/**
	 * 播放音频结束
	 */
	public static final String PLAY_SOUND_END = BASEACITION + "action.play_sound.end";

	/**
	 * 消息类型----接受语音识别的结果
	 */
	public static final String MSG_VOICE_RECOGNIZER_RESULT = BASEACITION + "action.msg_voice.recognizer.result";

	/**
	 * 消息类型----接受唤醒识别的结果
	 */
	public static final String MSG_VOICE_RECOGNIZER_WAKEUP = BASEACITION + "action.msg_voice.recognizer.wakeup";

	/**
     * 消息类型----人脸识别的结果
     */
    public static final String FACE_RECOGNIZER_RESULT = BASEACITION + "action.face.recognizer.result";

	/**
	 * 第三方注册结果
	 */
	public static final String FACE_RECOGNIZER_OTHERS_RESULT = BASEACITION+"action.face.recognizer.others.result";
	/**
	 * 退回到待机状态
	 */
	public static final String RECEIVER_MSG_SUSPEND = BASEACITION + "action.suspend";
	/**
	 * 开始声控状态
	 */
	public static final String RECEIVER_MSG_START_RECODER= BASEACITION + "action.start.recoder";
	/**
	 * 声控识别状态
	 */
	public static final String RECEIVER_MSG_START_RECOGNIZER = BASEACITION + "action.start.recognizer";
	
	
	/**
     * 播放音频结束
     */
    public static final String MSG_HEARTBEAT = BASEACITION + "action.heartbeat";

}
