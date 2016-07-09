package com.yjy.im.framework.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：huihaiAndroid
 * 类描述：
 * 创建人：PC
 * 创建时间：2016/3/8 14:07
 * 修改人：PC
 * 修改时间：2016/3/8 14:07
 * 修改备注：
 * <p/>
 * VolleyRequest vr=new VolleyRequest(Request.Method.POST, WebLinksUtil.login,params,new Response
 * .Listener() {
 *
 * @Override public void onResponse(Object o) {
 * <p/>
 * Log.e("--->>>>-----",o.toString());
 * }
 * }, new Response.ErrorListener() {
 * @Override public void onErrorResponse(VolleyError volleyError) {
 * Log.e("--->>>>-----",volleyError.getMessage());
 * }
 * });
 * <p/>
 * <p/>
 * VolleyUtil.getVolleyUtil(this).addToRequestQueue(vr,TAG);
 */
public class VolleyRequest extends Request<String> {

	private final String TAG = "VolleyRequest";

	private final Listener<String> listener;

	private Map<String, String> params;


	//构造方法
	public VolleyRequest(int method, String url, Map<String, String> params, Response.Listener
			listener, Response.ErrorListener errorListener) {
		super(method, url, errorListener);
		this.listener = listener;
		this.params = params;


		Log.e(TAG, "请求的URL：" + url);
		if (params != null) {
			Log.e(TAG, "请求的params：" + params);

			String str = "?";

			for (Map.Entry<String, String> entry : params.entrySet()) {
				str += entry.getKey() + "=" + entry.getValue() + "&";
			}

			Log.e(TAG, "请求：" + url + str);
		}
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
		String parsed;

		try {
			parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset
                    (networkResponse.headers));
			Log.e(TAG, "parseNetworkResponse");

			return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(networkResponse));

		} catch (UnsupportedEncodingException e) {
			// parsed = new String(networkResponse.data);

			Log.e(TAG, "UnsupportedEncodingException 超时了");

			return Response.error(new ParseError(e));
		} catch (Exception e) {
			Log.e(TAG, "超时了");
			e.printStackTrace();
			return Response.error(new ParseError(e));
		}
	}


	@Override
	protected void deliverResponse(String response) {
		listener.onResponse(response);
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params == null ? super.getParams() : params;
	}

//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//
//        HashMap localHashMap = new HashMap();
//        localHashMap.put("Charset", "UTF-8");
//        localHashMap.put("Content-Type", "application/x-www-form-urlencoded");
//        //localHashMap.put("Accept-Encoding", "gzip,deflate");
//
//        if (SystemConstant.session != null) {
//            localHashMap.put("Cookie", SystemConstant.session);
//        }
//
//        return localHashMap;
//    }


	@Override
	public RetryPolicy getRetryPolicy() {
		RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy
                .DEFAULT_BACKOFF_MULT);
		return retryPolicy;
	}


	@Override
	public void setRetryPolicy(RetryPolicy retryPolicy) {
		RetryPolicy Policy = new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy
                .DEFAULT_BACKOFF_MULT);
		super.setRetryPolicy(Policy);
	}
}
