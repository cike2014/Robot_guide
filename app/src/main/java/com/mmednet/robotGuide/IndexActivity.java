package com.mmednet.robotGuide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.mmednet.robotGuide.util.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndexActivity extends AppCompatActivity {

    @Bind(R.id.iv_quanshen)
    ImageView mIvQuanshen;
    @Bind(R.id.iv_toujing)
    ImageView mIvTouJing;
    @Bind(R.id.iv_xiongbu)
    ImageView mIvXiongBu;
    @Bind(R.id.iv_fubu)
    ImageView mIvFubu;
    @Bind(R.id.iv_sizhi)
    ImageView mIvSiZhi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mmednet.robotGuide.R.layout.activity_index);
        ButterKnife.bind(this);
    }

    @OnClick({com.mmednet.robotGuide.R.id.iv_quanshen, com.mmednet.robotGuide.R.id.iv_toujing, com.mmednet.robotGuide.R.id.iv_xiongbu, com.mmednet.robotGuide.R.id.iv_fubu, com.mmednet.robotGuide.R.id.iv_sizhi})
    void ivClick(View v) {
        String keyWord="";
        switch (v.getId()) {
            case R.id.iv_quanshen:
                keyWord="全身";
                break;
            case R.id.iv_toujing:
                keyWord="头颈";
                break;
            case R.id.iv_xiongbu:
                keyWord="胸部";
                break;
            case R.id.iv_fubu:
                keyWord="腹部";
                break;
            case R.id.iv_sizhi:
                keyWord="四肢";
                break;

        }
        startActivity(keyWord);
    }

    private void startActivity(String keyWord) {
        Intent intent=new Intent(this, MainActivity.class);
        intent.putExtra("keyWord", keyWord);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        RobotApplication.getInstance().setTop(Constant.TOP_INDEX);
    }
}
