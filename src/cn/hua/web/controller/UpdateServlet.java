package cn.hua.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hua.domain.Users;
import cn.hua.service.BusinessService;
import cn.hua.service.impl.BusinessServiceImpl;
@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String fid = request.getParameter("fid");
		String nickname = request.getParameter("nickname");
		String description = request.getParameter("description");
		PrintWriter pw = response.getWriter();
		if(type==null||id==null){
			return;
		}
		BusinessService service = new BusinessServiceImpl();
		Users user = service.findUser(id);	//通过查找用户
		if(user==null){
			return;
		}
		if(type.equals("updateInfo")){	//更新用户昵称，个人说明
			if(nickname==null||description==null){
				return;
			}
			user.setNickname(nickname);
			user.setDescription(description);
			if(service.updateUser(user)){
				pw.write("true");
				pw.close();
				return;
			}
		}else if(type.equals("delFriend")){	//删除好友
			if(fid==null){
				return;
			}
			Users user1 = service.findUser(fid);
			if(user1==null){
				return;
			}
			if(service.delFriend(id, fid)){
				pw.write("true");
				pw.close();
				return;
			}
			return;
		}else if(type.equals("updateSet")){		//更新铃声与背景
			String sid = request.getParameter("sid");
			String pid = request.getParameter("pid");
			if(sid==null||pid==null){
				return;
			}
			if(service.setSound(id, sid)&&service.setPicture(id, pid)){
				pw.write("true");
				pw.close();
				return;
			}
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	}

}
