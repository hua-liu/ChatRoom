<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>title</title>
  </head> 
<link href="${pageContext.request.contextPath}/css/friend.css" rel="stylesheet" type="text/css">
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.2.2.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/createCharWin.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/friend.js"></script>
  <body onmousewheel="scrollFunc()" onload="start('${user.id}','${pageContext.request.contextPath}')">
  	<c:forEach var="firend" items="${list}">
	<div class="box"><div class="current" id="kind" onclick="switchUl(this)">${firend.kind}</div><ul class="ul">
			<c:forEach var="user" items="${firend.list}">
			<li onmouseover="showDel(this)" onmouseout="hideDel(this)" onclick="showCharWin(this,'${user.id}','${user.nickname}','${pageContext.request.contextPath}','${user.photoid }')"><img src="${pageContext.request.contextPath}/SysPhotoServlet?id=${user.photoid}" class="img"><p class="name">${user.nickname}</p><p class="state">[<span>离线</span>] ${user.description}</p><p class="new">1</p><p onmouseover="showDel1(this)" onclick="delFriend(event,'${user.id}','${user.nickname}')" class="del">✘</p><input type="hidden" value="${user.id}" class="input"></li>
			</c:forEach>
		</ul>
	</div>
	</c:forEach>
	<input id="path" type="hidden" value="${pageContext.request.contextPath}">
	<audio id="sound" src="${pageContext.request.contextPath}/GetServlet?type=sysSetSound&id=${user.id}"></audio>
		<%-- <source src="${pageContext.request.contextPath}/sound/qq.mp3" type="audio/mpeg" class="source">
  		<source src="${pageContext.request.contextPath}/sound/qq.wav" type="audio/wav" class="source"> --%>
  </body>
</html>
