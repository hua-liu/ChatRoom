<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>title</title>
  </head> 
  <style type="text/css">
	*{
		color:#fff;
		background:rgba(0,0,0,0.2);
	}
	
  .watch{
  		width:300px;height:300px;
  		text-align:center;
  		border:1px solid green;
  	}
  	.watch button{
  		width:80px;height:50px;
  		
  		border-radius:10px;
  	}
  	.but{
  		text-align:right;
  	}
  	.but button{
  		width:60px;height:30px;
  		border-radius:10px;
  		
  	}
  	.watch .filebox{
  		width:100px;height:50px;
  		overflow:hidden;
  		position:absolute;
  		top:120px;
  		left:130px;
  	}
  	.watch input{
  		width:150px;height:50px;
  		font-size:15px;
  		position:absolute;
  		left:-80px;
  		background:none;
  		
  	}
	#img{
		width:270px;
	}
	span{
		color:#fff;
	}
  </style>
  <script type="text/javascript">
  	function upload(id){
  		var file = document.getElementById("file");
  		if(id==''||file!=null){
  			alert("没选择图片");
  			return;
  		}
  		parent.document.getElementById("photo").value=id;
  		var pop = parent.document.getElementById("photo1");
 		var pop1 = parent.document.getElementById("photo2");
 		pop1.setAttribute("class","currentPhoto");
 		pop.setAttribute("class","photo");
 		closeWin();
  	}
  	function check(){
  		var file = document.getElementById("file").value;
  		var value = file.substring(file.lastIndexOf('.')).toUpperCase();
  		var str = ".JPG,.JPEG,.PNG,.BMP,.GIF";
  		if(str.indexOf(value)==-1){
  			alert("不支持此格式：只支持[jpg,jpeg,png,bmp,gif]")
  			return;
  		} 
  		var form = document.getElementById("myform");
  		form.submit();
  	}
  	function closeWin(){
 		var win = parent.document.getElementsByClassName("customPhoto")[0];
 		win.style.display = "none";
 	}
  </script>
  <body>
  <table>
  <tr>
  	<td class="watch" colspan="2">
  		 <c:choose>
  			 <c:when test="${id==null}">
  		 <div class="filebox">
  			 <form id="myform" action="${pageContext.request.contextPath}/UploadPhotoServlet" method="post" enctype="multipart/form-data">
  				<input type="file" id="file" onchange="check()" name="file"/>
  			</form>
  		</div>
  			</c:when>
  			<c:otherwise>
  				<img id="img" src="${pageContext.request.contextPath}/SysPhotoServlet?id=${id}"/>
  			</c:otherwise>
  		</c:choose>
  	</td>
  </tr>
  	<tr>
  		<td><span>${message}</span></td>
  		<td class="but">
	  		<button onclick="upload('${id}')">确定</button>
			<button onclick="closeWin()">取消</button>
		</td>
  	</tr>
  </table>
  </body>
</html>
