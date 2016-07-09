package com.yjy.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.yjy.R;

import com.yjy.im.base.BaseActivity;
import com.yjy.im.base.Constant;
import com.yjy.im.base.Current;
import com.yjy.im.framework.volley.VolleyErrorHelper;
import com.yjy.im.framework.volley.VolleyRequest;
import com.yjy.im.utils.DialogUtil;
import com.yjy.im.utils.JSONUtil;
import com.yjy.im.utils.ShowMsgUtil;
import com.yjy.im.utils.VolleyUtil;
import com.yjy.im.utils.WebLinksUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CardActivity extends BaseActivity {

	private Button back_btn;
	private Button function_btn;
	private TextView title_string;
	// private RelativeLayout avaterRelativeLayout;
	// private ImageView avaterImageView;

	private RelativeLayout realnameRelativeLayout;
	private TextView realnameTextView;

	private RelativeLayout usernameRelativeLayout;
	private TextView usernameTextView;

	private RelativeLayout roleRelativeLayout;
	private TextView roleTextView;

	private RelativeLayout levelRelativeLayout;
	private TextView levelTextView;

	// private RelativeLayout phoneRelativeLayout;
	// private TextView phoneTextView;
	private RelativeLayout sexRelativeLayout;
	private TextView sexTextView;

	private SharedPreferences masterInfo; // 用户信息
	private OnClickListener relativeListener;

	private String TAG = "CardActivity";
	private String msg_json = "json";
	//private boolean UpdateResult;

	// 用于传递“个人信息修改完毕”的消息
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			JSONTokener jsonParser = new JSONTokener(msg.getData().getString(msg_json));
			try {
				JSONObject re = (JSONObject) jsonParser.nextValue();

				if (re.getString("result").equals("true")) {
					// 修改成功
					ShowMsgUtil.show(CardActivity.this, R.string
							.personalinfo_modify_success);
					Current.setCurrentuser(CardActivity.this,JSONUtil.forUser(re));
					InitUserInfo();

				} else {
					// 修改失败
					ShowMsgUtil.show(CardActivity.this, R.string
							.personalinfo_modify_fail);
				}


			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card);
		Init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.card, menu);
		return true;
	}

	private void Init() {
		title_string = (TextView) this.findViewById(R.id.ui_title_word_tv);
		back_btn = (Button) findViewById(R.id.ui_title_back_btn);
		function_btn = (Button) findViewById(R.id.ui_title_function_btn);

		//====设置标题======
		title_string.setText("粤教云客户端——我的名片a");
		title_string.setVisibility(View.VISIBLE);
		function_btn.setVisibility(View.GONE);
		back_btn.setVisibility(View.VISIBLE);

		// this.avaterRelativeLayout = (RelativeLayout)
		// findViewById(R.id.ui_card_avater);
		// this.avaterImageView = (ImageView) findViewById(R.id.card_avater);
		this.realnameRelativeLayout = (RelativeLayout) findViewById(R.id.ui_card_realname);
		this.realnameTextView = (TextView) findViewById(R.id.card_realname);

		this.usernameRelativeLayout = (RelativeLayout) findViewById(R.id.ui_card_username);
		this.usernameTextView = (TextView) findViewById(R.id.card_username);

		this.roleRelativeLayout = (RelativeLayout) findViewById(R.id.ui_card_role);
		this.roleTextView = (TextView) findViewById(R.id.card_role);

		this.levelRelativeLayout = (RelativeLayout) findViewById(R.id.ui_card_level);
		this.levelTextView = (TextView) findViewById(R.id.card_level);

		this.sexRelativeLayout = (RelativeLayout) findViewById(R.id.ui_card_sex);
		this.sexTextView = (TextView) findViewById(R.id.card_sex);

		InitUserInfo();

		this.relativeListener = new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				if (true) {
					switch (view.getId()) {
						case R.id.ui_card_avater:
							// if (pop.isShowing()) {
							// pop.dismiss();
							// } else {
							// pop.showAtLocation(findViewById(R.id.ui_card_layout),
							// Gravity.BOTTOM, 0, 0);
							// }
							break;
						case R.id.ui_card_realname:
							setRealName();
							break;
						case R.id.ui_card_level:
							// setPhone();
							setLevel();
							break;
						case R.id.ui_card_sex:
							//setSex();
							break;
						case R.id.ui_card_address:
							// setAddress();
							// optionOrAuto();
							break;
						case R.id.ui_card_signature:
							// setSignature();
							break;
					}
				} else {
					Toast.makeText(mContext, "无法链接服务器，请设置您的网络稍候重试...", Toast.LENGTH_SHORT);
				}

			}
		};

		this.realnameRelativeLayout.setOnClickListener(relativeListener);
		this.levelRelativeLayout.setOnClickListener(relativeListener);
		this.sexRelativeLayout.setOnClickListener(relativeListener);

		// 取消按钮添加事件
		back_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//startActivity(new Intent(CardActivity.this, SettingActivity.class));
				finish();
			}
		});
	}

	private void InitUserInfo()
	{
		this.realnameTextView.setText(Current.getCurrentuser().getRealname());
		this.usernameTextView.setText(Current.getCurrentuser().getUserName());
		this.roleTextView.setText(Current.getCurrentuser().getRole().getRoleName());
		this.levelTextView.setText(Current.getCurrentuser().getLevel().getLevelName());
	}

	/**
	 * 设置用户真实姓名
	 */
	private void setRealName() {
		final AlertDialog dlg = DialogUtil.getInputDialog(this, "修改真实姓名", Current.getCurrentuser()
						.getRealname(),
				"输入真实姓名");
		final Window window = dlg.getWindow();

		Button sure_btn = (Button) window.findViewById(R.id.alert_input_sure_btn);
		Button cancel_btn = (Button) window.findViewById(R.id.alert_input_cancel_btn);

		sure_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				String realName = ((EditText) window.findViewById(R.id
						.alert_input_content))
						.getText().toString();
				Log.e(TAG, "获取的新的名字：" + realName);
				if ("".equals(realName)) {
					ShowMsgUtil.show(mContext, "真实姓名不能为空");
				} else {
					if (!Current.getCurrentuser().getRealname().equals(realName)) {
						// 更新到服务器
						acceptPersonalInfoChange(Constant.PARA_REALNAME, realName);

					}
					dlg.cancel();
				}
			}
		});

		cancel_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dlg.cancel();
			}
		});


	}


	/**
	 * 设置用户级别
	 */
	private void setLevel() {


		final AlertDialog dlg = DialogUtil.getLevelDialog(this, "修改级别");
		Window window = dlg.getWindow();
		Button sure_btn = (Button) window.findViewById(R.id.alert_level_sure_btn);
		Button cancel_btn = (Button) window.findViewById(R.id.alert_level_cancel_btn);

		//确认
		View.OnClickListener positive = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!Current.getCurrentuser().getLevel().getLevelId().equals(DialogUtil
						.getCheckedLevelId())) {
					// 更新到服务器
					acceptPersonalInfoChange(Constant.PARA_LEVELID, 
							DialogUtil.getCheckedLevelId());

				}
				dlg.cancel();
			}
		};

		//取消
		View.OnClickListener negative = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dlg.dismiss();
			}
		};

		sure_btn.setOnClickListener(positive);
		cancel_btn.setOnClickListener(negative);

	}

	//将用户修的信息上传到服务器
	private void acceptPersonalInfoChange(final String PersonalInfo, final String value) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				Map<String, String> params = new HashMap<String, String>();
				params.put(Constant.PARA_USERID, Current.getCurrentuser().getId());
				params.put(Constant.PARA_USERNAME, Current.getCurrentuser().getUserName());
				params.put(PersonalInfo, value);

				VolleyRequest vr = new VolleyRequest(Request.Method.POST, WebLinksUtil.settingcard,
						params, new Response.Listener() {
					@Override
					public void onResponse(Object o) {
						// 连接成功了会这样做
						if (null != o) {
							String result = o.toString();
							Log.e(TAG, "http response： " + result);
							Message msg = new Message();
							msg.getData().putString(msg_json, result);
							mHandler.sendMessage(msg);
						}

					}
				}, new Response.ErrorListener() {
					// 连接失败了会这样执行
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						String m = VolleyErrorHelper.getMessage(volleyError,
								CardActivity.this);
						Log.e(TAG, volleyError.getMessage());

					}
				});

				// 加入，开始执行
				VolleyUtil.getVolleyUtil(CardActivity.this).addToRequestQueue(vr, TAG);

			}
		}).start();
	}

	// 返回键添加监听,从注册界面返回主界面
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//startActivity(new Intent(CardActivity.this, HomeActivity.class));
			finish();
		}
		return false;
	}


}
