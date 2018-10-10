package cn.hua.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hua.domain.Friend;
import cn.hua.domain.Users;
import cn.hua.service.BusinessService;
import cn.hua.service.impl.BusinessServiceImpl;

@WebServlet("/GetFriendServlet")
public class GetFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		BusinessService service = new BusinessServiceImpl();
		if(type==null||id==null){
			return;
		}
		if(id.equals("")){
			return;
		}
		if(type.equals("friend")){
			List<Friend> list = service.getFriends(id);	//获取好友
			request.setAttribute("list", list);
			request.getRequestDispatcher("/FriendServlet").forward(request, response);
		}else if(type.equals("nearby")){
			List<Users> list = service.getAllUser();		//获取所有用户
			request.setAttribute("list", list);
			request.getRequestDispatcher("/NearbyServlet").forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
