<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>待我审批</title>
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body> 

<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>待我审批</strong></span> 
</div>

<!--
<div id="QueryArea">
	<div style="height: 30px">
		<form action="#">
		<table border=0 cellspacing=3 cellpadding=5>
			<tr>
				<td>按条件查询：</td>
				<td><select name="arg1" class="SelectStyle">
						<option value="">查看全部模板</option>
						<option value="0">领款单</option>
						<option value="10">工作报告</option>
						<option value="13">设备采购单</option>
						<option value="21">员工请假单</option>
						<option value="22">加班申请</option>
					</select>
				</td>
				<td><a href=""><input type="IMAGE" src="${pageContext.request.contextPath}/style/blue/images/button/query.PNG"/></a></td>
			</tr>
		</table>
	
		</form>
	</div>
</div>
-->


<div style="width:100%">
    <table class="table table-hover table-bordered TableStyle">
        <!-- 表头-->
        <thead>
            <tr>
				<th width="110" style="background:#6fb0c0">任务ID</th>
				<th width="215" style="background:#6fb0c0">任务名称</th>
				<th width="215" style="background:#6fb0c0">办理人</th>
				<th width="300" style="background:#6fb0c0">创建时间</th>
				<th style="background:#6fb0c0">相关操作</th>
			</tr>
		</thead>		
		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="userList">
        	<s:if test="#tasks!=null && #tasks.size()>0">
        		<s:iterator value="#tasks">
        			<tr class="TableDetail1 template">
						<td><s:property value="id"/></td>
						<td><s:property value="name"/>&nbsp;</td>
						<td><s:property value="assignee"/>&nbsp;</td>
						<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
						<td><a href="task_viewTaskFrom.action?taskId=<s:property value="id"/>">处理任务</a>							
							<a href="task_showPresentDiagramUI.action?taskId=<s:property value="id"/>" target="_blank">查看当前流程图</a>
						</td>
					</tr>
        		</s:iterator>
        	</s:if>
			
        </tbody>
    </table>
  
	<!--分页信息-->
	
	<div style="height:40px"></div>
	<div class="Description" style="margin-left:5px">
		<div>说明：</div>
		<div style="margin-top:3px">&nbsp;这里列出的所有的表单状态都为"审批中"</div>
	</div>
	
	
</div>

</body>
</html>
