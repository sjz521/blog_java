package cn.edu.tzc.blog.controller.blog;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.tzc.blog.domain.Message;
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.MessageService;

/**
 * Servlet implementation class BlogMessageController
 */
@WebServlet("/blog/message")
public class BlogMessageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			request.setAttribute("msg", "未登录");
			request.getRequestDispatcher("/view/admin/login.jsp").forward(request, response);
			return;
		}
		int aId = Integer.parseInt(request.getParameter("aId"));
		String content = request.getParameter("content");
		Message reply = new Message();
		reply.setContent(content);
		reply.setaId(aId);
		reply.setuId(user.getId());
		MessageService service = new MessageService();
		boolean result = service.AddReply(reply);
		String message = "";
		if(!result) {
			message = "留言添加失败";
		}else {
			message = "留言添加成功";
		}
		String url = request.getContextPath()+"/blog/article?id="+aId;
		pw.println("<html><body><script language='javascript'>alert('"+message+"');window.location.href='"+url+"';</script></body></html>");
		pw.close();
		
	}

}
