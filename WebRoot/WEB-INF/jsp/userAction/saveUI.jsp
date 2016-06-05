<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>

<title>个人信息填写</title>
</head>

<body>

<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>个人信息填写</strong></span> 
</div>
   
  <!--显示表单内容-->
<div id="MainArea">
    
   <s:form action="user_%{id == null ? 'add' : 'edit'}">
   	  <s:hidden name="id"/>
   	  <s:hidden name="department.id"></s:hidden>
   	  
   	
   	   <div class="ItemBlockBorder">
            <div class="ItemBlock">
            <div style="height:15px"></div>
		      <table style="margin-left:10px">
		         <tr>
		            <td style="width:100px;font-size:13px"><label class="control-label">所属部门:</label></td>
		            <td >
		             <div class="form-group">
		            	<s:select name="departmentId" cssClass="form-control"
		                	list="#departmentList" listKey="id" listValue="name"
		                    headerKey="" headerValue="==请选择部门=="/></div>
		            </td>
		            <td ></td>
		         </tr>
		         <tr>
		            <td style="width:100px;font-size:13px"><label for="id" class="control-label">用户ID:</label></td>
		            <td><s:textfield name="loginName" cssClass="form-control" id="id" size="25" disabled="true"/></td>
		            <td>&nbsp;*（登录名要唯一）</td>
		         </tr>    
		         <tr>
		            <td style="width:100px;font-size:13px"><label for="name" class="control-label">姓名:</label></td>
		            <td><s:textfield name="name" cssClass="form-control" placeholder="姓名" size="25"/></td>
		            <td>&nbsp;*</td>
		         </tr>
		         <tr>
		            <td style="width:100px;font-size:13px"><label for="sex" class="control-label">性别:</label></td>
		            <td><s:radio name="gender" list="{'男','女'}" style="margin-left:8px"></s:radio></td>
		            <td></td>
		         </tr>
		         <tr>
		            <td style="width:100px;font-size:13px"><label for="tel" class="control-label">联系方式:</label></td>
		            <td><s:textfield name="phoneNumber" cssClass="form-control" id="tel" /></td>
		            <td>&nbsp;*</td>
		         </tr>
		         <tr>
		            <td style="width:100px;font-size:13px"><label for="email" class="control-label">电子邮件:</label></td>
		            <td><s:textfield name="email" cssClass="form-control"/></td>
		            <td>&nbsp;*</td>
		         </tr>
		         <tr>
		            <td style="width:100px;font-size:13px"><label for="description" class="control-label">描述:</label></td>
		            <td><s:textfield name="description" cssClass="form-control" id="description" row="4" size="20"/></td>
		            <td></td>
		         </tr>        
		         <tr>
					<td style="width:100px;font-size:13px"><b>岗位:</b></td>
		            <td>
		           	<div class="form-group">
		               <s:select name="roleIds" cssClass="form-control"
		                   multiple="true" size="5" 
		                   list="#roleList" listKey="id" listValue="name"/>            
						
						</div>
		            </td>
		            <td>&nbsp;&nbsp;按住Ctrl键可以多选或取消选择</td>
		         </tr>
		         <tr>
					<td style="width:100px;font-size:13px"><b>上级:</b></td>
		            <td>
		               <s:select name="parentId" cssClass="form-control" size="5" 
		                 list="#userList" listKey="id" listValue="name"/>            			
		            </td>
		            <td></td>
		        </tr>
		         	
		           
		      </table>
		     </div>
		 </div>
		 <div style="height:20px"></div>
		         <div id="InputDetailBar">
		  <button type="submit" class="btn btn-default otherButton"  style="height:30px;margin-left:9%">保存</button>
		            <a class="btn btn-default newButton" href="javascript:history.go(-1);" style="height:30px;margin-left:8px">返回</a>
  </div>
   </s:form>
</div>   
  
</body>
</html>
