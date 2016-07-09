package com.yjy.activity;

import com.example.yjy.*;
import com.yjy.im.base.*;
import com.yjy.im.comm.ALoginActivity;
import com.yjy.im.model.*;

import com.yjy.im.utils.*;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.*;

import java.io.File;

public class LoginActivity extends ALoginActivity {


	private EditText edt_username = null;
	private EditText edt_pwd = null;
	private Button btn_login = null;
	//设置标题的信息
	private Button btn_back = null;
	private Button btn_function = null;
	private TextView tv_word = null;

	private String TAG = "LoginActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Init();
	}

	private void Init() {
		edt_username = (EditText) findViewById(R.id.ui_username_input);
		edt_pwd = (EditText) findViewById(R.id.ui_password_input);
		btn_login = (Button) findViewById(R.id.ui_login_btn);
		//标题栏的三个按键
		btn_back = (Button) findViewById(R.id.ui_title_back_btn);
		btn_function = (Button) findViewById(R.id.ui_title_function_btn);
		tv_word = (TextView) findViewById(R.id.ui_title_word_tv);
		//this.initView();

		//===========设置标题栏=====================
		btn_back.setVisibility(View.VISIBLE);
		btn_function.setVisibility(View.INVISIBLE);
		tv_word.setText("登录");


		//===============按钮添加监听================
		//注册按钮添加监听
		btn_login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!checkUsername(edt_username.getText().toString())) {
					return;
				} else if (!checkPassword(edt_pwd.getText().toString())) {
					return;
				} else {
					try {
						//Log.e(TAG,"doLogin 1");
						doLogin(edt_username.getText().toString(), edt_pwd
								.getText().toString());
					} catch (Exception e) {
						Log.e(TAG, e.getMessage());
					}
				}
			}
		});

		//返回按钮添加监听
		btn_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, MainActivity.class));
				LoginActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	private void initView() {

		//自动填充
		User currentuser = (User) SharedPreUtil.readObject(mContext, Constant.USER_INFO);
		String username = currentuser.getUserName();
		String pass = currentuser.getPassword();
		//填充用户名和密码
		edt_username.setText(username);
		edt_pwd.setText(pass);

	}

	@Override
	protected void doLoginSuccess(User currentuser) {
		// TODO Auto-generated method stub

		Log.e(TAG, "doLoginSuccess");
		//ShowMsgUtil.show(this,"登录成功");

		//记录当前信息
		Current.setCurrentuser(LoginActivity.this, currentuser);

//		// 存储用户信息
//		SharedPreUtil.saveObject(mContext,currentuser,Constant.USER_INFO);


//		VCard vCard = new VCard();
//		try {
//			// vCard.load(ConnectionUtils.getConnection(),User.getMasterJid(username));
//			//
//			// masterInfo.edit()
//			// .putString(Constant.REALNAME, vCard.getField("REALNAME"))
//			// .commit();
//			// masterInfo.edit().putString(Constant.ROLE,
//			// vCard.getField("ROLE"))
//			// .commit();
//			// masterInfo.edit()
//			// .putString(Constant.LEVEL, vCard.getField("LEVEL"))
//			// .commit();
//			//
//			//
//			// if (vCard.getAvatar() != null) {
//			// masterInfo
//			// .edit()
//			// .putString(Constant.AVATER,
//			// SdCardUtil.getAvaterPath(username)).commit();
//			// File file = new File(SdCardUtil.getAvaterPath(username));
//			// if (!file.exists()) {
//			// AvaterManager.saveBytesToFile(vCard.getAvatar(),
//			// SdCardUtil.getAvaterPath(username));
//			// }
//			// }
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		// 这是什么意思？
		// if ("".equals(masterInfo.getString(Constant.ALERT_SHOCK, ""))
		// || "".equals(masterInfo.getString(Constant.ALERT_SOUND, ""))) {
		// masterInfo.edit()
		// .putString(Constant.ALERT_SHOCK, Constant.ALERT_ON)
		// .commit();
		// masterInfo.edit()
		// .putString(Constant.ALERT_SOUND, Constant.ALERT_ON)
		// .commit();
		// }
		// masterInfo.edit().putString(Constant.IS_CHAT, Constant.OFF_CHAT)
		// .commit();

		// 检测并创建软件在sd卡的文件和目录
		String path;
		if ((path = SdCardUtil.getExternalStoragePath()) != null) {

			Current.setUSERDIR(Constant.APKDIR + "/" + Current.getCurrentuser()
					.getId());//设置用户根目录

			FileUtils.createPath(Constant.APKDIR);//创建应用根目录
			FileUtils.createPath(Current.getUSERDIR());//创建用户根目录
			FileUtils.createPath(Constant.DOWNLOADDIR);//创建下载的目录

		}

//		FileUtils.createPath(SdCardUtil.getExternalStoragePath() + "/"
//				+ Constant.APKNAME);
//		FileUtils.createPath(SdCardUtil.getExternalStoragePath() + "/"
//				+ Constant.APKNAME + "/" + Current.getCurrentuser().getId());
//		FileUtils.createPath(SdCardUtil.getExternalStoragePath() + "/"
//				+ Constant.APKNAME + "/" + Constant.USERNAME + "/image");
//		//FileUtils.createPath(username);

		// 设置昵称
		// if ("".equals(masterInfo.getString(Constant.NICKNAME, ""))) {
		// Intent intent = new Intent(new Intent(mContext,
		// SetNickNameActivity.class));
		// startActivity(intent);
		// } else {
		// startActivity(new Intent(LoginActivity.this,
		// AllChatListActivity.class));
		// }

		startActivity(new Intent(LoginActivity.this, HomeActivity.class));

		finish();

	}

	// ========对输入的信息进行完整性检查==============================
	private boolean checkUsername(String username) {
		if (username == null || username.equals("")) {
			ShowMsgUtil.show(this, "用户名不能为空！");
			return false;
		}
		return true;
	}

	private boolean checkPassword(String psw) {
		if (psw == null || psw.equals("")) {
			ShowMsgUtil.show(this, "密码不能为空！");
			return false;
		}
		return true;
	}


	// 返回键添加监听,从登录界面返回主界面
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(mContext, MainActivity.class));
			finish();
		}
		return false;
	}

}
