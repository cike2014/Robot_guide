package com.mmednet.main.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.mmednet.main.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片选择工具类
 */
public class PicturesUtils {

    private Activity activity;
    private static final int CAMERA_REQUEST_CODE=1;
    private static final int GALLERY_REQUEST_CODE=2;
    private static final int CROP_REQUEST_CODE=3;
    private static PicturesUtils sInstance;
    private TextView tv_camera;
    private TextView tv_gallery;
    private TextView tv_cancel;
    private LinearLayout ll;

    private static final String TAG=PicturesUtils.class.getSimpleName();

    private PicturesUtils() {
    }

    public static PicturesUtils newInstance() {
        if (sInstance == null) {
            synchronized (PicturesUtils.class) {
                if (sInstance == null) {
                    sInstance=new PicturesUtils();
                }
            }
        }
        return sInstance;
    }

    @SuppressLint("InflateParams")
    public void showUploadDialog(final Activity activity) {
        this.activity=activity;
        final Dialog customDialog=new Dialog(activity, R.style.flashmode_DialogTheme);

        setDialogLayout();

        tv_camera.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activity.startActivityForResult(intent, CAMERA_REQUEST_CODE);
                customDialog.dismiss();
            }

        });
        tv_gallery.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                activity.startActivityForResult(intent, GALLERY_REQUEST_CODE);
                customDialog.dismiss();
            }
        });

        tv_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }

        });

        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(ll);

        Window window=customDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams lp=window.getAttributes();
        lp.width=android.view.ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height=android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        customDialog.setCanceledOnTouchOutside(true);
        customDialog.setCancelable(true);

        if (activity instanceof Activity) {
            Activity mActivity=activity;
            if (!mActivity.isFinishing()) {
                customDialog.show();
            }
        }

    }


    public Bitmap handlePictureData(int resultCode, int requestCode, Intent data) {
        if (resultCode == activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {// 相机返回结果
            if (data == null) {
                return null;
            } else {
                Bundle extras=data.getExtras();
                if (extras != null) {
                    Bitmap bm=extras.getParcelable("data");
                    Uri uri=saveBitmap(bm);
                    startImageZoom(uri);
                }
            }
        } else if (resultCode == activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {// 图库返回结果
            if (data == null) {
                return null;
            }
            Uri uri;
            uri=data.getData();
            Uri fileUri=convertUri(uri);
            startImageZoom(fileUri);
        } else {// 裁剪返回结果
            Log.d(TAG, "CROP:");
            if (resultCode == activity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
                final Uri resultUri=UCrop.getOutput(data);
                return compressPic(resultUri);
            } else if (resultCode == UCrop.RESULT_ERROR) {
                final Throwable cropError=UCrop.getError(data);
                return null;
            }
        }
        return null;
    }


    public Uri saveBitmap(Bitmap bm) {
        File img=new File(activity.getCacheDir(), "SampleCropImage.jpeg");
        try {
            FileOutputStream fos=new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @SuppressWarnings("deprecation")
    private Bitmap compressPic(Uri uri) {
        try {
            InputStream input=activity.getContentResolver().openInputStream(uri);
            BitmapFactory.Options onlyBoundsOptions=new BitmapFactory.Options();
            onlyBoundsOptions.inJustDecodeBounds=true;
            onlyBoundsOptions.inDither=true;// optional
            onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_4444;// optional
            BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
            input.close();
            if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
                return null;

            // 获取图片分别率
            int picwidth=onlyBoundsOptions.outWidth;
            int picheight=onlyBoundsOptions.outHeight;
            // 获取手机分辨率
            WindowManager wm=(WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            Display disp=wm.getDefaultDisplay();
            // disp.getSize(outSize);高版本方法
            int swidth=disp.getWidth();
            int sheight=disp.getHeight();
            // 计算缩放比
            int wr=picwidth / swidth;
            int hr=picheight / sheight;

            int r=1;
            if (wr >= 1 || hr >= 1) {
                r=wr >= hr ? wr : hr;
            }

            BitmapFactory.Options bitmapOptions=new BitmapFactory.Options();

            bitmapOptions.inSampleSize=r;// 图片长宽方向缩小倍数

            bitmapOptions.inDither=true;// 不进行图片抖动处理
            bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_4444;// 设置解码方式
            input=activity.getContentResolver().openInputStream(uri);
            Bitmap bitmap=BitmapFactory.decodeStream(input, null, bitmapOptions);

            input.close();
            return bitmap;
        } catch (FileNotFoundException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return null;
    }

    /**
     * 图片由图库保存到SD卡中
     */
    private Uri convertUri(Uri uri) {
        Bitmap bitmap=compressPic(uri);
        return saveBitmap(bitmap);
    }

    /**
     * 调用系统相机的本地裁剪功能
     */
    private void startImageZoom(Uri originUri) {

        Uri mDestinationUri=Uri.fromFile(new File(activity.getCacheDir(), "SampleCropImage.jpeg"));
        UCrop.of(originUri, mDestinationUri)
                .withAspectRatio(1, 1)
                .start(activity);

    }

    /**
     * 设置Dialog的布局
     */
    private void setDialogLayout() {
        ll=new LinearLayout(activity);
        LayoutParams llp=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        ll.setBackgroundColor(Color.parseColor("#FFFFFF"));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(llp);

        // 设置文本布局
        LayoutParams tvp=new LayoutParams(LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(activity, 54));

        tv_camera=new TextView(activity);
        tv_camera.setText("拍照");
        tv_camera.setTextColor(Color.parseColor("#333333"));
        tv_camera.setTextSize(16);
        tv_camera.setClickable(false);
        tv_camera.setGravity(Gravity.CENTER);
        tv_camera.setLayoutParams(tvp);

        tv_gallery=new TextView(activity);
        tv_gallery.setText("从相册选择");
        tv_gallery.setTextColor(Color.parseColor("#333333"));
        tv_gallery.setTextSize(16);
        tv_gallery.setClickable(false);
        tv_gallery.setGravity(Gravity.CENTER);
        tv_gallery.setLayoutParams(tvp);

        tv_cancel=new TextView(activity);
        tv_cancel.setText("取消");
        tv_cancel.setTextColor(Color.parseColor("#00BC9C"));
        tv_cancel.setTextSize(16);
        tv_cancel.setClickable(false);
        tv_cancel.setGravity(Gravity.CENTER);
        tv_cancel.setLayoutParams(tvp);

        // 设置分隔线
        LayoutParams vp=new LayoutParams(LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(activity, 1));
        vp.setMargins(DisplayUtil.dip2px(activity, 20), 0, DisplayUtil.dip2px(activity, 20), 0);
        View view1=new View(activity);
        view1.setBackgroundColor(Color.parseColor("#EBEBEB"));
        view1.setLayoutParams(vp);

        View view2=new View(activity);
        view2.setBackgroundColor(Color.parseColor("#EBEBEB"));
        view2.setLayoutParams(vp);

        ll.addView(tv_camera, 0);
        ll.addView(view1, 1);
        ll.addView(tv_gallery, 2);
        ll.addView(view2, 3);
        ll.addView(tv_cancel, 4);
    }
}
