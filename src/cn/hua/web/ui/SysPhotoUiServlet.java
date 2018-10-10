package cn.hua.web.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SysPhotoServlet
 */
@WebServlet("/SysPhotoUiServlet")
public class SysPhotoUiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String defaultId = request.getParameter("defaultId");
		request.setAttribute("defaultId", defaultId);	//这里好像多此一举了，不管了
		request.getRequestDispatcher("/WEB-INF/pages/sysPhoto.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
