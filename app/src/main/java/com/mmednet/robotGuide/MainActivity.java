package com.mmednet.robotGuide;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.mmednet.robotGuide.bean.IntelligentGuideDepartment;
import com.mmednet.robotGuide.bean.Question;
import com.mmednet.robotGuide.util.CommonUtils;
import com.mmednet.robotGuide.util.Constant;
import com.mmednet.robotGuide.util.DataCenter;
import com.mmednet.robotGuide.util.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements RobotService.IServiceConnectionlistener {

    @Bind(R.id.btn_submit)
    Button mBtnSubmit;
    @Bind(R.id.btn_yes)
    Button mBtnYes;
    @Bind(R.id.btn_no)
    Button mBtnNo;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    private static int NO=0;
    private Question curQuestion;
    private String sids="";
    private String finish="终止;结束;好了;退出;完成;提交";

    private static final String TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(com.mmednet.robotGuide.R.layout.activity_main);
        ButterKnife.bind(this);
        String keyWord=getIntent().getStringExtra("keyWord");
        DataCenter.getInstance().initData(keyWord);
        showNextQuestion();
        registerReceiver();
    }

    private void showNextQuestion() {
        Question q=DataCenter.getInstance().getQuestion(NO);
        if (q == null) {
            ToastUtil.showMsg(this, "回答完成");
            mBtnYes.setEnabled(false);
            mBtnNo.setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    submitData();
                }
            }, 1000);
            return;
        }
        mTvTitle.setText(q.title);
        CommonUtils.playTTS(this, q.title.substring(0, q.title.length() - 1) + "&&1");
        this.curQuestion=q;
    }

    @OnClick(R.id.btn_no)
    void clickBtnNo(View view) {
        nck();
    }

    @OnClick(R.id.btn_yes)
    void clickBtnYes(View view) {
        ack();
    }

    @OnClick(R.id.btn_submit)
    void clickBtnSubmit(View view) {
//        RobotService.getInstance().bindService(this);
//        RobotService.getInstance().setServiceConnectionlistener(this);


        submitData();
    }

    //肯定回答
    private void ack() {
        sids+=curQuestion.icon + ",";
        NO++;
        showNextQuestion();
    }

    //否定回答
    private void nck() {
        NO++;
        showNextQuestion();
    }

    private void submitData() {
        if (NO == 0) {
            ToastUtil.showMsg(this, "请选择");
            return;
        }
        ArrayList<IntelligentGuideDepartment> departments=(ArrayList<IntelligentGuideDepartment>) DataCenter.getInstance().guidingDepartment(sids, this);
        Bundle bundle=new Bundle();
        bundle.putSerializable("departments", departments);
        Intent intent=new Intent(this, ResultActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        NO=0;
        sids="";
        finish();
    }

    private void registerReceiver() {
        IntentFilter filter=new IntentFilter();
        filter.addAction(Constant.SEND_TO_ACTIVITY);
        registerReceiver(receiveVoice, filter);
    }


    private BroadcastReceiver receiveVoice=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result=intent.getStringExtra("result");
            if (Arrays.asList(curQuestion.positive.split(";")).contains(result)) {
                ack();
                return;
            }
            if (Arrays.asList(curQuestion.negative.split(";")).contains(result)) {
                nck();
                return;
            }
            if (Arrays.asList(finish.split(";")).contains(result)) {
                submitData();
                return;
            }
            CommonUtils.playTTS(context, "对不起，听不懂您在说什么");
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiveVoice);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RobotApplication.getInstance().setTop(Constant.TOP_MAIN);
    }

    @Override
    public void onServiceConnected() {
        Log.d(TAG, "链接成功回调");
    }

    @Override
    public void onServiceDisconnected() {
        Log.d(TAG, "链接断开回调");
    }
}
