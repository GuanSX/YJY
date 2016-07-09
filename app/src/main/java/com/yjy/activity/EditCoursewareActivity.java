package com.yjy.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.yjy.R;
import com.yjy.im.base.BaseActivity;
import com.yjy.im.base.Constant;
import com.yjy.im.framework.volley.VolleyErrorHelper;
import com.yjy.im.framework.volley.VolleyRequest;
import com.yjy.im.model.Courseware;
import com.yjy.im.utils.DialogUtil;
import com.yjy.im.utils.IconUtil;
import com.yjy.im.utils.JSONUtil;
import com.yjy.im.utils.ShowMsgUtil;
import com.yjy.im.utils.VolleyUtil;
import com.yjy.im.utils.WebLinksUtil;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

/**
 * Created by Guan on 2016/5/28.
 */
public class EditCoursewareActivity extends BaseActivity {

	private static boolean hasChanged = false;//标记位，用来标记该课件是否已经被修改过

	private static String levelId = "";

	private int position=0;
	private ImageView coursewareicon;
	private TextView name;
	private TextView uploader;
	private TextView level;
	private EditText cost;
	private Button btn_cancle;
	private Button btn_publish;
	//标题
	private Button btn_function;//添加课件按钮
	private TextView title_string;
	private Button btn_back;
	private RelativeLayout uploader_layout;
	private RelativeLayout level_layout;
	private Courseware courseware;
	private View.OnClickListener listener;
	private String TAG = "EditCoursewareActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editcourseware);
		Init();

	}

	private void Init() {

		btn_function = (Button) findViewById(R.id.ui_title_function_btn);
		title_string = (TextView) findViewById(R.id.ui_title_word_tv);
		btn_back = (Button) findViewById(R.id.ui_title_back_btn);

		coursewareicon = (ImageView) findViewById(R.id.edit_icon);
		name = (TextView) findViewById(R.id.edit_name);
		uploader = (TextView) findViewById(R.id.edit_uploder);
		level = (TextView) findViewById(R.id.edit_level);
		cost = (EditText) findViewById(R.id.edit_cost);

		btn_cancle = (Button) findViewById(R.id.edit_cancle);
		btn_publish = (Button) findViewById(R.id.edit_publish);

		//uploader_layout=(RelativeLayout) findViewById(R.id.edit_uploder_layout);
		level_layout = (RelativeLayout) findViewById(R.id.edit_level_layout);

		courseware = (Courseware) getIntent().getExtras().get("courseware");
		position=getIntent().getIntExtra("position",-1);
		//ShowMsgUtil.show(EditCoursewareActivity.this,courseware.getCourseware_originalname());

		if (courseware.isPublished()) {
			coursewareicon.setImageBitmap(IconUtil.getPublishedIcon(mContext));
		} else {
			coursewareicon.setImageBitmap(IconUtil.getunPublishedIcon(mContext));
		}

		//===设置主体内容=========
		name.setText(courseware.getOriginalName());
		uploader.setText(courseware.getUser().getUserName());
		level.setText(courseware.getLevel().getLevelName());
		cost.setText(courseware.getMoney());
		levelId=courseware.getLevel().getLevelId();

		//===设置标题====
		btn_back.setVisibility(View.VISIBLE);
		btn_function.setVisibility(View.GONE);
		title_string.setText("粤教云客户端——管理课件");

		//=====设置监听器===
		InitListener(listener);
		btn_publish.setOnClickListener(listener);
		btn_cancle.setOnClickListener(listener);
		btn_back.setOnClickListener(listener);
		level_layout.setOnClickListener(listener);
		name.setOnClickListener(listener);


	}

	/*
	*初始化“发布按钮”、“取消按钮”、“标题返回按钮”、“修改级别”、“修改名字”的监听器
	*/
	private void InitListener(View.OnClickListener listener) {

		this.listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.edit_publish:
						doPublish();
						break;
					case R.id.edit_cancle:
					case R.id.ui_title_back_btn:
						doCancle();
						break;
					case R.id.edit_level_layout:
						doChangeCoursewareLevel();
						break;
					case R.id.edit_name:
						doChangeCoursewareName();
						break;
					default:
						break;
				}
			}
		};

	}

	//========修改级别======
	private void doChangeCoursewareLevel() {
		final AlertDialog dlg = DialogUtil.getLevelDialog(EditCoursewareActivity.this, "修改级别");
		Window window = dlg.getWindow();
		Button sure_btn = (Button) window.findViewById(R.id.alert_level_sure_btn);
		Button cancel_btn = (Button) window.findViewById(R.id.alert_level_cancel_btn);

		//确认
		View.OnClickListener positive = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//finish();
				level.setText(DialogUtil.getCheckedlevel());
				levelId = DialogUtil.getCheckedLevelId();
				hasChanged = true;
				dlg.dismiss();
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


	//====修改课件名======
	private void doChangeCoursewareName() {

		final AlertDialog dlg = DialogUtil.getInputDialog(this, "修改课件名称", courseware
				.getOriginalName(), "输入课件名称");
		final Window window = dlg.getWindow();

		Button sure_btn = (Button) window.findViewById(R.id.alert_input_sure_btn);
		Button cancel_btn = (Button) window.findViewById(R.id.alert_input_cancel_btn);

		//确认
		View.OnClickListener positive = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String tmpname = ((EditText) window.findViewById(R.id
						.alert_input_content))
						.getText().toString();
				//Log.e(TAG, "获取的新的名字：" + newOriginalName);
				if ("".equals(tmpname)) {
					ShowMsgUtil.show(mContext, "课件名称不能为空");
				} else {
					if (!courseware.getOriginalName().equals(tmpname)) {
						// UI更新
						name.setText(tmpname);
						hasChanged = true;
					}
					dlg.cancel();
				}
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


	//确认发布
	private void doPublish() {

		final ProgressDialog dialog = DialogUtil.getCircularProgressDialog(
				EditCoursewareActivity.this, "正在提交...");

		ShowMsgUtil.show(mContext, "publish");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("docId", courseware.getDocId());
		params.put("levelId", levelId);
		params.put("originalName", name.getText().toString());
		params.put("money", cost.getText().toString());

		VolleyRequest vr = new VolleyRequest(Request.Method.POST, WebLinksUtil.UPDATECOURSEWARE,
				params, new Response.Listener() {
			@Override
			public void onResponse(Object o) {
				if (o != null) {
					dialog.dismiss();
					Log.e(TAG, "http response： " + o.toString());
					JSONTokener jsonParser = new JSONTokener(o.toString());

					try
					{
						JSONObject re = (JSONObject) jsonParser.nextValue();
						if(re.getString("result").equals("true"))
						{
							ShowMsgUtil.show(mContext,"修改成功！");
							Courseware c= JSONUtil.forCourseware(re);
							Log.e(TAG,c.getMoney());
							Intent intent=getIntent();
							intent.putExtra("courseware",c);
							setResult(RESULT_OK,intent);

						}

					}catch (Exception e)
					{
						e.printStackTrace();
					}
					//setResult(Constant.resultcode_uploadrecordfragment);

					setResult(RESULT_OK,getIntent());
					finish();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				dialog.dismiss();
				String m = VolleyErrorHelper.getMessage(volleyError,
						EditCoursewareActivity.this);
			}
		});

		VolleyUtil.getVolleyUtil(EditCoursewareActivity.this)
				.addToRequestQueue(vr, TAG);
	}

	//放弃编辑,给“标题返回键”，“取消按钮”，“手机的返回键”添加
	private void doCancle() {
		if (!(courseware.getMoney()).equals(cost.getText().toString())) {
			hasChanged = true;
		}

		if (hasChanged == true) {
			final AlertDialog dlg = DialogUtil.getYNAlertDialog(this, "放弃编辑", "您确定放弃对本课件的编辑吗？");
			Window window = dlg.getWindow();
			Button sure_btn = (Button) window.findViewById(R.id.alert_yn_sure_btn);
			Button cancel_btn = (Button) window.findViewById(R.id.alert_yn_cancel_btn);

			View.OnClickListener positive = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
					dlg.dismiss();
				}
			};

			View.OnClickListener negative = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dlg.dismiss();
				}
			};

			sure_btn.setOnClickListener(positive);
			cancel_btn.setOnClickListener(negative);
		} else {
			finish();
		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			doCancle();
		}
		return false;
	}
}
