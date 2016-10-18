package com.mmednet.robotGuide;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmednet.robotGuide.bean.IntelligentGuideDepartment;
import com.mmednet.robotGuide.util.CommonUtils;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultActivity extends AppCompatActivity  {

    @Bind(R.id.ll1)
    LinearLayout mLl1;
    @Bind(R.id.ll2)
    LinearLayout mLl2;
    @Bind(R.id.result_iv_1)
    ImageView mResultIv1;
    @Bind(R.id.result_department_1)
    TextView mResultDepartment1;
    @Bind(R.id.tv_percent_1)
    TextView mTvPercent1;
    @Bind(R.id.tv_doctor_1)
    TextView mTvDoctor1;
    @Bind(R.id.rl_doctor1)
    RelativeLayout mRlDoctor1;

    @Bind(R.id.result_iv_2)
    ImageView mResultIv2;
    @Bind(R.id.result_department_2)
    TextView mResultDepartment2;
    @Bind(R.id.tv_percent_2)
    TextView mTvPercent2;
    @Bind(R.id.tv_doctor_2)
    TextView mTvDoctor2;
    @Bind(R.id.rl_doctor2)
    RelativeLayout mRlDoctor2;

    @Bind(R.id.result_iv_3)
    ImageView mResultIv3;
    @Bind(R.id.result_department_3)
    TextView mResultDepartment3;
    @Bind(R.id.tv_percent_3)
    TextView mTvPercent3;
    @Bind(R.id.tv_doctor_3)
    TextView mTvDoctor3;
    @Bind(R.id.rl_doctor3)
    RelativeLayout mRlDoctor3;

    private static final String TAG=ResultActivity.class.getSimpleName();
    private static final String positive = "是;接听";
    private static final String negative = "否;不接听";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        ArrayList<IntelligentGuideDepartment> departments=(ArrayList<IntelligentGuideDepartment>) getIntent().getSerializableExtra("departments");

        if(departments!=null && departments.size()==3){
            Log.d(TAG, "departments:" + departments);
            IntelligentGuideDepartment department1=departments.get(0);
            IntelligentGuideDepartment department2=departments.get(1);
            IntelligentGuideDepartment department3=departments.get(2);

            mResultDepartment1.setText(department1.name);
            mResultIv1.setImageResource(IntelligentGuideDepartment.getDrawable(department1.id));
            mTvPercent1.setText(getPercent(70) + "");

            mResultDepartment2.setText(department2.name);
            mResultIv2.setImageResource(IntelligentGuideDepartment.getDrawable(department2.id));
            mTvPercent2.setText(getPercent(50) + "");

            mResultDepartment3.setText(department3.name);
            mResultIv3.setImageResource(IntelligentGuideDepartment.getDrawable(department3.id));
            mTvPercent3.setText(getPercent(40) + "");
        }else{
            mLl1.setVisibility(View.GONE);
            mLl2.setVisibility(View.GONE);
            CommonUtils.playTTS(this,"您回答的问题太少，无法计算您所需的科室。");
        }

    }

    private double getPercent(double baseline) {
        Random random=new Random();
        return baseline + random.nextInt(20);
    }

    @OnClick({R.id.rl_doctor1, R.id.rl_doctor2, R.id.rl_doctor3})
    void videoClick(View view) {
        showDialog();
    }


    private void showDialog() {
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_result);
        dialog.findViewById(R.id.dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.dialog_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager packageManager=ResultActivity.this.getPackageManager();
                Intent intent=packageManager.getLaunchIntentForPackage("com.chinatel.robotclient");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ResultActivity.this.startActivity(intent);
            }
        });
        dialog.findViewById(R.id.dialog_no).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
