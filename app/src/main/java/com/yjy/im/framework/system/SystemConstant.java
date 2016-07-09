package com.yjy.im.framework.system;

import android.app.Application;

public class SystemConstant extends Application{
	// 网络链接情况 0：网络连通 1：网络不通 注：这里指手机没有开启网络。如:没有数据，没有wifi等
	public static int connectionState;

	// 网络连接是否超时 0：网络畅通 1：网络超时 注：这里指手机有网络数据，但服务器连接超时等情况

	public static int connectionTimeOut;

	public static String session;// 链接SESSIONid

	public static String CPU_ABI;
}
