<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
    <title>信息箱</title>
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>信息箱</strong></span> 
</div>

<div style="width:100%">
    <table class="table table-hover table-bordered TableStyle">       
        <!-- 表头-->
        <thead>
            <tr align=center valign=middle id=TableTitle>
                <th width="500" style="background:#6fb0c0">标题内容</th>
                <th width="400" style="background:#6fb0c0">接收时间</th>                
            </tr>
        </thead>
       
        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="userList">    
        <s:iterator value="#informationList" var="sendee">
            <tr class="TableDetail1 template">
                <td>${content}&nbsp;</td>                                       
                <td><s:date name="sendTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>                                       
            </tr>
        </s:iterator> 
            
        </tbody>
    </table>
   
</div>

</body>
</html>
