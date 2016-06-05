<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
	<title>日程详情</title>
   	<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
   <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 日程详情
</div>

<!--显示表单内容-->
<div id=MainArea>
  
    <div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
    	<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 日程详情
    		<s:fielderror></s:fielderror>
    	</div> 
    </div>
    
    <!-- 表单内容显示 -->
    <div class="ItemBlockBorder">
        <div class="ItemBlock">
            <table class="table table-hover table-bordered TableStyle" >                   
                <tr><td>标题</td>
                    <td><s:property value="title"/></td>
                </tr>
                
                <tr><td>开始时间</td>
                    <td><s:date name="startTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr><td>结束时间</td>
                    <td><s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>                
                <tr><td>内容</td>
                    <td>${content}</td>
                </tr>
            </table>
        </div>
    </div>
    <!-- 表单操作 -->
    <div id="InputDetailBar">
        <a href="javascript:history.go(-1);">返回</a>
    </div>
	
</div>

</body>
</html>
