package com.yjy.im.utils;

public class WebLinksUtil {

	// release
	public static String mainHost = "http://i-test.com.cn";

	public static String login = mainHost + "/yjy/user/login";// 登陆

	public static String register = mainHost + "/yjy/user/register";// 注册

	public static String settingcard = mainHost + "/yjy/user/updateProfile";// 修改个人信息

	
	public static final String UPLOADFILE_URL = mainHost +"/yjy/file/upload";//上传文件
	
	public static final String GETCOURSELIST= mainHost +"/yjy/file/list";//获取文件列表

	public static final String UPDATECOURSEWARE=mainHost+"/yjy/file/updatePublish";//管理我的课件

	public  static final String DELETECOURSEWARE=mainHost+"/yjy/file/delete";
}