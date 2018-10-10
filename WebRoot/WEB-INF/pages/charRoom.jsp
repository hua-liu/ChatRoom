<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>title</title>
 </head>
<link href="${pageContext.request.contextPath}/css/char.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/char.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/sao.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/createCharWin.js"></script>
<body onload="start('${sessionScope.user.id}','${sessionScope.user.nickname }','${sessionScope.user.photoid }','${pageContext.request.contextPath}')" style="background-image:url('${pageContext.request.contextPath}/SysPhotoServlet?type=background&id=${user.id}')">
        <div id="menu" class="menu" onmouseover="move(this)">	<!--当鼠标进入区域  -->
            <div class="title">联系人</div>
            <iframe class="content" name="content" src="${pageContext.request.contextPath}/GetFriendServlet?type=friend&id=${user.id}" scrolling="no" id="content"></iframe>
            <div class="footMenu"><a class="current" id="friend" onclick="changClass(this)" href="${pageContext.request.contextPath}/GetFriendServlet?type=friend&id=${user.id}" target="content">好友</a><a class="other" id="nearby" onclick="changClass(this)"  href="${pageContext.request.contextPath}/GetFriendServlet?type=nearby&id=${user.id}" target="content">附近的人</a></div>
        </div>
       <!--  <div class="charWindow" id="charWindow" onmouseover="move(this)" >
            <div class="title">正在与谁聊天</div>
            <div class="content">聊天中</div>
            <div class="footMenu">
                <div class="face"></div>
                <input type="text" class="input">
                <button class="send">发送</button>
            </div>
        </div> -->
        <div class="userInfo" id="userInfo" onmouseover="move(this)">
        	<div class="close" onclick="closeWin(this)">✘</div>
        	<img class="img" id="infoImg"/>
        	<p class="text1">昵称</p>
        	<p class="text2">个性说明</p>
        	<p class="text3" id="infoText3"></p>
        	<p class="text4" id="infoText4"></p>
        	<input type="hidden" id="isOnline">
        	<input type="hidden" id="infoId">
        	<button class="but1" onclick="showAddFriend('${user.id}','${pageContext.request.contextPath}')">加为好友</button>
        	<button class="but2" onclick="showCharWin(this,'${pageContext.request.contextPath}')">发送消息</button>
        </div>
         <div class="modifyInfo" id="modifyInfo" onmouseover="move(this)" >
        	<div class="close" onclick="closeWin(this)">✘</div>
        	<img class="img" id="modifyInfoImg" src="${pageContext.request.contextPath}/SysPhotoServlet?id=${user.photoid}"/>
        	<p class="text1">昵称</p>
        	<p class="text2">个性说明</p>
        	<p class="text3" onclick="inputValue(this)">${user.nickname }</p>
        	<p class="text4" onclick="inputValue(this)">${user.description}</p>
        	<button class="but2" onclick="updateInfo(this)">更新信息</button>
        </div>
        <div class="addfriend" id="addFriend" onmouseover="move(this)" >
        	<p>选择分组</p>
        	<ul id="kind"></ul>
        	<input type="hidden" id="friend_id">
        	<p class="newKind" onclick="addKind(this)">＋</p><br/>
        	<button class="but1" onclick="add('${pageContext.request.contextPath}','${user.id}')">马上添加</button>
        	<button class="but2" onclick="closeWin(this)">取消</button>
        </div>
        <div class="warning">
        	<div class="content" id="warning"></div>
        </div>
        <div class="checkResult" id="checkResult"><p class="result"></p><p class="showtime"></p><p class="hint"></p>
       	 	<input type="hidden" value="${pageContext.request.contextPath}" id="path">
       	 	<input type="hidden" value="${user.id}" id="userid">
        </div>
        <div class="delFriend" id="delFriend" onmouseover="move(this)" >
        	<p>好友 <span class="fname">小三</span> <br/>确定要删除吗？</p>
        	<input type="hidden" class="fid"/>
        	<button class="but1" onclick="sureDel(this)">确定删除</button>
        	<button class="but2" onclick="closeWin(this)">取消</button>
        </div>
        <div  class="sysSet" id="sysSet" onmouseover="move(this)" >
        	<p class="desc">个性设置</p>
        	<div class="close" onclick="closeWin(this)">✘</div>
        	<p class="desc1">消息提示音</p>
        	<div class="soundSet">
        		<p class="sLeft" onclick="soundLeft()"></p><p class="showContent">${user.sname}</p><p class="sRight" onclick="soundRight()"></p>
        	</div><br/>
        	<p class="desc1">背景图片</p>
        	<div class="bgSet">
        		<p class="sLeft" onclick="bgLeft()"></p><p class="showContent">${user.pname}</p><p class="sRight" onclick="bgRight()"></p>
        	</div>
        	<button onclick="systemSet(this)">保存设置</button>
        </div>
        <div class="sao" id="sao">
        <ul class="saoMenu" id="saoMenu">
            <img src="${pageContext.request.contextPath}/SysPhotoServlet?id=${user.photoid}" class="img" id="saoImg" onclick="showModifyInfo()"/>
            <li class="myself">欢迎你：${user.nickname}</li>
            <li onclick="showSet()">个性设置</li>
            <li onclick="location.reload()">刷　新</li>
            <li onclick="window.location.href='${pageContext.request.contextPath}/LogOutServlet'">注　销</li>
            <li onclick="window.close()">退　出</li>
        </ul>
        </div>
</body>
</html>