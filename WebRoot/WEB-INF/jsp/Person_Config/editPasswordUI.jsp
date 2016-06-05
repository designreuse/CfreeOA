<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
	<title>修改密码</title>
   <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
    
    <script type="text/javascript">
    	$(document).ready(function (){
    		$("form").submit(function(){
    			if($(select[name='oldPassword'].val() == "")){
    				alsert("请输入原密码");
    			}
    			if($(select[name='newPassword'].val() == "")){
    				alsert("请输入新密码");
    			}
    			if($(select[name='confirmPassword'].val() == "")){
    				alsert("确认新密码不能为空");
    			}
    		});
    	});
    </script>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>密码修改</strong></span> 
</div>
<div style="height:20px"></div>
<!--显示表单内容-->
<div id="MainArea">
<!--显示表单内容-->
    <s:form action="person_editUserPassword.action">
    	
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
       			<table style="margin:5px">
					<tr>					
						<td style="width:100px;font-size:14px"><b>请输入原密码:</b></td>
						<td><input type="password" name="oldPassword" class="form-control"/></td>
					</tr>
					<tr style="height:6px"></tr>
					<tr>				
						<td style="width:100px;font-size:14px;"><b>请填写新密码:</b></td>						
						<td><s:password name="newPassword" cssClass="form-control" />　</td>
					</tr>
					<tr>					
						<td style="width:100px;font-size:14px;margin-top:0px"><b>请确认新密码:</b></td>						
						<td><s:password name="confirmPassword" cssClass="form-control"  />　</td>
					</tr>
                </table>
       		</div>
       </div>
       <div style="height:5px"></div>
       		 <!-- 表单操作 -->     
           <s:submit class="btn btn-default otherButton" value="保存" style="height:30px;margin-left:6%" />          
            <a class="btn btn-default newButton" href="javascript:history.go(-1);" style="height:30px;margin-left:5px">返回</a> 
               
     </s:form>
</div>
<div style="height:30px"></div>
<div class="Description" style="margin-left:2px">
	<span style="width:100%;float:left">验证规则：</span>
	<span style="width:100%;float:left;margin-top:4px">1.旧密码不能为空;</span>
	<span style="width:100%;float:left;margin-top:3px">2.新密码不能为空;</span>
	<span style="width:100%;float:left;margin-top:3px">3.再次输入的密码要和新密码一致;</span>
</div>

</body>
</html>
