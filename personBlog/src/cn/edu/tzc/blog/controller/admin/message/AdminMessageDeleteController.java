package cn.edu.tzc.blog.controller.admin.message;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.tzc.blog.service.MessageService;

/**
 * Servlet implementation class AdminMessageDeleteController
 */
@WebServlet("/admin/message/delete")
public class AdminMessageDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = request.getParameter("method");
		String message = "";
		MessageService service = new MessageService();
		if("all" == method) {
			message = service.DeleteAllMessage();
		}
		else if("single" == method) {
			int id = Integer.parseInt(request.getParameter("id"));
			message = service.DeleteMessage(id);
		}
		response.setContentType("text/html;charset=	UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write("<html><body><script language='javascript'>alert("+message+")</script></body></html>");
		pw.close();
		response.sendRedirect(request.getContextPath()+"/admin/message");
	}

}
