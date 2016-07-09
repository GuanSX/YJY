package com.yjy.im.utils;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guan on 2016/6/25.
 */
public class DoubleClickUtil {
	private static int DOUBLE_CLICK_TIME = 1000;
	private static Handler mHandler = new Handler();
	private static Runnable r;

	/**双击事件判断*/
	public static boolean isDoubleClick(Context mContext,ArrayList<Long> clicktimes) {
		if (clicktimes.size() == 2) {
			//已经完成了一次双击，list可以清空了
			if (clicktimes.get(clicktimes.size() - 1) - clicktimes.get(0) < DOUBLE_CLICK_TIME) {
				clicktimes.clear();
				Toast.makeText(mContext, "双击", Toast.LENGTH_SHORT).show();
				if (mHandler != null) {
					if (r != null) {
						//移除回调
						mHandler.removeCallbacks(r);
						r = null;
					}
				}
				return true;
			} else {
				//这种情况下，第一次点击的时间已经没有用处了，第二次就是“第一次”
				clicktimes.remove(0);
			}

		}

		r = new Runnable() {
			@Override
			public void run() {
				//此处可以添加提示
				//Toast.makeText(mContext, "单击", Toast.LENGTH_SHORT).show();
			}
		};
		//DOUBLE_CLICK_TIME时间后提示单击事件
		mHandler.postDelayed(r, DOUBLE_CLICK_TIME);
		return false;
	}

}
