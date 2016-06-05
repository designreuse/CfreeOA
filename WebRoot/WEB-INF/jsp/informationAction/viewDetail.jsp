<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
	<title>信息详情</title>
   	<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>信息详情</strong></span> 
</div>

<!--显示表单内容-->
<div id=MainArea>
  

    <div style="height:10px"></div>
    <!-- 表单内容显示 -->
    <div class="ItemBlockBorder">
        <div class="ItemBlock">
            <table cellpadding="0" cellspacing="0" class="table table-hover table-bordered TableStyle">                   
                <tr><td>标题</td>
                    <td><s:property value="title"/></td>
                </tr>
                
                <tr><td>发送时间</td>
                    <td><s:date name="sendTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>                
                
                <tr><td>内容</td>
                    <td>${content}</td>
                </tr>
            </table>
        </div>
    </div>
    <!-- 表单操作 -->
    <div id="InputDetailBar">
        <a class="btn btn-default newButton" href="javascript:history.go(-1);" style="height:30px">返回</a>
    </div>
	
</div>

</body>
</html>
