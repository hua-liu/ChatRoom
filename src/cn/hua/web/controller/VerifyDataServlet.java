package cn.hua.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hua.domain.Users;
import cn.hua.service.BusinessService;
import cn.hua.service.impl.BusinessServiceImpl;

@WebServlet("/VerifyDataServlet")
public class VerifyDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	//用户名，昵称验证
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String nickname = request.getParameter("nickname");
		BusinessService service = new BusinessServiceImpl();
		PrintWriter pw = response.getWriter();
		if(username!=null){	//验证用户名
			if(username.length()>=5&&username.length()<=20){
				Users user = service.isExistUser("username", username);	//验证此用户名是否已存在
				if(user==null){
					pw.write("true");
				}else{
					pw.write("false");
				}
			}
			pw.close();
			return;
		}else if(nickname!=null){
			Users user = service.isExistUser("nickname", nickname);//验证以此昵称的用户是否已存在
			if(user==null){
				pw.write("true");
			}else{
				pw.write("false");
			}
			pw.close();
			return;
		}
	}
}
