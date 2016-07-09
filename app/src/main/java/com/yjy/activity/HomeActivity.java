package com.yjy.activity;

import com.example.yjy.R;

import com.yjy.fragment.CoursewareListFragment;
import com.yjy.fragment.DownloadRecordFragment;
import com.yjy.fragment.UploadRecordFragment;
import com.yjy.fragment.SettingFragment;
import com.yjy.im.base.*;
import com.yjy.im.manager.*;
import com.yjy.im.utils.DoubleClickUtil;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

	private static String TAG = "HomeActivity";
	ArrayList<Long> clicktimes = new ArrayList<Long>();
	private Fragment coursewarelistfragment;
	private Fragment uploadrecordfragment;
	private Fragment downloadrecordfragment;
	private Fragment settingfragment;

	private PopupWindow pop;
	private OnClickListener tabClickListener;
	private OnClickListener menuItemListener;//点击底部弹出框的选项时触发事件的监听器
	private OnTouchListener touchListener;//点击其他弹出框以外的区域时事件的监听器
	private OnClickListener UploadFileListener;//点击上传按钮
	private Button btn_function;// 添加课件按钮
	private TextView title_string;
	//private boolean isBackBtnVisible;
	private Button btn_back;
	private TextView lightline_coursewarelist;
	private TextView lightline_record;
	private TextView lightline_yuliu2;
	private TextView lightline_setting;
	private Button tab_coursewarelist;
	private Button tab_mycourseware;
	private Button tab_yuliu2;
	private Button tab_setting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	private void Init() {

		this.btn_function = (Button) findViewById(R.id.ui_title_function_btn);
		this.title_string = (TextView) findViewById(R.id.ui_title_word_tv);
		this.btn_back = (Button) findViewById(R.id.ui_title_back_btn);
		//四条亮线
		this.lightline_coursewarelist = (TextView) findViewById(R.id.textview_courseware);
		this.lightline_record = (TextView) findViewById(R.id.textview_yuliu1);
		this.lightline_yuliu2 = (TextView) findViewById(R.id.textview_yuliu2);
		this.lightline_setting = (TextView) findViewById(R.id.textview_setting);

		//底部四个Tab按键
		tab_coursewarelist = (Button) findViewById(R.id.tab_courseware);
		tab_mycourseware = (Button) findViewById(R.id.tab_mycourseware);
		tab_yuliu2 = (Button) findViewById(R.id.tab_yuliu2);
		tab_setting = (Button) findViewById(R.id.tab_setting);


		//====初始化第一个Fragment======
		//Current.currentfragment = null;
		this.coursewarelistfragment = new CoursewareListFragment();
		ChangeFragment(coursewarelistfragment, lightline_coursewarelist.getId());

		//=====初始化底部tab按键
		InitTab(new Button[]{tab_coursewarelist, tab_mycourseware, tab_yuliu2, tab_setting});

		//=====初始化popupwindow======
		InitListenerForPop();
		pop=MenuManager.getUploadFileMenu(mContext, menuItemListener,touchListener);


		//======初始化顶部界面====================
		InitTitle(btn_back, title_string, btn_function);


	}//end Init()


	// =================== 底部TextView监听时间的处理，根据不同的ID来处理事件===================
	private void InitTab(Button[] btns) {
		tabClickListener = new TextView.OnClickListener() {
			@Override
			public void onClick(View v) {

				switch (v.getId()) {
					// 课件
					case R.id.tab_courseware:
						showCoursewareListFragment();
						break;
					// 记录
					case R.id.tab_mycourseware:


						showRecordFragment();
						break;
					// 预留2
					case R.id.tab_yuliu2:
						//showYuliu2Fragment();
						break;
					// 设置
					case R.id.tab_setting:
						showSettingFragment();
						break;
				}
			}
		};


		//为按钮添加监听
		for (Button btn : btns) {
			btn.setOnClickListener(tabClickListener);
		}
	}


	private void InitTitle(Button btn_back, TextView title_string, Button titleFunctionBtn) {
		// =====点击添加课件时的监听事件=====
		this.UploadFileListener = new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (true) {
					if (view.getId() == R.id.ui_title_function_btn) {
						if(!pop.isShowing())
						{
							pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
						}
					}
				} else {
					Toast.makeText(mContext, "无法链接服务器，请设置您的网络稍候重试...", Toast.LENGTH_SHORT);
				}

			}
		};


		//对于今后复杂的情况，这个方法还要丰富
		this.btn_back.setVisibility(View.INVISIBLE);
		this.title_string.setText("粤教云客户端——课件");

		// 如果角色是“教师”("1")
		if (Current.getCurrentuser().getRole().getRoleId().equals(Constant.ID_TEACHER)) {
			titleFunctionBtn.setVisibility(View.VISIBLE);
			titleFunctionBtn.setOnClickListener(UploadFileListener);
		} else if (Current.getCurrentuser().getRole().getRoleId().equals(Constant.ID_STUDENT)) {
			titleFunctionBtn.setVisibility(View.INVISIBLE);
		}

	}


	//供几个fragment使用，例如显示消息
	public Context getContext() {
		return mContext;
	}

	//=================更改Fragment====================
	private void showCoursewareListFragment() {
		if (coursewarelistfragment == null) {
			coursewarelistfragment = new CoursewareListFragment();
			Log.v(TAG, "new 了一个 coursewarelistfragment");
		}
		ChangeFragment(coursewarelistfragment, lightline_coursewarelist.getId());
		title_string.setText("粤教云客户端——课件");
	}

	private void showRecordFragment() {


		if (Current.getCurrentuser().getRole().getRoleId().equals(Constant.ID_TEACHER)) {
			//如果角色是老师
			clicktimes.add(SystemClock.uptimeMillis());


			if (Current.getCurrentfragment()!=uploadrecordfragment) {
				//


				if (uploadrecordfragment == null) {
					uploadrecordfragment = new UploadRecordFragment();
					Log.v(TAG, "new 了一个 uploadrecordfragment");
				}
				ChangeFragment(uploadrecordfragment, lightline_record.getId());
				title_string.setText("粤教云客户端——上传记录");

			} else {

				if (downloadrecordfragment == null) {
					downloadrecordfragment = new DownloadRecordFragment();
					Log.v(TAG, "new 了一个 downloadrecordfragment");
				}
				ChangeFragment(downloadrecordfragment, lightline_record.getId());
				title_string.setText("粤教云客户端——本地下载");

			}
		} else if (Current.getCurrentuser().getRole().getRoleId().equals(Constant
				.ID_STUDENT)) {
			//如果角色是学生
			if (downloadrecordfragment == null) {
				downloadrecordfragment = new DownloadRecordFragment();
				Log.v(TAG, "new 了一个 downloadrecordfragment");
			}
			ChangeFragment(downloadrecordfragment, lightline_record.getId());
			title_string.setText("粤教云客户端——查看下载");
		}

	}

	private void showYuliu2Fragment() {
	}

	private void showSettingFragment() {
		if (settingfragment == null) {
			settingfragment = new SettingFragment();
		}
		ChangeFragment(settingfragment, lightline_setting.getId());
		title_string.setText("粤教云客户端——设置");
	}

	private void ChangeFragment(Fragment newFragment, int lightline) {

		Fragment tmp = Current.getCurrentfragment();

		//依然是当前的fragment，则不响应
		if (tmp == newFragment) {
			Log.e(TAG, "新fragment和currentfragment为同一个");
			return;
		}
		//当初始化第一个fragment时
		if (null == tmp) {
			Log.e(TAG, "currentfragment为null");
			// 提交事务
			getSupportFragmentManager().beginTransaction()
					.add(R.id.content_layout, newFragment).commit();
			Current.setCurrentfragment(newFragment);

		} else {
			//==============显示新的fragment======================
			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();

			if (!newFragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
				fragmentTransaction.hide(tmp)
						.add(R.id.content_layout, newFragment).commit();
				Log.v(TAG, "新fragment的added成功");
			} else {
				fragmentTransaction.hide(tmp).show(newFragment).commit();
				Log.v(TAG, "新fragment已经added");
			}

			Current.setCurrentfragment(newFragment);
		}


		//=================设置底部tab的外观=================
		this.lightline_coursewarelist.setBackgroundColor(Color.parseColor("#737373"));
		this.lightline_setting.setBackgroundColor(Color.parseColor("#737373"));
		this.lightline_record.setBackgroundColor(Color.parseColor("#737373"));
		this.lightline_yuliu2.setBackgroundColor(Color.parseColor("#737373"));

		switch (lightline) {
			case R.id.textview_courseware:
				this.lightline_coursewarelist.setBackgroundColor(Color.parseColor("#A8FF24"));
				break;
			case R.id.textview_yuliu1:
				this.lightline_record.setBackgroundColor(Color.parseColor("#A8FF24"));
				break;
			case R.id.textview_yuliu2:
				this.lightline_yuliu2.setBackgroundColor(Color.parseColor("#A8FF24"));
				break;
			case R.id.textview_setting:
				this.lightline_setting.setBackgroundColor(Color.parseColor("#A8FF24"));
				break;


		}


	}
	//==================fragment系列方法结束==============

	private void InitListenerForPop() {

		menuItemListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mContext, BrowseFileActivity.class);
				switch (v.getId()) {
					// 本地
					case R.id.menu_courseware_photo_btn:
						pop.dismiss();
						Bundle bundle1 = new Bundle();
						bundle1.putString("type", "photo");
						intent.putExtras(bundle1);
						mContext.startActivity(intent);
						//HomeActivity.this.finish();
						break;

					case R.id.menu_courseware_video_btn:
						pop.dismiss();
						Bundle bundle2 = new Bundle();
						bundle2.putString("type", "video");
						intent.putExtras(bundle2);
						mContext.startActivity(intent);
						//HomeActivity.this.finish();
						break;

					case R.id.menu_courseware_doc_btn:
						pop.dismiss();
						Bundle bundle3 = new Bundle();
						bundle3.putString("type", "document");
						intent.putExtras(bundle3);
						mContext.startActivity(intent);
						//HomeActivity.this.finish();
						break;

					case R.id.menu_courseware_music_btn:
						pop.dismiss();
						Bundle bundle4 = new Bundle();
						bundle4.putString("type", "music");
						intent.putExtras(bundle4);
						mContext.startActivity(intent);
						//HomeActivity.this.finish();
						break;

					case R.id.menu_courseware_apk_btn:
						pop.dismiss();
						Bundle bundle5 = new Bundle();
						bundle5.putString("type", "apk");
						intent.putExtras(bundle5);
						mContext.startActivity(intent);
						//HomeActivity.this.finish();
						break;

					case R.id.menu_courseware_file_btn:
						pop.dismiss();
						Bundle bundle6 = new Bundle();
						bundle6.putString("type", "file");
						intent.putExtras(bundle6);
						mContext.startActivity(intent);
						//HomeActivity.this.finish();
						break;

					// 取消
					case R.id.menu_courseware_type_cancel_btn:
						pop.dismiss();
						break;
				}

			}

		};

		//点击其他弹出框以外的区域时事件的监听器
		touchListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					pop.dismiss();
				}
				return false;
			}
		};

	}
}
