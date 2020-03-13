package cn.edu.tzc.blog.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import cn.edu.tzc.blog.domain.FileHelper;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ImageUploadServlet
 */
@MultipartConfig //标识servlet支持文件上传
@WebServlet("/imageUpload")
public class ImageUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * 实现图片上传，后端返回报文的json格式为：
		 * {
		 * 		success: 0|1,//0表上传失败；1表上传成功，必须为数字
		 * 		message: "提示信息"
		 * 		url: "图片地址" //上传成功时才返回，为图片存在的地址
		 * }
		 * */
		JSONObject res = new JSONObject();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utr-8");
		
		FileHelper helper = new FileHelper();
		String savePath = request.getServletContext().getRealPath("/upload");
		File pathFolder = new File(savePath);
		if(!pathFolder.exists()) {
			pathFolder.mkdirs();
		}
		
		String projectName = request.getServletContext().getContextPath().replaceAll("/", "");
		String publicPath = helper.getFilePath(savePath, projectName);
		
		Collection<Part> parts = request.getParts();//获取上传的文件集合
		if(parts!=null && parts.size()==1) {//上传单个文件
			Part part = request.getPart("editormd-image-file");//---1.通过表单file控件（<input type="file"/>）的名字直接获取Part对象
			String header = part.getHeader("content-disposition");//获取请求头
			String fileName = getFileName(header);
			String url = savePath+File.separator+fileName;
			part.write(url);//把文件写到指定路径
			
			res.put("success", 1);
			res.put("message", "上传成功");
			res.put("url", "http://localhost:8080/personBlog/imageShow?fileName="+fileName);
		}else {
			res.put("success", 0);
			res.put("message", "上传失败");
		}
		
		PrintWriter out = response.getWriter();
		out.println(res.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 根据请求头解析出文件名
	 * @param header
	 * @return
	 */
	public String getFileName(String header) {
		String[] tmp = header.split(";")[2].split("=");
		//获取文件名
		String fileName = tmp[1].substring(tmp[1].lastIndexOf("\\")+1).replace("\"", "");
		return fileName;
	}

}
