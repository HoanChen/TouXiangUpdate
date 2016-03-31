package com.tiqiuzhe.touxiangupdate.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.tiqiuzhe.touxiangupdate.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 16/3/10.
 */
public class GuideActivity extends Activity implements OnGestureListener {

    @Bind(R.id.viewFlipper)
    ViewFlipper viewFlipper;

    private static final String TAG = "GuideActivity";
    private GestureDetector detector; //

    Animation leftInAnimation;
    Animation leftOutAnimation;
    Animation rightInAnimation;
    Animation rightOutAnimation;

    private int[] imgs = { R.mipmap.new_feature_1,  R.mipmap.new_feature_2,
            R.mipmap.new_feature_3,  R.mipmap.new_feature_4,
            R.mipmap.new_feature_5, R.mipmap.new_feature_6 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

//        initView();

        initView1();
    }

    private void initView1() {
        detector = new GestureDetector(GuideActivity.this);
        //����Ч��
        leftInAnimation = AnimationUtils.loadAnimation(this, R.anim.left_in);
        leftOutAnimation = AnimationUtils.loadAnimation(this, R.anim.left_out);
        rightInAnimation = AnimationUtils.loadAnimation(this, R.anim.right_in);
        rightOutAnimation = AnimationUtils.loadAnimation(this, R.anim.right_out);

        for (int i = 0; i < imgs.length; i++) { 			// 添加图片源
            ImageView iv = new ImageView(this);
            iv.setImageResource(imgs[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
//            viewFlipper.addView(iv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            viewFlipper.addView(iv,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        }

        viewFlipper.setAutoStart(true);			// 设置自动播放功能（点击事件，前自动播放）
        viewFlipper.setFlipInterval(3000);
        if(viewFlipper.isAutoStart() && !viewFlipper.isFlipping()){
            viewFlipper.startFlipping();
        }

    }


    private void initView() {
        detector = new GestureDetector(GuideActivity.this);

        //��viewFlipper���View
        viewFlipper.addView(getImageView(R.mipmap.new_feature_1));
        viewFlipper.addView(getImageView(R.mipmap.new_feature_2));
        viewFlipper.addView(getImageView(R.mipmap.new_feature_3));
        viewFlipper.addView(getImageView(R.mipmap.new_feature_4));
        viewFlipper.addView(getImageView(R.mipmap.new_feature_5));
        viewFlipper.addView(getImageView(R.mipmap.new_feature_6));

        //����Ч��
        leftInAnimation = AnimationUtils.loadAnimation(this, R.anim.left_in);
        leftOutAnimation = AnimationUtils.loadAnimation(this, R.anim.left_out);
        rightInAnimation = AnimationUtils.loadAnimation(this, R.anim.right_in);
        rightOutAnimation = AnimationUtils.loadAnimation(this, R.anim.right_out);

    }

    private ImageView getImageView(int id) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(id);
        return imageView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        viewFlipper.stopFlipping();				// 点击事件后，停止自动播放
        viewFlipper.setAutoStart(false);

        return this.detector.onTouchEvent(event); //
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        Log.i(TAG, "e1=" + e1.getX() + " e2=" + e2.getX() + " e1-e2=" + (e1.getX() - e2.getX()));

        if (e1.getX() - e2.getX() > 120) {
            viewFlipper.setInAnimation(leftInAnimation);
            viewFlipper.setOutAnimation(leftOutAnimation);
            viewFlipper.showNext();//���һ���
            return true;
        } else if (e1.getX() - e2.getY() < -120) {
            viewFlipper.setInAnimation(rightInAnimation);
            viewFlipper.setOutAnimation(rightOutAnimation);
            viewFlipper.showPrevious();//���󻬶�
            return true;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

}
