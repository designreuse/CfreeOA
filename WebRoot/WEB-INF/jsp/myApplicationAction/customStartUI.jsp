<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>提交申请</title>
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>
 
<!--显示表单内容-->
<div style="width:100%">
    <form action="myApplication_start.action" enctype="multipart/form-data">
    	
    	<s:hidden name="templateId"></s:hidden>
    	<s:hidden name="startProcessKey"></s:hidden>
     
		<div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
        	<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 申请信息 </div> 
        </div>
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table class="table table-hover table-bordered TableStyle">                 
                   ${formHtml }
                </table>
            </div>
        </div>
		
        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <s:submit class="btn btn-default otherButton" value="提交申请"/>           
			<a class="btn btn-default newButton" href="javascript:history.go(-1);">返回</a>
        </div>
    </form>
</div>

<br>
<div class="Description">
	
</div>

</body>
</html>
