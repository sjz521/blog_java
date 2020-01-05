package cn.edu.tzc.blog.controller.admin.article;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import cn.edu.tzc.blog.domain.Article;
import cn.edu.tzc.blog.domain.ArticleInfo;
import cn.edu.tzc.blog.domain.Type;
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.ArticleService;
import cn.edu.tzc.blog.service.TypeService;
import cn.edu.tzc.blog.service.exception.TypeException;

/**
 * Servlet implementation class AdminArticleEdit
 */
@WebServlet("/admin/article/edit")
public class AdminArticleEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null) {
			request.setAttribute("msg", "未登录");
			request.getRequestDispatcher("/view/admin/login.jsp").forward(request, response);
			return;
		}
		
		ArticleService articleService = new ArticleService();
		TypeService typeService = new TypeService();
		List<Type> types = new ArrayList<Type>();
		
		String ids = request.getParameter("id");
		if("" != ids && ids != null) {
			int id = Integer.parseInt(ids);
			ArticleInfo articleInfo = new ArticleInfo();
			try {
				articleInfo = articleService.findById(id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("article", articleInfo);
		}
		
		types = typeService.showAllTypes();
		request.setAttribute("types", types);
		
		request.getRequestDispatcher("/view/admin/article/article_edit.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		ArticleService articleService = new ArticleService();
		Article form = new Article();
		String ids = "";
		String message = "";
		
		/**
		 * 上传三步：
		 * 1.得到工厂
		 * 2.通过工厂创建解析
		 * 3.解析request，得到FileItem集合
		 * 4.遍历FileItem集合
		 */
		try {
			
			DiskFileItemFactory factory  = new DiskFileItemFactory();
			//创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			//判断提交上来的数据是否为表单数据
			if(!ServletFileUpload.isMultipartContent(request)) {
				//按照传统方式获取数据
				//return "";
				form.setTitle(request.getParameter("title"));
				form.setIntroduction(request.getParameter("introduction"));
				form.setContent(request.getParameter("content"));
				form.setPhoto("");//request.getParameter("photo"));
				form.setTid(Integer.parseInt(request.getParameter("select_value")));
				form.setUid(Integer.parseInt(request.getParameter("uId")));
			}
			
			List<FileItem> list = upload.parseRequest(new ServletRequestContext(request));
			FileItem id = list.get(0);
			FileItem title = list.get(1);//标题
			FileItem uId = list.get(2);//作者id
			FileItem author = list.get(3);//作者名称
			FileItem photo = list.get(4);//图片
			FileItem introduction = list.get(5);//简介
			FileItem tId = list.get(6);//标签id
			FileItem contentItem = list.get(7);//文章内容
			
			String content = contentItem.getString("utf-8");
			ids = id.getString("utf-8");
			String filePath = "";
			String path = "";
			String fileName = photo.getName();
			
			//String path = this.getServletContext().getRealPath("/public/images/articleimg");
			if(""!=fileName && fileName!=null) 
			{
				path = this.getServletContext().getRealPath("/public/images/articleimg");
				String projectName = request.getServletContext().getContextPath().replaceAll("/", "");
				int beginIndex = path.indexOf(projectName);
				int endIndex = path.lastIndexOf(projectName);
				filePath = path.substring(0, beginIndex+projectName.length()+1)+projectName+"/WebContent//"+path.substring(endIndex+projectName.length()+1);
				
				fileName = articleService.uploadFile(photo, filePath); //uploadPhoto(photo, filePath);
				articleService.uploadFile(photo, path, fileName);
				if(fileName == null) {
					String url = request.getContextPath()+"/admin/article";
					pw.println("<html><body><script language='javascript'>alert('文件上传失败');window.location.href='"+url+"';</script></body></html>");
					pw.close();
					return;
				}
				form.setPhoto(fileName);
			}
			else {
				if(""!=ids && ids!=null) {
					Article article = articleService.findById(Integer.parseInt(ids));
					form.setPhoto(article.getPhoto());
				}
				
			}
			
			form.setTitle(title.getString("utf-8"));
			form.setUid(Integer.parseInt(uId.getString("utf-8")));
			
			form.setIntroduction(introduction.getString("utf-8"));
			form.setTid(Integer.parseInt(tId.getString("utf-8")));
			form.setContent(content);
			
			if(ids == null || ids.length() == 0) {
				articleService.addArticle(form);
				message = "文章添加成功";
			}else {
				
				form.setId(Integer.parseInt(ids));
				articleService.updateArticle(form,path,filePath);
				message = "文章修改成功";
			} 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//response.sendRedirect(request.getContextPath()+"/admin/article");
		String url = request.getContextPath()+"/admin/article";
		pw.println("<html><body><script language='javascript'>alert('"+message+"');window.location.href='"+url+"';</script></body></html>");
		pw.close();
	}

}
