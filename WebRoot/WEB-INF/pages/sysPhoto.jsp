<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>title</title>
<style type="text/css">
table{
	margin:0px;
}
img{
    width:100px;
}
img:hover{
    margin:0;
    border:2px solid red;
    width:96px;
}
button{
	width:60px;height:30px;
	color:#fff;
	background:rgba(0,0,0,0.5);
	margin:0 20px;
	border-radius:10px;
}
.selected{
    border:2px solid green;
    width:96px;
}
</style>
</head>
<script type="text/javascript">
 	function closeWin(){
 		var win = parent.document.getElementsByClassName("sysPhoto")[0];
 		win.style.display = "none";
 	}
 	function choosePhoto(e,id){
 		var imgs = document.getElementsByTagName("img");
 		for(var i=0;i<imgs.length;i++){
 			imgs[i].setAttribute("class","");
 		}
 		e.setAttribute("class","selected");
 		var node = document.getElementById("id");
 		node.value=id;
 	}
 	function chooseOk(){
 		var val = document.getElementById("id").value;
 		if(val==''){
 			return;
 		}
 		parent.document.getElementById("photo").value=val;
 		var pop = parent.document.getElementById("photo1");
 		var pop1 = parent.document.getElementById("photo2");
 		pop.setAttribute("class","currentPhoto");
 		pop1.setAttribute("class","photo");
 		closeWin();
 	}
 </script>
<body>
	<table>
		<tr>
			<td><img src="${pageContext.request.contextPath}/SysPhotoServlet?id=1" onclick="choosePhoto(this,'1')" ${(defaultId=='1')?"class='selected'":''}/></td>
			<td><img src="${pageContext.request.contextPath}/SysPhotoServlet?id=2" onclick="choosePhoto(this,'2')" ${(defaultId=='2')?"class='selected'":''}/></td>
			<td><img src="${pageContext.request.contextPath}/SysPhotoServlet?id=3" onclick="choosePhoto(this,'3')" ${(defaultId=='3')?"class='selected'":''}/></td>
		</tr>
		<tr>
			<td><img src="${pageContext.request.contextPath}/SysPhotoServlet?id=4" onclick="choosePhoto(this,'4')" ${(defaultId=='4')?"class='selected'":''}/></td>
			<td><img src="${pageContext.request.contextPath}/SysPhotoServlet?id=5" onclick="choosePhoto(this,'5')" ${(defaultId=='5')?"class='selected'":''}/></td>
			<td><img src="${pageContext.request.contextPath}/SysPhotoServlet?id=6" onclick="choosePhoto(this,'6')" ${(defaultId=='6')?"class='selected'":''}/></td>
		</tr>
		<tr>
			<td><img src="${pageContext.request.contextPath}/SysPhotoServlet?id=7" onclick="choosePhoto(this,'7')" ${(defaultId=='7')?"class='selected'":''}/></td>
			<td><img src="${pageContext.request.contextPath}/SysPhotoServlet?id=8" onclick="choosePhoto(this,'8')" ${(defaultId=='8')?"class='selected'":''}/></td>
			<td><img src="${pageContext.request.contextPath}/SysPhotoServlet?id=9" onclick="choosePhoto(this,'9')" ${(defaultId=='9')?"class='selected'":''}/></td>
		</tr>
		<tr>
			<td><input type="hidden" id="id" value="${defaultId}">
			</td>
			<td>
				<button class="sps" onclick="chooseOk()">确定</button>
			</td>
			<td>
				<button class="spq" onclick="closeWin()">取消</button>
			</td>
		</tr>
	</table>

</body>
</html>
