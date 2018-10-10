package cn.hua.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hua.domain.Users;
import cn.hua.service.BusinessService;
import cn.hua.service.impl.BusinessServiceImpl;
import cn.hua.utils.MD5Utils;
@WebFilter("/AutoLogin")
public class AutoLogin implements Filter {
	public void destroy() {
		// TODO Auto-generated method stub
	}
	//自动登陆拦截器
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if(request.getSession().getAttribute("user")!=null){	//如果session有用户则直接放行
			chain.doFilter(request, response);
			return;
		}
		Cookie[] cooks = request.getCookies();	//读取浏览器带过来的Cook
		if(cooks==null){
			response.sendRedirect(request.getContextPath()+"/Login");
			return;
		}
		if(cooks.length<0){	//如果cook不存在则直接跳转直登陆
			response.sendRedirect(request.getContextPath()+"/Login");
			return;
		}
		String value="";
		for(Cookie cook : cooks){
			if(cook.getName().equals("autoLogin")){	//找出名字为autoLogin的cook
				value = cook.getValue();
				break;
			}
		}
		if(value==""){	//如果没有自动登陆cook则直接转直登陆
			response.sendRedirect(request.getContextPath()+"/Login");
			return;
		}
		String[] val = value.split(":");
		if(val.length!=3){	//如果长度不是3则说明自动cook被修改
			response.sendRedirect(request.getContextPath()+"/Login");
			return;
		}
		Long time;
		try{
			time = Long.parseLong(val[1]);	//如果cook中的时间不能正常转换则说明cook无效
		}catch(NumberFormatException e){
			response.sendRedirect(request.getContextPath()+"/Login");
			return;
		}
		if((time+1*60*60*24*7)<System.currentTimeMillis()){	//如果cook时间加上7天小于系统当前时间则无效
			response.sendRedirect(request.getContextPath()+"/Login");
			return;
		}
		BusinessService service = new BusinessServiceImpl();
		Users user = service.findUser(val[0]);	//查找cook用户是否存在
		if(user==null){
			response.sendRedirect(request.getContextPath()+"/Login");
			return;
		}
		String password = service.getPS(user.getId());	//获取用户密码
		if(password==null){
			response.sendRedirect(request.getContextPath()+"/Login");
			return;
		}
		String data = MD5Utils.md5(user.getId()+password+val[1]+"851860021");	//对数据进行加密后比对
		if(!data.equals(val[2])){	//如果比对失败则无效
			response.sendRedirect(request.getContextPath()+"/Login");
			return;
		}
		request.getSession().setAttribute("user", user);	//所有验证都通过则将用户存入session后放行
		chain.doFilter(request, response);
	}
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
