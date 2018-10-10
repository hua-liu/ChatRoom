package cn.hua.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hua.domain.LoginError;
import cn.hua.domain.Users;
import cn.hua.service.BusinessService;
import cn.hua.service.impl.BusinessServiceImpl;
import cn.hua.utils.DataVerifyUtils;
import cn.hua.utils.MD5Utils;
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {	//登陆
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String autologin = request.getParameter("autologin");
		PrintWriter pw = response.getWriter();
		try{
		LoginError error = DataVerifyUtils.loginData(request, null);
			if(error!=null){
				pw.write("用户名或密码不能为空");
				return;
			}
			BusinessService service = new BusinessServiceImpl();
			password = MD5Utils.md5(password);
			Users user = service.verifyUP(username, password);	//验证用户名密码
			if(user==null){
				pw.write("用户名或密码错误");
				return;
			}
			service.delLoginState();
			if(!service.findLoginState(user.getId())){	//查的状态表是否有当前登陆用户
				service.addLoginState(user.getId());
				pw.write("true");
			}else{
				pw.write("您已在别处登陆！");
				return;
			}
			if(autologin.equals("auto")){	//向浏览器存自动登陆COOK
				Long time = System.currentTimeMillis();
				String data = user.getId()+":"+time+":"+MD5Utils.md5(user.getId()+password+time+"851860021");
				Cookie cook = new Cookie("autoLogin", data);
				cook.setMaxAge(1*60*60*24*7);	//设置自动登陆时间为7天
				cook.setPath(request.getContextPath()+"/Char");
				response.addCookie(cook);
			}
			request.getSession().setAttribute("user", user);
		}catch(Exception e){
			pw.write("未连接到数据");
		}
		return;
	}

}
