<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
	<title>个人信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>个人信息修改</strong></span> 
</div>

<!--显示表单内容-->
<!--显示表单内容-->
<div id="MainArea">
    <s:form action="person_editUsrInfo.action" enctype="multipart/form-data">
        <s:hidden name="id" value="%{id}"></s:hidden>
        <s:hidden name="departmentId" value="%{department.id}"></s:hidden>
      
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder" style="margin-left:8%;margin-top:2%">
            <div class="ItemBlock">
        		<table cellpadding="0" cellspacing="0" class="table table-hover table-bordered TableStyle" style="width:60%">
					<tr>
                        <td width="130" style="font-size:14px">所属部门</td>
                        <td>
	                        <s:if test="department != null">
		                        <s:textfield name="departmentName" value="%{department.name}" cssClass="form-control" disabled="true"/>
	                        </s:if>
	                    </td>	
	                   		
                    </tr> 
					<tr>
                        <td width="15%" style="font-size:14px">用户ID（登录名）</td>
                        <td width="28%"><s:textfield name="loginName" value="%{loginName}" cssClass="form-control" disabled="true" style="width:240px"/></td>
	                   
                    </tr>                   
					<tr>
                        <td style="font-size:14px">岗位</td>
                        <td><s:property value="roleNames"/></td>
	                   	
                    </tr>
					<tr>
                        <td style="font-size:14px">姓名</td>
                        <td><s:textfield name="name" value="%{name }" cssClass="form-control" style="width:240px"/></td>
                        
                    </tr>	
					<tr>
                        <td style="font-size:14px">性别</td>
                        <td><s:textfield name="gender" value="%{gender}" cssClass="form-control" style="width:240px"/></td>
                       
                    </tr>	
                    <tr>
                        <td style="font-size:14px">联系方式</td>
                        <td><s:textfield name="phoneNumber" value="%{phoneNumber}" cssClass="form-control" style="width:240px"/></td>
                        
                    </tr>
                    <tr>
                        <td style="font-size:14px">电子邮件</td>
                        <td><s:textfield name="email" value="%{email}" cssClass="form-control" style="width:240px"/></td>
                       
                    </tr>
                    <tr>
                        <td style="font-size:14px">说明</td>
                        <td><s:textarea name="description" value="%{description}" cssClass="form-control" style="width:240px"/></td>
                       
                    </tr>				
                </table>
       		</div>
       </div>
        	<!-- 表单操作 -->               
        	<s:submit class="btn btn-default otherButton" value="保存" style="height:30px;margin-left:28%;width:70px"/>          
            <s:a class="btn btn-default newButton" href="person_editUsrInfoUI.action" style="height:30px;margin-left:10px;width:70px">返回</s:a>
    </s:form>
</div>

<div class="Description">
	
</div>

</body>
</html>
