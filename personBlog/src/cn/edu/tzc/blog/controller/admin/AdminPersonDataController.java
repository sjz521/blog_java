package cn.edu.tzc.blog.controller.admin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
		if(user == null) {
			request.setAttribute("msg", "未登录");
			request.getRequestDispatcher("/view/admin/login.jsp").forward(request, response);
			return;
		}
		
		request.setAttribute("user", user);
		
		request.setAttribute("personClass", "class=\"active\"");
		request.getRequestDispatcher("/view/admin/person_message.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			List<FileItem> list = upload.parseRequest(new ServletRequestContext(request));
			int id = Integer.parseInt(list.get(0).getString("utf-8"));//用户id
			String email = list.get(1).getString("utf-8");
			String name = list.get(2).getString("utf-8");
			String oldPassword = list.get(3).getString("utf-8");
			String newPassword = list.get(4).getString("utf-8");
			String newPassword2 = list.get(5).getString("utf-8");
			FileItem photo = list.get(6);
			String introduction = list.get(7).getString("utf-8");
			
			UserService service = new UserService();
			User user = service.GetUserByEmail(email);
			user.setName(name);
			user.setIntroduction(introduction);
			if(""!=newPassword && newPassword!=null || ""!=newPassword2 && newPassword2!=null) {
				if("" == oldPassword || oldPassword==null) {
					request.setAttribute("message", "请输入原密码");
					doGet(request, response);
					return;
				}
				if(newPassword!=newPassword2) {
					request.setAttribute("message", "两次密码输入不一致！！！");
					doGet(request, response);
					return;
				}
			}
			if(""!=oldPassword && oldPassword!=null) {
				if(oldPassword!=user.getPassword()) {
					request.setAttribute("message", "输入的原密码与原密码不符，请确认后重新输入！");
					doGet(request, response);
					return;
				}
			}
			user.setPassword(newPassword);
			
			if(photo!=null) {
				String path = this.getServletContext().getRealPath("/WebContent/public/images/photos");
				String projectName = request.getServletContext().getContextPath().replaceAll("/", "");
				int beginIndex = path.indexOf(projectName);
				int endIndex = path.lastIndexOf(projectName);
				String filePath = path.substring(0, beginIndex+projectName.length()+1)+path.substring(endIndex);
				
				String fileName = uploadPhoto(photo, filePath);
				user.setPhoto(fileName);
			}
			service.updateUser(user);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
