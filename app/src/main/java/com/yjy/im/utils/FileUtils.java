package com.yjy.im.utils;

import android.util.Log;

import java.io.File;

import java.io.IOException;

import com.yjy.im.base.Constant;

import org.jivesoftware.smackx.packet.StreamInitiation;

/**
 * 文件管理类
 */
public class FileUtils {

	private static String TAG = "FileUtils";

	/**
	 * 创建路径
	 *
	 * @param path
	 */
	public static boolean createPath(String path) {
		File file = new File(path.toString());
		if (!file.exists()) {
			if (file.mkdirs()) {
				Log.v(TAG, "创建文件夹成功：" + path);
				return true;
			} else {
				Log.e(TAG, "创建文件夹失败：" + path);
				return false;
			}
		}
		return true;
	}

	/**
	 * 创建文件
	 * @param dirPath 文件所在文件夹
	 * @param filename 文件名
	 * @return
	 */
	public static File createFile(String dirPath, String filename) {

		if (!createPath(dirPath)) {
			//创建目录失败
			return null;
		}
		File file = new File(dirPath, filename);
		if (!file.exists()) {
			try {
				if (file.createNewFile()) {
					Log.v(TAG, "创建文件成功：" + filename);
					return file;
				} else {
					Log.e(TAG, "创建文件失败：" + filename);
					return null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "创建文件失败：" + filename);
				return null;
			}
		}
		Log.v(TAG, "文件已存在，无需创建新的文件：" + filename);
		return file;
	}

	/**
	 * 创建文件
	 * @param fillPath 文件的完整路径
	 * @return
	 */
	public static File createFile(String fillPath) {

		File f=new File(fillPath);

		String dirPath=f.getParent();
		String filename=f.getName();

		return createFile(dirPath,filename);

	}


}

