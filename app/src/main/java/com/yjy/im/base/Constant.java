package com.yjy.im.base;

import android.support.v4.app.Fragment;

import com.yjy.im.utils.SdCardUtil;

public class Constant {
	/**
	 * 软件名
	 */
	public static final String APKNAME = "yuejiaoyun";
	public static String APKDIR = SdCardUtil.getExternalStoragePath() + "/" + Constant.APKNAME;
	public static String DOWNLOADDIR = APKDIR + "/Download";

	public static String STRING_DOWNLOAD="DOWNLOAD";
	public static String STRING_UPLOAD="UPLOAD";

	// /**
	// * 花名册有删除的ACTION和KEY
	// */
	// public static final String ROSTER_DELETED =
	// "com.quanxiang.roster.deleted";
	// public static final String ROSTER_DELETED_KEY =
	// "com.quanxiang.roster.deleted.key";
	//
	// /**
	// * 花名册有更新的ACTION和KEY
	// */
	// public static final String ROSTER_UPDATED =
	// "com.quanxiang.roster.updated";
	// public static final String ROSTER_UPDATED_KEY =
	// "com.quanxiang.roster.updated.key";
	//
	// /**
	// * 花名册有增加的ACTION和KEY
	// */
	// public static final String ROSTER_ADDED =
	// "com.quanxiang.roster.added";
	// public static final String ROSTER_ADDED_KEY =
	// "com.quanxiang.roster.added.key";
	//
	// /**
	// * 花名册中成员状态有改变的ACTION和KEY
	// */
	// public static final String ROSTER_PRESENCE_CHANGED =
	// "com.quanxiang.presence.changed";
	// public static final String ROSTER_PRESENCE_CHANGED_KEY =
	// "com.quanxiang.presence.changed.key";
	//
	// /**
	// * 收到好友邀请请求
	// */
	// public static final String ROSTER_SUBSCRIPTE =
	// "com.quanxiang.subscribe";
	// public static final String ROSTER_SUB_FROM =
	// "com.quanxiang.subscribe.from";
	// public static final String ROSTER_SUBSCRIPTED =
	// "com.quanxiang.subscribed";
	// public static final String NEW_MESSAGE_ACTION =
	// "com.quanxiang.newmessage";
	//
	//
	// /**
	// * 邀请好友
	// */
	// public static final String SUBCRIBE = "com.quanxiang.subscribe";
	// public static final String SUBCRIBED = "com.quanxiang.subscribed";
	// public static final String UNSUBCRIBE = "com.quanxiang.unsubscribe";
	//
	// /**
	// * 新消息，通知chatlist
	// *
	// */
	// public static final String NEWCHATLIST = "com.quanxiang.newchatlist";
	//
	// /**
	// * 消息设置
	// */
	// public static final String ALERT_SHOCK = "com.quanxiang.shock";
	// public static final String ALERT_SOUND = "com.quanxiang.sound";
	// public static final String ALERT_ON = "com.quanxiang.on";
	// public static final String ALERT_OFF = "com.quanxiang.off";
	//
	public static final String AVATER = "com.yjy.avater";
	public static final String PHONE = "com.yjy.phone";
	public static final String ADDRESS = "com.yjy.address";
	public static final String SIGNATURE = "com.yjy.signature";
	/**
	 * is on ChatActivity
	 */
	public static final String IS_CHAT = "com.quanxiang.ischat";
	public static final String ON_CHAT = "com.quanxiang.onchat";
	public static final String OFF_CHAT = "com.quanxiang.offchat";
	/**
	 * 每个文件记录数
	 */
	public static final Integer CONTENTNUM = 40;
	/**
	 * “我的空间”连接地址
	 */
//	public static final String IP = "127.0.0.1";// "14.17.106.235";
//	public static final String UPLOAD_URL = "http://" + IP
//			+ ":8888/QuanXiangCloud/UploadCL";
//	public static final String DOWNLOAD_URL = "http://" + IP
//			+ ":8888/QuanXiangCloud/";
//	public static final String READ_FILES_URL = "http://" + IP
//			+ ":8888/QuanXiangCloud/ReadFilesCL";
//	public static final String OPERATE_FILES_URL = "http://" + IP
//			+ ":8888/QuanXiangCloud/OperateFilesCL";
//	public static final String TYPE_FILES_URL = "http://" + IP
//			+ ":8888/QuanXiangCloud/TypeFilesCL";
//	public static final String SHARE_FILES_URL = "http://" + IP
//			+ ":8888/QuanXiangCloud/ShareFileCL";
//	public static final String READ_SHARE_FILES_URL = "http://" + IP
//			+ ":8888/QuanXiangCloud/ReadShareFilesCL";
//	public static final String SPACE_LEN_URL = "http://" + IP
//			+ ":8888/QuanXiangCloud/SpaceLenCL";
//	public static final String COPY_FILE_URL = "http://" + IP
//			+ ":8888/QuanXiangCloud/CopyFileCL";
//	public static final String STR_DOWNLOAD_URL = "http://" + IP
//			+ ":8888/QuanXiangCloud/DownloadCL?";
	/**
	 * user info
	 */
	public static String USER_INFO = "com.yjy.masterinfo";
	public static String USERNAME = "com.yjy.username";
	public static String PASSWORD = "com.yjy.password";
	public static String REALNAME = "com.yjy.realname";
	public static String SEX = "com.yjy.sex";
	public static String ROLE = "com.yjy.role";
	public static String LEVEL = "com.yjy.level";
	public static String MASTER_ID = "com.yjy.master_id";
	public static boolean isTeacher = false;
	public static boolean isStudent = false;
	public static String ROLE_TEACHER = "TEACHER";
	public static String ROLE_STUDENT = "STUDENT";
	public static Fragment CURRENTFRAGMENT;

	public static String ID_TEACHER = "1";
	public static String ID_STUDENT = "2";

	public static String ID_XIAOXUE = "1";
	public static String ID_CHUZHONG = "2";
	public static String ID_GAOZHONG = "3";
	public static String ID_DAXUE = "4";
	public static String ID_CHENGJIAO = "5";
	public static String ID_ZHIJIAO = "6";

	public static String STRING_TEACHER = "TEACHER";
	public static String STRING_STUDENT = "STUDENT";

	public static String STRING_XIAOXUE = "小学";
	public static String STRING_CHUZHONG = "初中";
	public static String STRING_GAOZHONG = "高中";
	public static String STRING_DAXUE = "大学";
	public static String STRING_CHENGJIAO = "成教";
	public static String STRING_ZHIJIAO = "职教";

	public static String PARA_USERID="userId";
	public static String PARA_USERNAME = "username";
	public static String PARA_PASSWORD = "password";
	public static String PARA_ROLEID = "roleId";
	public static String PARA_LEVELID = "levelId";
	public static String PARA_REALNAME="realName";

	//public static String INFO_LOGINSUCC="login success";

	//==========用于fragment中使用startactivityForResult（）系列方法==========
	//requestCode
	public static int requestcode_uploadrecordfragment_download =0;
	public static int requestcode_uploadrecordfragment_refresh =1;
	public static int requestcode_uploadrecordfragment_edit =2;
	//resultCode
	public static int resultcode_uploadrecordfragment =0;

	//===========用于fragment中的fragmenresult（）方法识别不同popwindow的code==============
	public static int POPITEM_DOWNLOAD =200;
	public static int POPITEM_REFRESH =201;
	public static int POPITEM_EDIT =202;
	public static int POPITEM_DELETE=203;

	public static int POPITEM_PHOTO=203;
	public static int POPITEM_VIDEO=204;
	public static int POPITEM_DOC=205;
	public static int POPITEM_MUSIC=206;
	public static int POPITEM_APK=207;
	public static int POPITEM_FILE=208;


	public static int POPUPID_MORE =102;
	public static int POPUPID_UPLOAD=103;


//==============Bundle key===============
	public static String BUNDLE_POPUPWINDOW="POPUPWINDOW";
	public static String BUNDLE_VIEWS="VIEWS";

	public static int count=0;

	// public static final String UPLOAD_URL =
	// "http://10.0.2.2:8888/QuanXiangCloud/UploadCL";
	// public static final String DOWNLOAD_URL =
	// "http://10.0.2.2:8888/QuanXiangCloud/";
	// public static final String READ_FILES_URL =
	// "http://10.0.2.2:8888/QuanXiangCloud/ReadFilesCL";
	// public static final String OPERATE_FILES_URL =
	// "http://10.0.2.2:8888/QuanXiangCloud/OperateFilesCL";
	// public static final String TYPE_FILES_URL =
	// "http://10.0.2.2:8888/QuanXiangCloud/TypeFilesCL";
	// public static final String SHARE_FILES_URL =
	// "http://10.0.2.2:8888/QuanXiangCloud/ShareFileCL";
	// public static final String READ_SHARE_FILES_URL =
	// "http://10.0.2.2:8888/QuanXiangCloud/ReadShareFilesCL";
	// public static final String SPACE_LEN_URL =
	// "http://10.0.2.2:8888/QuanXiangCloud/SpaceLenCL";
	// public static final String COPY_FILE_URL =
	// "http://10.0.2.2:8888/QuanXiangCloud/CopyFileCL";
	// public static final String STR_DOWNLOAD_URL =
	// "http://127.0.0.1:8888/QuanXiangCloud/DownloadCL?";

}
