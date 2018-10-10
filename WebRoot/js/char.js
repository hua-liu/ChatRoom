var xmlHttp;
var userid,nickname,photoid,path;	//当前用户id,昵称，头像id，工程目录
function start(id,name,photo,path1){	//将当前用户id,昵称，头像id，工程目录初始化
	userid=id;nickname=name;photoid=photo;path = path1;
	try {
		xmlHttp = new XMLHttpRequest();		//创建ajax引擎
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
	window.setInterval(stateCheck, 30000);	//30秒检查是否连接数据库
	window.setInterval(receive, 3000);	//3秒接收数据
}
var num=2;
function stateCheck(){	//状态更新与检测
	if(xmlHttp){
		num=2;	//初始化倒计时值
		var path = document.getElementById("path").value;
		var userid = document.getElementById("userid").value;
		var url = path+"/GetServlet";
		var data = "type=checkState&id="+userid;
		xmlHttp.open("post", url,true);
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttp.onreadystatechange=function(){
		if(xmlHttp.readyState==4){
				var result = xmlHttp.responseText;
				if(result!='true'){		//如果返回结果没是true则断开连接
				var resultbox= document.getElementById("checkResult");
				var resultShow = resultbox.getElementsByTagName("p");
				resultbox.style.display="block";
				resultShow[0].innerHTML="与服务器断开连接";
				resultShow[2].innerHTML="3秒后跳转至登陆";
				var timer1 = window.setInterval(function(){	//设置倒计时3秒
				if(num<0){	//当时间为0时清除定时器
					clearInterval(timer1);
					for(var i=0;i<3;i++)
					resultShow[i].innerHTML="";	//清除定时器内容
					resultbox.style.display="none";		//隐藏定时器
					window.location.href=path+"/Login";	//跳转地址
					return;
				}
				resultShow[1].innerHTML=num--;	//数字倒计时
			},1000);
			resultShow[1].innerHTML=3;
			
			}
		}
	}
		xmlHttp.send(data);
	}
}
function changClass(e){	//排它
    e.setAttribute("class","current");
    if(e.nextSibling){
        e.nextSibling.setAttribute("class","other");
    }else if(e.previousSibling){
        e.previousSibling.setAttribute("class","other");
    }
}
function closeWin(e){	//隐藏窗口
	e.parentNode.style.display="none";
}
function closeWin2(e){	//关闭窗口
	e.parentNode.parentNode.removeChild(e.parentNode);
}
function showAddFriend(id,path){	//显示添加好友
	document.getElementById("addFriend").style.display="block";
	document.getElementById("kind").innerHTML="";		//先置空元素
	var infoId = document.getElementById("infoId").value;
	if(infoId==null){	//如果没获取到ID直接退出函数
		return;
	}
	document.getElementById("friend_id").value = infoId;	//将ID存入隐藏项
	if(xmlHttp!=null){	//获取当前用户所有分组
		var url = path+"/GetKindServlet";
		var data = "id="+id;
		xmlHttp.open("post", url,true);
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttp.onreadystatechange=function(){
			if(xmlHttp.readyState==4){
				var result = xmlHttp.responseText;
				if(result==''){	//如果结果为空，则自动创建一个‘我的好友’分组
					var li = document.createElement("li");
					li.innerHTML="我的好友";
					li.setAttribute("onclick", "selectKind(this)");	//调用选择分组函数
					li.setAttribute("class", "current");	//默认选中
					document.getElementById("kind").appendChild(li);	//将节点添加到父级节点
					return;
				}
				else{
					var kind = result.split(",");	//将分组以，分割
					if(kind.length<1){		//如果长度少于1，自动创建一个‘我的好友’
						var li = document.createElement("li");
						li.innerHTML="我的好友";
						li.setAttribute("onclick", "selectKind(this)");
						li.setAttribute("class", "current");	//默认选中
						document.getElementById("kind").appendChild(li);
						return;	
					}
					for(var i=0;i<kind.length;i++){	//循环创建分组
						var li = document.createElement("li");
						li.innerHTML=kind[i];
						li.setAttribute("onclick", "selectKind(this)")
						document.getElementById("kind").appendChild(li);
						if(i==0){
							li.setAttribute("class", "current");	//默认选中第一个分组
						}
					}
					return;
				}
			}
		}
		xmlHttp.send(data);
	}
}
function showCharWin(ele,path){	//显示聊天界面
	var fid = document.getElementById("infoId").value;
	var fname = document.getElementById("infoText3").innerHTML;
	var isOnline = document.getElementById("isOnline");	//获取是否在线并传过去
	closeWin(ele);		// 关闭信息框
	createCharWin(ele,isOnline,fid,fname);//创建聊天窗口
}
function addKind(){	//添加分组
	var ul = document.getElementById("kind");
	var li = document.createElement("li");
	var input = document.createElement("input");
	input.setAttribute("placeholder", "输入分组名");	//设置提示文字
	li.setAttribute("onclick", "selectKind(this)")	//设置选中分组
	li.appendChild(input);
	ul.appendChild(li);
	input.focus();	//设置获取焦点
	input.setAttribute("onblur", "inputDone(this)")	//当失去焦点调用输入完成函数
	input.setAttribute("onkeypress", "inputKeyDone(event,this)");	//当按下键盘按键时调用输入完成
}
function inputKeyDone(e,input){	//判断如果是回车则调用输入完成函数
	if(e.keyCode==13){
		inputDone(input);
	}
}
function inputDone(input){	//将输入的内容赋给父级（li）
	var text = input.value;
	input.parentNode.innerHTML=text;
}
function selectKind(li){	//选中分组
	var child = li.parentNode.childNodes;
	for(var i=0;i<child.length;i++){	//循环移除所有类属性
		child[i].removeAttribute("class");
	}
	li.setAttribute("class", "current")	//添加当前类属性
}
function add(path,id){	//添加好友
	var friendid = document.getElementById("infoId").value;	//获取保存的好友id
	var kindChild = document.getElementById("kind").childNodes;	//获取分类ul
	var selectKindValue;
	for(var i=0;i<kindChild.length;i++){	//获取选中分组的名字
		if(kindChild[i].className=='current'){		//如果类的名字等于current
			selectKindValue = kindChild[i].innerHTML;
		}
	}
	if(xmlHttp!=null){
		var url = path+"/AddFriendServlet";
		var data = "id="+id+"&friendId="+friendid+"&kind="+selectKindValue;
		xmlHttp.open("post", url,true);
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttp.onreadystatechange=function(){
			if(xmlHttp.readyState==4){
				var warningEle = document.getElementById("warning");
				var kindEle = document.getElementById("kind");
				var result = xmlHttp.responseText;
				if(result==''){
					warningEle.innerHTML="添加好友失败";
					warningFunc();	//调用消息提示框显示结果
					closeWin(kindEle);
				}else{
					warningEle.innerHTML=result;
					warningFunc();	//调用消息提示框显示结果
					closeWin(kindEle);
				}
			}
		}
		xmlHttp.send(data);
	}
}
var warningTimer;
function warningFunc(){	//调用提示框
	warningTimer = window.setInterval(warningIn,20);	//20毫秒调用消息显示框显示
}
function warningFunc2(){//调用提示框
	warningTimer = window.setInterval(warningOut,20);//20毫秒调用消息显示框隐藏
}
function warningIn(){	//提示框显示运动
	var warningEle = document.getElementById("warning");
	warningEle.style.top = warningEle.offsetTop+3;
	if(warningEle.offsetTop>=0){	//如果提示框top为0，则说明已经显示在顶部，清除定时
		window.clearInterval(warningTimer);
		window.setTimeout(warningFunc2,3000);
	}
}
function warningOut(){//提示框隐藏运动
	var warningEle = document.getElementById("warning");
	warningEle.style.top = warningEle.offsetTop-3;
	console.log(warningEle.offsetTop);
	if(warningEle.offsetTop<=-60){	//如果提示框top为-60，则说明已经看不到了，清除定时
		window.clearInterval(warningTimer);
	}
}
function move(ele){		//移动元素函数
	var bool;	//保存鼠标是否放开
	var x,y;
	ele.onmousedown = function(event){	//当鼠标按下
		bool = true;	//设置鼠标为按下状态
		var e = event ? event: window.event;	//获取当前事件源
		x = e.clientX-ele.offsetLeft, y = e.clientY-ele.offsetTop;	//获取当前鼠标在该元素上的位置
	}
	document.onmouseup=function(event){	//鼠标松开设置松开状态
		bool=false;
	}
	document.onmousemove = function(event){	//鼠标移动
		var e = event ? event: window.event;
		if(bool){	//如果鼠标状态为按下
			var nowX = e.clientX, nowY = e.clientY;	//获取当前鼠标位置
			ele.style.left = nowX-x + "px";	//设置元素左坐标为当前鼠标位置减去鼠标在该元素上的位置
			ele.style.top = nowY-y + "px";
		}
	}
}
function sendMessage(ele){	//发送消息函数
	var foot = ele.parentNode;
	var mes = foot.getElementsByClassName("input")[0];	//获取消息内容
	if(mes==null){	//如果消息为空返回
		return;
	}
	var text = mes.value;
	if(text==''){	//如果消息为空返回
		return;
	}
	var fid = foot.getElementsByClassName("hid")[0].value;	//获取隐藏的好友id
	var prompt = createMessage(mes,userid,text,photoid,nickname);	//创建显示的信息
	send(foot,fid,text,prompt);	//发送信息
}
function send(foot,fid,text,prompt){	//发送信息函数
	var content = foot.parentNode;
	if(xmlHttp){
		xmlHttp.open("post", path+"/MessageServlet" , true);
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		var data = "type=send&id="+userid+"&fid="+fid+"&content="+text;
		xmlHttp.onreadystatechange = function(){
			if(xmlHttp.readyState==4){
				var result = xmlHttp.responseText;
				if(result=='true'){	//此时显示信息发送成功没启用
					prompt.innerHTML="发送成功";
					window.setTimeout(function() {
						prompt.style.display="none";
					}, 3000);
				}else{
					prompt.innerHTML="发送失败";
				}
			}
		}
		xmlHttp.send(data);
	}
}
var charWindows,i=0,messageTimer;
function receive(){	//接收所有聊天窗口信息
	charWindows= document.getElementsByClassName("charWindow");
	messageTimer=window.setInterval(requestMessage, 1000);	//调用检测在线
}

function requestMessage(){	//接收信息
	if(i>=charWindows.length){	//如果i大于窗口数则消息接收完毕，清除接收计时器
		window.clearInterval(messageTimer);
		i=0;
		return;
	}
	var fid = charWindows[i].getElementsByClassName("hid")[0].value;	//获取当前窗口好友ID
	if(xmlHttp){
		var url = path+"/MessageServlet";
		xmlHttp.open("post", url,true);
		var data = "type=receiveOne&id="+userid+"&fid="+fid;
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttp.onreadystatechange=function dispose(){
			if(xmlHttp.readyState==4){
				var result = xmlHttp.responseText;
				if(result!=''){
					var ee = eval("("+result+")");
					for(var j=0;j<ee.length;j++){
						createMessage1(charWindows[i],fid,ee[j].value);	//将结果显示至聊天窗口
					}
				}
				i++;//接收完一个窗口后继续下一个窗口
			}
		}
		xmlHttp.send(data);
	}
}
function createMessage1(ele,id,text1){	//创建信息显示对话框
	var content = ele.getElementsByClassName("content")[0];		//获取当前聊天窗口
	var boxs = content.getElementsByClassName("messageBox");
	if(boxs!=null)
	if(boxs.length>20){
		content.removeChild(boxs[0]);
	}
	var box = document.createElement("div");	//创建消息显示盒子
	box.setAttribute("class", "messageBox");
	content.appendChild(box);
	var img = document.createElement("img");	//显示当前用户头像
	img.setAttribute("class","messageImg");
	img.setAttribute("src",path+"/SysPhotoServlet?userid="+id);
	box.appendChild(img);
	var text = document.createElement("p");		//显示当前用户发送的内容
	text.setAttribute("class","messageText");
	textChuli(text1);
	text.innerHTML=text1;
	box.appendChild(text);
	var prompt;
	if(id==userid){					//如果判断为自己发送的内容，则增加一个判断是否发送成功的标签
		box.setAttribute("class", "myMessageBox");
		prompt = document.createElement("p");
		prompt.setAttribute("class","messagePrompt");
		prompt.innerHTML="正在发送";
		box.appendChild(text);
	}
	content.scrollTop=content.scrollHeight;//让滚动条在底部
	return prompt;
}
function textChuli(text){
	if(text.length<20){	//如果字符串长度小于20则直接返回
		return text;
	}
	for(var i=20;i<text.length;i+=20){	//字符串每20个字符添加一个换行
		var str1 = text.substring(0,i);
		var str2 = text.substring(i+1);
		text = str1+"<br/>"+str2;
	}
	return text;
	
}
function createMessage(ele,id,text1,photoid){	//创建消息，好友消息，好像跟上面差不多，有多余
	ele.value='';
	var content = ele.parentNode.parentNode.getElementsByClassName("content")[0];		//获取当前聊天窗口
	var boxs = content.getElementsByClassName("messageBox");
	if(boxs!=null)
	if(boxs.length>20){
		content.removeChild(boxs[0]);
	}
	var box = document.createElement("div");	//创建消息显示盒子
	box.setAttribute("class", "messageBox");
	content.appendChild(box);
	var img = document.createElement("img");	//显示当前用户头像
	img.setAttribute("class","messageImg");
	img.setAttribute("src",path+"/SysPhotoServlet?id="+photoid);
	box.appendChild(img);
	var text = document.createElement("p");		//显示当前用户发送的内容
	text.setAttribute("class","messageText");
	text.innerHTML=text1;
	box.appendChild(text);
	var prompt;
	if(id==userid){					//如果判断为自己发送的内容，则增加一个判断是否发送成功的标签
		box.setAttribute("class", "myMessageBox");
		prompt = document.createElement("p");
		prompt.setAttribute("class","messagePrompt");
		prompt.innerHTML="正在发送";
		box.appendChild(text);
	}
	content.scrollTop=content.scrollHeight;	//让滚动条在底部
	return prompt;
}
function showModifyInfo(){	//显示修改个人信息窗口
	document.getElementById("modifyInfo").style.display="block";
}
function inputValue(ele){	//当点击个人信息时创建一个输入框
	var val = ele.innerHTML;
	var input = document.createElement("input");
	input.setAttribute("onblur", "inputDone(this)")	//当失去焦点时调用输入完成
	input.setAttribute("onkeypress", "inputKeyDone(event,this)");
	ele.appendChild(input);
	input.focus();
	input.value=val;	//将当前信息加载到输入框
}
function updateInfo(ele){	//更新个人信息
	ele = ele.parentNode;
	var nickname = ele.getElementsByClassName("text3")[0].innerHTML;	//获取昵称内容
	var description = ele.getElementsByClassName("text4")[0].innerHTML;	//获取个人说明内容
	var warningEle = document.getElementById("warning");
	if(nickname==''){	//如果没输入昵称则调用提示框提示
		warningEle.innerHTML="昵称不能为空";
		warningFunc();
		return;
	}
	if(xmlHttp){
		xmlHttp.open("post", path+"/UpdateServlet", true);//打开一个ajax连接
		var data = "type=updateInfo&id="+userid+"&nickname="+nickname+"&description="+description;
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttp.onreadystatechange=function(){
			if(xmlHttp.readyState==4){
				var result = xmlHttp.responseText;
				if(result=='true'){
					warningEle.innerHTML="更新成功";
					warningFunc();
				}else{
					warningEle.innerHTML="更新失败";
					warningFunc();
				}
			}
		}
		xmlHttp.send(data);
	}
}
function sureDel(ele){	//确定删除函数
	var fidEle = ele.parentNode.getElementsByClassName("fid")[0];	//获取好友ID
	var fid = fidEle.value;
	fidEle.value="";	//将好友ID置为空
	closeWin(ele);	//关闭窗口
	if(xmlHttp){
		xmlHttp.open("post", path+"/UpdateServlet", true);
		var data = "type=delFriend&id="+userid+"&fid="+fid;
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttp.onreadystatechange=function(){
			if(xmlHttp.readyState==4){
				var warningEle = document.getElementById("warning");
				var result = xmlHttp.responseText;
				if(result=='true'){
					warningEle.innerHTML="删除成功";
					warningFunc();	//删除成功后更新内联框架地址
					document.getElementById("content").src=path+"/GetFriendServlet?type=friend&id="+userid;
				}else{
					warningEle.innerHTML="删除失败";
					warningFunc();
				}
			}
		}
		xmlHttp.send(data);
	}
}
var pname=['橙色木板','青青草原','自行之路','绿色天意','这是什么','黑色夜空'];	//背景图名字数组
var sname=['妹子音效','MSN音效','QQ音效','其它音效'];	//音效名字数组
var surl=['/sound/msg.mp3','/sound/msn.mp3','/sound/qq1.mp3','/sound/sys.mp3'];	//音效名字地址数组
var sid=0;var pid=1;
function showSet(){	//显示设置窗口
	document.getElementById("sysSet").style.display="block";
	var content = document.getElementById("sysSet").getElementsByClassName("showContent");
	//设置当前设置项名字
	for(var i=0;i<sname.length;i++){
		if(content[0].innerHTML==sname[i]){
			sid=i;	//设置当前铃声ID
		}
		if(content[1].innerHTML==pname[i]){
			pid=i+1;	//设置当前背景ID
		}
	}
}
function systemSet(ele){	//更新当前设置
	if(xmlHttp){
		xmlHttp.open("post", path+"/UpdateServlet", true);
		var data = "type=updateSet&id="+userid+"&sid="+sid+"&pid="+pid;
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttp.onreadystatechange=function(){
			if(xmlHttp.readyState==4){
				var warningEle = document.getElementById("warning");
				var result = xmlHttp.responseText;
				if(result=='true'){
					warningEle.innerHTML="更新成功";
					warningFunc();
					closeWin(ele);
				}else{
					warningEle.innerHTML="更新失败";
					warningFunc();
				}
			}
		}
		xmlHttp.send(data);
	}
}
function soundRight(){	//铃声向右选择
	var sound = document.getElementById("content").contentWindow.document.getElementById("sound");
	//获取当前音乐播放控件
	sid++;
	if(sid>3){	//总共内置了4种音乐，循环
		sid=0;
	}
	document.getElementsByClassName("showContent")[0].innerHTML=sname[sid];	//设置音乐名字
	sound.src=path+surl[sid];	//设置音乐路径
	sound.play();	//播放音乐
}
function soundLeft(){	//铃声向右选择
	var sound = document.getElementById("content").contentWindow.document.getElementById("sound");
	var source = sound.getElementsByClassName("source");
	sid--;
	if(sid<0){
		sid=3;
	}
	document.getElementsByClassName("showContent")[0].innerHTML=sname[sid];
	sound.src=path+surl[sid];
	sound.play();
}
function bgRight(){	//背景图向右选择
	pid++;
	if(pid>6){
		pid=1;
	}
	document.getElementsByClassName("showContent")[1].innerHTML=pname[pid-1];
	document.body.style.background="url("+path+"/img/bg"+pid+".jpg)";
}
function bgLeft(){	//背景图向左选择
	pid--;
	if(pid<1){
		pid=6;
	}
	document.getElementsByClassName("showContent")[1].innerHTML=pname[pid-1];
	document.body.style.background="url("+path+"/img/bg"+pid+".jpg)";
}