package com.yjy.im.utils;

import android.util.Log;

import com.yjy.im.model.Courseware;
import com.yjy.im.model.LEVEL;
import com.yjy.im.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Guan on 2016/6/9.
 */
public class JSONUtil {

	public static String TAG = "JSONUtil";

	//修改个人信息时用到了，以后在登录和注册也要添加上。
	public static User forUser(JSONObject jsonObject) {

		try {
			User user = new User();
			JSONObject JSON_user = jsonObject.getJSONObject("user");

			user.setId(JSON_user.getString("userId"));
			user.setUserName(JSON_user.getString("username"));
			user.setPassword(JSON_user.getString("password"));
			user.setRealname(JSON_user.getString("realName"));

			//当key值“role”对应的值为null时，会怎么样？
			//Log.e(TAG,.getClass().getName());
			if ( ! JSON_user.get("role").equals(JSONObject.NULL) ) {
				JSONObject JSON_role = JSON_user.getJSONObject("role");
				user.setRole(JSON_role.getString("roleId"),
						JSON_role.getString("roleName"),
						JSON_role.getString("roleDesc"),
						JSON_role.getString("users"));
			} else {
				//user.setRole(null);
				Log.e(TAG, "JSON_user.getJSONObject(\"role\") is JSONObject.NULL");
			}

			if (! JSON_user.get("level").equals(JSONObject.NULL)) {
				JSONObject JSON_level = JSON_user.getJSONObject("level");
				user.setLevel(JSON_level.getString("levelId"),
						JSON_level.getString("levelName"),
						JSON_level.getString("levelDesc"),
						JSON_level.getString("users"));
			} else {
				Log.e(TAG, "JSON_user.getJSONObject(\"level\") is JSONObject.NULL");
			}

			return user;
		} catch (Exception e) {
			Log.e(TAG, "JSONUtil forUser error, the parameter returned is null.");
			e.printStackTrace();
			return null;
		}
	}

	public static String forUploadFileResult(JSONObject jsonObject) {
		try {
			String result = jsonObject.getString("result");
			String info = jsonObject.getString("info");
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Courseware forCourseware(JSONObject jsonObject)
	{
		try {
			Courseware tmpC = new Courseware();
			JSONObject tmpJO = jsonObject.getJSONObject("document");
			tmpC.setDocId(tmpJO.getString("docId"));
			tmpC.setOriginalName(tmpJO.getString("originalName"));
			tmpC.setSaveName(tmpJO.getString("saveName"));
			tmpC.setSavePath(tmpJO.getString("savePath"));
			tmpC.setDownPath(tmpJO.getString("downPath"));
			tmpC.setType(0);//这里没有定义，以后要改
			tmpC.setPublishStatus(tmpJO.getString("publishStatus"));
			tmpC.setNeedPay(tmpJO.getString("needPay"));
			tmpC.setMoney(tmpJO.getString("money"));
			tmpC.setUploadTime(tmpJO.getString("uploadTime"));
			tmpC.setUser(forUser(tmpJO));
			tmpC.setLevel(forLevel(tmpJO));
			return tmpC;

		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static Courseware[] forCoursewarelist(JSONObject jsonObject) {
		try {

			int total = Integer.valueOf(jsonObject.getString("total"));
			Courseware[] coursewares = new Courseware[total];
			if (total == 0) {
				return coursewares;
			}

			JSONArray jsonArray = jsonObject.getJSONArray("documents");

			for (int i = 0; i < total; i++) {
				Courseware tmpC = new Courseware();
				JSONObject tmpJO = jsonArray.getJSONObject(i);

				tmpC.setDocId(tmpJO.getString("docId"));
				tmpC.setOriginalName(tmpJO.getString("originalName"));
				tmpC.setSaveName(tmpJO.getString("saveName"));
				tmpC.setSavePath(tmpJO.getString("savePath"));
				tmpC.setDownPath(tmpJO.getString("downPath"));
				tmpC.setType(0);//这里没有定义，以后要改
				tmpC.setPublishStatus(tmpJO.getString("publishStatus"));
				tmpC.setNeedPay(tmpJO.getString("needPay"));
				tmpC.setMoney(tmpJO.getString("money"));
				tmpC.setUploadTime(tmpJO.getString("uploadTime"));
				tmpC.setUser(forUser(tmpJO));
				tmpC.setLevel(forLevel(tmpJO));

				//Log.e(TAG,tmpC.getUser().getUserName());

				coursewares[i] = tmpC;
			}

			return coursewares;


		} catch (Exception e) {
			Log.e(TAG, "JSONUtil forCoursewarelist errors");
			e.printStackTrace();
			return null;
		}

	}


	public static LEVEL forLevel(JSONObject jsonObject) {
		try {
			JSONObject tmpJO = jsonObject.getJSONObject("level");
			LEVEL level = new LEVEL(tmpJO.getString("levelId"),
					tmpJO.getString("levelName"),
					tmpJO.getString("levelDesc"),
					tmpJO.getString("users"));
			return level;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
