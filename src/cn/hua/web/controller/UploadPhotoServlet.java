package cn.hua.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;









import cn.hua.domain.Photo;
import cn.hua.service.BusinessService;
import cn.hua.service.impl.BusinessServiceImpl;
import cn.hua.utils.UuidUtils;
@WebServlet("/UploadPhotoServlet")
public class UploadPhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}//头像上传处理
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!ServletFileUpload.isMultipartContent(request)){
			request.setAttribute("message", "不支持此方式提交");
			request.getRequestDispatcher("/WEB-INF/pages/customPhoto.jsp").forward(request, response);
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();	//创建文件处理工厂
		ServletFileUpload upload = new ServletFileUpload(factory);	//创建上传，记忆专业术语了
		Photo photo = new Photo();
		try {
			List<FileItem> list = upload.parseRequest(request);	//获取所有项
			for(FileItem item : list){		//遍历所有项
				if(!item.isFormField()){	//如果不是普通项则是上传文件
					String name = item.getName();	//获取上传文件名
					String ext = name.substring(name.lastIndexOf(".")).toUpperCase();		//获取上传文件的扩展名并转大写
					if(!".JPG.JPEG,.PNG,.BMP,.GIF".contains(ext)){	//如果不包含这些图像格式
						request.setAttribute("message", "不支持格式");
						request.getRequestDispatcher("/WEB-INF/pages/customPhoto.jsp").forward(request, response);
						return;
					}
					InputStream in = item.getInputStream();	//获取文件流
					String id = UuidUtils.getUuid();		//给文件分配唯一ID
					String path = "D:/DATA/WEBCHAR/PHOTO/USER/";	//路径
					photo.setId(id);
					photo.setName(id+ext);
					photo.setUrl(path);
					photo.setIsSys("0");	//设置文件为非系统
					File file = new File(path+id+ext);	
					OutputStream out = new FileOutputStream(file);	//写出文件
					byte[] buffer = new byte[1024];
					int len=0;
					while((len=in.read(buffer))>0){
						out.write(buffer,0,len);
					}
					out.flush();
					out.close();
					BusinessService service = new BusinessServiceImpl();
					if(service.addPhoto(photo)){	//向数据库注册文件信息
						request.setAttribute("id", id);
						request.setAttribute("message", "头像上传成功");
						request.getRequestDispatcher("/WEB-INF/pages/customPhoto.jsp").forward(request, response);
						return;
					}
					request.setAttribute("message", "数据库保存文件失败");
					request.getRequestDispatcher("/WEB-INF/pages/customPhoto.jsp").forward(request, response);
					return;
				}
			}
		} catch (FileUploadException e) {
			request.setAttribute("message", "服务器文件获取失败");
			request.getRequestDispatcher("/WEB-INF/pages/customPhoto.jsp").forward(request, response);
			return;
		}
		
	}

}
