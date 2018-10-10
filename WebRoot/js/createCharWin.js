function checkWin(id){	//检查窗口是否已被创建
	var cw = parent.document.getElementsByClassName("charWindow");
	if(cw==null){	//如果为空则没创建返回真
		return true;
	}
	if(cw.length<1){	//如果长度小于1也说明没创建返回真
		return true;
	}
	var bool = true;
	for(var i=0;i<cw.length;i++){	//如果有多个窗口则循环判断是否被创建过
		var val = cw[i].getElementsByClassName("hid")[0].value;
		if(val==id){
			cw[i].style.zIndex=10;	//如果发现窗口被创建则将窗口置顶
			bool=false;
			continue;
		}
		cw[i].style.zIndex=0;	
	}
	return bool;
}
function createCharWin(e,isOnline,fid,fname,pid){	//创建聊天窗口
	var charWin = document.createElement("div");	//创建一个div
	charWin.setAttribute("class", "charWindow");	//设置类
	charWin.setAttribute("onmouseover","move(this)");	//设置拖动事件
	var title = document.createElement("div");	//创建标题div
	title.setAttribute("class","title");
	if(isOnline==''){	//如果是否在线为空，则向sapn获取
		isOnline = e.getElementsByTagName("span")[0].innerHTML;
		if(isOnline=='在线'){
			title.innerHTML="正在与  <font size='5'>"+fname+"</font> 聊天";		
		}else{
			title.innerHTML="好友  <font size='5'>"+fname+"</font> 不在线,可能无法及时回复您";		
		}
	}else{
		if(isOnline.value=='在线'){
			title.innerHTML="正在与附近人  <font size='5'>"+fname+"</font> 聊天";		
		}else{
			title.innerHTML="附近人  <font size='5'>"+fname+"</font> 不在线,可能无法及时回复您";		
		}
	}
	
	var close = document.createElement("div");	//创建一个关闭窗口div
	close.setAttribute("class", "close");
	close.setAttribute("onclick", "closeWin2(this)")
	close.innerHTML="✘";
	charWin.appendChild(close);
	var content = document.createElement("div");	//创建一个聊天内容显示框div
	content.setAttribute("class","content");
	var footMenu = document.createElement("div");	//创建一个存入输入框，发送按钮，表情
	footMenu.setAttribute("class","footMenu");
	charWin.appendChild(title);
	charWin.appendChild(content);
	charWin.appendChild(footMenu);
	var face = document.createElement("div");
	face.setAttribute("class","face");
	var input = document.createElement("input");
	input.setAttribute("class","input");
	input.setAttribute("placeholder", "输入消息");
	input.setAttribute("onkeypress", "if(event.keyCode==13)sendMessage(this)")	//如果按下回车调用发送消息
	input.setAttribute("type", "text");
	var hid = document.createElement("input");
	hid.setAttribute("type", "hidden");
	hid.setAttribute("class", "hid");
	hid.value=fid;
	footMenu.appendChild(hid);
	var pid = document.createElement("input");
	pid.setAttribute("type", "hidden");
	pid.setAttribute("class", "pid");
	pid.value=pid;
	footMenu.appendChild(pid);
	var button = document.createElement("button");
	button.innerHTML="发送"
	button.setAttribute("class","send");
	button.setAttribute("onclick", "sendMessage(this)")
	footMenu.appendChild(face);
	footMenu.appendChild(input);
	footMenu.appendChild(button);
	parent.document.body.appendChild(charWin);
	return charWin;
}