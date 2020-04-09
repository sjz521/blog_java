package cn.edu.tzc.blog.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.edu.tzc.blog.domain.Type;
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.AdminService;
import cn.edu.tzc.blog.service.TypeService;

/**
 * Servlet Filter implementation class BasicFilter
 */
@WebFilter(filterName="BasicFilter"
			,urlPatterns="/*")
public class BasicFilter implements Filter {

    /**
     * Default constructor. 
     */
    public BasicFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		
		String path = request.getRequestURI();
		int index = path.indexOf("personBlog")+"personBlog".length();
		User user = (User)session.getAttribute("user");
		if(path.indexOf("admin", index)>-1) {
			if(user == null || user.getRole() !=0 ){
				PrintWriter pw = response.getWriter();
				String url = request.getContextPath()+"/login";
				pw.println("<html><body><script language='javascript'>alert('不是管理员，不能登录后台');window.location.href='"+url+"';</script></body></html>");
				pw.close();
			}
			else {
				chain.doFilter(arg0, arg1);
			}
		}
		else if(path.indexOf("blog",index)>-1) {
			int lastIndex = path.lastIndexOf("/");
			
			String title = "登录";
			if(user != null) {
				title = user.getName()+"/退出登录";
			}
			request.setAttribute("title", title);
			
			TypeService typeService = new TypeService();
			request.setAttribute("types", typeService.getAllTypes());
			
			if(path.indexOf("article",lastIndex)>-1) {
				// blog/article，需要判断是否登录
				if(user == null) {
					PrintWriter pw = response.getWriter();
					String url = request.getContextPath()+"/blog/home";
					pw.println("<html><body><script language='javascript'>alert('未登录不能查看文章具体内容。请先登录！！！');window.location.href='"+url+"';</script></body></html>");
					pw.close();
				}
				else {
					chain.doFilter(arg0, arg1);
				}
			}else {
				chain.doFilter(arg0, arg1);
			}
		}else {
			chain.doFilter(arg0, arg1);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
