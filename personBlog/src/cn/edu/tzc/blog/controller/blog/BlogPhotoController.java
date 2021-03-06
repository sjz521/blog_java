package cn.edu.tzc.blog.controller.blog;

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
import cn.edu.tzc.blog.domain.Type;
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.PhotoService;
import cn.edu.tzc.blog.service.TypeService;
import cn.edu.tzc.blog.service.UserService;
import cn.edu.tzc.blog.service.exception.PhotoException;
import cn.edu.tzc.blog.service.exception.UserException;

@WebServlet("/blog/photo")
public class BlogPhotoController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		PhotoService service = new PhotoService();
		UserService userService = new UserService();
		
		int id = userService.findAdminId();
		
		User user = (User) request.getSession().getAttribute("user");
		/*
		String title = "登录";
		if(user != null) {
			title = user.getName()+"/退出登录";
		}else {
			
			user = userService.findUserById(id);
			request.setAttribute("user", user);
		}
		request.setAttribute("title", title);*/
		if(user == null) {
			user = userService.findUserById(id);
			request.setAttribute("user", user);
		}
		
		int pageIndex = 0;
		String str = request.getParameter("pageIndex");
		if(""!=str && str != null) {
			pageIndex = Integer.parseInt(str);
		}
		int pageSize = 6;
		
		Page<PhotoInfo> page = service.findPhotoWithPage(pageIndex, pageSize, id);
		request.setAttribute("page", page);
		request.setAttribute("isPhoto", true);
		
		request.getRequestDispatcher("../view/blog/photo_show.jsp").forward(request, response);
		
	}
}
