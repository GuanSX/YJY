package com.yjy.im.model;

import android.content.*;
import android.graphics.Bitmap;
import android.renderscript.BaseObj;

import com.yjy.im.base.*;
import com.yjy.im.manager.*;
import com.yjy.im.utils.SdCardUtil;

import java.io.Serializable;


/*
*此处要继承BaseObject。SharedPreUtil以User对象写入本地磁盘，该对象必须是实现Serializable接口。
* 实现Serializable的基类为BaseObject。
*
 */

public class User extends BaseObject {
	public boolean isTeacher = false;
	public boolean isStudent = false;
	private String id = "";
	private String userName = "";
	private String password = "";
	private String realname = "";
	private ROLE Role = null;
	private LEVEL Level = null;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public ROLE getRole() {
		return Role;
	}

	public void setRole(String roleId, String roleName, String roleDesc, String users) {
		this.Role = new ROLE(roleId, roleName, roleDesc, users);
	}

	public LEVEL getLevel() {
		return Level;
	}

	public void setLevel(String levelId, String levelName, String levelDesc, String users) {
		this.Level = new LEVEL(levelId, levelName, levelDesc, users);
	}


	/**
	 * 获取master jid
	 * @return
	 */
//	public   String getMasterJid(){
//		return userName+"@"+ConnectionUtils.getConnection().getServiceName();
//	}
//	
//	public   String getMasterJid(String userName){
//		return userName+"@"+ConnectionUtils.getConnection().getServiceName();
//	}
//	

	/**
	 * 获取master头像
	 *
	 * @param context
	 * @return
	 */
	public Bitmap getMasterAvater(Context context) {
//		String masterAvaterPath = SdCardUtil.getAvaterPath(getUserName());
//		SharedPreferences masterInfo = context.getSharedPreferences(Constant.USER_INFO, 0);
//		if (masterAvaterPath.equals(masterInfo.getString(Constant.AVATER, ""))) {
//			return AvaterManager.getDiskBitmap(masterAvaterPath);
//		}
		return null;
	}


}
