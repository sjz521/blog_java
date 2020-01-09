package cn.edu.tzc.blog.controller.admin.photo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.PhotoService;

/**
 * Servlet implementation class AdminPhotoDeleteController
 */
@WebServlet("/admin/photo/delete")
public class AdminPhotoDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPhotoDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		
		User user = (User)request.getSession().getAttribute("user");
		PhotoService photoService = new PhotoService();
		String ids = request.getParameter("id");
		
		//处理文件路径
		String path = this.getServletContext().getRealPath("/WebContent/public/images/photos");
		String projectName = request.getServletContext().getContextPath().replaceAll("/", "");
		int beginIndex = path.indexOf(projectName);
		int endIndex = path.lastIndexOf(projectName);
		String filePath = path.substring(0, beginIndex+projectName.length()+1)+path.substring(endIndex);
		
		String message = "";
		try {
			if("" == ids || ids == null) {
				message = photoService.DeleteAllPhoto(user.getId(),path,filePath);
			}else {
				if(ids.contains(",")) {
					message = photoService.DeleteChecked(ids.split(","), path,filePath);
				}
				else {
					message = photoService.DeletePhoto(Integer.parseInt(ids),path,filePath);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			pw.println("<html><body><script language='javascript'>alert('"+e.getMessage()+"')</script></body></html>");
			pw.close();
			return;
		}
		String url = request.getContextPath()+"/admin/photo";
		pw.println("<html><body><script language='javascript'>alert('"+message+"');window.location.href='"+url+"';</script></body></html>");
		pw.close();
	}
	
}
