package cn.hua.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hua.domain.Photo;
import cn.hua.domain.Users;
import cn.hua.service.BusinessService;
import cn.hua.service.impl.BusinessServiceImpl;

/**
 * Servlet implementation class SysPhotoServlet
 */
@WebServlet("/SysPhotoServlet")
public class SysPhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String userid = request.getParameter("userid");
		String type = request.getParameter("type");
		BusinessService service = new BusinessServiceImpl();
		/*if(id==null){
			List<Photo> list = service.getSysPhoto();
			System.out.println(list);
			if(list!=null){
				request.setAttribute("img", list);
			}
			request.getRequestDispatcher("/WEB-INF/pages/sysPhoto.jsp").forward(request, response);
			return;
		}*/
		if(type!=null){	//获取当前用户背景
			String purl = service.getPicture(id);
			FileInputStream in = new FileInputStream(new File("D:/MyEclipse2015/ChatRoom/WebRoot"+purl));
			OutputStream out = response.getOutputStream();
			int len=0;
			byte[] buffer = new byte[1024];
			while((len=in.read(buffer))>0){
				out.write(buffer,0,len);
			}
			out.close();
			in.close();
			return;
		}
		if(userid!=null){
			Users user = service.findUser(userid);	//查找用户
			if(user==null){
				return;
			}
			id=user.getPhotoid();	//获取头像ID
		}
		if(id==null){
			id="1";
		}
		if(id.trim().equals("")){
			id="1";
		}
		try{
			Photo p= service.findPhoto(id);	//通过头像ID查找头像信息
		InputStream in = new FileInputStream(new File(p.getUrl()+p.getName()));	//写出头像
		int len=0;
		byte[] buffer = new byte[1024];
		while((len=in.read(buffer))>0){
			response.getOutputStream().write(buffer,0,len);
		}
		in.close();
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
