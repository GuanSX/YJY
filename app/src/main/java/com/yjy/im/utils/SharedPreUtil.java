package com.yjy.im.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.yjy.im.base.BaseObject;
import com.yjy.im.base.Constant;

import org.apache.commons.codec.binary.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Created by Guan on 2016/6/1.
 */
public class SharedPreUtil {

	private static String TAG="SharedPreUti";

	public static BaseObject readObject(Context context, String Objname)
	{
		SharedPreferences preferences = context.getSharedPreferences(Objname,
				context.MODE_PRIVATE);
		String productBase64 = preferences.getString("key", "");
		if (productBase64 == "") {
			Log.e(TAG, "没有对象:"+Objname);
			return null;
		}

		//读取字节
		byte[] base64 = Base64.decodeBase64(productBase64.getBytes());

		//封装到字节流
		ByteArrayInputStream bais = new ByteArrayInputStream(base64);
		try {
			//再次封装
			ObjectInputStream bis = new ObjectInputStream(bais);
			try {
				//读取对象
				BaseObject bo= (BaseObject) bis.readObject();
				Log.e(TAG, "读取成功"+bo.toString());
				return bo;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return  null;
			}
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static void saveObject(Context context,BaseObject baseobject,String Objname)
	{
		SharedPreferences preferences = context.getSharedPreferences(Objname,
				context.MODE_PRIVATE);

		//创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			//创建对象输出流，并封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);

			//将对象写入字节流
			oos.writeObject(baseobject);

			//将字节流编码成base64的字符窜
			String productBase64 = new String(Base64.encodeBase64(baos
					.toByteArray()));

			SharedPreferences.Editor editor = preferences.edit();
			editor.putString("key", productBase64);
			editor.commit();
			Log.e(TAG, "存储成功:"+productBase64.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "存储失败");
		}



	}

	public static void clearSharedPre(Context context)
	{
		SharedPreferences masterInfo = context.getSharedPreferences(Constant.USER_INFO, 0);
		masterInfo.edit().clear().commit();
	}
}
