package cn.hua.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hua.service.BusinessService;
import cn.hua.service.impl.BusinessServiceImpl;

@WebServlet("/GetKindServlet")
public class GetKindServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if(id==null){
			return;
		}
		BusinessService service = new BusinessServiceImpl();
		List list = service.getKind(id);		//获取当前用户所有分组
		if(list==null){
			return;
		}
		String kind = "";
		for(Object k : list){
			kind = kind+k+",";
		}
		kind = kind.substring(0,kind.length()-1);
		PrintWriter pw = response.getWriter();
		pw.write(kind);
		pw.close();
	}

}
