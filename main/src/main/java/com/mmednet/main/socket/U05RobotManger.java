package com.mmednet.main.socket;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class U05RobotManger {

	private static final String TAG = U05RobotManger.class.getSimpleName();
	private static U05RobotManger one = new U05RobotManger();

	private BroadcastReceiver wkReceiver;
	private BroadcastReceiver mRecongnizeResultReceiver;
	private BroadcastReceiver mVoiceResultEventReceiver;
	private BroadcastReceiver mBackToWakeUpReceiver;
	private final static String PHOTO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/msc/";
	private BroadcastReceiver mVoiceControlReceiver;

	private U05RobotManger() {

	}

	public static U05RobotManger getInstance() {
		return one;
	}

	/**
	 * 播放rk3288平板上的音频，路径是sd卡根开始的路径.
	 * 
	 * @param path
	 */
	public void playSound(String path) {
		// if(true)
		// {
		// System.out.println(TAG + "playSound "+path);
		// return ;
		// }

		MsgSendUtils.sendStringMsg(MsgType.SEND_MSGTYPE_PLAY_SOUND, path);
		// Intent intent = new Intent(ReceiverAPPAction.START_PLAY_SOUND);
		// intent.putExtra(IntentKey.SOUND_PATH, path);
		// intent.putExtra(IntentKey.COMPLETED_DO, IntentContent.NONE);
		// intent.putExtra(IntentKey.MSG_TYPE, MsgType.SEND_MSGTYPE_PLAY_SOUND);
		// sendBroadcast(intent);

	}

	/**
	 * wifi 连接 目前没有返回值，不知道是否执行成功.
	 * 
	 * @param ssid
	 * @param password
	 */
	public void connectToWIFI(String ssid, String password) {
		MsgSendUtils.sendStringMsg(MsgType.SEND_WIFI_PASSWORD, ssid + "&&" + password);
	}

	/**
	 * 开始语音识别
	 * 
	 */
	public void startVoiceRecognition() {
		MsgSendUtils.sendStringMsg(MsgType.SEND_START_RECOGNIZER, "ok");//
	}

	/**
	 * 停止语音识别
	 * 
	 */
	public void stopVoiceRecognition() {
		MsgSendUtils.sendStringMsg(MsgType.SEND_STOP_RECOGNIZER, "ok");//
	}

	/**
	 * 开始人脸识别
	 * 
	 */
	public void startFaceRecongnize() {
		// MsgSendUtils.sendStringMsg(MsgType.SEND_START_FACE_REGONGNIZE, "");

	}

	/**
	 * 注册人脸信息
	 *
	 * @param img
	 *            图片
	 * @param name
	 *            名字
	 * @param id
	 */
	public static void registerFaceRecongnize(Bitmap img, String name, String id) {
		if (img != null) {
			Log.d(TAG,"PHOTO_PATH:"+PHOTO_PATH);
			File dir = new File(PHOTO_PATH);
			if (!dir.exists()){
				dir.mkdirs();
			}
			File file = new File(PHOTO_PATH + "fr.jpg");// 将bitmap存成图片文件
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(file));
				img.compress(Bitmap.CompressFormat.JPEG, 100, bos);
				bos.flush();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (!file.exists()) {
				Log.i("FaceRegRegisterActivity", "图片不正确");
				return;
			}
			FileMsgBean voiceFile = new FileMsgBean(file.getName(),
					file.length(), MsgType.SEND_OTHERS_REGISTER,
					JSON.toJSONString(new OthersRegisterBean(name, id)));
			FaceRecManager.getInstance().senFileMsg(PHOTO_PATH + "fr.jpg",
					voiceFile);

			// 发送数据到优友本机

		}
	}



	/**
	 * 注册人脸信息
	 * 
	 * @param imgPath
	 *            图片路径
	 * @param name
	 *            名字
	 * @param id
	 */
	public void registerFaceRecongnize(String imgPath, String name, String id) {

		// File file=new File(imgPath);//取出图片
		// if(!file.exists()){
		// Log.i("FaceRegRegisterActivity", "找不到图片，路径:"+imgPath);
		// return;
		// }
		// FileMsgBean bean = new FileMsgBean(file.getName(), file.length(),
		// MsgType.SEND_FACE_REG_BITMAP,name);
		// // 发送数据到优友本机
		// MsgSendUtils.senFileMsg(imgPath, bean);

	}


	public void registerWakeUpReceiver(Context context, WakeUpReceiver receiver) {
		final WakeUpReceiver lo = receiver;
		wkReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				lo.onHandleWakeUpEvent();
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(ReceiverSocketAction.MSG_VOICE_RECOGNIZER_WAKEUP);
		LocalBroadcastManager.getInstance(context).registerReceiver(wkReceiver, filter);
	}

	public void recongnizeResultReceiver(Context context, RecongnizeResultReceiver receiver) {
		final RecongnizeResultReceiver lo = receiver;
		mRecongnizeResultReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				lo.onHandleRecongnizeResult(intent.getStringExtra("face_recognize_result"));
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(ReceiverSocketAction.FACE_RECOGNIZER_RESULT);
		LocalBroadcastManager.getInstance(context).registerReceiver(mRecongnizeResultReceiver, filter);
	}

	public void unRegisterWakeUpReceiver(Context context) {
		LocalBroadcastManager.getInstance(context).unregisterReceiver(wkReceiver);
	}

	public void registerVoiceResultReceiver(Context context, VoiceRecognitionResultReceiver receiver) {
		final VoiceRecognitionResultReceiver lo = receiver;
		mVoiceResultEventReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				String result = intent.getStringExtra(IntentKey.VOICE_RESULT);
				lo.onHandleVoiceResulEvent(context, result);
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(ReceiverSocketAction.MSG_VOICE_RECOGNIZER_RESULT);
		context.registerReceiver(mVoiceResultEventReceiver, filter);
	}

	public void unRegisterVoiceRecognitionResulReceiver(Context context) {
		context.unregisterReceiver(mVoiceResultEventReceiver);
	}

	/**
	 * 返回到待机界面
	 * 
	 * @param context
	 * @param receiver
	 */
	public void registerBackToWakeUpReceiver(Context context, BackToWakeUpReceiver receiver) {
		final BackToWakeUpReceiver lo = receiver;
		mBackToWakeUpReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				lo.onHandleEvent(context);
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(ReceiverSocketAction.RECEIVER_MSG_SUSPEND);
		LocalBroadcastManager.getInstance(context).registerReceiver(mBackToWakeUpReceiver, filter);
	}

	public void unRegisterBackToWakeUpReceiver(Context context) {
		LocalBroadcastManager.getInstance(context).unregisterReceiver(mBackToWakeUpReceiver);
	}

	/**
	 * 声控界面状态交互
	 * 
	 * @param context
	 * @param receiver
	 */
	public void registerChattingReceiver(Context context, ChattingReceiver receiver) {
		final ChattingReceiver lo = receiver;
		mVoiceControlReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				if (intent != null) {
					String action = intent.getAction();
					if (action != null && !action.isEmpty()) {
						if (action.equals(ReceiverSocketAction.RECEIVER_MSG_START_RECODER)) {
							lo.onHandleRecoderEvent(context);
						} else if (action.equals(ReceiverSocketAction.RECEIVER_MSG_START_RECOGNIZER)) {
							lo.onHandleRecognizerEvent(context);
						}
					}
				}

			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(ReceiverSocketAction.RECEIVER_MSG_START_RECODER);
		filter.addAction(ReceiverSocketAction.RECEIVER_MSG_START_RECOGNIZER);
		LocalBroadcastManager.getInstance(context).registerReceiver(mVoiceControlReceiver, filter);
	}

	public void unRegisterChattingReceiver(Context context) {
		LocalBroadcastManager.getInstance(context).unregisterReceiver(mVoiceControlReceiver);
	}

	public interface WakeUpReceiver {
		public void onHandleWakeUpEvent();
	};

	/**
	 * 识别结果
	 * 
	 * @author ruandan
	 *
	 */
	public interface RecongnizeResultReceiver {
		public void onHandleRecongnizeResult(String name);
	};

	public static interface VoiceRecognitionResultReceiver {
		public void onHandleVoiceResulEvent(Context context, String result);
	};

	public static interface BackToWakeUpReceiver {
		public void onHandleEvent(Context context);
	};

	public static interface ChattingReceiver {
		public void onHandleRecoderEvent(Context context);

		public void onHandleRecognizerEvent(Context context);
	};

}
