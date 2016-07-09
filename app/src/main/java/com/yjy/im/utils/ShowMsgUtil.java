package com.yjy.im.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ShowMsgUtil {
	// 显示信息
	public static void show(Context mContext,String string) {
		Toast toast = Toast.makeText(mContext, string, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	public static void testShow(Context mContext,CharSequence hint) {
		Toast.makeText(mContext, hint, Toast.LENGTH_SHORT).show();
	}
		// 显示信息
	public static void show(Context mContext,int id) {
		Toast toast = Toast.makeText(mContext, id, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
