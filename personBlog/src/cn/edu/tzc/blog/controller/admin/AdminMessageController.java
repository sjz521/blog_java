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

import cn.edu.tzc.blog.domain.Message;
import cn.edu.tzc.blog.domain.MessageInfo;
import cn.edu.tzc.blog.domain.Page;
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.MessageService;

/**
 * Servlet implementation class AdminMessageController
 */
@WebServlet("/admin/message")
public class AdminMessageController extends HttpServlet {
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
		MessageService service = new MessageService();
		
		String method = request.getParameter("method");
		if(method == null ||"".equals(method)) {
			int pageIndex=0;
			String str = request.getParameter("pageIndex");
			if(""!=str && str!=null) {
				pageIndex = Integer.parseInt(str);
			}
			
			//处理分页
			int pageSize = 10;
			Page<MessageInfo> page = service.findMessageWithPage(pageIndex, pageSize);
			
			request.setAttribute("page", page);
			request.setAttribute("messageClass", "class=\"active\"");
			request.getRequestDispatcher("/view/admin/message_list.jsp").forward(request, response);
		}
		else {
			String ids = request.getParameter("id");
			String message = "";
			if(ids==null || "".equals(ids)) {
				message = service.deleteAllMessage();
			}else {
				if(ids.contains(",")) {
					message = service.deleteChecked(ids.split(","));
				}else {
					message = service.deleteMessage(Integer.parseInt(ids));
				}
			}
			/*String method = request.getParameter("method");
			String message = "";
			
			if("all" == method) {
				message = service.DeleteAllMessage();
			}
			else if("single" == method) {
				int id = Integer.parseInt(request.getParameter("id"));
				message = service.DeleteMessage(id);
			}*/
			response.setContentType("text/html;charset=	UTF-8");
			PrintWriter pw = response.getWriter();
			String url =  request.getContextPath()+"/admin/message";
			pw.println("<html><body><script language='javascript'>alert('"+message+"');window.location.href='"+url+"';</script></body></html>");
			pw.close();
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
