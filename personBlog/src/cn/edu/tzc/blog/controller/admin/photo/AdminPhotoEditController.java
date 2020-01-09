package cn.edu.tzc.blog.controller.admin.photo;

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
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import cn.edu.tzc.blog.domain.Photo;
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.PhotoService;

/**
 * Servlet implementation class AdminPhotoEditController
 */
@WebServlet("/admin/photo/edit")
public class AdminPhotoEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPhotoEditController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null) {
			request.setAttribute("msg", "未登录");
			request.getRequestDispatcher("/view/admin/login.jsp").forward(request, response);
			return;
		}
		
		PhotoService photoService = new PhotoService();
		String ids = request.getParameter("id");
		if("" != ids && ids != null) {
			int id = Integer.parseInt(ids);
			Photo photo = photoService.GetPhotoById(id);
			request.setAttribute("photo", photo);
		}
		
		request.setAttribute("user", user);
		request.setAttribute("photoClass", "active");
		request.getRequestDispatcher("/view/admin/photo/photo_edit.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		PhotoService service =new PhotoService();
		Photo photo = new Photo();
		
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
						
			List<FileItem> list = upload.parseRequest(new ServletRequestContext(request));
			int uId = Integer.parseInt(list.get(0).getString("utf-8"));//获得uId
			FileItem image = list.get(2);//上传的图片
			String fileName = list.get(3).getString("utf-8");
			
			String path = this.getServletContext().getRealPath("/public/images/photos");
			String projectName = request.getServletContext().getContextPath().replaceAll("/", "");
			int beginIndex = path.indexOf(projectName);
			int endIndex = path.lastIndexOf(projectName);
			String filePath = path.substring(0, beginIndex+projectName.length()+1)+projectName+"/WebContent//"+path.substring(endIndex+projectName.length()+1);
			
			service.uploadFile(image, path,fileName);
			boolean fileResult =service.uploadFile(image, filePath, fileName);
			if(!fileResult) {
				String url = request.getContextPath()+"/admin/photo/edit";
				pw.println("<html><body><script language='javascript'>alert('文件上传失败');window.location.href='"+url+"';</script></body></html>");
				pw.close();
				return;
			}
			photo.setName(fileName);
			photo.setuId(uId);
			service.AddPhoto(photo);
			
		} catch (Exception e) {
			// TODO: handle exception
			/*response.setContentType("text/html;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write("<script language='javascript'>alert('"+e.getMessage()+"')</script?");
			return;*/
			e.printStackTrace();
		}
		String url = request.getContextPath()+"/admin/photo";
		pw.println("<html><body><script language='javascript'>alert('图片添加成功');window.location.href='"+url+"';</script></body></html>");
		pw.close();
	}

}
