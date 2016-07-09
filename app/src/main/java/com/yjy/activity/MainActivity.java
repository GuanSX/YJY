package com.yjy.activity;

import java.util.List;

import com.example.yjy.R;
import com.yjy.im.base.*;
import com.yjy.im.model.*;
import com.yjy.im.utils.SharedPreUtil;

import android.os.Bundle;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

	private Button login;
	private Button register;

	private String TAG="MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Init();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void Init() {
		this.login = (Button) findViewById(R.id.main_login_btn);
		this.register = (Button) findViewById(R.id.main_regist_btn);

		User saveduser=(User) SharedPreUtil.readObject(mContext,Constant.USER_INFO);

		//ShowMsgUtil.show(mContext,saveduser.getUserName());
		//Log.e(TAG,saveduser.getUserName());
//		String username = saveduser.getUserName();
//		String password = saveduser.getPassword();
		// 设置Master类

		//
		// username = "";
		if (saveduser!=null) {
			// xx的用户名和xx的密码同时具有输入的信息才继续（maybe自动登录）

			//===========
			//应该先验证存储的用户名和密码是不是有效，然后再进行下面的一步
			//
			//=========
			Current.setCurrentuser(saveduser);
			Intent intent = new Intent(new Intent(mContext, HomeActivity.class));
			startActivity(intent);
			this.finish();
		} else {
			// 否则，让用户选择登陆或者注册，手动
			this.login.setVisibility(View.VISIBLE);
			this.register.setVisibility(View.VISIBLE);
			// 进入登陆的activity
			this.login.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(mContext,
							LoginActivity.class);
					startActivity(intent);
					MainActivity.this.finish();
				}
			});
			// 进入注册的activity
			this.register.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(mContext,
							RegisterActivity.class);
					startActivity(intent);
					MainActivity.this.finish();
				}
			});

		}

	}

	/**
	 * 用来判断服务是否后台运行 context className 判断的服务名字 true 在运行 false 不在运行
	 */
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean IsRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				IsRunning = true;
				break;
			}
		}
		return IsRunning;
	}

}
