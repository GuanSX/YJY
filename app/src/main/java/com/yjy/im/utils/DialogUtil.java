package com.yjy.im.utils;

import com.example.yjy.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

/*
*Dialog，对话框，一个对话框就是一个小窗口，并不会填满整个屏幕，通常是以模态显示，要求用户必须采取行动才能继续进行剩下的操作。
*　	Android提供了丰富的对话框支持，它提供了如下4中常用的对话框：
*	AlertDialog：警告对话框，使用最广泛功能最丰富的一个对话框。
*	ProgressDialog：进度条对话框，只是对进度条进行了简单的封装。
*	DatePickerDialog：日期对话框。
*	TimePickerDialog：时间对话框。
*
*/

public class DialogUtil {


	public static String checkedlevel="xiaoxue";
	private static String checkedlevelId="1";

	public static String getCheckedLevelId()
	{
		return checkedlevelId;
	}
	public static String getCheckedlevel()
	{
		return checkedlevel;
	}

	private static ProgressDialog dialog = null;

	// 圆形进度条(正在加载)
	public static ProgressDialog getCircularProgressDialog(Context context, String message) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.view_load, null);

		TextView dialogText = (TextView) v.findViewById(R.id.dialogText);
		dialogText.setText(message);
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);

		dialog = new ProgressDialog(context, R.style.Theme_CustomDialog);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		dialog.setContentView(v);

		return dialog;
	}

	public static AlertDialog getYNAlertDialog(Context context, String title, String content) {
		AlertDialog dlg = new AlertDialog.Builder(context).create();
		dlg.show();
		Window window = dlg.getWindow();
		window.setContentView(R.layout.alert_yesorno);
		TextView titleTextView = (TextView) window.findViewById(R.id.alert_yn_title);
		TextView contentTextView = (TextView) window.findViewById(R.id.alert_yn_content);
		titleTextView.setText(title);
		contentTextView.setText(content);
		return dlg;
	}

	public static AlertDialog getLevelDialog(Context context, String title) {
		AlertDialog exitDlg = new AlertDialog.Builder(context).create();
		exitDlg.show();
		Window window = exitDlg.getWindow();
		window.setContentView(R.layout.alert_level_radio);


		final TextView titleTextView = (TextView) window.findViewById(R.id.alert_level_title);

		final RadioGroup levelRadioGroup = (RadioGroup) window.findViewById(R.id.level_radiogroup);

//		final RadioButton xiaoxueRadioButton = (RadioButton) window.findViewById(R.id
//				.radioxiaoxue);
//		final RadioButton chuzhongRadioButton = (RadioButton) window
//				.findViewById(R.id.radiochuzhong);
//		final RadioButton gaozhongRadioButton = (RadioButton) window
//				.findViewById(R.id.radiogaozhong);
//		final RadioButton daxueRadioButton = (RadioButton) window.findViewById(R.id.radiodaxue);
//		final RadioButton chengjiaoRadioButton = (RadioButton) window
//				.findViewById(R.id.radiochengjiao);
//		final RadioButton zhijiaoRadioButton = (RadioButton) window.findViewById(R.id
//				.radiozhijiao);


		checkedlevel = "xiaoxue";
		checkedlevelId="1";

		RadioGroup.OnCheckedChangeListener radiolistener = new RadioGroup.OnCheckedChangeListener
				() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.radioxiaoxue:
						checkedlevel = "xiaoxue";
						checkedlevelId="1";
						break;
					case R.id.radiochuzhong:
						checkedlevel = "chuzhong";
						checkedlevelId="2";
						break;
					case R.id.radiogaozhong:
						checkedlevel = "gaozhong";
						checkedlevelId="3";
						break;
					case R.id.radiodaxue:
						checkedlevel = "daxue";
						checkedlevelId="4";
						break;
					case R.id.radiochengjiao:
						checkedlevel = "chengjiao";
						checkedlevelId="5";
						break;
					case R.id.radiozhijiao:
						checkedlevel = "zhijiao";
						checkedlevelId="6";
						break;
					default:
						break;


				}
			}
		};

		titleTextView.setText(title);
		levelRadioGroup.setOnCheckedChangeListener(radiolistener);


		return exitDlg;

	}

	public static AlertDialog getInputDialog(Context context, String title, String content, String
			hint) {
		InputFilter[] filters = {new InputFilter.LengthFilter(30)};

		/*
		*在调用show方法前先调用setView(layout),show后再调用window.setContentView(layout),
		* 两个Layout布局应该是相同的。至于原因，暂时不明，但是确实解决了问题，在EditText上点击，可以调出软键盘，输入法了。
		 */
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.alert_input, null);

		AlertDialog dlg = new AlertDialog.Builder(context).create();
		dlg.setView(v);
		dlg.show();
		Window window = dlg.getWindow();
		window.setContentView(R.layout.alert_input);//注意这里用的是R里面的id
		//window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

		TextView titleTextView = (TextView) window.findViewById(R.id.alert_input_title);
		TextView contentTextView = (TextView) window.findViewById(R.id.alert_input_content);
		titleTextView.setText(title);
		contentTextView.setHint(hint);
		contentTextView.setFilters(filters);
		contentTextView.setText(content);
		return dlg;
	}


}
