package com.yjy.im.model;

import com.yjy.im.base.BaseObject;

/**
 * Created by Guan on 2016/6/23.
 */
public class ROLE extends BaseObject
{
	private String roleId="";
	private String roleName="";
	private String roleDesc="";
	private String users="";

	public ROLE(String roleId,String roleName,String roleDesc,String users)
	{
		this.roleId=roleId;
		this.roleName=roleName;
		this.roleDesc=roleDesc;
		this.users=users;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
