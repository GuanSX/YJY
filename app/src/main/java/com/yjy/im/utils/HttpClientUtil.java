package com.yjy.im.utils;

import android.os.Message;
import android.util.Log;

import com.yjy.im.base.Constant;
import com.yjy.im.base.Current;
import com.yjy.im.framework.system.SystemConstant;

import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Guan on 2016/6/20.
 */

/**
 * 网络连接工具类
 */

public class HttpClientUtil {

	private static final String TAG = "HttpClientUtil";

	private static final String CHARSET = HTTP.UTF_8;
	private static HttpURLConnection uRLConnection;

	private static int downloadLength = 0;

	private HttpClientUtil() {

	}

	// 通过httpURLConnection访问
	public static String httpURLConnection(String urlAddress, String param) {

		String response = "";

		try {
			URL url = new URL(urlAddress);
			uRLConnection = (HttpURLConnection) url.openConnection();
			uRLConnection.setDoInput(true);
			uRLConnection.setDoOutput(true);
			uRLConnection.setRequestMethod("POST");
			uRLConnection.setUseCaches(false);
			uRLConnection.setInstanceFollowRedirects(false);
			uRLConnection.setRequestProperty("Accept-Charset", "UTF-8");
			uRLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			uRLConnection.setConnectTimeout(10000);

			if (null != SystemConstant.session) {
				uRLConnection.setRequestProperty("Cookie",
						SystemConstant.session);

				Log.v(TAG, SystemConstant.session);

			}

			uRLConnection.connect();

			if ("" != param) {
				DataOutputStream out = new DataOutputStream(
						uRLConnection.getOutputStream());
				out.writeBytes(param);
				out.flush();
				out.close();
			}
			int i = uRLConnection.getResponseCode();
			Log.e("i", i + "");
			if (uRLConnection.getResponseCode() == 200) {
				SystemConstant.connectionTimeOut = 0;
				// return response;
			} else {
				SystemConstant.connectionTimeOut = 1;
			}

			InputStream is = uRLConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			if (null == SystemConstant.session) {
				String session_value = uRLConnection
						.getHeaderField("Set-Cookie");

				String[] sessionId = session_value.split(";");
				SystemConstant.session = sessionId[0];
			}
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				// response = br.readLine();
				response = readLine;
			}

			is.close();
			br.close();
			uRLConnection.disconnect();

		} catch (Exception e) {
		}

		Log.e(TAG, "到服务器拿response：" + response);

		return response;

	}


	// 不需要缓存数据
	public static String getHttpURLConnection(String urlAddress, Map<String, String> params) {

		Log.e(TAG, "不需要缓存");
		String response = "";
		// 请求参数
		String param = "";
		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				param += entry.getKey() + "=" + entry.getValue() + "&";
			}
			urlAddress = urlAddress + "?" + param.replace(param.substring(0, param.length() - 1),
					"");
		}

		Log.e(TAG, "" + urlAddress);

		response = HttpClientUtil.httpURLConnection(urlAddress, param);

		return response;
	}

	// 需要缓存数据
//	public static String getHttpURLConnection(Context context,
//											  String urlAddress, boolean refresh, Map<String,
//			String> params) {
//
//		Log.e(TAG, "需要缓存");
//
//		String response = "";
//		// 请求参数
//
//		String param = "";
//		if (params != null) {
//			for (Map.Entry<String, String> entry : params.entrySet()) {
//				param += entry.getKey() + "=" + entry.getValue() + "&";
//			}
//			urlAddress = urlAddress + "?" + param.replace(param.substring(0, param.length() - 1),
//					"");
//		}
//
//		Log.e(TAG, "" + urlAddress);
//		boolean haveCache = false;
//		//ACache cache = ACache.get(context);
//		if (!refresh) {
//			response = ACache.getAsString(urlAddress);
//			if (response != null && !response.equals("null")) {
//				haveCache = true;
//			}
//			Log.e(TAG, "看看缓存里有没有：" + response);
//		}
//
//		if (response == null || response.equals("null") || response == "") {
//			response = HttpClientUtil.httpURLConnection(urlAddress, param);
//		}
//
//		// 是否缓存
//		if (haveCache == false && response != null) {
//			Log.e(TAG, "放入缓存里面：" + response);
//			// 缓存只保存一天，过期无效。
//			ACache.put(urlAddress, response, 1 * ACache.TIME_DAY);
//		}
//		return response;
//	}
//

	// 文件上传

	/**
	 * 上传文件到服务器
	 *
	 * @param file       需要上传的文件
	 * @param RequestURL 请求的rul
	 * @return 返回响应的内容
	 */
	public static int uploadFile(File file, String RequestURL, Map<String, String> param) {
		int TIME_OUT = 10 * 1000; // 超时时间
		String CHARSET = "utf-8"; // 设置编码
		int currentLength = 0;// 显示当前已经上传的文件大小

		int res = 0;
		String result = null;

		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // 内容类型

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);

			Log.e(TAG, file.getAbsolutePath());

			DataOutputStream out = new DataOutputStream(
					conn.getOutputStream());


			/***
			 * 以下是用于上传参数
			 */
			if (param != null && param.size() > 0) {
				Iterator<String> it = param.keySet().iterator();
				while (it.hasNext()) {
					StringBuffer sb = new StringBuffer();
					String key = it.next();
					String value = param.get(key);
					sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
					sb.append("Content-Disposition: form-data; name=\"")
							.append(key).append("\"").append(LINE_END)
							.append(LINE_END);
					sb.append(value).append(LINE_END);
					String params = sb.toString();

					Log.e(TAG, key + "=" + params + "##");

					out.write(params.getBytes());
					// dos.flush();
				}
			}


			if (file != null) {
				/**
				 * 当文件不为空时执行上传
				 */

				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);

				/**
				 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
				 * filename是文件的名字，包含后缀名
				 */
				sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
						+ file.getName() + "\"" + LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINE_END);
				sb.append(LINE_END);
				out.write(sb.toString().getBytes());

				InputStream is = new FileInputStream(file);
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					out.write(bytes, 0, len);
					currentLength += len;
					//currentLength

				}
				is.close();
				out.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				out.write(end_data);
				out.flush();

				/**
				 * 获取响应码 200=成功 当响应成功，获取响应的流
				 */
				res = conn.getResponseCode();
				Log.e(TAG, "response code:" + res);
				if (res == 200) {
					Log.e(TAG, "request success");
					InputStream input = conn.getInputStream();
					StringBuffer sb1 = new StringBuffer();
					int ss;
					while ((ss = input.read()) != -1) {
						sb1.append((char) ss);
					}
					result = sb1.toString();
					Log.e(TAG, "result : " + result);
				} else {
					Log.e(TAG, "request error");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}


	public static HashMap<String,Object> Upload(String filename, String RequestURL, Map<String, String> param) {
		int TIME_OUT = 10 * 1000; // 超时时间
		String CHARSET = "utf-8"; // 设置编码

		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // 内容类型

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);


			DataOutputStream out = new DataOutputStream(
					conn.getOutputStream());


			/***
			 * 以下是用于上传参数
			 */
			if (param != null && param.size() > 0) {
				Iterator<String> it = param.keySet().iterator();
				while (it.hasNext()) {
					StringBuffer params_sb = new StringBuffer();
					String key = it.next();
					String value = param.get(key);
					params_sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
					params_sb.append("Content-Disposition: form-data; name=\"")
							.append(key).append("\"").append(LINE_END)
							.append(LINE_END);

					params_sb.append(value).append(LINE_END);

					Log.e(TAG, key + "=" + value + "##");

					out.write(params_sb.toString().getBytes());

				}
			}


			StringBuffer sb = new StringBuffer();
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINE_END);

			/**
			 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
			 * filename是文件的名字，包含后缀名
			 */
			sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
					+ filename + "\"" + LINE_END);
			sb.append("Content-Type: application/octet-stream; charset="
					+ CHARSET + LINE_END);
			sb.append(LINE_END);
			out.write(sb.toString().getBytes());

			byte[] end_data = (LINE_END + PREFIX + BOUNDARY + PREFIX + LINE_END)
					.getBytes();

			HashMap<String,Object> map=new HashMap<String,Object>();

			map.put("DataOutputStream",out);
			map.put("end_data",end_data);
			//map.put("InputStream",conn.getInputStream());
			//map.put("ResponseCode",conn.getResponseCode());
			map.put("HttpURLConnection",conn);
			return map;


		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}


	// 用于下载文件
	public static List<Object> DownLoad(String murl) {

		try {

			Log.e(TAG, murl);
			URL url = new URL(murl);
			uRLConnection = (HttpURLConnection) url.openConnection();
			uRLConnection.setDoInput(true);
			uRLConnection.setDoOutput(true);
			uRLConnection.setRequestMethod("POST");
			uRLConnection.setUseCaches(false);
			uRLConnection.setInstanceFollowRedirects(false);
			uRLConnection.setRequestProperty("connection", "keep-alive");
			uRLConnection.setRequestProperty("Charset", CHARSET);
			uRLConnection.setConnectTimeout(10000);

			uRLConnection.connect();


			long length = uRLConnection.getContentLength();
			InputStream is = uRLConnection.getInputStream();

			List<Object> list = new ArrayList<Object>();

			list.add(length);
			list.add(is);

			return list;


		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
			return null;
		}

	}


}
