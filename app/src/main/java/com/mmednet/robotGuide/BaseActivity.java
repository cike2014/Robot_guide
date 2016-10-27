package com.mmednet.robotGuide;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);

    }

    protected void showLoading(int resourceTitle,int resourceMsg){
        if(progressDialog!=null && !progressDialog.isShowing()){
            progressDialog.setTitle(getResources().getString(resourceTitle));
            progressDialog.setMessage(getResources().getString(resourceMsg));
            progressDialog.show();
        }else{

        }
    }

    protected void hideLoading(){
        if(progressDialog!=null && progressDialog.isShowing()){
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }


}
