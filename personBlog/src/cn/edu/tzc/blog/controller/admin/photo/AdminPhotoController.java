package cn.edu.tzc.blog.controller.admin.photo;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.tzc.blog.domain.Page;
import cn.edu.tzc.blog.domain.Photo;
import cn.edu.tzc.blog.domain.PhotoInfo;
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.PhotoService;
import cn.edu.tzc.blog.service.exception.PhotoException;

/**
 * Servlet implementation class AdminPhotoController
 */
@WebServlet("/admin/photo")
public class AdminPhotoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPhotoController() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		
		int pageIndex = 0;
		String str = request.getParameter("pageIndex");
		if("" != str && str != null) {
			pageIndex = Integer.parseInt(str);
		}
		
		PhotoService photoService = new PhotoService();
		int pageSize = 5;
		Page<PhotoInfo> page = photoService.findPhotoWithPage(pageIndex, pageSize, user.getId());
		
		request.setAttribute("user", user);
		request.setAttribute("page", page);
		request.setAttribute("photoClass", "active");
		request.getRequestDispatcher("/view/admin/photo/photo_list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
