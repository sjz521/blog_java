package cn.edu.tzc.blog.controller.admin.article;

import java.io.IOException;
import java.io.PrintWriter;
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
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.ArticleService;
import cn.edu.tzc.blog.service.exception.ArticleException;

/**
 * Servlet implementation class AdminArticleController
 */
@WebServlet("/admin/article")
public class AdminArticleController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		User user = (User) request.getSession().getAttribute("user");
		/*if(user == null) {
			request.setAttribute("msg", "未登录");
			request.getRequestDispatcher("/view/admin/login.jsp").forward(request, response);
			return;
		}*/
		request.setAttribute("user", user);
		
		int pageIndex = 0;
		String str = request.getParameter("pageIndex");
		if("" != str && str != null) {
			pageIndex = Integer.parseInt(str);
		}
		
		ArticleService articleService = new ArticleService();
		int pageSize = 5;
		Page<ArticleInfo> page = null;
		//List<ArticleInfo> articles = new ArrayList<ArticleInfo>();
		
		String tids = request.getParameter("tid");
		String keyWords = request.getParameter("keywords");
		if("" != tids && tids != null){
			int tId = Integer.parseInt(tids);
			if(""!=keyWords && keyWords != null) {
				keyWords.trim();
				page = articleService.searchArticles(user.getId(), keyWords, pageIndex, pageSize, tId);
			}
			else {
				page = articleService.findArticleWithPageByTid(pageIndex, pageSize, user.getId(), tId);
			}
		}else {
			if(""!=keyWords && keyWords != null) {
				keyWords.trim();
				page = articleService.searchArticles(user.getId(), keyWords, pageIndex, pageSize);
			}
			else {
				page = articleService.findArticlesWithPage(pageIndex, pageSize, user.getId());
			}
		}
		
		
		request.setAttribute("page", page);
		//request.setAttribute("articles", articles);
		
		request.getRequestDispatcher("/view/admin/article/article_list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}
