package com.yjy.im.alipay;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {
	
	
	public static final String DES="DES";
	public static SecretKey key=null;
	/**
	 * 只能英文、数字加密
	 * @author zsb
	 * @version Apr 1, 2015 3:09:33 PM
	 * @param value
	 * @return
	 */
//	public static String biEncrypt(String value){
//		return  biEncrypt(EncryptionConstant.ENCRYPT_KEY, value);
//	}
//	
//	
//	//解密  只能英文、数字加密
//	public static String biDecrypt(String value){
//		return  biDecrypt(EncryptionConstant.ENCRYPT_KEY, value);
//	}
//	
	
	/**
	 * 替换加密key
	 * @param encryptKey
	 * @param replacements
	 * @param splitRegex
	 * @return
	 * @author lwh
	 * @Date 2015-10-13 下午4:48:29
	 */
	public static String replaceEncryptKey(String encryptKey,String replacements,String splitRegex){
//		if(StringUtils.isNotBlank(encryptKey)&&StringUtils.isNotBlank(replacements)){
//			String oldkey = encryptKey;
//			String newkey = oldkey;
//			String[] replacement = replacements.split(",");
//			if(replacement!=null&&replacement.length>0){
//				for(String replaces:replacement){
//					if(StringUtils.isNotBlank(replaces)){
//						String target = replaces.split(splitRegex)[0];
//						String replace = replaces.split(splitRegex)[1];
//						newkey.replaceAll(target, replace);
//					}
//				}
//			}
//			return newkey;
//			
//		}
		return null;
	}
	
	/**
	 * biEncrypt(String aKey,String aWord) is a bi-directional encryption
	 * function for Encrypt a string with specific word. encrypted string can be
	 * once again decrypted to original with the same word using
	 * biDecrypt(String aKey,String aWord).
	 * 
	 * @param aKey
	 * @param aWord
	 */
	public static String biEncrypt(String aKey, String aWord) {
		final int INT_0 = 0;
		final int INT_1 = 1;
		final int INT_M1 = -1;
		
		int len = aKey.length();
		int ikey = INT_0, iPos = INT_0, temp = INT_0;
		StringBuffer buffer = new StringBuffer();

		for (int x = INT_1; x <= aWord.length(); x++) {
			iPos = x % len - len * (((x % len) == INT_0) ? INT_M1 : INT_0);
			ikey = aKey.charAt(iPos - INT_1);
			temp = aWord.charAt(x - INT_1) ^ ikey;
			buffer.append(toHex(temp));
		}
		return buffer.toString();
	}

	/**
	 * biDecrypt(String aKey, String aWord) can decrypted the string which is
	 * encrypted by biEncrypt(String aKey, String aWord) to its original
	 * 
	 * @param aKey
	 * @param aWord
	 */
	public static String biDecrypt(String aKey, String aWord) {
		final int INT_0 = 0;
		final int INT_1 = 1;
		final int INT_2 = 2;
		final int INT_16 = 16;
		final int INT_M1 = -1;
		
		int len = aKey.length();
		int ikey = INT_0, iPos = INT_0, iAsc = INT_0;
		StringBuffer buffer1 = new StringBuffer(), buffer2 = new StringBuffer();
		char cTmp;

		for (int x = 0; x < aWord.length() - INT_1; x += INT_2) {
			iAsc = toDec(aWord.charAt(x)) * INT_16 + toDec(aWord.charAt(x + INT_1));
			cTmp = (char) iAsc;
			buffer1.append(cTmp);
		}

		String sTmp = buffer1.toString();
		for (int x = INT_1; x <= sTmp.length(); x++) {
			iPos = x % len - len * (((x % len) == INT_0) ? INT_M1 : INT_0);
			ikey = aKey.charAt(iPos - INT_1);
			iAsc = sTmp.charAt(x - INT_1) ^ ikey;
			cTmp = (char) iAsc;
			buffer2.append(cTmp);
		}

		return buffer2.toString();
	}

	private static int toDec(char aHex) {
		final int INT_48 = 48;
		final int INT_55 = 55;
		final int INT_57 = 57;
		
		int iDec = 0;
		if (aHex <= INT_57) {
			iDec = aHex - INT_48;
		} else {
			iDec = java.lang.Character.toUpperCase(aHex) - INT_55;
		}
		return iDec;
	}

	private static String toHex(int aDec) {
		final int INT_16 = 16;
		final int INT_255 = 255;
		
		final String hexChars = "0123456789ABCDEF";
		if (aDec > INT_255) {
			return null;
		}
		int i = aDec % INT_16;
		int j = (aDec - i) / INT_16;
		StringBuffer buffer = new StringBuffer();
		buffer.append(hexChars.charAt(j)).append(hexChars.charAt(i));
		return buffer.toString();
	}
	
	/* ############################################################ */
	
//	/**
//	 * 加密String明文输入
//	 * @param strMing密文输出
//	 * @return
//	 */
//	public static String getEncString(String strMing) {
//		String strMi = "";
//		try {
//			return byte2hex(getEncCode(strMing.getBytes()));			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return strMi;
//	}
//	
//	/**
//	 * 解密 以String密文输入
//	 * @param strMi明文输出
//	 * @return
//	 */
//	public static String getDesString(String strMi) {
//		String strMing = "";
//		try {
//			return new String(getDesCode(hex2byte(strMi.getBytes())));
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		return strMing;
//	}
	
	/**新的替换
	 * 加密String明文输入
	 * @param strMing密文输出
	 * @return
	 */
	/**
	 *
	 * @param strMing
	 * @return
	 */
	public static String getEncString(String strMing) {
		//byte[] byteMi = null;
		//byte[] byteMing = null;
		String strMi = "";
		try {
			if(strMing==null){
				strMi=null;
			}else{
			    strMi=byte2hex(getEncCode(strMing.getBytes()));
			}
			// byteMing = strMing.getBytes("UTF8");
			// byteMi = this.getEncCode(byteMing);
			// strMi = new String( byteMi,"UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		} /*finally {
			byteMing = null;
			byteMi = null;
		}*/
		return strMi;
	}
	
	/**新的替换
	 * 加密String明文输入
	 * @param strMing密文输出
	 * @return
	 */
	public static String getEncString(String strMing,String key) {
		//byte[] byteMi = null;
		//byte[] byteMing = null;
		String strMi = "";
		try {
			if(strMing==null){
				strMi=null;
			}else{
			    strMi=byte2hex(getEncCode(strMing.getBytes(),key));
			}
			// byteMing = strMing.getBytes("UTF8");
			// byteMi = this.getEncCode(byteMing);
			// strMi = new String( byteMi,"UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		} /*finally {
			byteMing = null;
			byteMi = null;
		}*/
		return strMi;
	}
	
	/**新的替换
	 * 加密String明文输入
	 * @param strMing密文输出
	 * @return
	 */
	public static String getEncMobileString(String strMing,String key) {
		//byte[] byteMi = null;
		//byte[] byteMing = null;
		String strMi = "";
		try {
			if(strMing==null){
				strMi=null;
			}else{
			    strMi=byte2hex(getEncMobileCode(strMing.getBytes(),key));
			}
			// byteMing = strMing.getBytes("UTF8");
			// byteMi = this.getEncCode(byteMing);
			// strMi = new String( byteMi,"UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		} /*finally {
			byteMing = null;
			byteMi = null;
		}*/
		return strMi;
	}

	/**
	 * 解密 以String密文输入
	 * @param strMi明文输出
	 * @return
	 */
	public static String getDesString(String strMi) {
		//byte[] byteMing = null;
		//byte[] byteMi = null;
		String strMing = "";
		try {
			if(strMi==null){
				strMing=null;
			}else{
				strMing=new String(getDesCode(hex2byte(strMi.getBytes())));
			}
			// byteMing = this.getDesCode(byteMi);
			// strMing = new String(byteMing,"UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}/* finally {
			byteMing = null;
			byteMi = null;
		}*/
		return strMing;
	}
	
	/**
	 * 解密 以String密文输入
	 * @param strMi明文输出
	 * @return
	 */
	public static String getDesString(String strMi,String key) {
		//byte[] byteMing = null;
		//byte[] byteMi = null;
		String strMing = "";
		try {
			if(strMi==null){
				strMing=null;
			}else{
				strMing=new String(getDesCode(hex2byte(strMi.getBytes()),key));
			}
			// byteMing = this.getDesCode(byteMi);
			// strMing = new String(byteMing,"UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}/* finally {
			byteMing = null;
			byteMi = null;
		}*/
		return strMing;
	}
	
	
	/**
	 * 解密 以String密文输入
	 * @param strMi明文输出
	 * @return
	 */
	public static String getDesMobileString(String strMi,String key) {
		//byte[] byteMing = null;
		//byte[] byteMi = null;
		String strMing = "";
		try {
			if(strMi==null){
				strMing=null;
			}else{
				strMing=new String(getDesMobileCode(hex2byte(strMi.getBytes()),key));
			}
			// byteMing = this.getDesCode(byteMi);
			// strMing = new String(byteMing,"UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}/* finally {
			byteMing = null;
			byteMi = null;
		}*/
		return strMing;
	}
	
	/**
	 * 根据参数生成KEY
	 * @param strKey
	 */
	private static Key getKey(String paramKey) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance(DES);
			_generator.init(new SecureRandom(paramKey.getBytes()));
			key = _generator.generateKey();
			_generator = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}
	
	/**
	 * 根据参数生成KEY
	 * @param strKey
	 */
	private static Key getKey() {
		Key key=null;
//		try {
//			KeyGenerator _generator = KeyGenerator.getInstance(DES);
//			_generator.init(new SecureRandom(EncryptionConstant.ENCRYPT_KEY.getBytes()));
//			key = _generator.generateKey();
//			_generator = null;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return key;
	}
	
	/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 * @param byteD
	 * @return
	 */
	private static byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.DECRYPT_MODE, getKey());
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}
	
	/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 * @param byteD
	 * @return
	 */
	private static byte[] getDesCode(byte[] byteD,String paramKey) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.DECRYPT_MODE, getKey(paramKey));
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}
	
	/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 * @param byteD
	 * @return
	 */
	private static byte[] getDesMobileCode(byte[] byteD,String paramKey) {
		Cipher cipher;
		byte[] byteFina = null;
		byte[] iv =  {'m','e','c','o','m','1','2','3'};
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		try {
			SecretKeySpec decryptKey = new SecretKeySpec(paramKey.getBytes(), "DES");
			cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, decryptKey,zeroIv);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}
	
	/**
	 * 
	 * @author zsb
	 * @version Apr 1, 2015 3:03:52 PM
	 * @param b
	 * @return
	 */
	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}
	
	/**
	 * 二行制转字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) { // 一个字节的数，
		// 转成16进制字符串
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			// 整数转成十六进制表示
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase(); // 转成大写
	}

	
	
	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 * @param byteS
	 * @return
	 */
	private static byte[] getEncCode(byte[] byteS,String key) {
		byte[] byteFina = null;
		Cipher cipher;
		byte[] iv =  {'m','e','c','o','m','1','2','3'};
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		try {
			SecretKeySpec decryptKey = new SecretKeySpec(key.getBytes(), "DES");
			cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, decryptKey,zeroIv);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}
	
	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 * @param byteS
	 * @return
	 */
	private static byte[] getEncMobileCode(byte[] byteS,String key) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, getKey(key));
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}
	
	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 * @param byteS
	 * @return
	 */
	private static byte[] getEncCode(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, getKey());
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}
	
	
	public static void main(String[] args) {
		String str = "B2C订单";
		String key="ECOM";
		System.out.println("原文:" + str);
		String strEnc = getEncString(str, key);// 加密字符串,返回String的密文
		System.out.println("加密：" + strEnc);
		String strDes = getDesString(strEnc,key);// 把String 类型的密文解密
		System.out.println("解密：" + strDes);
	}
	
	
}
