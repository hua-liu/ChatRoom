package cn.hua.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/LogOutServlet")
public class LogOutServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override//注销用户
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getSession().removeAttribute("user");	//移除当前用户session
		Cookie cook = new Cookie("autologin", null);	//清空用户cook
		cook.setMaxAge(0);
		resp.addCookie(cook);
		resp.sendRedirect(req.getContextPath()+"/Login");
	}
	
}
