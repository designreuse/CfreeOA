<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>用户列表</title>
	<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>个人联系方式详情列表</strong></span> 
</div>

<div style="width:100%">
    <table class="table table-hover table-bordered TableStyle">      
        <!-- 表头-->
        <thead>
            <tr align=center valign=middle id=TableTitle>
                <th width="100" style="background:#6fb0c0">用户名</th>
                <th width="150" style="background:#6fb0c0">电话号码</th>
               <th width="150" style="background:#6fb0c0">邮件地址</th>        
            </tr>
        </thead>        
        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="userList">
	        <s:iterator value="recordList">
	            <tr class="TableDetail1 template">
	                <td>${name } </td>
	                <td>${phoneNumber}&nbsp;</td>
	                <td>${email}&nbsp;</td>                      
	            </tr>
	        </s:iterator>        
        </tbody>
    </table>
    
     <!-- 分页信息 -->
	<div class="hint">
		<%@ include  file="/WEB-INF/jsp/public/pageView.jspf"%>
  		<s:form action="user_findConnection"></s:form>
  	</div>
 
</div>

</body>
</html>
