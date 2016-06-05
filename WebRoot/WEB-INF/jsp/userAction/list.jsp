<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>	

<title>用户管理</title>
</head>

<body>
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>用户列表</strong></span> 
</div>

<!-- 表格内容 -->
<div style="width:100%" >
   <table class="table table-hover table-bordered TableStyle">
      <thead>
         <tr>
            <th width="10%" style="background:#6fb0c0">登录名</th>
            <th width="15%" style="background:#6fb0c0">姓名</th>
            <th width="15%" style="background:#6fb0c0">系统权限</th>
            <th width="15%" style="background:#6fb0c0">所属部门</th>
            <th width="30%" style="background:#6fb0c0">备注</th>
            <th width="15%" style="background:#6fb0c0">相关操作</th>
         </tr>
      </thead>
     <tbody id="TableData" class="dataContainer" datakey="userList">
      
         <s:iterator value="recordList">
            <tr >
                <td>${loginName}&nbsp;</td>
                <td>${name}&nbsp;</td>        
                <td>
                	<s:iterator value="roles">
                		${name}
                	</s:iterator>
                </td>
                <td><s:if test="department != null">${department.name}</s:if>&nbsp;</td>
                <td>${description}&nbsp;</td>
                <td>
                	<s:a action="user_delete?id=%{id}" onclick="return delConfirm()">删除</s:a>
                    <s:a action="user_editUI?id=%{id}">修改</s:a>
					<s:a action="user_initPassword?id=%{id}" onclick="return window.confirm('您确定要初始化密码为1234吗？')">初始化密码</s:a>
                </td>
            </tr>
        </s:iterator> 
        
      </tbody>
   </table>
     <div class="footer">
     	 <!--新建按钮-->
      	 <s:a class="btn btn-default newButton" action="user_addUI" style="height:30px">新建</s:a>
     </div>
     <div style="height:20px"></div>
     <!-- 分页信息 -->
	<div class="hint">
		<%@ include  file="/WEB-INF/jsp/public/pageView.jspf"%>
  		<s:form action="user_list"></s:form>
  	</div>
     
</div>
   

</body>
</html>
