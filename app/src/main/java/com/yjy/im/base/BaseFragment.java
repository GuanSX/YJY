package com.yjy.im.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yjy.activity.HomeActivity;
import com.yjy.im.framework.volley.VolleyErrorHelper;
import com.yjy.im.framework.volley.VolleyRequest;
import com.yjy.im.model.Courseware;
import com.yjy.im.utils.DialogUtil;
import com.yjy.im.utils.JSONUtil;
import com.yjy.im.utils.VolleyUtil;
import com.yjy.im.utils.WebLinksUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Guan on 2016/5/14.
 */
public abstract class BaseFragment extends Fragment {


	private static String TAG = "BaseFragment";

	protected void loadAllCoursewareList(final List<Courseware> coursewarelist, String levelId,
										 String uploadUserId, int money) {

		final ProgressDialog dialog = DialogUtil.getCircularProgressDialog(((HomeActivity) getActivity()).getContext(),
				"正在加载列表...");

		// ===================
		Map<String, String> params = new HashMap<String, String>();
		if(levelId!=null) {
			params.put("levelId", levelId);
		}
		if(uploadUserId!=null)
		{
			params.put("uploadUserId", uploadUserId);
		}
		if(money>=-1)
		{
			//params.put("money", money);
		}

		coursewarelist.clear();


		VolleyRequest vr = new VolleyRequest(Request.Method.POST, WebLinksUtil.GETCOURSELIST,
				params, new Response.Listener() {
			@Override
			public void onResponse(Object o) {
				// 连接成功了会这样做
				if (null != o) {
					String result = o.toString();
					Log.e(TAG, "http response： " + result);
					JSONTokener jsonParser = new JSONTokener(result);
					JSONObject re;

					try {
						re = (JSONObject) jsonParser.nextValue();

						if (re.getString("result").equals("true")) {

							Courseware[] coursewares=JSONUtil.forCoursewarelist(re);

							for(Courseware tmpC:coursewares)
							{
								coursewarelist.add(tmpC);
							}

							doLoadAllCoursewareListSuccess();

						} else {
							// 获取列表失败
							Toast.makeText(((HomeActivity) getActivity()).getContext(), "获取列表失败,请检查您网络设置.",
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
				String m = VolleyErrorHelper.getMessage(volleyError, ((HomeActivity) getActivity()).getContext());
				Log.e(TAG, m);

			}
		});

		// 加入，开始执行
		VolleyUtil.getVolleyUtil(((HomeActivity) getActivity()).getContext()).addToRequestQueue(vr, TAG);

	}

	protected  void doLoadAllCoursewareListSuccess()
	{
		Log.e(TAG,"do not override doLoadAllCoursewareListSuccess method");
	}

	//用于各种fragment调用的java文件回调原来的fragment的方法
	public  void fragmentResult(int resultCode, Bundle bundle)
	{

	}


}
