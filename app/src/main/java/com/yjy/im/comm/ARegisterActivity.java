package com.yjy.im.comm;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yjy.im.framework.volley.VolleyErrorHelper;
import com.yjy.im.framework.volley.VolleyRequest;
import com.yjy.im.utils.DialogUtil;
import com.yjy.im.utils.VolleyUtil;
import com.yjy.im.utils.WebLinksUtil;
import com.yjy.im.base.*;

public abstract class ARegisterActivity extends BaseActivity {
	static final String TAG = "ARegistActivity";
	String info = "";

	protected void doRegist(final String username, final String password,
			 final String role, final String level) {

		Map<String, String> test_params = new HashMap<String, String>();
		test_params.put(Constant.PARA_USERNAME, username.trim());
		test_params.put(Constant.PARA_PASSWORD, password);
		// test_params.put("realname", realname);
		test_params.put(Constant.PARA_ROLEID, role);
		test_params.put(Constant.PARA_LEVELID, level);

		final ProgressDialog dialog = DialogUtil.getCircularProgressDialog(
				ARegisterActivity.this, "正在注册...");

		// ===================

		VolleyRequest vr = new VolleyRequest(Request.Method.POST,
				WebLinksUtil.register, test_params, new Response.Listener() {
					@Override
					// 连接成功了会这样做
					public void onResponse(Object o) {
						if (null != o) {
							String result = o.toString();

							Log.e(TAG, "http response： " + result);
							JSONTokener jsonParser = new JSONTokener(result);

							try {
								JSONObject re = (JSONObject) jsonParser.nextValue();
								//info = re.getString("info");
								//String isSuccess = re.getString("result");
								if (re.getString("result").equals("true")) {
									// 注册成功
									doRegisterSuccess(username, password,
											 role, level);

								} else {
									// 登录失败
									Toast.makeText(mContext,
											"注册失败,用户名已被注册或检查您的网络设置.",
											Toast.LENGTH_SHORT).show();

								}

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						dialog.dismiss();
					}
				}, new Response.ErrorListener() {
					// 连接失败了会这样执行
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						dialog.dismiss();
						String m = VolleyErrorHelper.getMessage(volleyError,
								ARegisterActivity.this);
						//Log.e(TAG,m);

					}
				});

		// 加入，开始执行
		VolleyUtil.getVolleyUtil(ARegisterActivity.this).addToRequestQueue(vr,
				TAG);
	}

	protected abstract void doRegisterSuccess(String username, String password,
			 String role, String level);

}
