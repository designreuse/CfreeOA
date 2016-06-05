<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>Top</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/top.css" />
</head>

<body style="margin: 0;">

	<div id="Head1">
		<div class="topLogo">
			<img src="${pageContext.request.contextPath}/style/images/login_logo.gif" />
		</div>
		<!--快捷键功能-->
		<span class="option" style="margin-right:3px;margin-top:3px;margin-bottom:6px"> 
		<!--退出--> 
			<a href="user_logout.action" target="_parent"> 
			<img src="${pageContext.request.contextPath}/style/images/top/5.png"
				onMouseOver="this.src='${pageContext.request.contextPath}/style/images/top/6.png'"
				onMouseOut="this.src='${pageContext.request.contextPath}/style/images/top/5.png'">
			</a>
		</span> 
		<span class="option" style="margin-right:-10px;margin-top:3px;margin-bottom:6px"> 
		<!--个人设置--> 
			<a href="person_editUsrInfoUI.action" target="right"> 
			<img src="${pageContext.request.contextPath}/style/images/top/1.png"
				onMouseOver="this.src='${pageContext.request.contextPath}/style/images/top/2.png'"
				onMouseOut="this.src='${pageContext.request.contextPath}/style/images/top/1.png'">
			</a>
		</span>
		<!--公文审批-->
		<span class="option" style="margin-right:-10px;margin-top:3px;margin-bottom:6px"> 
			<a href="task_list.action" target="right">
				<img src="${pageContext.request.contextPath}/style/images/top/3.png"
				onMouseOver="this.src='${pageContext.request.contextPath}/style/images/top/4.png'"
				onMouseOut="this.src='${pageContext.request.contextPath}/style/images/top/3.png'">
			</a>
		</span>
		<!--首页-->
		<span class="option" style="margin-right:-10px;margin-top:3px;margin-bottom:6px"> 
			<a href="home_right.action" target="right">
				<img src="${pageContext.request.contextPath}/style/images/top/7.png"
				onMouseOver="this.src='${pageContext.request.contextPath}/style/images/top/8.png'"
				onMouseOut="this.src='${pageContext.request.contextPath}/style/images/top/7.png'">
			</a>
		</span>

	</div>

</body>
</html>





