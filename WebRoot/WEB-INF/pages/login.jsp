<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/register.css" rel="stylesheet" type="text/css">
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.2.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/shower.js"></script>
</head>
<body>
<div class="boss">
    <div class="boxRain"></div>
     <div class="boxData"> 
		<div class="box" style="display:block" id="login">
			<div class="warning" id="warning"></div>
		    <h1>登 陆 黑 色 夜 空</h1>
		    <form action="${pageContext.request.contextPath}/LoginServlet" method="post" id="loginform">
		        <table >
		            <tr>
		                <td><label for="loginUsername">用户名：</label></td>
		                <td><input type="text" class="in" name="username" id="loginUsername" placeholder="输入用户名" autocomplete="off" onblur="loginCheckUsername()" onkeypress="if(event.keyCode==13)login('${pageContext.request.contextPath}')"></td>
		                <td><span class="error" id="loginUsernameError"></span></td>
		            </tr>
		            <tr>
		                <td><label for="loginPassword">密　码：</label></td>
		                <td><input type="password" class="in" name="password" id="loginPassword" placeholder="输入密码" onblur="loginCheckPassword()"  onkeypress="if(event.keyCode==13)login('${pageContext.request.contextPath}')"></td>
		                <td><span class="error" id="loginPasswordError"></span></td>
		            </tr>
		            <tr>
		                <td></td>
		                <td><input type="checkbox" name="autologin" id="autologin" onblur="autologinCheck(this)"><label for="autologin">下次自动登陆</label></td>
		            </tr>
		            <tr>
		                <td></td>
		                <td>
		                    <input type="button" value="登　陆" class="button" onclick="login('${pageContext.request.contextPath}')">
		                    <input type="button" value="忘记密码" class="button">
		                </td>
		            </tr>
		            <tr>
		                <td></td>
		                <td><p>没有账号？<span class="regBut" onclick="showRegister()">点此注册</span></p></td>
		            </tr>
		        </table>
		    </form>
		</div>
		<div class="register">
		    <form>
		    <h1>注 册 黑 色 夜 空</h1>
		        <table >
		            <tr>
		                <td><label for="username">用&ensp;户&ensp;名：</label></td>
		                <td><input type="text" class="in" name="username" id="username" placeholder="输入用户名" autocomplete="off" onblur="checkUsername(this,'${pageContext.request.contextPath}')" maxlength="20"></td>
		                <td><span class="error" id="usernameError">${error.username }</span></td>
		            </tr>
		            <tr>
		                <td><label for="password">密　　码：</label></td>
		                <td><input type="password" class="in" name="password" id="password" placeholder="输入密码"  onblur="checkPassword(this)" maxlength="20"></td>
		                <td><span class="error" id="passwordError">${error.password}</span></td>
		            </tr>
		            <tr>
		                <td><label for="password2">确定密码：</label></td>
		                <td><input type="password" class="in" name="password2" id="password2" placeholder="再次输入密码"  onblur="checkPassword2(this)" maxlength="20"></td>
		                <td><span class="error" id="password2Error">${error.password2}</span></td>
		            </tr>
		            <tr>
		                <td><label for="nickname">昵　　称：</label></td>
		                <td><input type="text" class="in" name="username" id="nickname" placeholder="输入昵称" autocomplete="off"  onblur="checkNickname(this,'${pageContext.request.contextPath}')" maxlength="25"></td>
		                <td><span class="error" id="nicknameError">${error.nickname}</span></td>
		            </tr>
		            <tr>
		                <td>设置头像：</td>
		                <td><a id="photo1" class="photo" onclick="showSysPhoto('${pageContext.request.contextPath}/SysPhotoUiServlet')" target="photoIframe">系统默认</a><a id="photo2" class="photo" onclick="showCusPhoto('${pageContext.request.contextPath}/CustomPhotoServlet')" target="cusframe">自定义头像</a></td>
		                <td><input type="hidden" name="photoid" id="photo"/></td>
		            </tr>
		            <tr>
		                <td></td>
		                <td>
		                    <input type="button" value="注　册" class="button" onclick="checkData('${pageContext.request.contextPath}')">
		                    <input type="reset" value="重　置" class="button">
		                </td>
		            </tr>
		            <tr>
		                <td></td>
		                <td><p>已有账号？<span class="regBut" onclick="showLogin()">点此登陆</span></p></td>
		            </tr>
		        </table>
			</form>
		    <iframe class="sysPhoto" name="photoIframe" scrolling="no"></iframe> 
  		   <iframe class="customPhoto" scrolling="no" name="cusframe"></iframe>
  		   <div class="registerResult" id="registerResult"><p class="result"></p><p class="showtime"></p><p class="hint"></p></div>
		</div>
	</div>
 </div> 
</body>
</html>