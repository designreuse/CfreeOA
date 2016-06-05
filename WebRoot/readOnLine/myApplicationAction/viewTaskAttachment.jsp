<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>加载文件中，请稍后</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/readOnline.css"/>  
  </head>
  
  <body>
<!-- 加载文件等待开始 -->
<div id="blackDiv" style="display:block"></div>
<div id="contentDiv" style="display:block">
<img src="${pageContext.request.contextPath}/style/images/Onlineloading.gif" id="loadImg"/>
<div id="textDiv">加载文件中...</div>
</div>

<script>
	window.location.href="myApplication_viewAttachment.action?taskId="+<%=request.getParameter("taskId")%>;
</script>
  </body>
</html>
