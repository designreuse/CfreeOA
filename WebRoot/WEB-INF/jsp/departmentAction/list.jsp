<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>	
<title>部门列表</title>

</head>

<body>
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>部门列表</strong></span> 
</div>

<!-- 表格内容 -->
<div style="width:100%;">
   <table class="table table-hover table-bordered TableStyle">
      <thead>
         <tr>           
           	<th width="200px" style="background:#6fb0c0">部门名称</th>
			<th width="200px" style="background:#6fb0c0">上级部门名称</th>
			<th width="450px" style="background:#6fb0c0">职能说明</th>
			<th style="background:#6fb0c0">相关操作</th>           
         </tr>
      </thead>
      
      <tbody id="TableData" class="dataContainer" datakey="userList">   
        <s:iterator value="#departmentList">
			<tr class="TableDetail1 template">
				<td><s:a action="department_list?parentId=%{id}">${name}</s:a>&nbsp;</td>
				<td>${parent.name}&nbsp;</td>
				<td>${description}&nbsp;</td>
				<td>
					<s:a action="department_delete?id=%{id}&parentId=%{parent.id}" onclick="return window.confirm('这将删除所有的下级部门，您确定要删除吗？')">删除</s:a>
					<s:a action="department_editUI?id=%{id}" style="margin-left:5px">
						修改
					</s:a>
					&nbsp;
				</td>
			</tr>
		</s:iterator>	
        
      </tbody>
   </table>
     
	<div class="footer">
	  	  <!--新建按钮-->
	      <s:a class="btn btn-default newButton" action="department_addUI?parentId=%{parentId}" style="height:30px" >新建部门</s:a>
		  <s:a class="btn btn-default newButton" action="department_list?parentId=%{#parent.parent.id}" style="margin-left:10px;height:30px">上一级部门</s:a>    
	</div>
</div>
   
   
<div class="hint2" style="margin-left:1%;margin-top:1%">
	   说明：<br />
	1.列表页面只显示一层的（同级的）部门数据，默认显示最顶级的部门列表。<br />
	2.点击部门名称，可以查看此部门相应的下级部门列表。<br />
	3.删除部门时，同时删除此部门的所有下级部门。
</div>
   
</body>
</html>
