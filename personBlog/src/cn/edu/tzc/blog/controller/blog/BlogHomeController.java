package cn.edu.tzc.blog.controller.blog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.tzc.blog.domain.Article;
import cn.edu.tzc.blog.domain.ArticleInfo;
import cn.edu.tzc.blog.domain.Page;
import cn.edu.tzc.blog.domain.Type;
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.ArticleService;
import cn.edu.tzc.blog.service.TypeService;
import cn.edu.tzc.blog.service.UserService;
import cn.edu.tzc.blog.service.exception.TypeException;
import cn.edu.tzc.blog.service.exception.UserException;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("/blog/home")
public class BlogHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArticleService articleService = new ArticleService();
		UserService userService = new UserService();
		
		boolean isType = false;
		int uid = userService.findAdminId();
		User user = (User) request.getSession().getAttribute("user");
		/*
		String title = "登录";
		if(user != null) {
			title = user.getName()+"/退出登录";
		}else {
			user = userService.findUserById(uid);
			request.setAttribute("user", user);
		}*/
		if(user == null) {
			user = userService.findUserById(uid);
			request.setAttribute("user", user);
		}
		
		int pageIndex = 0;
		String str = request.getParameter("pageIndex");
		if(""!=str && str != null) {
			pageIndex = Integer.parseInt(str);
		}
		int pageSize = 5;
		
		Page<ArticleInfo> page = null;
		String tIds = request.getParameter("tId");
		if(""!=tIds && tIds != null) {
			//显示某一分类下的所有文章
			int tId = Integer.parseInt(tIds);
			page = articleService.findArticleWithPageByTid(pageIndex, pageSize, uid, tId);
			request.setAttribute("tId", tId);
			isType = true;
			TypeService typeService = new TypeService();
			request.setAttribute("type", typeService.getTypeById(tId));
			
		}else {
			page = articleService.findArticlesWithPage(pageIndex, pageSize, uid);
		}
		
		request.setAttribute("page", page);
		//request.setAttribute("title", title);
		
		request.setAttribute("isType", isType);
		request.setAttribute("isArticle", true);
		request.getRequestDispatcher("../view/blog/blog_home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		ArticleService articleService = new ArticleService();
		UserService userService = new UserService();
		TypeService typeService = new TypeService();
		boolean isType = false;
		
		int uid=0;
		List<Type> types = new ArrayList<Type>();
		String title = "登录";
		
		uid = userService.findAdminId();
		types = typeService.getAllTypes();
		
		User user = (User) request.getSession().getAttribute("user");
		if(user != null) {
			title = user.getName()+"/退出登录";
		}else {
			user = userService.findUserById(uid);
			request.setAttribute("user", user);
		}
		
		int pageIndex = 0;
		String str = request.getParameter("pageIndex");
		if(""!=str && str != null) {
			pageIndex = Integer.parseInt(str);
		}
		int pageSize = 5;
		
		Page<ArticleInfo> page = null;
		String tIds = request.getParameter("tId");
		
		String keyWords = request.getParameter("keywords").trim();//去除收尾空格
		if(""!=tIds && tIds != null) {
			int tId = Integer.parseInt(tIds);
			page = articleService.searchArticles(uid, keyWords, pageIndex, pageSize,tId);
			request.setAttribute("tId", tId);
			isType = true;
			request.setAttribute("type", typeService.getTypeById(tId));	
		}else {
			page = articleService.searchArticles(uid, keyWords, pageIndex, pageSize);
		}
		
		request.setAttribute("keyWords", keyWords);
		request.setAttribute("page", page);
		request.setAttribute("title", title);
		//request.setAttribute("articles", articles);
		request.setAttribute("types", types);
		request.setAttribute("isType", isType);
		request.getRequestDispatcher("../view/blog/blog_home.jsp").forward(request, response);
	}
	

}
