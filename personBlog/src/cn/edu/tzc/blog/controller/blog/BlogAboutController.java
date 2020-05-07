package cn.edu.tzc.blog.controller.blog;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.tzc.blog.domain.Type;
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.TypeService;
import cn.edu.tzc.blog.service.UserService;

/**
 * Servlet implementation class BlogAboutController
 */
@WebServlet("/blog/aboutMe")
public class BlogAboutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BlogAboutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String title = "登录";
		UserService userService = new UserService();
		
		int uId = userService.findAdminId();
		User admin = userService.findUserById(uId);
		/*User user = (User) request.getSession().getAttribute("user");
		if(user != null) {
			title = user.getName()+"/退出登录";
		}else {
			user = userService.findUserById(uId);
			request.setAttribute("user", user);
		}
		request.setAttribute("title", title);*/
		
		request.setAttribute("introduction", admin.getIntroduction());
		request.setAttribute("isAbout", true);
		request.getRequestDispatcher("../view/blog/about.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
