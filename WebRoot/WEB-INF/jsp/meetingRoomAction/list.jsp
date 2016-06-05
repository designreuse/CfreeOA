<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>	

<title>会议室信息列表</title>
</head>

<body>
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>会议室信息列表</strong></span> 
</div>

<!-- 表格内容 -->
<div style="width:100%">
   <table class="table table-hover table-bordered TableStyle">
      <thead>
         <tr>
           
            	<th style="width:20%;background:#6fb0c0">会议室名称</th>
                <th style="width:15%;background:#6fb0c0">位置</th>
                <th style="width:15%;background:#6fb0c0">可容纳人数</th>
                <th style="width:30%;background:#6fb0c0">设配情况</th>
                <th style="width:20%;background:#6fb0c0">操作</th>
            
         </tr>
      </thead>
      <tbody id="TableData" class="dataContainer" datakey="userList">
            <s:iterator value="recordList">
			<tr class="TableDetail1 template">
				<td>${name}&nbsp;</td>
				<td>${position}&nbsp;</td>
				<td>${capacity}&nbsp;</td>
				<td>${equipmentCases}&nbsp;</td>
				<td>
					<s:a action="meetingRoom_delete?id=%{id}" onclick="return confirm('确定要删除吗？')">删除</s:a>
					<s:a action="meetingRoom_editUI?id=%{id}">修改</s:a>					
					<a target="_blank" href="meetingRoom_reservationDetails.action?meetingRoomId=<s:property value="id"/>">预约详情</a>	
				</td>
			</tr>  
                </s:iterator>
        
        
      </tbody>
   </table>
   <div class="footer">
      <!--新建按钮-->
      <s:a class="btn btn-default newButton" action="meetingRoom_addUI" style="height:30px">新建</s:a>
   </div>
      <div style="height:30px"></div>
   <!--分页信息-->
  	<div class="hint">
	   <%@ include  file="/WEB-INF/jsp/public/pageView.jspf"%>
	   <s:form action="meetingRoom_list"></s:form>
   </div>
   
</div>
   
</body>
</html>
