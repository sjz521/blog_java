package cn.edu.tzc.blog.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.tzc.blog.service.UserService;
import cn.edu.tzc.blog.service.exception.UserException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf8");
		request.getRequestDispatcher("view/admin/register.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//获取表单数据
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String message = "";
		
		UserService userService = new UserService();
		try {
			message = userService.register(email, name, password);
		} catch (UserException e) {
			//获取异常并保存到request域
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/view/admin/register.jsp").forward(request, response);
			return;
		}
		/*PrintWriter pw = response.getWriter();
		pw.println("<script>alert('注册成功')</script>");*/
		request.setAttribute("msg", message);
		request.getRequestDispatcher("/view/admin/login.jsp").forward(request, response);
		
	}
}
