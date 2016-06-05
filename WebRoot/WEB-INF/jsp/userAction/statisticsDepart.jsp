<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>用户列表</title>
	<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>部门信息详情列表</strong></span> 
</div>

<div style="width:100%">
    <table class="table table-hover table-bordered TableStyle">       
        <!-- 表头-->
        <thead>
            <tr align=center valign=middle id=TableTitle>
                <th width="15%" style="background:#6fb0c0">部门名称</th>
                <th width="15%" style="background:#6fb0c0">人数</th>   
   				<th width="30%" style="background:#6fb0c0">联系电话</th>   
   				 <th width="40%" style="background:#6fb0c0">备注</th>        
            </tr>
        </thead>       
        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="userList">
	        <s:iterator value="#statisticsDeparts">
	            <tr class="TableDetail1 template">
	                <td><s:a action="user_findConnection?departmentname=%{departName}">${departName}</s:a>&nbsp;</td>
	                <td>${amount}&nbsp;</td>                      
	                <td>&nbsp; </td>
	                <td>&nbsp;</td>
	            </tr>
	        </s:iterator>        
        </tbody>
    </table>
    
   
</div>

</body>
</html>
