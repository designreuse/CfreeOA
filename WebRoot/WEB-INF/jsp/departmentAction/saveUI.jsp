<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>

<title>部门信息填写</title>
</head>

<body>
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>部门信息填写</strong></span> 
</div>

<!--显示表单内容-->
<div id="MainArea">
 	  
  	  <s:form action="department_%{id == null ? 'add' : 'edit'}">
       <s:hidden name="id"></s:hidden>
       
       <div style="height:20px"></div>
       <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
		       <table style="margin:5px 0 0 10px" width="800px">
		         <tr>
		         	<td style="font-size:13px;width:80px"><b>上级部门:</b></td>
		                <td>
		                <div class="form-group">
		                   <s:select name="parentId" cssClass="form-control"
		                      list="#departmentList" listKey="id" listValue="name"
		                      headerKey="" headerValue="==请选择部门==" style="width:300px"/>
		                </div>
		                </td>
		         </tr>
		         <tr><td style="font-size:13px;width:80px"><b>部门名称:</b></td>
		             <td><s:textfield  name="name" cssClass="form-control" id="id" size="20" style="width:300px"/></td>
		         </tr>
		         <tr><td style="font-size:13px;width:80px"><b>职能说明:</b></td>
		             <td><s:textarea name="description" cssClass="form-control" style="width:300px;height:150px"/></td>
		         </tr>           
		        
		      </table>
	      </div>
      </div>
      <div style="height:30px"></div>
      <!-- 表单操作 -->
      <s:submit class="btn btn-default otherButton" value="保存" style="height:30px;margin-left:12%;width:60px"/>
       <a class="btn btn-default newButton" href="javascript:history.go(-1);" style="margin-left:10px;width:60px;height:30px">返回</a>
      
   </s:form>
</div>
  
</body>
</html>