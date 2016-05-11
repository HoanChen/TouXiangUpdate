package com.tiqiuzhe.touxiangupdate.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tiqiuzhe.touxiangupdate.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 16/5/11.
 */
public class PictureActivity extends AppCompatActivity {

    @Bind(R.id.id_simple_drawee_view)
    SimpleDraweeView mSimpleDraweeView;
    @Bind(R.id.id_simple_drawee_view1)
     SimpleDraweeView mSimpleDraweeView1;
    @Bind(R.id.id_simple_drawee_view2)
     SimpleDraweeView mSimpleDraweeView2;
    @Bind(R.id.id_simple_drawee_view3)
     SimpleDraweeView mSimpleDraweeView3;
    @Bind(R.id.id_simple_drawee_view4)
     SimpleDraweeView mSimpleDraweeView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        ButterKnife.bind(this);

        initData();

    }

    private void initData() {

//        Uri uri = Uri.parse();
//        mAvatorImg.setImageURI(uri);
        String url="http://t12.baidu.com/it/u=4095575894,102452705" +
                "&fm=32&s=A98AA55F526172A6F6A058E50300A060&w=623&h=799&img.JPEG";

//        http://img.huofar.com/data/jiankangrenwu/shizi.gif
//        String urlgif="http://img0.bdstatic.com/img/image/shitu/feimg/uploading.gif";
        Uri urlgif = Uri.parse("http://img.huofar.com/data/jiankangrenwu/shizi.gif");
        //显示一张HTTP图片
        mSimpleDraweeView.setImageURI(Uri.parse(url));

        //显示一张HTTP图片保持一定宽高比例，如果4:3(1.33f)，注意xml里的写法
        mSimpleDraweeView1.setImageURI(Uri.parse(url));
        mSimpleDraweeView1.setAspectRatio(1.33f);

        //显示一张HTTP的GIF图片
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
//                                                  .setUri(Uri.parse(urlgif))
                                                  .setUri(urlgif)
                                                  .setAutoPlayAnimations(true)
                                                  .build();
        mSimpleDraweeView2.setController(draweeController);

        //显示一张HTTP的图片，以圆形图片显示
        mSimpleDraweeView3.setImageURI(Uri.parse(url));

        //显示一张HTTP的图片，以圆形带边框图片显示
        mSimpleDraweeView4.setImageURI(Uri.parse(url));

    }
}
