<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>	

<title>预约信息列表</title>
</head>

<body>
<div id="Title_bar">
   <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 预约信息列表 
</div>

<!-- 表格内容 -->
<div style="width:100%">
   <table class="table table-hover table-bordered TableStyle">
      <thead>
         <tr>          
           	<th>会议主题</th>
           	<th>紧急程度</th>
           	<th>预约申请时间</th>           	
           	<th>会议室使用时间段</th>
            <th>预约人</th>
            <th>预约人联系方式</th>
            <th>相关操作</th>           
         </tr>
      </thead>
      <tbody id="TableData" class="dataContainer" datakey="userList">
      
         <s:iterator value="recordList">
			<tr >
				<td><a href="reservation_detailsUI.action?id=<s:property value="id"/>">${title}</a>&nbsp;</td>				
				<td>${emergencyLevel}&nbsp;</td>
				<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
				<td><s:date name="startTime" format="yyyy-MM-dd HH:mm:ss"/> 至
					<s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
				<td>${user.department.name}:${user.name}</td>
				<td>${user.phoneNumber}</td>
				<td>
					
					<s:a action="reservation_handelAppointUI?id=%{id}">处理预约</s:a>					
				</td>
			</tr>  
        </s:iterator>
        
      </tbody>
   </table>
  
      
   <!--分页信息-->
   <div class="hint">
	   <%@ include  file="/WEB-INF/jsp/public/pageView.jspf"%>
	   <s:form action="reservation_listAll"></s:form>
   </div>
   
</div>
   
</body>
</html>
