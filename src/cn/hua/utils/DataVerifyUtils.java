package cn.hua.utils;

import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import cn.hua.domain.LoginError;
import cn.hua.domain.RegisterError;
//数据验证
public class DataVerifyUtils {
	public static RegisterError registerData(HttpServletRequest request){
		Enumeration<String> names = request.getParameterNames();
		RegisterError error = new RegisterError();
		boolean bool = true;
		String password = null;
		while(names.hasMoreElements()){
			String name = names.nextElement();
			String value = request.getParameter(name);
			if("username".equals(name)){
				if(value==null){
					error.setUsername("用户名不能为空");
					bool = false;
				}
				if(value.length()<5&&value.length()>20){
					error.setUsername("长度需在5-20位");
					bool = false;
				}
			}
			if("password".equals(name)){
				if(value==null){
					error.setPassword("密码不能为空");
					bool = false;
				}
				password = value;
				if(value.length()<6||value.length()>20){
					error.setPassword("长度需在6-20位");
					bool = false;
				}
			}
			if("password2".equals(name)){
				if(value==null){
					error.setPassword2("密码不能为空");
					bool = false;
				}
				if(!password.equals(value)){
					error.setPassword2("两次密码不一样");
					bool = false;
				}
			}
			if("nickname".equals(name)){
				if(value==null){
					error.setNickname("昵称不能为空");
					bool = false;
				}
				if(value.length()>25){
					error.setNickname("昵称长度超过限制");
					bool = false;
				}
			}
		}
		if(bool){
			return null;
		}
		return error;
	}
	//登陆数据验证
	public static LoginError loginData(HttpServletRequest request,String code){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String isVerify = request.getParameter("isVerify");
		String verifycode = request.getParameter("verifycode");
		LoginError error = new LoginError();
		boolean isOk = true;
		if(username.equals("")){
			error.setUsername("用户名不能为空");
			isOk = false;
		}
		if(password.equals("")){
			error.setPassword("密码不能为空");
			isOk = false;
		}
		/*if(!isVerify.equals("")){
			if(verifycode.equals("")){
				error.setVerifycode("验证码不能为空");
				isOk = false;
			}else{
				if(!verifycode.equals(code)){
					error.setVerifycode("验证码错误");
					isOk = false;
				}
				
			}
		}*/
		if(isOk){
			return null;
		}
		return error;
	}
}
