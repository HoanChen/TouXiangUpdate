package com.tiqiuzhe.touxiangupdate.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.view.BadgeView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FragmentPage1 extends Fragment {

    @Bind(R.id.position_target)
    Button btnPosition;
    @Bind(R.id.colour_target)
    Button btnColour;
    @Bind(R.id.anim1_target)
    Button btnAnim1;
    @Bind(R.id.anim2_target)
    Button btnAnim2;
    @Bind(R.id.custom_target)
    Button btnCustom;
    @Bind(R.id.click_target)
    Button btnClick;
    @Bind(R.id.increment_target)
    Button btnIncrement;

    private BadgeView badge1;
    private BadgeView badge2;
    private BadgeView badge3;
    private BadgeView badge4;
    private BadgeView badge5;
    private BadgeView badge6;
    private BadgeView badge7;
    private BadgeView badge8;

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, null);
        ButterKnife.bind(this, view);
        initBadgeView();

        return view;
    }

    private void initBadgeView() {

            // *** set position ***

            badge1 = new BadgeView(context, btnPosition);
            badge1.setText("12");
            badge1.setBadgePosition(BadgeView.POSITION_BOTTOM_LEFT);
            btnPosition.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    badge1.toggle();
                }
            });

            // *** badge/text size & colour ***

            badge2 = new BadgeView(context, btnColour);
            badge2.setText("New!");
            badge2.setTextColor(Color.BLUE);
            badge2.setBadgeBackgroundColor(Color.YELLOW);
            badge2.setTextSize(12);
            btnColour.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    badge2.toggle();
                }
            });

            // *** default animation ***

            badge3 = new BadgeView(context, btnAnim1);
            badge3.setText("84");
            btnAnim1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    badge3.toggle(true);
                }
            });

            // *** custom animation ***

            badge4 = new BadgeView(context, btnAnim2);
            badge4.setText("123");
            badge4.setBadgePosition(BadgeView.POSITION_TOP_LEFT);
            badge4.setBadgeMargin(15);
            badge4.setBadgeBackgroundColor(Color.parseColor("#A4C639"));
            btnAnim2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
                    anim.setInterpolator(new BounceInterpolator());
                    anim.setDuration(1000);
                    TranslateAnimation anim2 = new TranslateAnimation(0, -100, 0, 0);
                    anim2.setDuration(500);
                    badge4.toggle(anim, anim2);
                }
            });

            // *** custom background ***

            badge5 = new BadgeView(context, btnCustom);
            badge5.setText("37");
            badge5.setBackgroundResource(R.mipmap.badge_ifaux);
            badge5.setTextSize(16);
            btnCustom.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    badge5.toggle(true);
                }
            });

            // *** clickable badge ***

            badge6 = new BadgeView(context, btnClick);
            badge6.setText("click me");
            badge6.setBadgeBackgroundColor(Color.BLUE);
            badge6.setTextSize(16);
            badge6.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "clicked badge",
                                   Toast.LENGTH_SHORT).show();
                }
            });
            btnClick.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    badge6.toggle();
                }
            });

            // *** increment ***

            badge8 = new BadgeView(context, btnIncrement);
            badge8.setText("0");
            btnIncrement.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (badge8.isShown()) {
                        badge8.increment(1);
                    } else {
                        badge8.show();
                    }
                }
            });
        }

}
