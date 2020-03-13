package cn.edu.tzc.blog.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import cn.edu.tzc.blog.domain.FileHelper;

/**
 * Servlet implementation class PicServlet
 */
@WebServlet("/image/upload")
public class PicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PicServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		SmartUpload smu = new SmartUpload();
		//upload文件夹在.metadata里的路径
		String rootPath = request.getSession().getServletContext().getRealPath("/upload");
		java.io.File filePath = new java.io.File(rootPath);
		if(!filePath.exists()) {
			filePath.mkdirs();//创建文件目录
		}
		
		FileHelper helper = new FileHelper();
		String projectName = request.getServletContext().getContextPath().replaceAll("/", "");
		//upload文件夹在src下的WebContent里的路径
		String publicPath = helper.getFilePath(rootPath, projectName);
		
		try {
			smu.initialize(this.getServletConfig(),request,response);
			smu.setCharset("utf-8");
			smu.setAllowedFilesList("gif,jpg,jpeg,png,bmp");
			smu.upload();
			for(int i=0;i<smu.getFiles().getCount();i++) {
				File file = smu.getFiles().getFile(i);
				int index = file.getFileName().lastIndexOf(".");
				if(index == -1) {
					return;
				}
				String suffix = file.getFileName().substring(index);
				String fileName = helper.renameFileByDate(suffix);
				
				
				java.io.File realFile = new java.io.File(rootPath+java.io.File.separator+fileName);
				file.saveAs(realFile.toString());
				
				java.io.File realFile2 = new java.io.File(publicPath+java.io.File.separator+fileName);
				file.saveAs(realFile2.toString());
				String path = request.getContextPath();
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
				//返回json串
				response.getWriter().write("{\"success\":1,\"message\":\"upload successful\",\"url\":\"/"+projectName+"/upload/"+fileName+"\"}");
				
			}
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			response.getWriter().write("{\"success\":0}");
			e.printStackTrace();
		}
		
	}

}
