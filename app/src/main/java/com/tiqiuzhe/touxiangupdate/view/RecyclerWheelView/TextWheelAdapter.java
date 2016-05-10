package com.tiqiuzhe.touxiangupdate.view.RecyclerWheelView;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiqiuzhe.touxiangupdate.view.RecyclerWheelView.TextWheelAdapter.MyViewHolder;

import java.util.List;

/**
 * 数据需要完成toString方法
 * Created by voiddog on 2015/10/22.
 */
public class TextWheelAdapter extends Adapter<MyViewHolder> {
    List mTextList;
    //字体颜色
    int mTextColor;
    //字体padding
    int mTextPadding;
    //字体大小
    float mTextSize;
    //上下文
    Context mContext;

    public TextWheelAdapter(Context context) {
        mContext = context;
    }

    public void setTextSize(int unit, float size) {
        mTextSize = TypedValue.applyDimension(
                unit, size, mContext.getResources().getDisplayMetrics());
    }

    public void setTextPadding(int padding) {
        mTextPadding = padding;
    }

    public void setTextColor(int color) {
        mTextColor = color;
    }

    public void setData(List dataList) {
        mTextList = dataList;
        notifyDataSetChanged();
    }

    public void addData(List dataList) {
        mTextList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView.setTextColor(Color.GRAY);
        textView.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        return new MyViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (mTextSize != 0 && holder.tv.getTextSize() != mTextSize) {
            holder.tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        }
        if (mTextColor != 0 && holder.tv.getCurrentTextColor() != mTextColor) {
            holder.tv.setTextColor(mTextColor);
        }
        if (holder.tv.getPaddingTop() != mTextPadding) {
            holder.tv.setPadding(0, mTextPadding, 0, mTextPadding);
        }
        holder.tv.setText(mTextList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mTextList == null ? 0 : mTextList.size();
    }

    class MyViewHolder extends ViewHolder {
        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view;
        }
    }
}
