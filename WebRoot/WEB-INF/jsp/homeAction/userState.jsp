<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${pageContext.request.contextPath}/style/blue/userState.css" rel="stylesheet">
<script language="JavaScript" src="${pageContext.request.contextPath}/script/jquery-1.1.22.min.js"></script>

<title>用户状态</title>
</head>

<body style="margin:0px;background: #4d4d4d;">
	<div style="text-align:center;margin-top:15px">
      <span class="welcome">欢迎登陆!</span>
      <span class="userId">${user.name }</span>
   </div>
   <!--待办事项-->
   <div style="float:left;margin:10px">
      <img src="${pageContext.request.contextPath}/style/images/bell.gif" style="float:left" width="25px" height="25px"/>
      <span style="line-height:25px">
      	 你有 <a href="task_list.action" target="right"><span id="sum"></span></a> 件待办事项</span>
   </div>
</body>
<script type="text/javascript">
	function getRootPath(){
	
		    //获取当前网址，如： http://localhost:8083/maintain/share/meun.jsp
	
		    var curWwwPath=window.document.location.href;
	
		    //获取主机地址之后的目录，如： maintain/share/meun.jsp
	
		    var pathName=window.document.location.pathname;
	
		    var pos=curWwwPath.indexOf(pathName);
	
		    //获取主机地址，如： http://localhost:8083
	
		    var localhostPaht=curWwwPath.substring(0,pos);
	
		    //获取带"/"的项目名，如：/maintain
	
		    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	
		    return(localhostPaht+projectName);
	}

	function getData(){
		var rootPath = getRootPath();
		$.ajax({
			url: rootPath + '/task_countTask.action',
			type: 'post',
			success: function(sum){
				if(sum != null){
					$('span#sum').text(sum);
				}
			}
		});
	}

	$(document).ready(function(){
		 getData(); //首次立即加载  
         window.setInterval(getData, 5000); //循环执行！！ 
	});
</script>
</html>