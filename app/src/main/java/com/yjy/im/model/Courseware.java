package com.yjy.im.model;

import java.io.Serializable;

/**
 * 文件类
 */
public class Courseware implements Serializable{
	
	private String docId="";
	private String originalName="";
	private String saveName="";
	private String savePath="";
	private String downPath="";
	private int type;    //是否为文件： 1是 0否
	private String publishStatus="";
	private String needPay="";
	private String money="";
	private Long size;
	private String uploadTime="";

	private User user;
	private LEVEL level;
	
	public Courseware(){
		
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDownPath() {
		return downPath;
	}

	public void setDownPath(String downPath) {
		this.downPath = downPath;
	}

	public LEVEL getLevel() {
		return level;
	}

	public void setLevel(LEVEL level) {
		this.level = level;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getNeedPay() {
		return needPay;
	}

	public void setNeedPay(String needPay) {
		this.needPay = needPay;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isPublished()
	{
		if(this.publishStatus.equals("published"))
		{
			return true;
		}else
		{
			return false;
		}
	}

	public String getFileType() {
		return originalName.substring(originalName.lastIndexOf(".") + 1,
				originalName.length());
	}
}
