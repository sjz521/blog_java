package cn.edu.tzc.blog.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.tzc.blog.domain.Page;
import cn.edu.tzc.blog.domain.Type;
import cn.edu.tzc.blog.domain.TypeInfo;
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.TypeService;
import cn.edu.tzc.blog.service.exception.TypeException;

/**
 * Servlet implementation class AdminType
 */
@WebServlet("/admin/type")
public class AdminTypeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		request.setAttribute("user", user);
		request.setAttribute("typeClass", "class=\"active\"");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		String method = request.getParameter("method");
		TypeService service = new TypeService();
		
		if(method.toLowerCase().equals("add")) {
			//添加分类
			request.getRequestDispatcher("/view/admin/type/type_edit.jsp").forward(request, response);
		}
		else if(method.toLowerCase().equals("show")) {
			//显示分类列表
			int pageIndex=0;
			String str = request.getParameter("pageIndex");
			if(""!=str && str != null) {
				pageIndex = Integer.parseInt(str);
			}
			
			int pageSize=5;
			Page<TypeInfo> page = service.findTypeWithPage(pageIndex, pageSize);
			
			request.setAttribute("page", page);
			request.getRequestDispatcher("/view/admin/type/type_list.jsp").forward(request, response);
		}
		else if(method.toLowerCase().equals("delete")) {
			//删除分类
			String message = "";
			String ids = request.getParameter("id");
			if(ids.contains(",")) {
				String[] idList = ids.split(",");
				message = service.delChecked(idList);
			}else {
				message = service.deleteType(Integer.parseInt(ids));
			}
			String url = request.getContextPath()+"/admin/type?method=show&pageIndex=0";
			pw.println("<html><body><script language='javascript'>alert('"+message+"');window.location.href='"+url+"';</script></body></html>");
		}else if(method.toLowerCase().equals("deleteAll")) {
			String message = service.deleteAll();
			String url = request.getContextPath()+"/admin/type?method=show&pageIndex=0";
			pw.println("<html><body><script language='javascript'>alert('"+message+"');window.location.href='"+url+"';</script></body></html>");
		}
		pw.close();
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
		
		String name = request.getParameter("name");
		if(""==name || name == null) {
			String url = request.getContextPath()+"/admin/type?method=add";
			pw.println("<html><body><script language='javascript'>alert('请输入分类名称');window.location.href='"+url+"';</script></body></html>");
		}else {
			Type type = new Type();
			type.setName(name);
			TypeService service = new TypeService();
			String message = service.addType(type);
			String url = request.getContextPath()+"/admin/type?method=show&pageIndex=0";
			pw.println("<html><body><script language='javascript'>alert('"+message+"');window.location.href='"+url+"';</script></body></html>");
		}
		
	}

}
