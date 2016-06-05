<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>	

<title>岗位列表</title>
</head>

<body>
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>岗位列表</strong></span> 
</div>

<!-- 表格内容 -->
<div style="width:100%">
   <table class="table table-hover table-bordered TableStyle">
      <thead>
         <tr>
            <tr>
            	<th width="30%" style="background:#6fb0c0">岗位名称</th>
                <th width="40%" style="background:#6fb0c0">岗位说明</th>
                <th width="30%" style="background:#6fb0c0">相关操作</th>
            </tr>
         </tr>
      </thead>
      <tbody id="TableData" class="dataContainer" datakey="userList">
      
         <s:iterator value="recordList">
			<tr class="TableDetail1 template">
				<td>${name}&nbsp;</td>
				<td>${description}&nbsp;</td>
				<td>
					<s:a action="role_delete?id=%{id}" onclick="return confirm('确定要删除吗？')">删除</s:a>
					<s:a action="role_editUI?id=%{id}">修改</s:a>
					<s:a action="role_setPrivilegeUI?id=%{id}">设置权限</s:a>
				</td>
			</tr>  
        </s:iterator>
        
      </tbody>
   </table>
   <div class="footer">
      <!--新建按钮-->
      <s:a class="btn btn-default newButton" action="role_addUI" style="height:30px">新建</s:a>
   </div>
      <div style="height:20px"></div>
    <!-- 分页信息 -->
	<div class="hint">
	   <%@ include  file="/WEB-INF/jsp/public/pageView.jspf"%>
	   <s:form action="role_list"></s:form>
    </div>
   
</div>
   
</body>
</html>
