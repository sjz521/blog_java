package cn.edu.tzc.blog.controller.admin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.w3c.dom.ls.LSInput;

import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.UserService;
import cn.edu.tzc.blog.util.MD5Util;

/**
 * Servlet implementation class AdminPersonDataController
 */
@WebServlet("/admin/person")
public class AdminPersonDataController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = (User) request.getSession().getAttribute("user");
		/*if(user == null) {
			request.setAttribute("msg", "未登录");
			request.getRequestDispatcher("/view/admin/login.jsp").forward(request, response);
			return;
		}*/
		
		request.setAttribute("user", user);
		request.setAttribute("personClass", "class=\"active\"");
		
		String method = request.getParameter("method");
		if("password".equals(method)) {
			request.getRequestDispatcher("/view/admin/person_password.jsp").forward(request, response);
		}
		else {
			request.getRequestDispatcher("/view/admin/person_message.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		UserService service = new UserService();
		User user = new User();
		
		String method = request.getParameter("method");
		if("password".equals(method)) {
			//1.获得邮箱和密码
			String email = request.getParameter("email");
			String password = request.getParameter("newPassword");
			//2.更新密码
			user = service.getUserByEmail(email);
			user.setPassword(password);
			String message = service.updateUser(user);
			request.setAttribute("message", message);
			
		}
		else {
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("utf-8");
				List<FileItem> list = upload.parseRequest(new ServletRequestContext(request));
				int id = Integer.parseInt(list.get(0).getString("utf-8"));//用户id
				String email = list.get(1).getString("utf-8");
				String name = list.get(2).getString("utf-8");
				FileItem photo = list.get(3);
				String introduction = list.get(4).getString("utf-8");
				
				user = service.getUserByEmail(email);
				user.setName(name);
				user.setIntroduction(introduction);
				String photoName = photo.getName();
				if(photoName!=null && !"".equals(photoName)) {
					String path = this.getServletContext().getRealPath("/public/images/headimg");
					service.judgeFile(path);
					String projectName = request.getServletContext().getContextPath().replaceAll("/", "");
					String filePath = service.getFilePath(path, projectName);
					
					//String fileName = uploadPhoto(photo, filePath);
					String fileName = service.uploadFile(photo, path);
					service.uploadFile(photo, filePath,fileName);
					String oldPhoto = user.getPhoto();
					if(oldPhoto!=null && !"".equals(oldPhoto)) {
						service.delFile(path, oldPhoto);
						service.delFile(filePath, oldPhoto);
					}
					user.setPhoto(fileName);
				}
				String message = service.updateUser(user);
				request.setAttribute("message", message);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		if(user.getEmail()!=null && !"".equals(user.getEmail())) {
			request.getSession().setAttribute("user", user);
		}
		doGet(request, response);
	}
	
	private String uploadPhoto(FileItem fileItem,String path) throws Exception {
		
		String fileName = fileItem.getName();
		if("" == fileName || fileName == null) {
			return null;
		}
		if(fileName.lastIndexOf(".") == -1) {
			return null;
		}
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		String str = new Date().getTime()+suffix;
		byte[] b = fileName.getBytes("ISO-8859-1");
		fileName = new String(b,"utf-8");
		
		InputStream in = fileItem.getInputStream();
		String separator = System.getProperty("file.separator");
		FileOutputStream out = new FileOutputStream(path+separator+str);
		byte buffer[] = new byte[1024];
		int len = 0;
		while((len = in.read(buffer))>0) {
			out.write(buffer,0,len);
		}
		in.close();
		out.close();
		return str;
		
	}

}
