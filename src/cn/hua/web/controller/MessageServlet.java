package cn.hua.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hua.domain.Message;
import cn.hua.service.BusinessService;
import cn.hua.service.impl.BusinessServiceImpl;
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String fid = request.getParameter("fid");
		PrintWriter pw = response.getWriter();
		BusinessService service = new BusinessServiceImpl();
		if(type==null){
			return;
		}
		
		if(id==null){
			pw.write("false");
			return;
		}
		if(type.equals("send")){		
			String content = request.getParameter("content");
			if(id==null||fid==null||content==null){
				pw.write("false");
				return;
			}
			if(service.addMessage(id, fid, content)){//发送的信息处理
				pw.write("true");
				return;
			}
		}else if(type.equals("receive")){	//获取当前用户所有信息
			Map<String,Object> map = service.getAllMessage(id);
			if(map==null){
				return;
			}
			String data = "[";
			int i=0;
			for(Entry<String, Object> set : map.entrySet()){		//使用json包装后返回
				String key = set.getKey();
				Object value = set.getValue();
				data = data+"{'key':'"+key+"','value':'"+value+"'},";
			}
			data = data.substring(0,data.length()-1)+"]";
			if(data.equals("]")){
				return;
			}
			pw.write(data);
			return ;
		}else if(type.equals("receiveOne")){	//接收单个好友消息
			if(fid==null){
				return;
			}
			List<Message> list = service.getMessage(id, fid);
			if(list==null){
				return;
			}
			if(list.size()<1){
				return;
			}
			String data = "[";
			int i=0;
			for(Message message :list){
				String key = message.getSender();
				Object value = message.getContent();
				data = data+"{'key':'"+key+"','value':'"+value+"'},";
			}
			data = data.substring(0,data.length()-1)+"]";
			if(data.equals("]")){
				return;
			}
			pw.write(data);
			return ;
		}
	}

}
