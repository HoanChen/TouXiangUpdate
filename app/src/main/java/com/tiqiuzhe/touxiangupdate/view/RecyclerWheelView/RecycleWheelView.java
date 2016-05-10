package com.tiqiuzhe.touxiangupdate.view.RecyclerWheelView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tiqiuzhe.touxiangupdate.R;

/**
 * 可回收的滑轮view
 * Created by voiddog on 2015/10/21.
 */
public class RecycleWheelView extends RecyclerView {
    //子view可以被缩放到最小的view的大小
    static final float MIN_SCALE_VALUE = 0.1f;
    static final float BASE_ALPHA_VALUE = 0.5f;

    //设置一开始的padding
    int mOldPaddingLeft, mOldPaddingTop, mOldPaddingRight, mOldPaddingBottom;
    //分割线粗细
    int mLineThickness = 0;
    //分割线颜色
    int mLineColor = 0;
    //中间提示颜色线
    ColorDrawable mLineDrawable;
    //中间提示自定线
    Drawable mCustomLineDrawable;
    //drawable padding
    int mDrawablePadding = 0;
    //是否需要重排
    boolean mNeedAdjust = false;
    //当前激活的view
    View mCurView;
    //方向 0 垂直 1 水平
    int mDirection = 0;
    //滚动监听器
    OnSelectItemListener mSelectListener;
    //上一次选中的位置
    int mLastSelectPosition = -1;

    public RecycleWheelView(Context context) {
        super(context);
        initAttr(null, 0);
    }

    public RecycleWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs, 0);
    }

    public RecycleWheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttr(attrs, defStyle);
    }

    public void setOnSelectListener(OnSelectItemListener listener) {
        this.mSelectListener = listener;
    }

    public void setLineColor(int color) {
        if (mLineDrawable != null) {
            mLineDrawable.setColor(color);
        } else {
            mLineDrawable = new ColorDrawable(color);
        }
        requestLayout();
    }

    public void setLineThickness(int thickness) {
        mLineThickness = thickness;
        requestLayout();
    }

    public void setLineDrawable(Drawable drawable) {
        mCustomLineDrawable = drawable;
        requestLayout();
    }

    void initAttr(AttributeSet attrs, int defStyle) {
        mOldPaddingLeft = getPaddingLeft();
        mOldPaddingBottom = getPaddingBottom();
        mOldPaddingRight = getPaddingRight();
        mOldPaddingTop = getPaddingTop();
        setClipToPadding(false);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.RecycleWheelView, defStyle, 0);

        mLineThickness = a.getDimensionPixelSize(R.styleable.RecycleWheelView_recycleWheelLineThickness, 0);
        mLineColor = a.getColor(R.styleable.RecycleWheelView_recycleWheelLineColor, 0);
        mCustomLineDrawable = a.getDrawable(R.styleable.RecycleWheelView_recycleWheelLineDrawable);
        mDrawablePadding = a.getDimensionPixelSize(R.styleable.RecycleWheelView_recycleWheelLinePadding, 0);

        if (mLineColor != 0 && mLineThickness != 0 && mCustomLineDrawable == null) {
            mLineDrawable = new ColorDrawable(mLineColor);
        }

        mDirection = a.getInt(R.styleable.RecycleWheelView_recycleWheelDirection, 0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        if (mDirection == 0) {
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        } else {
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        }
        setLayoutManager(layoutManager);

        a.recycle();
    }

    public int getSelectPosition() {
        int curPosition;
        if (getLayoutManager().canScrollHorizontally()) {
            curPosition = ViewUtils.getCenterXChildPosition(this);
        } else {
            curPosition = ViewUtils.getCenterYChildPosition(this);
        }
        return curPosition;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mNeedAdjust = true;
                break;
            }
        }
        return super.onTouchEvent(e);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        mSelectListener.onSelectChanged(getSelectPosition());
        switch (state) {
            case SCROLL_STATE_IDLE: {
                if (getLayoutManager() != null
                        && getLayoutManager().canScrollHorizontally()) {
                    adjustPositionX();
                } else {
                    adjustPositionY();
                }
                break;
            }
        }
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (getChildCount() > 0) {
            View view = getChildAt(0);
            int paddingH = (getWidth() - view.getWidth()) >> 1;
            int paddingV = (getHeight() - view.getHeight()) >> 1;
            if (getLayoutManager().canScrollHorizontally()) {
                if (getPaddingLeft() != paddingH || getPaddingRight() != paddingH) {
                    setPadding(paddingH, mOldPaddingTop, paddingH, mOldPaddingBottom);
//                    scrollToPosition(0);
                    //                    mUpdateHandler.sendEmptyMessageDelayed(0, 60);
                }
            } else {
                if (getPaddingTop() != paddingV || getPaddingBottom() != paddingV) {
                    setPadding(mOldPaddingLeft, paddingV, mOldPaddingRight, paddingV);
//                    scrollToPosition(0);
                    //                    mUpdateHandler.sendEmptyMessageDelayed(0, 60);
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        //绘制中间分割线
        if ((mLineDrawable != null || mCustomLineDrawable != null)) {

            Drawable drawable = mCustomLineDrawable == null ? mLineDrawable
                    : mCustomLineDrawable;
            if (getLayoutManager() != null && getLayoutManager().canScrollHorizontally()) {
                int width = 0;
                if (getChildCount() > 0) {
                    width = getChildAt(0).getWidth();
                }
                int startLeft = ((getWidth() - width) >> 1) - mLineThickness;
                drawable.setBounds(startLeft,
                                   mDrawablePadding,
                                   startLeft + mLineThickness,
                                   getHeight() - mDrawablePadding);
                drawable.draw(c);
                startLeft = ((getWidth() - width) >> 1) + width;
                drawable.setBounds(startLeft,
                                   mDrawablePadding,
                                   startLeft + mLineThickness,
                                   getHeight() - mDrawablePadding);
                drawable.draw(c);
            } else {
                int height = 0;
                if (getChildCount() > 0) {
                    height = getChildAt(0).getHeight();
                }
                int startTop = ((getHeight() - height) >> 1) - mLineThickness;
                drawable.setBounds(mDrawablePadding,
                                   startTop,
                                   getWidth() - mDrawablePadding,
                                   startTop + mLineThickness);
                drawable.draw(c);
                startTop = ((getHeight() - height) >> 1) + height;
                drawable.setBounds(mDrawablePadding,
                                   startTop,
                                   getWidth() - mDrawablePadding,
                                   startTop + mLineThickness);
                drawable.draw(c);
            }
        }

        updateChildUI();
    }

    /***
     * adjust position before Touch event complete and fling action start.
     */
    protected void adjustPositionY() {
        if (!mNeedAdjust) {
            return;
        }
        mNeedAdjust = true;

        int curPosition = getSelectPosition();
        if (curPosition != mLastSelectPosition) {
            mLastSelectPosition = curPosition;
            if (mSelectListener != null) {
                mSelectListener.onSelectChanged(mLastSelectPosition);
            }
        }
        smoothScrollToPosition(curPosition);
    }

    /***
     * adjust position before Touch event complete and fling action start.
     */
    protected void adjustPositionX() {
        if (!mNeedAdjust) {
            return;
        }
        mNeedAdjust = true;

        int curPosition = getSelectPosition();
        if (curPosition != mLastSelectPosition) {
            mLastSelectPosition = curPosition;
            if (mSelectListener != null) {
                mSelectListener.onSelectChanged(mLastSelectPosition);
            }
        }
        smoothScrollToPosition(curPosition);
    }

    /**
     * 更新子view的ui
     */
    protected void updateChildUI() {
        if (getLayoutManager().canScrollHorizontally()) {
            mCurView = ViewUtils.getCenterXChild(this);
        } else {
            mCurView = ViewUtils.getCenterYChild(this);
        }

        for (int i = getChildCount() - 1; i >= 0; --i) {
            View view = getChildAt(i);
            float value;
            if (getLayoutManager().canScrollHorizontally()) {
                float midX = view.getLeft() + view.getWidth() / 2.0f;
                float size = getWidth() / 2.0f;
                value = (size - midX) / size;
                if (ViewUtils.isChildInCenterX(this, view)) {
                    mCurView = view;
                }
            } else {
                float midY = view.getTop() + view.getHeight() / 2.0f;
                float size = getHeight() / 2.0f;
                value = (size - midY) / size;
                if (ViewUtils.isChildInCenterY(this, view)) {
                    mCurView = view;
                }
            }
            value = (float) Math.sqrt(1.0f - value * value);
            value = Math.min(1.0f, Math.abs(value));
            float alphaValue = 1.0f;
            if (view != mCurView) {
                alphaValue = BASE_ALPHA_VALUE * value;
                value -= MIN_SCALE_VALUE;
                if (value < 0.0f) {
                    value = 0.0f;
                }
            }
            view.setScaleX(value);
            view.setScaleY(value);
            view.setAlpha(alphaValue);
        }
    }

    public interface OnSelectItemListener {
        void onSelectChanged(int position);
    }
}
