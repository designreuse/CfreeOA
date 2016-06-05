<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
    <title>日程列表</title>   
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<div id="Title_bar">    
    <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 日程信息列表       
</div>

<div style="width:100%">
    <table class="table table-hover table-bordered TableStyle">       
        <!-- 表头-->
        <thead>
            <tr >
                <th width="100">标题</th>
                <th width="150">创建时间</th>
                <th width="200">开始时间</th>
                <th width="100">结束时间</th>
                <th width="200">状态</th>
                <th>相关操作</th>
            </tr>
        </thead>        
        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="userList">      
        <s:iterator value="recordList">
            <tr class="TableDetail1 template">
                <td><a href="schedule_viewDetail.action?id=${id}">${title}&nbsp;</a></td>
                <td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>        
                <td><s:date name="startTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
                <td><s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
                <td>${status}&nbsp;</td>
                <td>
                	<s:a action="schedule_delete?id=%{id}" onclick="return delConfirm()">删除</s:a>
                    <s:a action="schedule_editUI?id=%{id}">修改</s:a>
                </td>
            </tr>
        </s:iterator>            
        <tbody id="TableData" class="dataContainer" datakey="userList">
    </table>
    
    <!-- 其他功能超链接 -->
    <div class="footer">      
        <s:a class="btn btn-default newButton" action="schedule_addUI">新建</s:a>
    </div>
    
    <!--分页信息-->
    <div class="hint">
		<%@ include  file="/WEB-INF/jsp/public/pageView.jspf"%>
		<s:form action="schedule_list"></s:form>
    </div>
    
</div>

</body>
</html>
