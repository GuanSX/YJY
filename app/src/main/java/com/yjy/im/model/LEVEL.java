package com.yjy.im.model;

import com.yjy.im.base.BaseObject;

/**
 * Created by Guan on 2016/6/23.
 */
public class LEVEL extends BaseObject
{
	private String levelId="";
	private String levelName="";
	private String levelDesc="";
	private String users="";

	public LEVEL()
	{

	}
	public LEVEL(String levelId,String levelName,String levelDesc,String users)
	{
		this.levelId=levelId;
		this.levelName=levelName;
		this.levelDesc=levelDesc;
		this.users=users;
	}

	public String getLevelDesc() {
		return levelDesc;
	}

	public void setLevelDesc(String levelDesc) {
		this.levelDesc = levelDesc;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}
}
