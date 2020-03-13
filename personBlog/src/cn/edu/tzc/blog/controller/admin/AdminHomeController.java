package cn.edu.tzc.blog.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.tzc.blog.domain.ArticleInfo;
import cn.edu.tzc.blog.domain.PhotoInfo;
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.AdminService;
import cn.edu.tzc.blog.service.ArticleService;
import cn.edu.tzc.blog.service.PhotoService;
import cn.edu.tzc.blog.service.UserService;

@WebServlet("/admin/home")
public class AdminHomeController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			request.setAttribute("msg", "未登录");
			request.getRequestDispatcher("/view/admin/login.jsp").forward(request, response);
			return;
		}
		AdminService adminService = new AdminService();
		boolean isAdmin = adminService.isAdmin(user);
		if(!isAdmin) {
			request.setAttribute("msg", "不是管理员，不能登录！！！");
			request.getRequestDispatcher("/view/admin/login.jsp").forward(request, response);
			return;
		}
		
		ArticleService articleService = new ArticleService();
		PhotoService photoService = new PhotoService();
		List<ArticleInfo> articles = articleService.get5Article(user.getId());
		List<PhotoInfo> photos = photoService.get5Photo(user.getId());
		request.setAttribute("articles", articles);
		request.setAttribute("photos", photos);
		
		request.setAttribute("user", user);
		request.setAttribute("homeClass", "class=\"active\"");
		request.getRequestDispatcher("/view/admin/admin_home.jsp").forward(request, response);
	}
}
