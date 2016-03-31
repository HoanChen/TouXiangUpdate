package com.tiqiuzhe.touxiangupdate.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.dialog.ActionSheetDialog;
import com.tiqiuzhe.touxiangupdate.dialog.ActionSheetDialog.OnSheetItemClickListener;
import com.tiqiuzhe.touxiangupdate.dialog.ActionSheetDialog.SheetItemColor;
import com.tiqiuzhe.touxiangupdate.view.XCRoundImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class TouXiangActivity extends AppCompatActivity {

    /** 标记是拍照还是相册0 是拍照1是相册 */
    private int cameraorpic;

    /** 指定拍摄图片文件位置避免获取到缩略图 */
    File outFile;

    /** 编辑头像相册选取 */
    private static final int REQUESTCODE_PICK = 1;
    /** 设置头像 */
    private static final int REQUESTCODE_CUTTING = 2;
    /** 编辑头像拍照选取 */
    private static final int PHOTO_REQUEST_TAKEPHOTO = 3;

    /** 头像 */
    private XCRoundImageView avatar;
    private XCRoundImageView avatar2;
    private XCRoundImageView avatar3;

    Button btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_setting);

        btnChange= (Button) findViewById(R.id.bt_touxiang);


        avatar = (XCRoundImageView) findViewById(R.id.headimg);
        avatar2 = (XCRoundImageView) findViewById(R.id.headimg2);
        avatar3 = (XCRoundImageView) findViewById(R.id.headimg3);

        avatar2.setType(XCRoundImageView.TYPE_ROUND);
        avatar2.setRoundBorderRadius(20);//设置圆角角度

        avatar3.setType(XCRoundImageView.TYPE_OVAL);
        avatar3.setRoundBorderRadius(50);

        initListener();

    }

    private void initListener() {

        btnChange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popCheck();
            }
        });

        avatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popCheck();
            }
        });

        avatar2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popCheck();
            }
        });

        avatar3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popCheck();
            }
        });
    }

    private void popCheck() {
        {

            new ActionSheetDialog(TouXiangActivity.this).builder()
              .setCancelable(true).setCanceledOnTouchOutside(true)
                   .addSheetItem("拍照", SheetItemColor.Blue, new OnSheetItemClickListener() {

                       @Override
                       public void onClick(int which) {

                           cameraorpic = 0;
                           openCamera();

                       }
                   }).addSheetItem("打开相册", SheetItemColor.Blue, new OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                    cameraorpic = 1;
                    skipPic();
                }
            }).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 判断请求码是编辑就跳到编辑
        switch (requestCode) {
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            case PHOTO_REQUEST_TAKEPHOTO:
                //			if (data == null || data.getData() == null) {
                //				return;
                //			}
                startPhotoZoom(Uri.fromFile(outFile));
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * save the picture data 设置头像并保存头像
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(getResources(), photo);

            avatar.setImageDrawable(drawable);
//            avatar2.setImageDrawable(drawable);
//            avatar3.setImageDrawable(drawable);

            /** 可用于图像上传 */
            UpdateAvater("url",Bitmap2Bytes(photo));
        }
    }

    public void UpdateAvater(String url,byte[] bs){
//        RequestParams params=new RequestParams();
//        params.put("此处是上传的参数名字", bs);
//        HttpUtil.post(url, params, new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//                //成功做什么
//            }
//
//            @Override
//            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//                // TODO Auto-generated method stub
//                //失败做什么
//            }
//        });
    }

    /** 将BItmap转换成字节数组 */
    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /** 设置可编辑头像 */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }



    /** 打开相册 */
    private void skipPic() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                  "image/*");
        startActivityForResult(pickIntent, REQUESTCODE_PICK);
    }

    /** 打开相机 */
    private void openCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
        } else {
            Log.e("CAMERA", "请确认已经插入SD卡");
        }
    }

}
