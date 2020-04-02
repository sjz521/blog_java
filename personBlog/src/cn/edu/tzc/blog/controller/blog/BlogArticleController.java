package cn.edu.tzc.blog.controller.blog;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.tzc.blog.domain.ArticleInfo;
import cn.edu.tzc.blog.domain.MessageInfo;
import cn.edu.tzc.blog.domain.Page;
import cn.edu.tzc.blog.domain.Type;
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.ArticleService;
import cn.edu.tzc.blog.service.MessageService;
import cn.edu.tzc.blog.service.TypeService;
import cn.edu.tzc.blog.service.exception.ArticleException;
import cn.edu.tzc.blog.service.exception.TypeException;

@WebServlet("/blog/article")
public class BlogArticleController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		User user = (User)request.getSession().getAttribute("user");
		/*if(user == null) {
			String url = request.getContextPath()+"/login";
			pw.println("<html><body><script language='javascript'>alert('登录后才能阅读文章，请先登录');window.location.href='"+url+"';</script></body></html>");
			pw.close();
			return;
		}*/
		/*String title = user.getName()+"/退出登录";
		request.setAttribute("title", title);*/
		String idString = request.getParameter("id");
		int id = 0;
		if(idString!=null && idString!="") {
			id = Integer.parseInt(idString);
		}else {
			String url = request.getContextPath()+"/blog/home";
			pw.println("<html><body><script language='javascript'>alert('请输入必要参数id');window.location.href='"+url+"';</script></body></html>");
			pw.close();
			return;
		}
		
		ArticleService articleService = new ArticleService();
		TypeService typeService = new TypeService();
		MessageService messageService = new MessageService();
		
		//List<Type> types = new ArrayList<>();
		try {
			ArticleInfo articleInfo = articleService.findById(id);
			request.setAttribute("articleInfo", articleInfo);
			//types = typeService.showAllTypes();
			//request.setAttribute("types", types);
			
			int pageIndex = 0;
			String str = request.getParameter("pageIndex");
			if(""!=str && str!=null) {
				pageIndex = Integer.parseInt(str);
			}
			int pageSize = 5;
			
			Page<MessageInfo> page = messageService.findMessageWithPageByAid(pageIndex, pageSize, articleInfo.getId());
			request.setAttribute("page", page);
			
		} catch (ArticleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("Articleerror", "找不到该文章！！");
		}
		
		request.getRequestDispatcher("../view/blog/article_show.jsp").forward(request, response);
	}
}
