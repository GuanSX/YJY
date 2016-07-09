package com.yjy.im.utils;

import android.util.Log;

import com.yjy.im.base.*;
import com.yjy.im.model.*;

/**
 * 读取SDCARD工具类
 */
public class SdCardUtil {

	private static String TAG = "SDCardUtil";

	// 获取sdcard路径
	public static String getExternalStoragePath() {
		// 获取状态
		String state = android.os.Environment.getExternalStorageState();
		// 判断是否可读
		if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
			if (android.os.Environment.getExternalStorageDirectory().canRead()) {
				return android.os.Environment.getExternalStorageDirectory()
						.getPath();
			} else {
				Log.e(TAG, "目录不可读");
				return null;
			}

		} else {
			Log.e(TAG, "未挂载");
			return null;
		}

	}

	/**
	 * 获取avater图片路径
	 *
	 * @param userName
	 * @return
	 */
	public static String getAvaterPath(String userName) {
//		return getExternalStoragePath() + "/" + Constant.APKNAME + "/"
//				+ User.getUserName() + "/image/" + userName + ".jpg";
		return null;
	}


	public static void aaa() {
		if (android.os.Environment.getExternalStorageDirectory().canWrite()) {
			Log.e(TAG, "SD卡可写");
		} else {
			Log.e(TAG, "SD卡不可写");
		}
	}
}
