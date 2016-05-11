package com.tiqiuzhe.touxiangupdate.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;
import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.bean.Dish;

import java.util.ArrayList;

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

    @Bind(R.id.listview)
    ListView mListView;
    private static final String BASE_URL = "http://img1.3lian.com/img2011/w1/106/85/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        ButterKnife.bind(this);

        initData();

        init();

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

    private void init() {
        ArrayList<Dish> dishList = new ArrayList<Dish>();

        dishList.add(new Dish(BASE_URL + "42.jpg", "水煮鱼片", "38.00"));
        dishList.add(new Dish(BASE_URL + "34.jpg", "小炒肉", "18.00"));
        dishList.add(new Dish(BASE_URL + "37.jpg", "清炒时蔬", "15.00"));
        dishList.add(new Dish(BASE_URL + "11.jpg", "金牌烤鸭", "36.00"));
        dishList.add(new Dish(BASE_URL + "12.jpg", "粉丝肉煲", "20.00"));

        MainListViewAdapter adapter = new MainListViewAdapter(dishList);
        mListView.setAdapter(adapter);

    }


    // ListView适配器
    private class MainListViewAdapter extends BaseAdapter {

        private ArrayList<Dish> dishList;

        public MainListViewAdapter(ArrayList<Dish> list) {
            this.dishList = list;
        }

        @Override
        public int getCount() {
            return dishList.size();
        }

        @Override
        public Object getItem(int position) {
            return dishList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewItemHolder item = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(PictureActivity.this).inflate(
                        R.layout.item_fancai, null);
                item = new ListViewItemHolder();
                item.img_iv = (ImageView) convertView
                        .findViewById(R.id.imageView1);
                item.name_textview = (TextView) convertView
                        .findViewById(R.id.textView1);
                item.price_textview = (TextView) convertView
                        .findViewById(R.id.textView2);

                convertView.setTag(item);
            } else {
                item = (ListViewItemHolder) convertView.getTag();
            }

            Dish dish = dishList.get(position);

            //这里就是异步加载网络图片的地方
            Picasso.with(PictureActivity.this).load(dish.getImgUrl())
                   .into(item.img_iv);

            item.name_textview.setText(dish.getName());
            item.price_textview.setText(dish.getPrice() + "元");

            return convertView;
        }

    }

    // ListView的Item组件类
    private static class ListViewItemHolder {
        ImageView img_iv;
        TextView name_textview;
        TextView price_textview;
    }
}
