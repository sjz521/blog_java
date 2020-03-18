package cn.edu.tzc.blog.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tomcat.util.http.fileupload.FileItem;

public class FileUtil {
	
	/**
	 * 文件上传
	 * @param fileItem
	 * @param path
	 * @param fileName
	 * @return
	 */
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
	
	/**
	 * 文件上传
	 * @param fileItem
	 * @param path
	 * @return
	 */
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
	
	/**
	 * 删除文件
	 * @param path
	 * @param fileName
	 * @return
	 */
	public boolean delFile(String path,String fileName) {
		boolean result = false;
		String separator = System.getProperty("file.separator");
		File file = new File(path+separator+fileName);
		if(file.exists()) {
			result = file.delete();
		}else {
			result = true;
		}
		return result;
	}
	
	/**
	 * 读取文件内容
	 * @param path
	 * @return
	 */
	public String readFile(String path) {
		StringBuilder sb = new StringBuilder();
		try {
			InputStreamReader input = new InputStreamReader(new FileInputStream(new File(path)),"UTF-8");
			BufferedReader br = new BufferedReader(input);
			String content = null;
			while((content=br.readLine())!=null) {
				sb.append(System.lineSeparator()+content);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * 写入文件
	 * @param path
	 * @param fileName
	 * @param content
	 * @throws IOException
	 */
	public void writeFile(String path,String fileName,String content) {
		String filePath = path+File.separator+fileName;
		File file = new File(filePath);
		File fileParent = new File(file.getParent());
		if(!fileParent.exists()) {
			fileParent.mkdirs();
		}
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			/*FileWriter out = new FileWriter(path+File.separator+fileName);
			out.write(content);
			out.flush();
			out.close();*/
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据oldPath找到文件在项目里的地址
	 * @param oldPath .metadata里的地址
	 * @param projectName
	 * @return
	 */
	public String getFilePath(String oldPath,String projectName) {
		String newPath = "";
		int beginIndex = oldPath.indexOf(projectName);
		int endIndex = oldPath.lastIndexOf(projectName);
		
		newPath = oldPath.substring(0, beginIndex+projectName.length()+1)+projectName+File.separator+"WebContent"+File.separator+oldPath.substring(endIndex+projectName.length()+1);
		File filePath = new File(newPath);
		if(!filePath.exists()) {
			filePath.mkdirs();
		}
		return newPath;
	}
	
	/**
	 * 将文件以日期重命名
	 * @return
	 */
	public String renameFileByDate(String suffix) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		String fileName = df.format(new Date())+suffix;
		return fileName;
	}
	
	
	/**
	 * 验证路径是否存在，不存在的话，生成目录
	 * @param path
	 */
	public void judgeFile(String path) {
		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}
	}
}
