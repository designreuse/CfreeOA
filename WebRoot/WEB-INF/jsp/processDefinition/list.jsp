<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>审批流程列表</title>
   <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>审批流程信息列表</strong></span> 
</div>

<div style="width:100%">

    <table class="table table-hover table-bordered tableStyle">       
        <!-- 表头-->
        <thead>
            <tr width="100%">
            	<th style="background:#6fb0c0">流程名称</th>
            	<th style="background:#6fb0c0">最新版本</th>
                <th style="background:#6fb0c0">部署时间</th>
                <th style="background:#6fb0c0">流程定义名称</th>
                <th style="background:#6fb0c0">流程定义KEY</th>
                <th style="background:#6fb0c0">流程定义文件名称</th>
                <th style="background:#6fb0c0">流程定义图片名称</th>
                <th style="background:#6fb0c0">相关操作</th>
            </tr>
        </thead>

		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="userList">
			<s:iterator value="#processOVs">
			<tr class="TableDetail1 template">
					<td><s:property value="deploymentName"/>&nbsp;</td>
					<td align="CENTER"><s:property value="version"/>&nbsp;</td>
					<td><s:date name="deploymentTime" format="yyyy-MM-dd HH:mm:ss" />&nbsp;</td>				
					<td><s:property value="processDedinitionName"/>&nbsp;</td>
					<td><s:property value="processDedinitionKey"/>&nbsp;</td>
					<td><s:property value="processDedinitionFileName"/>&nbsp;</td>
					<td><s:property value="processDedinitionImageName"/>&nbsp;</td>
					<td><a onClick="return delConfirm()" href="process_delete.action?deploymentId=<s:property value='deploymentId'/>">删除</a>
						<a target="_blank" href="process_showDiagram.action?deploymentId=<s:property value="deploymentId"/>&processDedinitionImageName=<s:property value="processDedinitionImageName"/>">查看流程图</a>
					</td>  
			</tr>
			</s:iterator>
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
     <div class="footer">
     	<s:a class="btn btn-default newButton" action="process_deployUI" style="height:30px">部署流程定义文档</s:a>
     </div>
       
</div>
<div style="height:30px"></div>
<div class="Description">
	说明：<br />
	&nbsp;1.列表显示的是所有流程定义（不同名称）的最新版本;<br />
	&nbsp;2.删除流程定义时，此名称的所有版本的流程定义都会被删除。<br />
</div>

</body>
</html>
