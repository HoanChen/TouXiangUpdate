package com.tiqiuzhe.touxiangupdate.view.popupwindow;

import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.view.RecyclerWheelView.RecycleWheelView;
import com.tiqiuzhe.touxiangupdate.view.RecyclerWheelView.TextWheelAdapter;

import java.util.List;

/**
 * Created by baiduo on 16/3/16.
 * 滑动选择器popupwindow
 */
public class SelectTextPopupWindow extends BasePopupWindow implements View.OnClickListener{
    private Activity instance;
    private List<String> dateList;
    private TextWheelAdapter twAdapter;
    private TextView editText;
    private String selectString = "";
    private RecycleWheelView rwv_select;

    public TextView getEditText() {

        return editText;
    }

    public void setEditText(TextView editText) {
        this.editText = editText;
    }

    public List getDateList() {
        return dateList;
    }

    /**
     * 设置数据和默认滚动条目
     * @param dateList
     * @param defaultPosition
     */
    public void setDateList(List dateList,int defaultPosition) {
        this.dateList = dateList;
        twAdapter.setData(dateList);
        rwv_select.smoothScrollToPosition(defaultPosition);
    }

    public SelectTextPopupWindow(Activity instance) {
        super(instance);
        this.instance = instance;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.pop_select_layout;
    }

    @Override
    protected void initView() {
        rwv_select = (RecycleWheelView) mMenuView.findViewById(R.id.rwv_select);
        mMenuView.findViewById(R.id.tv_cancel).setOnClickListener(this);
        mMenuView.findViewById(R.id.tv_determine).setOnClickListener(this);
        initWheelView(rwv_select);
    }

    public void initWheelView(RecycleWheelView rwv_select) {
        twAdapter = new TextWheelAdapter(rwv_select.getContext());
        twAdapter.setTextPadding(3);
        twAdapter.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        twAdapter.setTextColor(rwv_select.getResources().getColor(R.color.bg_popup));
        rwv_select.setOnSelectListener(new RecycleWheelView.OnSelectItemListener() {
            @Override
            public void onSelectChanged(int position) {
//                ShowToastUtil.ShowMsg(instance,position+"");
                selectString = dateList.get(position);
            }
        });

        rwv_select.setAdapter(twAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_determine:
                editText.setText(selectString);
                dismiss();
                break;
        }
    }
}