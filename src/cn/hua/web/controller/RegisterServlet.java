package cn.hua.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hua.domain.RegisterError;
import cn.hua.domain.Users;
import cn.hua.service.BusinessService;
import cn.hua.service.impl.BusinessServiceImpl;
import cn.hua.utils.ConvertUtils;
import cn.hua.utils.DataVerifyUtils;
import cn.hua.utils.MD5Utils;
import cn.hua.utils.UuidUtils;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RegisterError error = DataVerifyUtils.registerData(request);	//注册数据检测
		PrintWriter out = response.getWriter();		
		if(error!=null){	//数据数据有误则以json方式返回
			String message = "{'un':'"+error.getUsername()+"','nn':'"+error.getNickname()+
					"','pd':'"+error.getPassword()+"','pd2':'"+error.getPassword2()+"'}";
			out.write(message);
			return;
		}
		Users user = ConvertUtils.requestToBean(request, Users.class);	//将注册信息封装bean
		if(user!=null){
			user.setId(UuidUtils.getUuid());	//获取随机唯一ID
			user.setPassword(MD5Utils.md5(user.getPassword())); //密码加密	
			BusinessService service = new BusinessServiceImpl();
			if(!service.addUser(user)){
				out.write("false");
				return;
			}
		}
		out.write("true");
		return;
	}

}
