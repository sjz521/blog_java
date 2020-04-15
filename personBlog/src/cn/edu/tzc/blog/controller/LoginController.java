package cn.edu.tzc.blog.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.UserService;
import cn.edu.tzc.blog.service.exception.UserException;
import cn.edu.tzc.blog.util.MD5Util;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf8");
		User user = (User) request.getSession().getAttribute("user");
		if(user != null) {
			request.getSession().removeAttribute("user");
			response.sendRedirect(request.getContextPath()+"/blog/home");
			logger.info(user.getEmail()+"退出登录");
			return;
		}
		
		request.getRequestDispatcher("view/login.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		/*
		 * 从session中获取正确的验证码并与获取的验证码进行比较
		 */
		String sessionCode = (String) request.getSession().getAttribute("session_vcode");
		String paramCode = request.getParameter("verifyCode");
		if(!paramCode.equalsIgnoreCase(sessionCode)) {
			request.setAttribute("msg", "验证码错误！！！");
			request.getRequestDispatcher("/view/login.jsp").forward(request, response);
			return;
		}
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		UserService service = new UserService();
		try {
			User user = service.login(email, password);
			request.getSession().setAttribute("user", user);
			if("true".equals(remember)) {
				Cookie cookie = new Cookie("remember", remember);
				//cookie.setMaxAge(60*60*24*30);
				Cookie emailCookie = new Cookie("email", email);
				//emailCookie.setMaxAge(60*60);
				Cookie passwordCookie = new Cookie("password", password);
				//passwordCookie.setMaxAge(60*60);
				response.addCookie(cookie);
				response.addCookie(emailCookie);
				response.addCookie(passwordCookie);
			}else {
				//清除cookie
				Cookie[] cookies = request.getCookies();
				List<String> strList = new ArrayList<String>();
				strList.add("remember");
				strList.add("email");
				strList.add("password");
				if(cookies != null) {
					for(Cookie c:cookies) {
						if(strList.contains(c.getName())) {
							c.setMaxAge(0);
							response.addCookie(c);
						}
					}
				}
			}
			
			if (user.getRole() == 0) {
				//为管理员
				response.sendRedirect(request.getContextPath()+"/admin/home");
			}else {
				//为普通用户
				response.sendRedirect(request.getContextPath()+"/blog/home");
			}
		} catch (UserException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/view/login.jsp").forward(request, response);
			return;
		}
	}
}
