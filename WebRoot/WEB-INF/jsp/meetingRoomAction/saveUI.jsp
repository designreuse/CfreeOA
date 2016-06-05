<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>	
</head>
<body> 

<!-- 标题显示 -->
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>会议室资料填写</strong></span> 
</div>

<!--显示表单内容-->
<div id="MainArea">

    <s:form action="meetingRoom_%{ id == null ? 'add' : 'edit' }">
    	<s:hidden name="id"></s:hidden>    
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table style="margin-top:20px;margin-left:20px">
                    <tr>
                        <td width="120" style="font-size:14px"><b>会议室名称:</b></td>
                        <td><s:textfield name="name" cssClass="form-control" /></td>
                    </tr>
                    <tr>
                        <td width="120" style="font-size:14px"><b>位置:</b></td>
                        <td><s:textfield name="position" cssClass="form-control" /></td>
                    </tr>
                    <tr>
                        <td width="120" style="font-size:14px"><b>可容纳人数:</b></td>
                        <td><s:textfield name="capacity" cssClass="form-control" /></td>
                    </tr>
                    <tr>
                        <td width="120"  style="font-size:14px"><b>设备情况描述:</b></td>
                        <td><s:textarea name="equipmentCases" cssClass="form-control"/></td>
                    </tr>
                </table>
            </div>
        </div>
        <div style="width:100%;height:30px"></div>
        <!-- 表单操作 -->        
       	<s:submit class="btn btn-default otherButton" value="保存" style="width:70px;margin-left:80px;height:30px"/>       
        <a class="btn btn-default newButton" href="javascript:history.go(-1);" style="width:70px;height:30px;margin-left:10px">返回</a>    
    </s:form>
</div>

</body>
</html>

