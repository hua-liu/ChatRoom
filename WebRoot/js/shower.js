/**
 * Created by 刘华 on 2016/5/10.
 */
var isOk = [1,1,1,1];
var xmlHttp;
var isIng=true;
var num=2;
window.onload=function(){
    window.setInterval(start,300);
    window.setInterval(clearElem,300);
    	try {
    		xmlHttp = new XMLHttpRequest();
    	} catch (e) {
    		try {
    			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
    		} catch (e) {
    			try {
    				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    			} catch (e) {
    				alert("您的浏览器不支持AJAX！");
    				return false;
    			}
    		}
    	}
}
function start(){	//显示流星雨
    var $img = $("<p class='img'>");
    var x =randX();
    var w = randW();
    $img.css({'left':x,'width':w,'height':w});
    $(".boxRain").append($img);
    $img.animate({left:x-1000,top:1000},3000);
    var wid = document.body.clientWidth;
    var hei = document.body.clientHeight;
    $(".boss").css({width:wid,height:hei});
    $(".boxRain").css({width:wid,height:hei});
    $(".boxData").css({width:wid,height:hei});
}
function randX(){	//获得随机坐标
    return Math.random()*document.body.clientWidth+300;
}
function randW(){//获得随机坐标
    return Math.random()*41+60
}
function clearElem(){	//当流星雨坐标已超过指定位置则清除节点
   var img= document.getElementsByClassName("img")[0];
    if(img.offsetTop>=1000){
        img.parentNode.removeChild(img);
    }
}
function showRegister(){	//显示注册
  $(".register").stop().show(500);
    $(".box").stop().hide(500);
}
function showLogin(){		//显示登陆
    $(".register").stop().hide(500);
    $(".box").stop().show(500);
}
function showSysPhoto(url){		//显示系统图像框
	var id = $("#photo").val();
	if(id!=null){
		url = url+"?defaultId="+id;
	}
	$(".sysPhoto").attr("src", url);
   $(".sysPhoto").stop().show(500);
}
function showCusPhoto(url){	//显示用户图像
	var id = $("#photo").val();
	if(id!=null){
		url = url+"?defaultId="+id;
	}
	$(".customPhoto").attr("src", url);
   $(".customPhoto").stop().show(500);
}
function checkData(path){		//检查注册数据
	if(!isIng){
		return;
	}
	isIng=false;
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	var password2 = document.getElementById("password2").value;
	var nickname = document.getElementById("nickname").value;
	var photoid = document.getElementById("photo").value;
	if(username==''||password==''||password2==''||nickname==''){
		isIng=true;
		return;
	}
	if(isOk.indexOf(0)!=-1){
		isIng=true;
		return;
	}
	if(xmlHttp!=null){
		num=2;	//初始化倒计时值
		var url = path+"/RegisterServlet";
		var data = "username="+username+"&password="+password+
		"&password2="+password2+"&nickname="+nickname+"&photoid="+photoid;
		xmlHttp.open("post", url,true);
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttp.onreadystatechange=function(){
		if(xmlHttp.readyState==4){
			var result = xmlHttp.responseText;
			var resultbox= document.getElementById("registerResult");
			var resultShow = resultbox.childNodes;
			resultbox.style.display="block";
			var timer1 = window.setInterval(function(){	//设置倒计时3秒
				if(num<0){
					clearInterval(timer1);
					if(result=='true'){
						clearInfor();
					}else{
						for(var i=0;i<3;i++)
						resultShow[i].innerHTML="";
						resultbox.style.display="none";
					}
					isIng=true;
					return;
				}
				resultShow[1].innerHTML=num--;
			},1000);
			resultShow[1].innerHTML=3;
			isIng=true;
			if(result=='true'){
				resultShow[0].innerHTML="注册成功";
				resultShow[2].innerHTML="3秒后跳转至登陆";
			}else if(result=='false'){
				resultShow[0].innerHTML="注册失败,稍后再试";
				resultShow[2].innerHTML="3秒后返回注册";
			}else{
				resultShow[0].innerHTML="注册失败,格式错误";
				resultShow[2].innerHTML="3秒后返回注册";
				var error = eval("("+result+")");
				if(error.nn!=''){
					isOk[3]=0;
					document.getElementById("nicknameError").innerHTML=error.nn;
				}
				if(error.un!=''){
					isOk[0]=0;
					document.getElementById("usernameError").innerHTML=error.un;
				}
				if(error.pd!=''){
					isOk[1]=0;
					document.getElementById("passwordError").innerHTML=error.pd;
				}
				if(error.pd2!=''){
					isOk[2]=0;
					document.getElementById("password2Error").innerHTML=error.pd2;
				}
			}
		}
	}
		xmlHttp.send(data);
	}
	
}
function checkUsername(e,path){	//检测用户名是否被使用
	var error = document.getElementById("usernameError");
	error.innerHTML="";
	var val = e.value;
	if(val==''){
		error.innerHTML="用户名不能为空";
		isOk[0] = 0;
		return;
	}
	var exp = /^[A-Za-z0-9]+$/;
	if(!exp.test(val)){
		error.innerHTML="必须字母或数字";
		isOk[0] = 0;
		return;
	}
	if(val.length<5||val.length>20){
		error.innerHTML="长度需在5-20位";
		isOk[0] = 0;
		return;
	}
	if(xmlHttp!=null){
		var url = path+"/VerifyDataServlet";
		var data = "username="+val;
		xmlHttp.open("post", url,true);
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttp.onreadystatechange=function(){
			if(xmlHttp.readyState==4){
				var result = xmlHttp.responseText;
				if(result=='true'){
					error.innerHTML ="<font style='color:green'>用户名可用✔</font>";
					isOk[0] = 1;
				}else if(result=='false'){
					error.innerHTML ="用户名已被使用✘";
					isOk[0] = 0;
				}else{
					error.innerHTML ="发生错误";
					isOk[0] = 0;
				}
			}
		}
		xmlHttp.send(data);
	}
}
function checkPassword(e){	//检查密码
	var error = document.getElementById("passwordError");
	error.innerHTML="";
	var val = e.value;
	if(val==''){
		error.innerHTML="密码不能为空";
		isOk[1] = 0;
		return;
	}
	var exp = /^[A-Za-z0-9]+$/;
	if(!exp.test(val)){
		error.innerHTML="必须字母或数字";
		isOk[1] = 0;
		return;
	}
	if(val.length<6||val.length>20){
		error.innerHTML="长度需在6-20位";
		isOk[1] = 0;
		return;
	}
	var password2 = document.getElementById("password2");
	var error1 = document.getElementById("password2Error");
	if(password2.value!=''&&password2.value!=val){
		error1.innerHTML="两次密码不一致";
		return;
	}
	isOk[1] = 1;
	isOk[2] = 1;
	error1.innerHTML="";
}
function checkPassword2(e){	//检查重复密码
	var error = document.getElementById("password2Error");
	error.innerHTML="";
	var val = e.value;
	if(val==''){
		error.innerHTML="密码不能为空";
		isOk[2] = 0;
		return;
	}
	var password = document.getElementById("password");
	if(password.value!=val){
		error.innerHTML="两次密码不一致";
		isOk[2] = 0;
		return;
	}
	isOk[2] = 1;
}
function checkNickname(e,path){	//检查昵称是否被使用
	var error = document.getElementById("nicknameError");
	error.innerHTML="";
	var val = e.value;
	if(val==''){
		error.innerHTML="昵称不能为空";
		isOk[3] = 0;
		return;
	}
	if(val.length>25){
		error.innerHTML="长度已超出限制";
		isOk[3] = 0;
		return;
	}
	if(xmlHttp!=null){
		var url = path+"/VerifyDataServlet";
		var data = "nickname="+val;
		xmlHttp.open("post", url,true);
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttp.onreadystatechange=function(){
			if(xmlHttp.readyState==4){
				var result = xmlHttp.responseText;
				if(result=='true'){
					error.innerHTML ="<font style='color:green'>昵称可用✔</font>";
					isOk[3] = 1;
				}else if(result=='false'){
					error.innerHTML ="昵称已被使用✘";
					isOk[3] = 0;
				}
				else{
					error.innerHTML ="发生错误";
					isOk[3] = 0;
				}
			}
		}
		xmlHttp.send(data);
	}
}
function clearInfor(){	//清除注册框所有信息
	document.getElementById("username").value="";
	document.getElementById("password").value="";
	document.getElementById("password2").value="";
	document.getElementById("nickname").value="";
	document.getElementById("photo").value="";
	document.getElementById("nicknameError").innerHTML="";
	document.getElementById("usernameError").innerHTML="";
	document.getElementById("passwordError").innerHTML="";
	document.getElementById("password2Error").innerHTML="";
	parent.document.getElementById("photo1").setAttribute("class", "photo");
	parent.document.getElementById("photo2").setAttribute("class", "photo");
	var resultbox= document.getElementById("registerResult");
	var resultShow = resultbox.childNodes;	//获取所有字节点    
	for(var i=0;i<3;i++)
	resultShow[i].innerHTML="";
	resultbox.style.display="none";
	resultbox.parentNode.style.display="none";
	document.getElementById("login").style.display="block";
}
function loginCheckUsername(){	//检查登陆用户名错误区
	var usernameError = document.getElementById("loginUsernameError");
	usernameError.innerHTML="";
}
function loginCheckPassword(){	//检查密码错误区
	var passwordError = document.getElementById("loginPasswordError");
	passwordError.innerHTML="";
}
function autologinCheck(e){	//检查自动登陆
	if(e.checked){	//判断多先框是否被选中
		e.value="auto";
	}else{
		e.value="";
	}
}
function login(path){	//登陆
	var isPass = true;
	var username = document.getElementById("loginUsername").value;
	var password = document.getElementById("loginPassword").value;
	var autologin = document.getElementById("autologin").value;
	var usernameError = document.getElementById("loginUsernameError");
	var passwordError = document.getElementById("loginPasswordError");
	usernameError.innerHTML="";
	passwordError.innerHTML="";
	if(username==''){
		usernameError.innerHTML="用户名不能为空";
		isPass = false;
	}
	if(password==''){
		passwordError.innerHTML="密码不能为空";
		isPass = false;
	}
	if(!isPass){
		return;
	}
	if(xmlHttp!=null){
		var url = path+"/LoginServlet";
		var data = "username="+username+"&password="+password+"&autologin="+autologin;
		xmlHttp.open("post", url,true);
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttp.onreadystatechange=function(){
			if(xmlHttp.readyState==4){
				var warningEle = document.getElementById("warning");
				var result = xmlHttp.responseText;
				if(result=='true'){
					window.location.href=path+"/Char";
				}else{
					warningEle.innerHTML = result;
					warningFunc();
					return;
				}
			}
		}
		xmlHttp.send(data);
	}
	
}
var warningTimer;
function warningFunc(){	//调用提示框
	warningTimer = window.setInterval(warningIn,20);
}
function warningFunc2(){//调用提示框
	warningTimer = window.setInterval(warningOut,20);
}
var aplha=0;
function warningIn(){	//提示框向下
	aplha+=0.1;
	var warningEle = document.getElementById("warning");
	warningEle.style.opacity = aplha;
	if(warningEle.style.opacity>=1){
		window.clearInterval(warningTimer);
		window.setTimeout(warningFunc2,3000);
	}
}
function warningOut(){//提示框向上
	aplha -=0.1;
	var warningEle = document.getElementById("warning");
	warningEle.style.opacity = warningEle.style.opacity-0.1;
	if(warningEle.style.opacity<=0){
		window.clearInterval(warningTimer);
	}
}