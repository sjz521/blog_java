package cn.edu.tzc.blog.controller.admin.article;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.ArticleService;
import cn.edu.tzc.blog.service.exception.ArticleException;

/**
 * Servlet implementation class AdminArticleDeleteController
 */
@WebServlet("/admin/article/delete")
public class AdminArticleDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=	UTF-8");
		User user = (User)request.getSession().getAttribute("user");
		ArticleService articleService = new ArticleService();
		String ids = request.getParameter("id");
		
		//文件路径
		String path = this.getServletContext().getRealPath("/WebContent/public/images/photos");
		String projectName = request.getServletContext().getContextPath().replaceAll("/", "");
		int beginIndex = path.indexOf(projectName);
		int endIndex = path.lastIndexOf(projectName);
		String filePath = path.substring(0, beginIndex+projectName.length()+1)+path.substring(endIndex);
		
		String message = "";
		PrintWriter pw = response.getWriter();
		try {
			if("" == ids || ids == null) {
				message = articleService.DeleteAllArticle(user.getId(),path);
			}else {
				if(ids.contains(",")) {
					String[] idList = ids.split(",");
					message = articleService.delCheck(idList, filePath);
				}
				else {
					message = articleService.DeleteArticle(Integer.parseInt(ids),path);
				}
			}
			String url = request.getContextPath()+"/admin/article";
			pw.write("<html><body><script language='javascript'>alert("+message+");window.location.href='\"+url+\"';</script></body></html>");
			pw.close();
		} catch (Exception e) {
			// TODO: handle exception
			pw.write("<html><body><script language='javascript'>alert('"+e.getMessage()+"');window.location.href='\"+url+\"';</script></body></html>");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
