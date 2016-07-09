package com.yjy.im.utils;

/**
 * String 工具类
 */;

public class MyStringUtil {
	//param jid
	//根据jid截取到用户的name
	public static String chatFileName(String jid){
		String name = null;
		name = jid.substring(0, jid.indexOf("@"));		
		return name;
	}
	
	//根据time获取符合规则的time形式  2012-12-21-5:4:38
	public static String getFomatTime(String time){
		String localTime = null, localDate = null;
		localTime = time.substring(time.indexOf("-")+1, time.lastIndexOf("-"));
		localDate = localTime.substring(0, localTime.indexOf("-")) +"月"
					 + localTime.substring(localTime.indexOf("-")+1) + "日";
		String h = time.substring(time.lastIndexOf("-")+1, time.indexOf(":"));
		if(h.length() == 1)
			h = "0" + h;
		String m = time.substring(time.indexOf(":")+1, time.lastIndexOf(":"));
		if(m.length() == 1)
			m = "0"+m;
		localDate += h + ":" + m;
		return localDate;
	}
	
}
