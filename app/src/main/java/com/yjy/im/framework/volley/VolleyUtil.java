package com.yjy.im.framework.volley;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;


import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 创建人：kokJuis
 * 创建时间：2016/3/8 14:57
 * 备注：
 */
public class VolleyUtil {

	public static final String TAG = "VolleyUtil";


	private static VolleyUtil volleyUti;
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	private Context mContext;

	/**
	 * 不用说了，典型的单例模式呗。
	 *
	 * @param context
	 */
	private VolleyUtil(Context context) {
		this.mContext = context;
		mRequestQueue = getRequestQueue();
		mImageLoader = new ImageLoader(mRequestQueue,
				new ImageLoader.ImageCache() {
					private final LruCache<String, Bitmap> cache = new LruCache<>(20);

					@Override
					public Bitmap getBitmap(String url) {
						return cache.get(url);
					}

					@Override
					public void putBitmap(String url, Bitmap bitmap) {
						cache.put(url, bitmap);
					}
				});
	}

	public static synchronized VolleyUtil getVolleyUtil(Context context) {
		if (volleyUti == null) {
			volleyUti = new VolleyUtil(context);
		}
		return volleyUti;
	}

	//获取一个队列
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
		}
		return mRequestQueue;
	}


	//把请求添加到队列里面
	public <T> void addToRequestQueue(Request<T> req, String tag) {

		//设置一个标记，便于取消队列里的请求
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);

	}


	//把请求添加到队列里面
//	public <T> void addToRequestQueue(final Request<T> req, final String tag, boolean checkLogin) {
//
//		if (checkLogin) {
//
////			new Thread() {
////
////				@Override
////				public void run() {
////
////					//凌空一个线程，让它优先同步执行
////					RequestFuture<String> future = RequestFuture.newFuture();
////
////					VolleyRequest request = new VolleyRequest(Request.Method.POST, WebLinksUtil.CHECK_LOGIN,null, future, future);
////					VolleyUtil.getVolleyUtil(mContext).addToRequestQueue(request, "checkLogin");
////
////					try {
////						String result = future.get();
////						if (result != null) {
////							Log.v("checkLogin", "先验证登录是否超时");
////							Log.v("checkLogin", result);
////							if ("false".equals(result)) {
////								reLogin(req,tag);
////							}else{
////								getVolleyUtil(mContext).addToRequestQueue(req, tag);
////							}
////						}
////
////					} catch (InterruptedException e) {
////						e.printStackTrace();
////					} catch (ExecutionException e) {
////						e.printStackTrace();
////					}
////				}
////			}.start();
//
//
//			VolleyRequest request = new VolleyRequest(Request.Method.POST, WebLinksUtil.CHECK_LOGIN, null, new Response.Listener() {
//				@Override
//				public void onResponse(Object o) {
//					if (o != null) {
//						String result = o.toString();
//						Log.v("checkLogin", "先验证登录是否超时");
//						Log.v("checkLogin", result);
//						if ("false".equals(result)) {
//							reLogin(req, tag);
//						} else {
//							addToRequestQueue(req, tag);
//						}
//					}
//				}
//			}, new Response.ErrorListener() {
//				@Override
//				public void onErrorResponse(VolleyError volleyError) {
//
//					Log.v(TAG, "超时登录失败");
//
//
//				}
//			});
//
//
//			addToRequestQueue(request, "checkLogin");
//
//
//		} else {
//			//设置一个标记，便于取消队列里的请求
//			req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
//			getRequestQueue().add(req);
//		}
//
//
//	}

	//取消指定请求
	public void cancelRequest(String tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}


	public ImageLoader getImageLoader() {
		return mImageLoader;
	}


	private void reLogin(final Request req, final String tag) {

//		SystemConstant.session = null;
//		// 实例化SharedPreferences对象
//		SharedPreferences mySharedPreferences = mContext
//				.getSharedPreferences("loginUser", Activity.MODE_PRIVATE);
//		String name = mySharedPreferences.getString("loginName", "");
//		String psws = mySharedPreferences.getString("loginPsw", "");
//
//		String passworkMD5 = Md5Util.GetMD5Code32(psws);
//		Map<String, String> params = new HashMap<>();
//
//		params.put("userName", name);
//		params.put("password", passworkMD5);
//		params.put("ret", "2");
//		params.put("entry", "2");
//		params.put("SystemType", "mecom");
//		params.put("systemSubType", "mecom");
//		params.put("itemOrgId", AppConstant.itemOrgId);
//
//
//		VolleyRequest vr = new VolleyRequest(Request.Method.POST, WebLinksUtil.login, params, new Response.Listener() {
//			@Override
//			public void onResponse(Object o) {
//				try {
//
//					Log.v("checkLogin", "重新登录" + o.toString());
//
//					if (o != null) {
//
//						String result = o.toString();
//
//						JSONTokener jsonParser = new JSONTokener(result);
//						JSONObject re;
//						re = (JSONObject) jsonParser.nextValue();
//						String info = re.getString("info");
//						String isSuccess = re.getString("result");
//						if (isSuccess.equals("0")) {
//
//							if (re.getJSONObject("extUser").length() != 0) {
//
//								AppConstant.userOpenFireId = re.getJSONObject("extUser")
//										.getJSONObject("QUANXIANG").getString("loginName");
//								ALoginActivity.LoginIM(mContext,
//										re.getJSONObject("extUser").getJSONObject("QUANXIANG")
//												.getString("loginName"),
//										re.getJSONObject("extUser").getJSONObject("QUANXIANG")
//												.getString("loginPasswd"));
//							}
//							addToRequestQueue(req, tag);
//
//						}
//
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}, new Response.ErrorListener() {
//			@Override
//			public void onErrorResponse(VolleyError volleyError) {
//
//			}
//		});
//
//		addToRequestQueue(vr, "reLogin");

	}

}
