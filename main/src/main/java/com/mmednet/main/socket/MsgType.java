package com.mmednet.main.socket;

/**
 * Created by alpha on 2016/10/8.
 */
public class MsgType {

    // 平板接受rk3288的数据格式： 80 。。。数据(第一个字节是0x80，数据位是从第9个字节开始)

    // 平板发送给rk3288的数据格式：00 80 60 60 数据长度 (給mcu) 数据(数据位是从第9个字节开始)
    // 数据=消息类型+数据类型(byte or String)+数据长度(给3288) + 内容
    /**
     * 协议头
     */
    public static final byte[] SEND_MSG_HEAD = { 0x00, (byte) 0x80 };

    /**
     * 数据类型
     */
    public static final byte DATATYPE_BYTE = (byte) 0x55;
    public static final byte DATATYPE_STRING = (byte) 0xaa;

    // 平板接受的消息编号---- 1--- 8399
    // 平板发送的消息编号---- 8400-65535
    /**
     * 消息类型---停止播放音频
     */
    public static final int SEND_MSGTYPE_STOP_SOUND = 8399;
    /**
     * 消息类型---播放音频
     */
    public static final int SEND_MSGTYPE_PLAY_SOUND = 8400;
    /**
     * 消息类型---播放TTS
     */
    public static final int SEND_MSGTYPE_PLAY_TTS = 8401;
    /**
     * 消息类型--健康数据
     */
    public static final int SEND_SMARTHOME_HEALTH_DATA = 8402;
    /**
     * 消息类型--开始声控
     */
    public static final int SEND_START_RECOGNIZER = 8403;
    /**
     * 消息类型--取消声控
     */
    public static final int SEND_STOP_RECOGNIZER = 8404;
    /**
     * 消息类型--设置wifi密码
     */
    public static final int SEND_WIFI_PASSWORD = 8405;

    /**
     * pad 可以 请求 3288 进行更新请求，3288 返回是否需要更新。
     */
    public static final int SEND_UPDATE_APP = 8406;

    /**
     * 消息类型--停止滑动声音SeekBar时的进度
     */
    public static final int SEND_VOICE_PROGRESS = 8407;
    /**
     * 消息类型--停止滑动亮度SeekBar时的进度
     */
    public static final int SEND_LIGHT_PROGRESS = 8408;

    /**
     * 消息类型--用户确认升级固件
     */
    public static final int SEND_COMFIRM_UPDATE_FIREWARE = 8409;

    /**
     * 消息类型--自定义声控对话
     */
    public static final int SEND_CUSTOM_DIALOGUE = 8410;

    /**
     * 消息类型--自定义开机语音
     */
    public static final int SEND_CUSTOM_START_VOICE = 8411;

    /**
     * 消息类型--自定义感应反馈
     */
    public static final int SEND_CUSTOM_SENSOR_VOICE = 8412;

    /**
     * 消息类型--自定义待机语
     */
    public static final int SEND_CUSTOM_STANDBY_VOICE = 8413;

    /**
     * 消息类型--删除自定义聊聊天-可能是file or string
     */
    public static final int SEND_DELETE_DIALOGUE = 8414;

    /**
     * 消息类型--删除待机语-可能是file or string
     */
    public static final int SEND_DELETE_STANDBY_VOICE = 8415;

    /**
     * 消息类型--设置开机语 或 感应器 为 系统自带提示语or 自定义提示语
     */
    public static final int SEND_Setting_Custom_Voice = 8416;

    /**
     * 消息类型--进入触控模式--有pad控制
     */
    public static final int SEND_ACCESS_TOUCH_MODE = 8418;

    /**
     * 消息类型--进入声控模式--有pad控制
     */
    public static final int SEND_ACCESS_VOICE_MODE = 8419;

    /**
     * 发送动作指令
     */
    public static final int SEND_ACTION = 8420;

    // 人脸识别相关

    /**
     * 消息类型--拍照
     */
    public static final int SEND_TAKE_PHOTO = 8421;

    /**
     * 消息类型--取消注册
     */
    public static final int SEND_CANCLE_REGISTER = 8424;

    /**
     * 消息类型--获取姓名
     */
    public static final int SEND_GET_NAME_AND_SEX = 8425;

    /**
     * 消息类型--音频文件
     */
    public static final int SEND_VOICE_FILE = 8426;

    /**
     * 消息类型--接收图片
     */
    public static final int RECEIVER_PHOTO_FILE = 8427;

    /**
     * 消息类型--自定义语音
     */
    public static final int SEND_VOICE_STR = 8428;

    /**
     * 消息类型--随机语音
     */
    public static final int SEND_VOICE_RANDOM = 8429;

    // ////////////////

    /**
     * 发送眼睛指令
     */
    public static final int SEND_EYE_MOTION = 8422;
    /**
     * 消息类型--识别结果
     */
    public static final int RECEIVER_FACE_REGONGNIZE_RESULT = 8423;

    /**
     * 消息类型--心跳
     */
    public static final int SEND_HEARTBEAT = 8430;

    /**
     * 消息类型----接受语音识别的结果--同时是结束录音的消息
     */
    public static final int RECEIVER_MSG_VOICE_RECOGNIZER_RESULT = 1;

    /**
     * 消息类型---接受rk3288音频播放结束
     */
    public static final int RECEIVER_MSG_PLAY_SOUND_END = 2;

    /**
     * 消息----接受rk3288是否更新app的请求，
     */
    public static final int RECEIVER_MSG_UPDATE_INFO = 3;

    /**
     * 消息----接受rk3288更新app的进度，
     */
    public static final int RECEIVER_MSG_UPDATE_PROGRESS = 4;

    /**
     * 消息----接受rk3288有更新固件的请求，
     */
    public static final int RECEIVER_MSG_UPDATE_FIREWARE = 5;

    /**
     * 消息----收到唤醒的消息，
     */
    public static final int RECEIVER_MSG_WAKE_UP = 6;

    /**
     * 自定义语音成功
     */
    public static final int RECEIVER_CUSTOM_SUCCESS = 7;

    /**
     * 自定义语音失败
     */
    public static final int RECEIVER_CUSTOM_FAIL = 8;

    /**
     * 退出识别，进入唤醒时--pad跳转到唤醒界面
     */
    public static final int RECEIVER_MSG_SUSPEND = 9;

    /**
     * 开始录音
     */
    public static final int RECEIVER_MSG_START_RECODER = 10;

    /**
     * 开始识别
     */
    public static final int RECEIVER_MSG_START_RECOGNIZER = 11;

    /**
     * 切换平板相关的界面，，兼容之前的发送方式
     */
    public static final int RECEIVER_MSG_SWITCH_ACTIVITY = 200;

    public static final int FILE_TYPE = 8398;

    /**
     * 消息类型--第三方注册
     */
    public static final int SEND_OTHERS_REGISTER = 8439;

    /**
     * 消息类型--接收人脸识别结果
     */
    public static final int RECEIVER_FACE_RECOGNIZE_RESULT = 8440;

    /**
     * 消息类型--接收删除结果
     */
    public static final int RECEIVER_DELETE_RESULT = 8436;

    /**
     * 消息类型--接收启动注册命令
     */
    public static final int RECEIVER_START_REGISTER = 8441;
    /**
     * 消息类型--第三方注册结果
     */
    public static final int RECEIVER_OTHERS_REGISTER_RESULT = 8438;
    /**
     * 消息类型--第三方识别结果
     */
    public static final int RECEIVER_OTHERS_RECONGNIZE_RESULT = 8443;

    /**
     * 消息类型--发送图片结果
     */
    public static final int RECEIVER_PHOTO_RESULT = 8442;

    /**
     * 消息类型--注册结果
     */
    public static final int RECEIVER_REGISTE_RESULT = 8433;

}
