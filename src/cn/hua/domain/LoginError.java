package cn.hua.domain;

public class LoginError {
	private String username="";		//用户名错误
	private String password="";	//密码错误
	private String autologin="";	//自动登陆
	private String verifycode="";	//登陆验证码，暂时没用
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAutologin() {
		return autologin;
	}
	public void setAutologin(String autologin) {
		this.autologin = autologin;
	}
	public String getVerifycode() {
		return verifycode;
	}
	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}
	
}
