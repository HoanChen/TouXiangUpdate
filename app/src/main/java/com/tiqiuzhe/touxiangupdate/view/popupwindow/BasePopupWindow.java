package com.tiqiuzhe.touxiangupdate.view.popupwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.tiqiuzhe.touxiangupdate.R;

public abstract class BasePopupWindow extends PopupWindow {

    protected Activity mInstance;

    protected View mMenuView = null;

    public BasePopupWindow(Activity instance) {
        super(instance);
        init(instance);
    }

    private void init(Activity instance) {
        mInstance = instance;

        LayoutInflater inflater = (LayoutInflater) instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mMenuView = inflater.inflate(getLayoutResourceId(), null);
        this.setContentView(mMenuView);

        initView();

        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupAnimation);

        ColorDrawable colorDrawable = new ColorDrawable(Color.argb(0, 0, 0, 0));
        this.setBackgroundDrawable(colorDrawable);

        mMenuView.setOnTouchListener(new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.getTop();
                int y = (int) event.getY();

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height) {
                        dismiss();
                    }
                }

                return true;
            }
        });
    }

    protected abstract int getLayoutResourceId();

    protected abstract void initView();

    public void show() {

        showAtLocation(mMenuView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        setBackground(0.7f);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        setBackground(1f);
    }

    //设置背景透明度
    public void setBackground(float alpha) {
        WindowManager.LayoutParams lp = mInstance.getWindow().getAttributes();
        lp.alpha = alpha;
        mInstance.getWindow().setAttributes(lp);
    }

}