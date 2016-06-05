<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>
<title>OA</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<frameset cols="180,*" id="resize" framespacing="0" border="0" frameborder="0">
        <frameset rows="80,*">
              <frame noresize name="userState" src="${pageContext.request.contextPath}/home_userState.action" scrolling="no" />
              <frame noresize name="menu" src="${pageContext.request.contextPath}/home_left.action" scrolling="yes" />
        </frameset>
        <frameset rows="80,*" framespacing="0" border="0" frameborder="0" style="height:1000px">
			<frame src="${pageContext.request.contextPath}/home_top.action" name="TopMenu"  scrolling="no" noresize />
        	<frame noresize name="right" src="${pageContext.request.contextPath}/home_right.action" scrolling="yes" />
   		 </frameset>
	</frameset><noframes></noframes>
	
</html>

