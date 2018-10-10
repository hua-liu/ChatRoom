function switchUl(e){	//好友选项卡切换
	var node = e.nextSibling;
	if(node.style.display=='none'){
		node.style.display="block";
		e.setAttribute("class","current");
	}else{
		node.style.display="none";
		e.setAttribute("class","nocurrent");
	}
}
var xmlHttp,userid,path;
function start(id,pa) {
	userid=id;path=pa;
	try {
		xmlHttp = new XMLHttpRequest();	//创建AJAX引擎
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
	checkOnline();	//调用在线检测
	window.setTimeout(receive,2000);	//延时接收信息
	window.setInterval(checkOnline, 5000);	//调用在线检测
	window.setInterval(receive, 2000);//设置信息接收
}
var lis,i=0,onlineTimer;
function checkOnline(){
	lis = document.getElementsByTagName("li");
	onlineTimer=window.setInterval(requestOnline, 100);	//调用检测在线
}

function requestOnline(){	//在线检测
	if(i>=lis.length){	//循环检测
		window.clearInterval(onlineTimer);
		i=0;
		return;
	}
	var id = lis[i].getElementsByTagName("input")[0].value;
	if(xmlHttp){
		var url = document.getElementById("path").value+"/GetServlet";
		xmlHttp.open("post", url,true);
		var data = "type=online&id="+id;
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttp.onreadystatechange=function(){
			if(xmlHttp.readyState==4){
				var result = xmlHttp.responseText;
				var img = lis[i].getElementsByTagName("img")[0];
				var span = lis[i++].getElementsByTagName("span")[0];
				if(result=='true'){		//返回真则在线
					img.style.opacity="1";	//头像设置为不透明
					span.innerHTML="在线";
				}else{
					img.style.opacity="0.5";	//不在线头像设置为半透明
					span.innerHTML="离线";
				}
			}
		}
		xmlHttp.send(data);
	}
}

function scrollFunc(e){	//鼠标滚轮滚动事件
	var content = document.body;	//获取body
	e = e||window.event;	//获取鼠标事件
	if(e.wheelDelta){	//浏览器兼容
		if(e.wheelDelta > 0){	//鼠标滚轮向下
			content.scrollTop = content.scrollTop-10;
		}
		if(e.wheelDelta<0){		//鼠标滚轮向上
			content.scrollTop = content.scrollTop+10;
		}
	}else if(e.detail){//浏览器兼容
		if(e.detail>0){	//鼠标滚轮向下
			content.scrollTop = content.scrollTop-10;
			pare.scrollTop = pare.scrollTop;
		}
		if(e.detail < 0){//鼠标滚轮向上
			content.scrollTop = content.scrollTop+10;
		}
	}
}
function showInfo(ele,id,photoid,nickname,desc,path){	//显示用户信息
	var sp = ele.getElementsByTagName("span")[0].innerHTML;
	parent.document.getElementById("userInfo").style.display="block";
	parent.document.getElementById("infoId").value=id;
	parent.document.getElementById("infoImg").setAttribute("src",path+"/SysPhotoServlet?id="+photoid);
	parent.document.getElementById("infoText3").innerHTML=nickname;
	parent.document.getElementById("infoText4").innerHTML=desc;
	parent.document.getElementById("isOnline").value=sp;
}
function showCharWin(e,fid,fname,path,pid){	//显示聊天窗口
	if(checkWin(fid)){		//显示聊天窗口是否存在，不存在返回真
		var charWin = createCharWin(e,'',fid,fname,pid);
		requestMessage1(charWin);
	}
}
function receive(){	//接收消息
	var lis1= document.getElementsByTagName("li");
	requestMessage(lis1);
}
var soundMessage;
function requestMessage(lis1){	//接收消息
	if(xmlHttp){
		var url = path+"/MessageServlet";
		xmlHttp.open("post", url,true);
		var data = "type=receive&id="+userid;
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttp.onreadystatechange=function(){
			if(xmlHttp.readyState==4){
				var result = xmlHttp.responseText;
				for(var m=0;m<lis1.length;m++){	//清除已有的新消息提示
					var newMessage = lis1[m].getElementsByClassName("new")[0];
					newMessage.innerHTML="";
					newMessage.style.display="none";
				}
				if(result!=''){
					if(result!=soundMessage){
						document.getElementById("sound").play();	//播放新消息铃声
						soundMessage = result;
					}
					var ev = eval("("+result+")");
					for(var j=0;j<lis1.length;j++){	//循环显示每个好友的消息条数
						var fid = lis1[j].getElementsByClassName("input")[0].value;
						var newMessage = lis1[j].getElementsByClassName("new")[0];
						newMessage.innerHTML="";
						newMessage.style.display="none";
						for(var k=0;k<ev.length;k++){
							if(ev[k].key==fid){
								newMessage.innerHTML=ev[k].value;
								newMessage.style.display="block";
							}
						}
					}
				}
			}
		}
		xmlHttp.send(data);
	}
}
function requestMessage1(charWindows){	//打开窗口时立马接收消息
	var fid = charWindows.getElementsByClassName("hid")[0].value;
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
						createMessage1(charWindows,fid,ee[j].value);
					}
				}
			}
		}
		xmlHttp.send(data);
	}
}
function createMessage1(ele,id,text1){	//创建消息
	var content = ele.getElementsByClassName("content")[0];		//获取当前聊天窗口
	var box = document.createElement("div");	//创建消息显示盒子
	box.setAttribute("class", "messageBox");
	content.appendChild(box);
	var img = document.createElement("img");	//显示当前用户头像
	img.setAttribute("class","messageImg");
	img.setAttribute("src",path+"/SysPhotoServlet?userid="+id);
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
	content.scrollTop=content.scrollHeight;//让滚动条在底部
	return prompt;
}
var delTimer;
function showDel(ele){	//鼠标放上两秒后显示删除
	delTimer = window.setTimeout(function() {
		ele.getElementsByClassName("del")[0].style.display="block";
	}, 2000);
}
function hideDel(ele){	//鼠标移出后隐藏删除
	ele.getElementsByClassName("del")[0].style.display="none";
	window.clearTimeout(delTimer);
}
function showDel1(ele){	//鼠标移上删除后显示删除
	ele.style.display="block";
}
function delFriend(e,fid,fname){	//点击删除后显示确定删除对话框
	if(e && e.stopPropagation){	//取消冒泡事件
	   //W3C取消冒泡事件
	   e.stopPropagation();
	   }else{
	   //IE取消冒泡事件
	   window.event.cancelBubble = true;
	   }
	var delParent = parent.document.getElementById("delFriend");
	delParent.getElementsByClassName("fname")[0].innerHTML=fname;
	delParent.getElementsByClassName("fid")[0].value=fid;
	delParent.style.display="block";
}
