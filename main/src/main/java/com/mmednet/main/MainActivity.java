package com.mmednet.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmednet.main.bean.Account;
import com.mmednet.main.service.U05RobotService;
import com.mmednet.main.socket.SocketManager;
import com.mmednet.main.socket.U05RobotManger;
import com.mmednet.main.util.CommonUtil;
import com.mmednet.main.util.Constant;
import com.mmednet.main.util.PackageUtil;
import com.mmednet.main.util.SettingUtils;
import com.mmednet.main.util.TimeUtil;
import com.mmednet.main.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements U05RobotManger.WakeUpReceiver, U05RobotManger.BackToWakeUpReceiver, U05RobotManger.VoiceRecognitionResultReceiver {

    private static final String TAG=MainActivity.class.getSimpleName();
    @Bind(R.id.ll_info)
    LinearLayout mLlInfo;
    @Bind(R.id.ll_info1)
    LinearLayout mLlInfo1;
    @Bind(R.id.ll_info2)
    LinearLayout mLlInfo2;
    @Bind(R.id.ll_register)
    LinearLayout mLlRegister;
    @Bind(R.id.tv_time1)
    TextView mTvTime1;
    @Bind(R.id.tv_time2)
    TextView mTvTime2;
    @Bind(R.id.ll_xueya)
    LinearLayout mLlXueYa;
    @Bind(R.id.ll_tizhong)
    LinearLayout mLlTiZhong;
    @Bind(R.id.ll_tiwen)
    LinearLayout mLlWen;
    @Bind(R.id.ll_xuetang)
    LinearLayout mLlXueTang;
    @Bind(R.id.iv_tizhong)
    ImageView mIvTiZhong;
    @Bind(R.id.iv_xueya)
    ImageView mIvXueYa;
    @Bind(R.id.iv_tiwen)
    ImageView mIvTiwen;
    @Bind(R.id.iv_xuetang)
    ImageView mIvXueTang;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_sex)
    TextView mTvSex;
    @Bind(R.id.tv_age)
    TextView mTvAge;
    @Bind(R.id.tv_diseases)
    TextView mTvDiseases;
    @Bind(R.id.tv_risk)
    TextView mTvRisk;


    private String t_tizhong;
    private String t_bmi;
    private String t_shuzhangya;
    private String t_yasuoya;
    private String t_xueyang;
    private String t_mailv;
    private String t_kongfu;
    private String t_canqian;
    private String t_canhou;
    private String t_shuiqian;

    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SocketManager.getInstance().init(this);
//        U05RobotManger.getInstance().registerVoiceResultReceiver(getApplicationContext(), this);
//        U05RobotManger.getInstance().registerWakeUpReceiver(getApplicationContext(), this);
//        U05RobotManger.getInstance().registerBackToWakeUpReceiver(getApplicationContext(), this);
        startService(new Intent(this, U05RobotService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        userId=SettingUtils.getSharedPreferences(this, Constant.USER_ID, "");
        mTvTime1.setText(TimeUtil.getTimeSx());
        mTvTime2.setText(TimeUtil.getTimeSx());
        if (CommonUtil.getLoginSte(this)) {
            mLlInfo.setVisibility(View.VISIBLE);
            mLlRegister.setVisibility(View.GONE);
            mTvTime1.setVisibility(View.VISIBLE);
            mTvTime2.setVisibility(View.GONE);
            Account account=CommonUtil.getAccount(this, userId);
            mTvName.setText(account.name);
            mTvSex.setText(account.sex == 1 ? "男" : "女");
            mTvAge.setText(TimeUtil.getAge(account.birth) + "");
            mTvDiseases.setText(account.diseases);
            mTvRisk.setText(account.risk);
        } else {
            mLlInfo1.setVisibility(View.INVISIBLE);
            mLlInfo2.setVisibility(View.INVISIBLE);
            mTvTime1.setVisibility(View.INVISIBLE);
            mTvTime2.setVisibility(View.VISIBLE);
            mLlRegister.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(t_tizhong) && !TextUtils.isEmpty(t_bmi)) {
            animate(mIvTiZhong, mLlTiZhong);
        }
    }

    @OnClick({R.id.iv_daozhen, R.id.iv_pinggu, R.id.iv_xueya, R.id.iv_tizhong, R.id.iv_tiwen, R.id.iv_xuetang})
    void imageClick(View view) {

        if (CommonUtil.getLoginSte(this)) {
            PackageUtil.MyPackage myPackage=PackageUtil.getPackage(view.getId());
            if (myPackage != null && CommonUtil.existPackage(this, myPackage.getPackageName())) {
                Bundle args=null;
                switch (view.getId()) {
                    case R.id.iv_pinggu:
                        Account account=CommonUtil.getAccount(this, userId);
                        args=new Bundle();
                        args.putString("userid", account.id);
                        args.putInt("sex", account.sex);
                        args.putInt("age", TimeUtil.getAge(account.birth));
                        PackageUtil.setArgs(view.getId(), args);
                        break;
                }
                Log.d(TAG, "myPackageInfo:" + myPackage);
                CommonUtil.redirect(myPackage.getPackageName(), myPackage.getActivityName(), myPackage.getArgs(), this);
            } else {
                ToastUtil.showMsg(this, R.string.not_exist_app);
            }
        } else {
            ToastUtil.showMsg(this, R.string.not_login);
        }

    }

    @OnClick(R.id.ll_register)
    void btnRegisterClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
//        SocketManager.getInstance().init(this);
//        MsgSendUtils.sendStringMsg(MsgType.SEND_MSGTYPE_PLAY_SOUND, "/ai/Ai05res/a1/res/audio/communicate/077.mp3")
// ;
//        String m="您的收缩压为" + 150 + "," + "您的舒张压为" + 90;
//
//        MsgSendUtils.sendStringMsg(MsgType.SEND_MSGTYPE_PLAY_TTS, m);
//
//        //跨应用发送一个广播
////        Intent intent=new Intent("com.mmednet.interprocess");
////        sendBroadcast(intent);
//        MsgSendUtils.sendStringMsg(MsgType.SEND_MSGTYPE_PLAY_TTS, "你好");
    }

    @OnClick(R.id.ll_peiban)
    void btnPeibanClick(View view) {
        ToastUtil.showMsg(this, "开启视频咨询");
    }

    private void animate(ImageView image, final LinearLayout layout) {
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(image, "translationY", -image.getMeasuredHeight());
        objectAnimator.setDuration(2000);
        objectAnimator.setStartDelay(2000);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                layout.setVisibility(View.VISIBLE);
                layout.setAlpha(0);
                ObjectAnimator layoutAnimator=ObjectAnimator.ofFloat(layout, "alpha", 0f, 0.3f, 0.6f, 0.8f, 1.0f);
                layoutAnimator.setDuration(2000);
                layoutAnimator.start();
            }
        });
        objectAnimator.start();
    }


    @Override
    public void onHandleWakeUpEvent() {
        //机器人被唤醒了，可以开始语音识别.
        Toast.makeText(getApplicationContext(), "onHandleWakeUpEvent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHandleEvent(Context context) {
        //结束语音识别，重新进入待唤醒状态。
        Toast.makeText(getApplicationContext(), "registerBackToWakeUpReceiver", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHandleVoiceResulEvent(Context arg0, String arg1) {
        //收到语音识别的结果.
        if (!"error".equals(arg1)) {
            Intent intent=new Intent(Constant.OTHER_FLAG);
            Bundle bundle=new Bundle();
            bundle.putString("result", arg1);
            intent.putExtras(bundle);
            sendBroadcast(intent);
        }
        Toast.makeText(getApplicationContext(), "收到识别结果 " + arg1, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        U05RobotManger.getInstance().unRegisterBackToWakeUpReceiver(this);
//        U05RobotManger.getInstance().unRegisterVoiceRecognitionResulReceiver(this);
//        U05RobotManger.getInstance().unRegisterWakeUpReceiver(this);
    }
}
