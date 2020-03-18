package cn.edu.tzc.blog.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.tzc.blog.util.FileUtil;

/**
 * Servlet implementation class ImageShowServlet
 */
@WebServlet("/imageShow")
public class ImageShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		response.setContentType("image/jpeg");
		request.setCharacterEncoding("utf-8");
		String fileName = request.getParameter("fileName");
		
		request.setAttribute("fileName", fileName);
		request.getRequestDispatcher("view/imageShow.jsp").forward(request, response);
		
		/*try {
			FileImageInputStream input = new FileImageInputStream(new File(savePath+File.separator+fileName));
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len = -1;
			while((len = input.read(buf)) != -1) {
				output.write(buf,0,len);
			}
			byte[] data = output.toByteArray();
			os.write(data);
			os.flush();
			
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
