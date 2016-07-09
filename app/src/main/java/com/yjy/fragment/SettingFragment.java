package com.yjy.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.yjy.R;
import com.yjy.activity.CardActivity;
import com.yjy.activity.HomeActivity;
import com.yjy.im.base.Constant;
import com.yjy.im.utils.DialogUtil;
import com.yjy.im.utils.SharedPreUtil;
import com.yjy.im.utils.ShowMsgUtil;

public class SettingFragment extends Fragment {


	private View.OnClickListener relativeListener;
	private RelativeLayout cardRelativeLayout;
	private RelativeLayout optionRelativeLayout;
	private RelativeLayout systemInfoRelativeLayout;
	private RelativeLayout blacklistRelativeLayout;
	private RelativeLayout updateRelativeLayout;
	private RelativeLayout marketRelativeLayout;
	private RelativeLayout aboutRelativeLayout;
	private RelativeLayout exitRelativeLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
	Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting, container,
				false);
		Init(view);
		return view;
	}

	private void Init(View view) {
		this.cardRelativeLayout = (RelativeLayout) view.findViewById(R.id.ui_setting_card);
		this.optionRelativeLayout = (RelativeLayout) view.findViewById(R.id
				.ui_setting_msg_options);
		this.systemInfoRelativeLayout = (RelativeLayout) view.findViewById(R.id
				.ui_setting_system_info);
		this.blacklistRelativeLayout = (RelativeLayout) view.findViewById(R.id
				.ui_setting_blacklist);
		this.updateRelativeLayout = (RelativeLayout) view.findViewById(R.id.ui_setting_update);
		this.marketRelativeLayout = (RelativeLayout) view.findViewById(R.id.ui_setting_market);
		this.aboutRelativeLayout = (RelativeLayout) view.findViewById(R.id.ui_setting_about);
		this.exitRelativeLayout = (RelativeLayout) view.findViewById(R.id.ui_setting_exit);

		this.relativeListener = new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				switch (view.getId()) {
					case R.id.ui_setting_card:
						Intent intent = new Intent(getActivity(),
								CardActivity.class);
						getActivity().startActivity(intent);
						break;
					case R.id.ui_setting_msg_options:

						break;
					case R.id.ui_setting_system_info:

						break;
					case R.id.ui_setting_blacklist:

						break;
					case R.id.ui_setting_update:

						break;
					case R.id.ui_setting_market:

						break;
					case R.id.ui_setting_about:

						break;
					case R.id.ui_setting_exit:
						doExit();
						break;
				}
			}
		};

		this.cardRelativeLayout.setOnClickListener(relativeListener);
		this.optionRelativeLayout.setOnClickListener(relativeListener);
		this.systemInfoRelativeLayout.setOnClickListener(relativeListener);
		this.blacklistRelativeLayout.setOnClickListener(relativeListener);
		this.updateRelativeLayout.setOnClickListener(relativeListener);
		this.marketRelativeLayout.setOnClickListener(relativeListener);
		this.aboutRelativeLayout.setOnClickListener(relativeListener);
		this.exitRelativeLayout.setOnClickListener(relativeListener);
	}


	// 退出粤教云
	private void doExit() {

		final AlertDialog exitDlg= DialogUtil.getYNAlertDialog(getActivity(),"退出粤教云","确定退出吗！");
		Window window = exitDlg.getWindow();
		Button sure_btn = (Button) window.findViewById(R.id.alert_yn_sure_btn);
		Button cancel_btn = (Button) window.findViewById(R.id.alert_yn_cancel_btn);

		View.OnClickListener positive= new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreUtil.clearSharedPre(getActivity());
				getActivity().finish();
				exitDlg.dismiss();
			}
		};

		View.OnClickListener negative = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShowMsgUtil.show(getActivity(), "neg");
				exitDlg.dismiss();
			}
		};

		sure_btn.setOnClickListener(positive);
		cancel_btn.setOnClickListener(negative);


	}

}
