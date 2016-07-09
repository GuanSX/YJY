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
import com.yjy.im.model.User;
import com.yjy.im.utils.DialogUtil;
import com.yjy.im.utils.VolleyUtil;
import com.yjy.im.utils.WebLinksUtil;
import com.yjy.im.base.*;

public abstract class ALoginActivity extends BaseActivity {
	static final String TAG = "ALoginActivity";
	String info = "";

	protected void doLogin(final String username, final String password) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username.trim());
		params.put("password", password);

		// ==================
		final ProgressDialog dialog = DialogUtil.getCircularProgressDialog(
				ALoginActivity.this, "正在登录...");

		// ===================`1

		VolleyRequest vr = new VolleyRequest(Request.Method.POST,
				WebLinksUtil.login, params, new Response.Listener() {

			@Override
			// 连接成功了会这样做
			public void onResponse(Object o) {

				if (null != o) {
					String result = o.toString();
					Log.e(TAG, "http response： " + result);
					JSONTokener jsonParser = new JSONTokener(result);

					try {
						//解析JSON
						JSONObject re = (JSONObject) jsonParser.nextValue();
						//info = re.getString("info");

						//String isSuccess = re.getString("result");
						if (re.getString("result").equals("true")) {
							// 登录成功
							User currentuser = new User();
							JSONObject JSON_user = re.getJSONObject("user");
							JSONObject JSON_role = JSON_user.getJSONObject("role");
							JSONObject JSON_level = JSON_user.getJSONObject("level");

							currentuser.setId(JSON_user.getString("userId"));
							currentuser.setUserName(JSON_user.getString("username"));
							currentuser.setPassword(JSON_user.getString("password"));
							currentuser.setRealname(JSON_user.getString("realName"));
							currentuser.setRole(JSON_role.getString("roleId"),
									JSON_role.getString("roleName"),
									JSON_role.getString("roleDesc"),
									JSON_role.getString("users"));
							currentuser.setLevel(JSON_level.getString("levelId"),
									JSON_level.getString("levelName"),
									JSON_level.getString("levelDesc"),
									JSON_level.getString("users"));
//							currentuser.setRole(re.getJSONObject("user").getString("role"));
//							currentuser.setLevel(re.getJSONObject("user").getString("level"));

							doLoginSuccess(currentuser);

						} else {
							// 登录失败
							Toast.makeText(mContext,
									"登陆失败,请检查您的用户名、密码或网络设置.",
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
						ALoginActivity.this);
				//Log.e(TAG, m);

			}
		});

		// 加入，开始执行
		VolleyUtil.getVolleyUtil(ALoginActivity.this)
				.addToRequestQueue(vr, TAG);

	}

	// /**
	// * 初始化各项服务
	// */
	// private void initServer() {
	// Intent server = new Intent(mContext, IMContactService.class);
	// startService(server);
	// Intent chatServer = new Intent(mContext, IMChatService.class);
	// startService(chatServer);
	// }

	protected abstract void doLoginSuccess(User user);


}
