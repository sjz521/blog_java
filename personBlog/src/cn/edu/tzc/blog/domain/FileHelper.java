package cn.edu.tzc.blog.domain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.tomcat.util.http.fileupload.FileItem;

public class FileHelper {
	
	public boolean uploadFile(FileItem fileItem,String path,String fileName) {
		boolean result = false;
		try {
			InputStream in = fileItem.getInputStream();
			String separator = System.getProperty("file.separator");
			FileOutputStream out = new FileOutputStream(path+separator+fileName);
			byte buffer[] = new byte[1024];
			int len = 0;
			while((len = in.read(buffer))>0) {
				out.write(buffer,0,len);
			}
			in.close();
			out.close();
			result = true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public String uploadFile(FileItem fileItem,String path) {
		String fileName = fileItem.getName();
		if("" == fileName || fileName == null) {
			return null;
		}
		if(fileName.lastIndexOf(".") == -1) {
			return null;
		}
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		String str = new Date().getTime()+suffix;
		try {
			byte[] b = str.getBytes("ISO-8859-1");
			fileName = new String(b,"utf-8");
			boolean result = uploadFile(fileItem, path, fileName);
			if (!result) {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		return str;
	}
	
	public boolean delFile(String path,String fileName) {
		boolean result = false;
		String separator = System.getProperty("file.separator");
		File file = new File(path+separator+fileName);
		if(file.exists()) {
			result = file.delete();
		}
		return result;
	}
}
