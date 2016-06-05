<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>	
</head>
<body> 

<!-- 标题显示 -->
<div id="Title_bar">
      <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/>预约信息填写 
</div>

<!--显示表单内容-->
<div id="MainArea">

    <s:form action="reservation_handelAppoint">
    	<s:hidden name="id"></s:hidden>    
    	<s:hidden name="user.id"></s:hidden>    
    	<s:hidden name="meetingRoom.id"></s:hidden>    
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table >
                    <tr>
                        <td width="100">会议主题</td>
                        <td><s:textfield name="title" cssClass="form-control" disabled="true"/></td>
                        <td width="100">紧急程度</td>
                        <td><s:textfield name="emergencyLevel" cssClass="form-control" disabled="true"/></td>
                    </tr>                  
                    <tr>
                        <td width="100">使用开始时间</td>
                        <td><s:textfield name="startTime" cssClass="form-control" disabled="true"/></td>
                        <td width="100">使用结束时间</td>
                        <td><s:textfield name="endTime" cssClass="form-control" disabled="true"/></td>
                    </tr>                                                                           
                    <tr>
                        <td>预约说明</td>
                        <td colspan="3"><s:textarea name="summary" cssClass="form-control" disabled="true"/></td>
                    </tr>
                    <tr><td colspan="4"><hr/></td></tr>
                    <tr>
                    	<td>是否同意</td>
                    	<td colspan="3">
                    	<div class="form-group">
		                   <s:select name="state" cssClass="form-control"
		                      list="#{ 0:'请选择', 1:'预约成功', 2:'预约失败'}"/>
		                </div>
                    	</td>
                    </tr>
                    <tr>
                    	<td>审批反馈</td>
                   		<td colspan="3"><s:textarea name="feedback" cssClass="form-control"/></td>                   	
                    </tr>
                    
                </table>
            </div>
        </div>
        
        <!-- 表单操作 -->        
       	<s:submit class="btn btn-default otherButton" value="保存"/>      
        <a class="btn btn-default newButton" href="javascript:history.go(-1);">返回</a>    
    </s:form>
</div>

</body>
</html>

