package com.yjy.im.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.yjy.im.model.User;
import com.yjy.im.utils.SdCardUtil;
import com.yjy.im.utils.SharedPreUtil;

/**
 * Created by Guan on 2016/6/1.
 */
public class Current {
	private static String TAG = "Current";
	private static Fragment currentfragment = null;
	private static User currentuser = null;

	private static String USERDIR = null;


	public static void setCurrentuser(Context context, User currentuser) {
		//修改当前的user信息，同时写入本地磁盘
		Current.currentuser = currentuser;
		SharedPreUtil.saveObject(context, currentuser, Constant.USER_INFO);
	}

	public static User getCurrentuser() {
		return currentuser;
	}

	public static void setCurrentuser(User currentuser) {
		//修改当前的user信息，但写入本地磁盘
		Current.currentuser = currentuser;
	}

	public static Fragment getCurrentfragment() {
		if (null == currentfragment) {
			Log.e(TAG, "返回null的currentfragment");
		} else {
			Log.e(TAG, "返回非null的currentfragment");
		}
		return currentfragment;
	}

	public static void setCurrentfragment(Fragment currentfragment) {
		if (null == currentfragment) {
			Log.e(TAG, "currentfragment被设置成null");
		} else {
			Log.e(TAG, "currentfragment被设置成 非 null");
		}

		Current.currentfragment = currentfragment;
	}



	public static String getUSERDIR() {
		USERDIR = Constant.APKDIR + "/" + Current.getCurrentuser()
				.getId();
		return USERDIR;
	}

	public static void setUSERDIR(String USERDIR) {
		Current.USERDIR = USERDIR;
	}


}
