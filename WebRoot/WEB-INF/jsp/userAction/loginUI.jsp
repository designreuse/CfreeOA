<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib  prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>OA-Login</title>
<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/login.css" />	

<script type="text/javascript">
		$(function(){
			document.forms[0].loginName.focus();
		});
		
		// 在被嵌套时就刷新上级窗口
		if(window.parent != window){
			window.parent.location.reload(true);
		}
</script>

<title>登录</title>
<style>
.login{
background-color:#759dbb
}
.login:hover{
background-color:#000
}

</style>
</head>

<body>
	<div id="logo">
		<!-- logo -->
		<img alt="CFree Logo" src="${pageContext.request.contextPath}/style/images/login_logo.gif">
		<div id="description">让办公更简单，更规范</div>
	</div>
	<div id="login">
        <s:form action="user_login.action" method="post">
        	<s:textfield name="loginName" cssClass="form-control input-lg" placeholder="用户名"
                 			 id="username" size="20"/>
           <s:password name="password" cssClass="form-control input-lg" placeholder="密码" 
                         id="password" size="20"/>
           <input class="control-label btn btn-default input-lg login" id="loginButton" type="submit" value="登录">
        </s:form>
	</div>
	<div id="footer">校园行政管理办公自动化系统  @Dreamweaver团队</div>
</body>
</html>
