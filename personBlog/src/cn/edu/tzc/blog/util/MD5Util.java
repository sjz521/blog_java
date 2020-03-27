package cn.edu.tzc.blog.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	
	public String getMD5String(String str) {
		if(null == str) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] strs = str.getBytes("utf-8");
			byte[] mdStrs = digest.digest(strs);
			
			for(int i=0;i<mdStrs.length;i++) {
				int val = ((int)mdStrs[i])&0xff;
				if(val<16) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(val));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
}
