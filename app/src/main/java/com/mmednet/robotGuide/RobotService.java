package com.mmednet.robotGuide;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class RobotService {
    private static RobotService my;
    private static final int MESSAGE_CODE_PLAY_COMPLETE = 0;
    private static final String TAG = "RobotService";

    private Messenger sendMessenger;

    private Messenger messenger;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_CODE_PLAY_COMPLETE:
                	Log.e("RobotService", "handleMessage");
                    if(mOnCompletionListener!=null)
                    {
                        mOnCompletionListener.onCompletion(null);
                        mOnCompletionListener = null;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private RobotService() {
        sendMessenger = new Messenger(handler);

    }
    boolean  binded=false;
    /**
     * application context.
     *
     * @param context
     */
    public void bindService(Context context) {
        if(binded)
        {
            return ;
        }
        binded=true;
        Log.i("messenger","bindService");
        Intent a = new Intent();
        a.setClassName("com.mmednet.main",
                "com.mmednet.main.service.U05RobotService");
        context.bindService(a, conn, Context.BIND_AUTO_CREATE);
        
        
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.unisrobot.uurobot_u5.action.play_sound.end");
        context.registerReceiver(mBroadcastReceiver, filter);
    }
    
    
    
    BroadcastReceiver  mBroadcastReceiver = new   BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
        	Log.e("RobotService", "onReceive");
               if(mOnCompletionListener!=null)
               {
                   mOnCompletionListener.onCompletion(null);
//                   mOnCompletionListener = null;
               }
        } 

    }  ;

    public static RobotService getInstance() {
        if (my == null) {
            my = new RobotService();
        }
        return my;
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.i("messenger", "onServiceConnected: onServiceConnected");
            messenger = new Messenger(binder);
            if (serviceConnectionlistener!=null) {
            	serviceConnectionlistener.onServiceConnected();
			}
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("messenger", "onServiceDisconnected");
            if (serviceConnectionlistener!=null) {
            	serviceConnectionlistener.onServiceDisconnected();
			}
        }
    };
    
    private OnCompletionListener mOnCompletionListener;

    public void setMOnCompletionListener(OnCompletionListener mOnCompletionListener) {
        this.mOnCompletionListener = mOnCompletionListener;
    }

    public void play(String path,  int  actionID,int  eyeID) {
//        this.mOnCompletionListener = null;
        sendMessageToServer(path,actionID,eyeID);
    }
    /**
     * ������Ϣ������� ע������ msg.replyTo = sendMessenger ����Ȼ�ղ����ظ�����Ϣ.
     * 
     * @param str
     */
    private void sendMessageToServer(String str,int  actionID,int  eyeID) {
        
        Log.e(TAG, "sendMessageToServer paly "+str);
        Message msg = Message.obtain(null, 0);
        // msg.obj = new Intent(str);
        Bundle b = new Bundle();
        b.putString("path", str);
        if(actionID!=-1)
        {
            b.putInt("actionID", actionID);
        }
        if(eyeID!=-1)
        {
            b.putInt("eyeID", eyeID);
        }
        msg.obj = b;
        msg.replyTo = sendMessenger;
        try {
            // invoke mTarget.send(message);
            // paratmer Message is Parcelable
            // and also is Messenger .
            // need to read createFromParcel
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public interface IServiceConnectionlistener{
    	void onServiceConnected();
    	void onServiceDisconnected();
    }
    private IServiceConnectionlistener serviceConnectionlistener;

	public void setServiceConnectionlistener(IServiceConnectionlistener serviceConnectionlistener) {
		this.serviceConnectionlistener = serviceConnectionlistener;
	}
	 /**
     * 开始语音识别
     * 
     */
    public void startVoiceRecognition() {
        // 以下2行和需求相关，可以传递自己需要的参数信息
        Message msg = Message.obtain(null, MESSAGE_CODE_START_RECOGNIZER);
        // msg.obj = new Intent(str);
//        Bundle b = new Bundle();
//        b.putString("path", str);
//        msg.obj = b;
        // 告诉服务端如果回复信息的话，回复到这个Messenger。
        msg.replyTo = sendMessenger;
        try {
            // invoke mTarget.send(message);
            // paratmer Message is Parcelable
            // and also is Messenger .
            // need to read createFromParcel
            messenger.send(msg);
        } catch (RemoteException e) {
            // 跨进程调用，所以才有 RemoteException 。
            e.printStackTrace();
        }
    }
    /**
     * 结束语音识别
     * 
     */
    public void stopVoiceRecognition() {
        // 以下2行和需求相关，可以传递自己需要的参数信息
        Message msg = Message.obtain(null, MESSAGE_CODE_STOP_RECOGNIZER);
        // msg.obj = new Intent(str);
//        Bundle b = new Bundle();
//        b.putString("path", str);
//        msg.obj = b;
        // 告诉服务端如果回复信息的话，回复到这个Messenger。
        msg.replyTo = sendMessenger;
        try {
            // invoke mTarget.send(message);
            // paratmer Message is Parcelable
            // and also is Messenger .
            // need to read createFromParcel
            messenger.send(msg);
        } catch (RemoteException e) {
            // 跨进程调用，所以才有 RemoteException 。
            e.printStackTrace();
        }
    }
    
    private static  final int MESSAGE_CODE_PLAY = 0;
    private static  final int MESSAGE_CODE_START_RECOGNIZER = MESSAGE_CODE_PLAY + 1;
    private static  final int MESSAGE_CODE_STOP_RECOGNIZER = MESSAGE_CODE_PLAY + 2;
//    Intent intent = new Intent(
//            ReceiverSocketAction.MSG_VOICE_RECOGNIZER_RESULT);
//    intent.putExtra(IntentKey.VOICE_RESULT,
//            result);
////    LocalBroadcastManager
////    .getInstance(VoiceCtrService.this)
////    .sendBroadcast(intent);
//    sendBroadcast(intent);
    
    public interface ISoundReceiverlistener{
    	void handleVoiceResult(String result);
    }
    private ISoundReceiverlistener soundReceiverlistener;

	public void setSoundReceiverlistener(ISoundReceiverlistener soundReceiverlistener) {
		this.soundReceiverlistener = soundReceiverlistener;
	}
    
    public void registerSoundReceiver(Context context,ISoundReceiverlistener soundReceiverlistener){
    	this.soundReceiverlistener = soundReceiverlistener;
    	IntentFilter filter = new IntentFilter();
    	filter.addAction(MSG_VOICE_RECOGNIZER_RESULT);
		context.registerReceiver(soundReceiver, filter );
    }
    private BroadcastReceiver soundReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (soundReceiverlistener!=null) {
				soundReceiverlistener.handleVoiceResult(intent.getStringExtra(VOICE_RESULT));
			}
		}
    };
    public void unRegisterSoundReceiver(Context context){
    	context.unregisterReceiver(soundReceiver);
    	setSoundReceiverlistener(null);
    }
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
* 声控字符串结果
*/
public static final String VOICE_RESULT = "voice_result";

}
