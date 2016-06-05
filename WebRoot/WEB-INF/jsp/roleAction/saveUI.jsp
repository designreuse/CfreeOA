<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>	
</head>
<body> 

<!-- 标题显示 -->
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>个人信息填写</strong></span> 
</div>

<!--显示表单内容-->
<div id="MainArea">

    <s:form action="role_%{ id == null ? 'add' : 'edit' }">
    	<s:hidden name="id"></s:hidden>    
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table style="margin-left:8px;margin-top:20px">
                    <tr>
                        <td style="font-size:14px;width:80px"><b>岗位名称:</b></td>
                        <td><s:textfield name="name" cssClass="form-control" /></td>
                    </tr>
                    <tr>
                        <td style="font-size:14px"><b>岗位说明:</b></td>
                        <td><s:textarea name="description" cssClass="form-control"/></td>
                    </tr>
                </table>
            </div>
        </div>
        <div style="height:30px"></div>
        <!-- 表单操作 -->        
       	<s:submit class="btn btn-default otherButton" value="保存" style="height:30px;margin-left:6%"/>
        <%-- <input type="image" src="${pageContext.request.contextPath}/style/images/save.png"/> --%>
        <a href="javascript:history.go(-1);" class="btn btn-default newButton" style="height:30px;margin-left:5px">返回</a>    
    </s:form>
</div>

</body>
</html>

