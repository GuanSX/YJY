

package com.yjy.im.comm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.Roster.SubscriptionMode;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.GroupChatInvitation;
import org.jivesoftware.smackx.PrivateDataManager;
import org.jivesoftware.smackx.bytestreams.ibb.provider.CloseIQProvider;
import org.jivesoftware.smackx.bytestreams.ibb.provider.DataPacketProvider;
import org.jivesoftware.smackx.bytestreams.ibb.provider.OpenIQProvider;
import org.jivesoftware.smackx.bytestreams.socks5.provider.BytestreamsProvider;
import org.jivesoftware.smackx.packet.ChatStateExtension;
import org.jivesoftware.smackx.packet.LastActivity;
import org.jivesoftware.smackx.packet.OfflineMessageInfo;
import org.jivesoftware.smackx.packet.OfflineMessageRequest;
import org.jivesoftware.smackx.packet.SharedGroupsInfo;
import org.jivesoftware.smackx.provider.DataFormProvider;
import org.jivesoftware.smackx.provider.DelayInformationProvider;
import org.jivesoftware.smackx.provider.DiscoverInfoProvider;
import org.jivesoftware.smackx.provider.DiscoverItemsProvider;
import org.jivesoftware.smackx.provider.MUCAdminProvider;
import org.jivesoftware.smackx.provider.MUCOwnerProvider;
import org.jivesoftware.smackx.provider.MUCUserProvider;
import org.jivesoftware.smackx.provider.MessageEventProvider;
import org.jivesoftware.smackx.provider.MultipleAddressesProvider;
import org.jivesoftware.smackx.provider.RosterExchangeProvider;
import org.jivesoftware.smackx.provider.StreamInitiationProvider;
import org.jivesoftware.smackx.provider.VCardProvider;
import org.jivesoftware.smackx.provider.XHTMLExtensionProvider;
import org.jivesoftware.smackx.search.UserSearch;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.yjy.im.base.Constant;

import com.yjy.im.model.*;
import com.yjy.im.utils.*;

public class ConnectionUtils {
	private static XMPPConnection connection;

	//public static String host = "10.0.2.2";//app1.max-tech.com.cn
	public static String host = "121.199.23.184";//app1.max-tech.com.cn
	private static int port = 5222;
	private static ConnectionConfiguration connectionConfig;
	
	public ConnectionUtils(){
		
	}
	// init
//	static {
//		Connection.DEBUG_ENABLED = false;
//
//		ProviderManager pm = ProviderManager.getInstance();
//		configure(pm);
//
//		SmackConfiguration.setKeepAliveInterval(60000 * 5); // 5 mins
//		SmackConfiguration.setPacketReplyTimeout(10000); // 10 secs
//		SmackConfiguration.setLocalSocks5ProxyEnabled(false);
//		Roster.setDefaultSubscriptionMode(SubscriptionMode.manual);
////gmail.com
//		connectionConfig = new ConnectionConfiguration(host, port);
//		connectionConfig.setTruststorePath("/system/etc/security/cacerts.bks");
//		connectionConfig.setTruststorePassword("changeit");
//		connectionConfig.setTruststoreType("bks");
//		// 允许自动连接
//		connectionConfig.setReconnectionAllowed(true);
//		// 允许登陆成功后更新在线状态
//		connectionConfig.setSendPresence(true);
//		//收到邀请后，管理邀请是否接受
//		Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
//	}
//
//	public static XMPPConnection getRegConnection() {
//		return new XMPPConnection(connectionConfig);
//	}
//
//	public static XMPPConnection getConnection() {
//
//		if (ConnectionUtils.connection == null) {
//			ConnectionUtils.connection = new XMPPConnection(connectionConfig);
//		}
//
//		if (!ConnectionUtils.connection.isConnected()) {
//			try {
//				ConnectionUtils.connection.connect();
//			} catch (XMPPException e) {
//				e.printStackTrace();
//			}
//		}
//		return ConnectionUtils.connection;
//	}
//	
//	public static void setIsAuthenticated(){
//
//    	if (!getConnection().isAuthenticated()) {
//			try {
//				getConnection().login(User.getUserName(), User.getPassword());
//			} catch (XMPPException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} 
//	}
//	
//	/**
//	 * 登录是输入错误用户名或密码时，需要从新创建xmppconnection，否则调用connect时会采用之前错误的用户名、密码
//	 * @return
//	 */
//	public static XMPPConnection getLoginConnection() {
//		
//		ConnectionUtils.connection = new XMPPConnection(connectionConfig);
//
//		if (!ConnectionUtils.connection.isConnected()) {
//			try {
//				ConnectionUtils.connection.connect();
//			} catch (XMPPException e) {
//				e.printStackTrace();
//			}
//		}
//		return ConnectionUtils.connection;
//	}
//
//	// 获取HttpClient
//	public static HttpClient getHttpClient() {
//		HttpClient client = new DefaultHttpClient();
//		return client;
//	}
//
//	public static String getStringTime() {
//		Calendar c = Calendar.getInstance();
//		c.setTime(new Date());
//		return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
//				+ c.get(Calendar.DAY_OF_MONTH) + "-" + c.get(Calendar.HOUR_OF_DAY) + ":"
//				+ c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
//	}
//
//	public static void configure(ProviderManager pm) {
//
//		// Private Data Storage
//		pm.addIQProvider("query", "jabber:iq:private",
//				new PrivateDataManager.PrivateDataIQProvider());
//
//		// Time
//		try {
//			pm.addIQProvider("query", "jabber:iq:time",
//					Class.forName("org.jivesoftware.smackx.packet.Time"));
//		} catch (ClassNotFoundException e) {
//		}
//
//		// XHTML
//		pm.addExtensionProvider("html", "http://jabber.org/protocol/xhtml-im",
//				new XHTMLExtensionProvider());
//
//		// Roster Exchange
//		pm.addExtensionProvider("x", "jabber:x:roster",
//				new RosterExchangeProvider());
//		// Message Events
//		pm.addExtensionProvider("x", "jabber:x:event",
//				new MessageEventProvider());
//		// Chat State
//		pm.addExtensionProvider("active",
//				"http://jabber.org/protocol/chatstates",
//				new ChatStateExtension.Provider());
//		pm.addExtensionProvider("composing",
//				"http://jabber.org/protocol/chatstates",
//				new ChatStateExtension.Provider());
//		pm.addExtensionProvider("paused",
//				"http://jabber.org/protocol/chatstates",
//				new ChatStateExtension.Provider());
//		pm.addExtensionProvider("inactive",
//				"http://jabber.org/protocol/chatstates",
//				new ChatStateExtension.Provider());
//		pm.addExtensionProvider("gone",
//				"http://jabber.org/protocol/chatstates",
//				new ChatStateExtension.Provider());
//
//		// FileTransfer
//		pm.addIQProvider("si", "http://jabber.org/protocol/si",
//				new StreamInitiationProvider());
//		pm.addIQProvider("query", "http://jabber.org/protocol/bytestreams",
//				new BytestreamsProvider());
//		pm.addIQProvider("open", "http://jabber.org/protocol/ibb",
//				new OpenIQProvider());
//		pm.addIQProvider("close", "http://jabber.org/protocol/ibb",
//				new CloseIQProvider());
//		pm.addExtensionProvider("data", "http://jabber.org/protocol/ibb",
//				new DataPacketProvider());
//
//		// Group Chat Invitations
//		pm.addExtensionProvider("x", "jabber:x:conference",
//				new GroupChatInvitation.Provider());
//		// Service Discovery # Items
//		pm.addIQProvider("query", "http://jabber.org/protocol/disco#items",
//				new DiscoverItemsProvider());
//		// Service Discovery # Info
//		pm.addIQProvider("query", "http://jabber.org/protocol/disco#info",
//				new DiscoverInfoProvider());
//		// Data Forms
//		pm.addExtensionProvider("x", "jabber:x:data", new DataFormProvider());
//		// MUC User
//		pm.addExtensionProvider("x", "http://jabber.org/protocol/muc#user",
//				new MUCUserProvider());
//		// MUC Admin
//		pm.addIQProvider("query", "http://jabber.org/protocol/muc#admin",
//				new MUCAdminProvider());
//		// MUC Owner
//		pm.addIQProvider("query", "http://jabber.org/protocol/muc#owner",
//				new MUCOwnerProvider());
//		// Delayed Delivery
//		pm.addExtensionProvider("x", "jabber:x:delay",
//				new DelayInformationProvider());
//		// Version
//		try {
//			pm.addIQProvider("query", "jabber:iq:version",
//					Class.forName("org.jivesoftware.smackx.packet.Version"));
//		} catch (ClassNotFoundException e) {
//		}
//		// VCard
//		pm.addIQProvider("vCard", "vcard-temp", new VCardProvider());
//		// Offline Message Requests
//		pm.addIQProvider("offline", "http://jabber.org/protocol/offline",
//				new OfflineMessageRequest.Provider());
//		// Offline Message Indicator
//		pm.addExtensionProvider("offline",
//				"http://jabber.org/protocol/offline",
//				new OfflineMessageInfo.Provider());
//		// Last Activity
//		pm.addIQProvider("query", "jabber:iq:last", new LastActivity.Provider());
//		// User Search
//		pm.addIQProvider("query", "jabber:iq:search", new UserSearch.Provider());
//		// SharedGroupsInfo
//		pm.addIQProvider("sharedgroup",
//				"http://www.jivesoftware.org/protocol/sharedgroup",
//				new SharedGroupsInfo.Provider());
//		// JEP-33: Extended Stanza Addressing
//		pm.addExtensionProvider("addresses",
//				"http://jabber.org/protocol/address",
//				new MultipleAddressesProvider());
//
//	}
//	
//	//我的空间：tomcat6.0
//	
//	/**
//	 * 读取我的空间文件列表
//	 */
//	public static List<Courseware> readServerFiles(String filePath){
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("filePath", filePath));
//		HttpPost httpRequest = new HttpPost(Constant.READ_FILES_URL);
//		List<Courseware> files = null;
//		try{
//			HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");
//			httpRequest.setEntity(httpentity);
//			HttpClient httpClient = new DefaultHttpClient();
//			HttpResponse httpResponse = httpClient.execute(httpRequest);
//			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//				StringBuilder builder = new StringBuilder(); 
//				BufferedReader bufferedReader = new BufferedReader( 
//						new InputStreamReader(httpResponse.getEntity().getContent()));
//				for (String s = bufferedReader.readLine(); s != null; s = bufferedReader 
//                        .readLine()) { 
//                    builder.append(s); 
//                }
//				/** 
//                 * 这里需要分析服务器回传的json格式数据，
//                 */ 
//				String strFiles = builder.toString();
//                JSONObject jsonObject = new JSONObject(strFiles); 
//                JSONArray jsonArray = jsonObject.getJSONArray("files");
//                files = new ArrayList<Courseware>();
//                for(int i = 0; i < jsonArray.length(); i ++){ 
//                    JSONObject jObject = jsonArray.getJSONObject(i);
//                    Courseware file = new Courseware();
//                    file.setDocId(jObject.getInt("id"));
//                    /*
//                	 * 文件名有可能为中文，故需要进行编码防止中文乱码
//                	 * */
//                    file.setOriginalName(CoderUtils.decoder(jObject.getString("name")));
//                    file.setSize(jObject.getLong("size"));
//                    file.setUploadTime(jObject.getString("creatTime"));
//                    file.setType(jObject.getInt("type"));
//                    file.setPath(CoderUtils.decoder(jObject.getString("path")));
//                    files.add(file); 
//                }
//			}
//		}
//		catch(Exception e){
//			e.printStackTrace(); 
//		}
//		return files;
//	}
//	
//	/**
//	 * 读取我的空间文件列表（带有类型选择）
//	 */
//	public static List<Courseware> readTypeFiles(String filePath, String type){
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("filePath", filePath));
//		params.add(new BasicNameValuePair("type", type));
//		HttpPost httpRequest = new HttpPost(Constant.TYPE_FILES_URL);
//		List<Courseware> files = null;
//		try{
//			HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");
//			httpRequest.setEntity(httpentity);
//			HttpClient httpClient = new DefaultHttpClient();
//			HttpResponse httpResponse = httpClient.execute(httpRequest);
//			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//				StringBuilder builder = new StringBuilder(); 
//				BufferedReader bufferedReader = new BufferedReader( 
//						new InputStreamReader(httpResponse.getEntity().getContent()));
//				for (String s = bufferedReader.readLine(); s != null; s = bufferedReader 
//                        .readLine()) { 
//                    builder.append(s); 
//                }
//				/** 
//                 * 这里需要分析服务器回传的json格式数据
//                 */ 
//				String strFiles = builder.toString();
//                JSONObject jsonObject = new JSONObject(strFiles); 
//                JSONArray jsonArray = jsonObject.getJSONArray("files");
//                files = new ArrayList<Courseware>();
//                for(int i=0;i<jsonArray.length();i++){ 
//                    JSONObject jObject = jsonArray.getJSONObject(i);
//                    Courseware file = new Courseware();
//                    file.setDocId(jObject.getInt("id"));
//                    /*
//                	 * 文件名有可能为中文，故需要进行编码防止中文乱码
//                	 * */
//                    file.setOriginalName(CoderUtils.decoder(jObject.getString("name")));
//                    file.setSize(jObject.getLong("size"));
//                    file.setUploadTime(jObject.getString("creatTime"));
//                    file.setType(jObject.getInt("type"));
//                    file.setPath(jObject.getString("path"));
//                    files.add(file); 
//                }
//			}
//		}
//		catch(Exception e){
//			e.printStackTrace(); 
//		}
//		return files;
//	}
//	
//	/**
//	 * 新建空间文件夹
//	 */
//	public static Boolean newFloader(String filePath){
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("filePath", filePath));
//		params.add(new BasicNameValuePair("create", "true"));
//		return httpPost(params, Constant.OPERATE_FILES_URL);
//	}
//	
//	/**
//	 * 删除空间文件
//	 */
//	public static Boolean deleteFile(String filePath){
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("filePath", filePath));
//		params.add(new BasicNameValuePair("delete", "true"));
//		return httpPost(params, Constant.OPERATE_FILES_URL);
//	}
//	
//
//	/**
//	 * httpPost连接
//	 */
//	public static Boolean httpPost(List<NameValuePair> params, String httpUrl){
//		HttpPost httpRequest = new HttpPost(httpUrl);
//		try{
//			HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");
//			httpRequest.setEntity(httpentity);
//			HttpClient httpClient = new DefaultHttpClient();
//			HttpResponse httpResponse = httpClient.execute(httpRequest);
//			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//				return true;
//			}
//		}
//		catch(Exception e){
//			e.printStackTrace(); 
//		}
//		return false;
//	}
//	
//	/**
//	 * 分享文件
//	 */
//	public static Boolean shareFile(String name, String username, String sharers, String path){
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("username", username));
//		params.add(new BasicNameValuePair("sharers", sharers));
//		params.add(new BasicNameValuePair("name", name));
//		params.add(new BasicNameValuePair("path", path));
//		return httpPost(params, Constant.SHARE_FILES_URL);
//	}
//	
//	/**
//	 * 复制文件
//	 */
//	public static Boolean copyFile(String sourceFilePath, String targetFilePath){
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("sourceFilePath", sourceFilePath));
//		params.add(new BasicNameValuePair("targetFilePath", targetFilePath));
//		return httpPost(params, Constant.COPY_FILE_URL);
//	}
//	
//	/**
//	 * 读取圈友分享的文件列表
//	 */
//	public static List<Courseware> readShareFiles(String username, String sharer){
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("username", username));
//		params.add(new BasicNameValuePair("sharer", sharer));
//		HttpPost httpRequest = new HttpPost(Constant.READ_SHARE_FILES_URL);
//		List<Courseware> files = null;
//		try{
//			HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");
//			httpRequest.setEntity(httpentity);
//			HttpClient httpClient = new DefaultHttpClient();
//			HttpResponse httpResponse = httpClient.execute(httpRequest);
//			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//				StringBuilder builder = new StringBuilder(); 
//				BufferedReader bufferedReader = new BufferedReader( 
//						new InputStreamReader(httpResponse.getEntity().getContent()));
//				for (String s = bufferedReader.readLine(); s != null; s = bufferedReader 
//                        .readLine()) { 
//                    builder.append(s); 
//                }
//				/** 
//                 * 这里需要分析服务器回传的json格式数据，
//                 */ 
//				String strFiles = builder.toString();
//                JSONObject jsonObject = new JSONObject(strFiles); 
//                JSONArray jsonArray = jsonObject.getJSONArray("files");
//                files = new ArrayList<Courseware>();
//                for(int i=0;i<jsonArray.length();i++){ 
//                    JSONObject jObject = jsonArray.getJSONObject(i);
//                    Courseware file = new Courseware();
//                    file.setDocId(jObject.getInt("id"));
//                    file.setOriginalName(CoderUtils.decoder(jObject.getString("name")));
//                    file.setSize(jObject.getLong("size"));
//                    file.setUploadTime(jObject.getString("creatTime"));
//                    file.setType(jObject.getInt("type"));
//                    file.setPath(jObject.getString("path"));
//                    //file.setUsername(jObject.getString("username"));
//                    files.add(file); 
//                }
//			}
//		}
//		catch(Exception e){
//			e.printStackTrace(); 
//		}
//		return files;
//	}
//	
//	/**
//	 * 计算用户空间容量
//	 * @param username
//	 * @return
//	 */
//	public static String calSpaceLen(String username){
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("username", username));
//		HttpPost httpRequest = new HttpPost(Constant.SPACE_LEN_URL);
//		String length = null;
//		try{
//			HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");
//			httpRequest.setEntity(httpentity);
//			HttpClient httpClient = new DefaultHttpClient();
//			HttpResponse httpResponse = httpClient.execute(httpRequest);
//			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//				StringBuilder builder = new StringBuilder(); 
//				BufferedReader bufferedReader = new BufferedReader( 
//						new InputStreamReader(httpResponse.getEntity().getContent()));
//				for (String s = bufferedReader.readLine(); s != null; s = bufferedReader 
//                        .readLine()) { 
//                    builder.append(s); 
//                }
//				length = builder.toString();
//			}
//		}
//		catch(Exception e){
//			e.printStackTrace(); 
//		}
//		return length;
//	}
}
