package com.yjy.im.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.yjy.R;

/**
 * Created by Guan on 2016/5/25.
 */
public class IconUtil {
	private static Bitmap floder, def, music, video, photo, doc, xls, ppt, txt, pdf;


	public static Bitmap getIcon(Context mContext, String fType) {
		if (fType.equals("floder")) {
			floder = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.folder);
			return floder;
		} else if (fType.equals("mp3") || fType.equals("wav") || fType.equals("wma")) {
			music = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.music);
			return music;
		} else if (fType.equals("3gp") || fType.equals("mp4") || fType.equals("rmvb") ||
				fType.equals("rm") || fType.equals("avi") || fType.equals("mov") ||
				fType.equals("wmv") || fType.equals("mkv") || fType.equals("asf")) {
			video = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.video);
			return video;
		} else if (fType.equals("jpg") || fType.equals("gif") || fType.equals("png") ||
				fType.equals("jpeg") || fType.equals("bmp")) {
			photo = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.photo);
			return photo;
		} else if (fType.equals("doc") || fType.equals("docx")) {
			doc = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.doc);
			return doc;
			//Log.d(TAG, "doc" + fType);
		} else if (fType.equals("xls") || fType.equals("xlsx")) {
			xls = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.xls);
			return xls;
		} else if (fType.equals("ppt") || fType.equals("pptx")) {
			ppt = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ppt);
			return ppt;
		} else if (fType.equals("pdf")) {
			pdf = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pdf);
			return pdf;
		} else if (fType.equals("txt")) {
			txt = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.txt);
			return txt;
		} else {
			def = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.def);
			return def;
			//Log.d(TAG, fType);
		}
	}

	public static Bitmap getPublishedIcon(Context mContext) {
		return BitmapFactory.decodeResource(mContext.getResources(), R.drawable
				.courseware_published);
	}

	public static Bitmap getunPublishedIcon(Context mContext) {
		return BitmapFactory.decodeResource(mContext.getResources(), R.drawable
				.courseware_unpublished);
	}
}
