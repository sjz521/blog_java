package cn.edu.tzc.blog.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.tzc.blog.domain.Page;
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.UserService;

/**
 * Servlet implementation class AdminUserController
 */
@WebServlet("/admin/user")
public class AdminUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = (User) request.getSession().getAttribute("user");
		UserService service = new UserService();
		
		String method = request.getParameter("method");
		if(method==null || "".equals(method)) {
			int pageIndex = 0;
			String str = request.getParameter("pageIndex");
			if(""!=str && str !=null) {
				pageIndex = Integer.parseInt(str);
			}
			int pageSize = 10;
			Page<User> page = service.findUserWithPage(pageIndex, pageSize);
			
			request.setAttribute("page", page);
			
			request.setAttribute("user", user);
			request.setAttribute("userClass", "class=\"active\"");
			request.getRequestDispatcher("/view/admin/user_list.jsp").forward(request, response);
		}else{
			String ids = request.getParameter("id");
			String message = "";
			if(ids == null || "".equals(ids)) {
				//删除除博主以外的所有用户
				message = service.deleteAllUser();
			}else {
				if(ids.contains(",")) {
					message = service.deleteUsers(ids);
				}else {
					message = service.deleteUser(Integer.parseInt(ids));
				}
			}
			response.setContentType("text/html;charset=	UTF-8");
			PrintWriter pw = response.getWriter();
			String url =  request.getContextPath()+"/admin/user";
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
