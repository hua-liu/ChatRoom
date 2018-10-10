package cn.hua.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hua.domain.Users;
import cn.hua.service.BusinessService;
import cn.hua.service.impl.BusinessServiceImpl;

@WebServlet("/GetServlet")
public class GetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		BusinessService service = new BusinessServiceImpl();
		if(type==null){
			return;
		}
		if(type.equals("online")){		//是否在线检测
			if(id==null){
				return;
			}
			PrintWriter pw = response.getWriter();
			if(service.findLoginState(id)){
				pw.write("true");
				pw.close();
				return;
			}
			pw.write("false");
			pw.close();
			return;
		}else if(type.equals("checkState")){		//更新登陆状态并检测
			if(id==null){
				return;
			}
			PrintWriter pw = response.getWriter();
			if(service.findLoginState(id)){
				if(service.updateLoginState(id)){
					service.delLoginState();
					pw.write("true");
					pw.close();
					return;
				}
			}
			if(service.updateLoginState(id)){
				pw.write("true");
				pw.close();
				return;
			}
			return;
		}else if(type.equals("sysSetSound")){		//获取铃声
			if(id==null){
				return;
			}
			String value = service.getSound(id);
			FileInputStream in = new FileInputStream(new File("D:/MyEclipse2015/ChatRoom/WebRoot"+value));//此处地址写死了，以后有空优化
			response.addHeader("Content-Type", "audio/mpeg;charset=UTF-8");	//告诉浏览器写出数据的格式
			OutputStream out = response.getOutputStream();
			int len;
			byte[] buffer = new byte[1024];
			while((len=in.read(buffer))>0){
				out.write(buffer,0,len);
			}
			out.close();
			in.close();
			return;
		}else if(type.equals("sysSetPicture")){		//获取背景图像id
			if(id==null){
				return;
			}
			PrintWriter pw = response.getWriter();
			String value = service.getPicture(id);
			pw.write(value);
			pw.close();
			return;
		}
	}

}
