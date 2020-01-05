package cn.edu.tzc.blog.controller.admin.message;

import java.io.IOException;
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
		if(user == null) {
			request.setAttribute("msg", "未登录");
			request.getRequestDispatcher("/view/admin/login.jsp").forward(request, response);
			return;
		}
		request.setAttribute("user", user);
		
		int pageIndex=0;
		String str = request.getParameter("pageIndex");
		if(""!=str && str!=null) {
			pageIndex = Integer.parseInt(str);
		}
		
		//处理分页
		MessageService service = new MessageService();
		int pageSize = 10;
		Page<MessageInfo> page = service.findMessageWithPage(pageIndex, pageSize);
		
		request.setAttribute("page", page);
		request.setAttribute("messageClass", "class=\"active\"");
		request.getRequestDispatcher("/view/admin/message_list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
