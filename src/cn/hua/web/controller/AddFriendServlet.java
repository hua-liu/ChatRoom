package cn.hua.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hua.service.BusinessService;
import cn.hua.service.impl.BusinessServiceImpl;

/**
 * Servlet implementation class AddFriendServlet
 */
@WebServlet("/AddFriendServlet")
public class AddFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String friendId = request.getParameter("friendId");
		String kind = request.getParameter("kind");
		if(id==null||friendId==null||kind==null){
			return;
		}
		BusinessService service = new BusinessServiceImpl();
		PrintWriter pw = response.getWriter();
		if(service.isExistFriend(id, friendId)){
			pw.write("请勿重复添加");
			return;
		}
		if(service.addFriend(id, friendId, kind)){		//添加好友
			pw.write("添加好友成功");
			return;
		}
		pw.write("添加好友失败");
		return;
	}

}
