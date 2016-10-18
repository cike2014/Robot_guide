package com.mmednet.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.mmednet.main.bean.Account;
import com.mmednet.main.db.actual.AccountDao;
import com.mmednet.main.socket.U05RobotManger;
import com.mmednet.main.util.Constant;
import com.mmednet.main.util.PicturesUtils;
import com.mmednet.main.util.SettingUtils;
import com.mmednet.main.util.ToastUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.iv_head)
    ImageView mIvHead;
    @Bind(R.id.et_name)
    EditText mEtName;
    @Bind(R.id.et_phone)
    EditText mEtPhone;
    @Bind(R.id.rg_sex)
    RadioGroup mRgSex;
    @Bind(R.id.et_year)
    EditText mEtYear;
    @Bind(R.id.et_month)
    EditText mEtMonth;
    @Bind(R.id.et_day)
    EditText mEtDay;
    @Bind(R.id.bt_register)
    Button mBtRegister;

    public static final Pattern PATTERN_PHONE=Pattern.compile("^[1][3-8]+\\d{9}");
    private PicturesUtils picturesUtils;
    private Bitmap ivHeadBitmap;

    private static final String TAG=RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        picturesUtils=PicturesUtils.newInstance();

//        Bitmap bm = getBitmap("12341234.png");
//        if(bm!=null)
//        mIvHead.setImageBitmap(bm);

    }

    @OnClick(R.id.bt_register)
    void clickBtnRegister(View view) {
        if (prepareRegister()) {
            handleRegister();
        }
    }

    @OnClick(R.id.iv_head)
    void clickIvHead(View view) {
        picturesUtils.showUploadDialog(this);
    }

    private void handleRegister() {

        Account account=new Account();
        account.id=UUID.randomUUID().toString();
        account.name=mEtName.getText().toString().trim();
        account.sex=mRgSex.getCheckedRadioButtonId() == R.id.rb_nan ? 1 : 2;
        account.birth=mEtYear.getText().toString().trim() + "-" + mEtMonth.getText().toString().trim() + "-" + mEtDay.getText().toString().trim();
        account.phone=mEtPhone.getText().toString().trim();
        AccountDao ad=new AccountDao(this);
        ad.insert(account);
        SettingUtils.setEditor(this, Constant.USER_ID,account.id);
        ToastUtil.showMsg(this, "name:" + ad.queryObject("name", account.name));
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);
    }

    private boolean prepareRegister() {
        if (TextUtils.isEmpty(mEtName.getText().toString())) {
            ToastUtil.showMsg(this, "请输入真实姓名");
            mEtName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
            ToastUtil.showMsg(this, "请输入手机号");
            mEtPhone.requestFocus();
            return false;
        }
        if (!PATTERN_PHONE.matcher(mEtPhone.getText().toString()).matches()) {
            ToastUtil.showMsg(this, "请输入正确手机号");
            mEtPhone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(mEtYear.getText().toString()) || TextUtils.isEmpty(mEtMonth.getText().toString()) || TextUtils.isEmpty(mEtDay.getText().toString())) {
            ToastUtil.showMsg(this, "请填写出生日期");
            mEtYear.requestFocus();
            return false;
        }
        if (Integer.parseInt(mEtMonth.getText().toString()) > 12 || Integer.parseInt(mEtMonth.getText().toString()) < 1) {
            ToastUtil.showMsg(this, "请正确填写出生月份");
            mEtMonth.requestFocus();
            return false;
        }
        if (Integer.parseInt(mEtDay.getText().toString()) > 31 || Integer.parseInt(mEtDay.getText().toString()) < 1) {
            ToastUtil.showMsg(this, "请正确填写出生日期");
            mEtDay.requestFocus();
            return false;
        }

        if(ivHeadBitmap==null){
            ToastUtil.showMsg(this,"请先选择头像上传");
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        ivHeadBitmap=picturesUtils.handlePictureData(resultCode, requestCode, data);
        if (ivHeadBitmap != null){
            mIvHead.setImageBitmap(ivHeadBitmap);
            U05RobotManger.registerFaceRecongnize(ivHeadBitmap,"andy","12341234");
            saveBitmapToSD(ivHeadBitmap,"12341234.png");
        }

    }

    private Bitmap getBitmap(String fileName){
        String path = Constant.ALBUM_PATH+fileName;
        File file = new File(path);
        if(file.exists()){
            return BitmapFactory.decodeFile(path);
        }else{
            return null;
        }

    }

    private void saveBitmapToSD(Bitmap bitmap,String fileName){

        String ALBUM_PATH = Constant.ALBUM_PATH;
        File dirFile = new File(Constant.ALBUM_PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }

        File myCaptureFile = new File(ALBUM_PATH + fileName);
        BufferedOutputStream bos =null;
        try {
            bos=new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        try {
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
