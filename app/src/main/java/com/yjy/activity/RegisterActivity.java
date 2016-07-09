package com.yjy.activity;

import com.example.yjy.R;

import com.yjy.im.base.Constant;
import com.yjy.im.comm.*;

import android.os.Bundle;
import android.content.*;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;

import android.widget.*;
import android.widget.RadioGroup.*;

public class RegisterActivity extends ARegisterActivity {

	private EditText edt_username = null;
	private EditText edt_pwd = null;
	private EditText edt_repwd; // 重新输入密码

	//设置标题的信息
	private Button btn_back = null;
	private Button btn_function = null;
	private TextView tv_word = null;

	private RadioGroup rag_role; // 角色
	private RadioGroup rag_level; // 级别

	private RadioButton rab_teacher; // 教师
	private RadioButton rab_student; // 学生

	private RadioButton rab_xiaoxue; // 教师
	private RadioButton rab_chuzhong; // 学生
	private RadioButton rab_gaozhong; // 教师
	private RadioButton rab_daxue; // 学生
	private RadioButton rab_chengjiao; // 教师
	private RadioButton rab_zhijiao; // 学生

	private Button btn_regist = null;


	private String st_role = "0";
	private String st_level = "0";

	private OnClickListener levelListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		Init();
	}

	private void Init() {

		edt_username = (EditText) findViewById(R.id.ui_username_input);
		edt_pwd = (EditText) findViewById(R.id.ui_password_input);
		edt_repwd = (EditText) findViewById(R.id.ui_passwordconfirm_input);

		//标题栏的三个按键
		btn_back = (Button) findViewById(R.id.ui_title_back_btn);
		btn_function = (Button) findViewById(R.id.ui_title_function_btn);
		tv_word = (TextView) findViewById(R.id.ui_title_word_tv);
		// ///////////
		rag_role = (RadioGroup) findViewById(R.id.ui_role);
		rag_level = (RadioGroup) findViewById(R.id.ui_level);
		// //////////
		rab_teacher = (RadioButton) findViewById(R.id.ui_roleteacher);
		rab_student = (RadioButton) findViewById(R.id.ui_rolestudent);
		// //////////
		rab_xiaoxue = (RadioButton) findViewById(R.id.ui_levelxiaoxue);
		rab_chuzhong = (RadioButton) findViewById(R.id.ui_levelchuzhong);
		rab_gaozhong = (RadioButton) findViewById(R.id.ui_levelgaozhong);
		rab_daxue = (RadioButton) findViewById(R.id.ui_leveldaxue);
		rab_chengjiao = (RadioButton) findViewById(R.id.ui_levelchengjiao);
		rab_zhijiao = (RadioButton) findViewById(R.id.ui_levelzhijiao);
		// //////////
		btn_regist = (Button) findViewById(R.id.ui_regist_btn);// 注册按钮


		//================设置标题栏的按键===============
		btn_function.setVisibility(View.INVISIBLE);
		btn_back.setVisibility(View.VISIBLE);
		tv_word.setText("注册");


		// ===============按钮添加事件=====================
		// 注册
		btn_regist.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!checkUsername(edt_username.getText().toString())) {
					return;
				} else if (!checkPassword(edt_pwd.getText().toString())) {
					return;
				} else if (!checkConfirmPassword(edt_pwd.getText().toString(),
						edt_repwd.getText().toString())) {
					return;
				} else if (!checkRole(st_role.toString())) {
					return;
				} else if (!checkLevel(st_level.toString())) {
					return;
				} else {

					// ==========主体==========
					// 进行注册……
					doRegist(edt_username.getText().toString(), edt_pwd
							.getText().toString(), st_role, st_level);
				}
			}
		});

		// 取消按钮添加事件
		btn_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, MainActivity.class));
				finish();
			}
		});

		// 角色按钮添加事件
		rag_role.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// Log.e("Res level", String.valueOf(group.getDocId()));
				// showMsg(String.valueOf(checkedId));
				if (checkedId == rab_teacher.getId()) {
					//st_role = "教师";
					st_role = Constant.ID_TEACHER;
				} else if (checkedId == rab_student.getId()) {
					//st_role = "学生";
					st_role = Constant.ID_STUDENT;
				}
			}
		});

		// 级别按钮添加事件,还能有比这更笨的方法吗。。。。。无语了
		levelListener = new RadioButton.OnClickListener() {
			@Override
			public void onClick(View v) {

				Log.e("Register level", String.valueOf(v.getId()));
				switch (v.getId()) {

					case R.id.ui_levelxiaoxue:
						//st_level = "小学";
						st_level = Constant.ID_XIAOXUE;
						CheckOnlyOneRadioButton(v.getId());
						break;
					case R.id.ui_levelchuzhong:
						//st_level = "初中";
						st_level = Constant.ID_CHUZHONG;
						CheckOnlyOneRadioButton(v.getId());
						break;
					case R.id.ui_levelgaozhong:
						//st_level = "高中";
						st_level = Constant.ID_GAOZHONG;
						CheckOnlyOneRadioButton(v.getId());
						break;
					case R.id.ui_leveldaxue:
						//st_level = "大学";
						st_level = Constant.ID_DAXUE;
						CheckOnlyOneRadioButton(v.getId());
						break;
					case R.id.ui_levelchengjiao:
						//st_level = "成教";
						st_level = Constant.ID_CHENGJIAO;
						CheckOnlyOneRadioButton(v.getId());
						break;
					case R.id.ui_levelzhijiao:
						//st_level = "职教";
						st_level = Constant.ID_ZHIJIAO;
						CheckOnlyOneRadioButton(v.getId());
						break;
				}
			}
		};
		rab_xiaoxue.setOnClickListener(levelListener);
		rab_chuzhong.setOnClickListener(levelListener);
		rab_gaozhong.setOnClickListener(levelListener);
		rab_daxue.setOnClickListener(levelListener);
		rab_chengjiao.setOnClickListener(levelListener);
		rab_zhijiao.setOnClickListener(levelListener);

		// ==================按钮添加事件，完毕=======================
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.regist, menu);
		return true;
	}

	// ========对输入的信息进行完整性检查==============================
	private boolean checkUsername(String username) {
		if (username == null || username.equals("")) {
			showMsg("用户名不能为空！");
			return false;
		}
		return true;
	}

	private boolean checkPassword(String psw) {
		if (psw == null || psw.equals("")) {
			showMsg("密码不能为空！");
			return false;
		}
		return true;
	}

	// 检测用户再次输入的密码是否正确的函数
	private boolean checkConfirmPassword(String psw, String pswConfirm) {
		if (pswConfirm == null || !psw.equals(pswConfirm)) {
			showMsg("密码不相同！");
			return false;
		}
		return true;
	}

	private boolean checkRealName(String realname) {
		if (realname == null || realname.equals("")) {
			showMsg("姓名不能为空！");
			return false;
		}
		return true;
	}

	private boolean checkRole(String role) {
		if (role == null || role.equals("0")) {
			showMsg("角色不能为空！");
			return false;
		}
		return true;
	}

	private boolean checkLevel(String level) {
		if (level == null || level.equals("0")) {
			showMsg("级别不能为空！");
			return false;
		}
		return true;
	}

	// ==============信息完整性检查，完毕=================================================

	// 注册成功
	@Override
	protected void doRegisterSuccess(String username, String password,
									 String role, String level) {
		Toast.makeText(getApplicationContext(), "恭喜你注册成功", Toast.LENGTH_SHORT)
				.show();
		Intent intent = new Intent(new Intent(mContext, LoginActivity.class));
		startActivity(intent);
		this.finish();
	}

	// }

	// 显示信息
	private void showMsg(String string) {
		Toast toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	// 在6个级别按钮中，唯一选中一个
	private void CheckOnlyOneRadioButton(int rab) {
		rab_xiaoxue.setChecked(false);
		rab_chuzhong.setChecked(false); // 初中
		rab_gaozhong.setChecked(false); // 高中
		rab_daxue.setChecked(false); // 大学
		rab_chengjiao.setChecked(false); // 成教
		rab_zhijiao.setChecked(false); // 职教

		if (rab_xiaoxue.getId() == rab) {
			rab_xiaoxue.setChecked(true);
		} else if (rab_chuzhong.getId() == rab) {
			rab_chuzhong.setChecked(true);
		} else if (rab_gaozhong.getId() == rab) {
			rab_gaozhong.setChecked(true);
		} else if (rab_daxue.getId() == rab) {
			rab_daxue.setChecked(true);
		} else if (rab_chengjiao.getId() == rab) {
			rab_chengjiao.setChecked(true);
		} else if (rab_zhijiao.getId() == rab) {
			rab_zhijiao.setChecked(true);
		}

	}

	// 返回键添加监听,从注册界面返回主界面
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(mContext, MainActivity.class));
			finish();
		}
		return false;
	}

}
