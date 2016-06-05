<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
    <title>信息列表</title>
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>消息详细信息列表</strong></span> 
</div>

<div style="width:100%">
    <table class="table table-hover table-bordered TableStyle">       
        <!-- 表头-->
        <thead>
            <tr align=center valign=middle id=TableTitle>
                <th width="500" style="background:#6fb0c0">标题</th>
                <th width="200" style="background:#6fb0c0">收件人</th>
                <th width="200" style="background:#6fb0c0">发送时间</th>
                <th style="background:#6fb0c0">相关操作</th>
            </tr>
        </thead>        
        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="userList">  
       
        <s:iterator value="recordList" var="sendee">
            <tr class="TableDetail1 template">
                <td>                             
                <a href="information_viewDetail.action?id=${id}">${title}&nbsp;</a>              
                </td>                
                <td>
                	<s:iterator value="sendee">
                		${name}
                	</s:iterator>
                </td>
                <td><s:date name="sendTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
                <td>
                	<s:a action="information_delete?id=%{id}" onclick="return delConfirm()">删除</s:a>
                </td>
            </tr>
        </s:iterator>   
               
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div class="footer">        
       <s:a class="btn btn-default newButton" action="information_addUI" style="height:30px">发送消息</s:a>   
      	       
    </div>
     <div style="height:30px"></div>
	<!--分页信息-->
	<div class="hint">
		<%@ include  file="/WEB-INF/jsp/public/pageView.jspf"%>
		<s:form action="information_list"></s:form>
	</div>
	
</div>

</body>
</html>
