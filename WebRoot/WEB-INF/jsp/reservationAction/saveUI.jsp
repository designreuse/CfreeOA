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

    <s:form action="reservation_%{ id == null ? 'add' : 'edit' }">
    	<s:hidden name="id"></s:hidden>    
    	<s:hidden name="meetingRoom.id"></s:hidden>    
    	<s:hidden name="user.id"></s:hidden>    
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table >
                    <tr>
                        <td width="100">会议主题</td>
                        <td><s:textfield name="title" cssClass="form-control" /></td>
                    </tr>
                    <tr>
                        <td width="100">紧急程度</td>
                        <td><s:textfield name="emergencyLevel" cssClass="form-control" /></td>
                    </tr>
                    <tr class="inline">
                    	<td width="100">使用开始时间</td>
                        <td><s:textfield name="startTime" placeholder="请输入日期" id="start"
                        		 class="inline laydate-icon" cssClass="form-control"/></td>
						<td width="100">使用结束时间</td>
                        <td><s:textfield name="endTime" placeholder="请输入日期" id="end"
                        		class="inline laydate-icon" cssClass="form-control"/></td>
                    </tr>
                    
                     <tr>
                        <td width="100">申请会议室</td>
                        <td>
                        <s:if test="#meetingRooms != null">
                        	<div class="form-group">
                        	<s:select  cssClass="form-control" name="reservationMeetingRoomId"
                        		list="#meetingRooms" listKey="id" listValue="name"
                        		headerKey="on" headerValue="请选择会议室"/>
                        	</div>
                        </s:if>
						</td>
                    </tr>                                      
                    <tr>
                        <td>预约说明</td>
                        <td><s:textarea name="summary" cssClass="form-control"/></td>
                    </tr>
                </table>
            </div>
        </div>
        
        <!-- 表单操作 -->        
       	<s:submit class="btn btn-default otherButton" value="保存"/>      
        <a class="btn btn-default newButton" href="javascript:history.go(-1);">返回</a>    
    </s:form>
</div>

<!-- 日期计算(需放在最后) -->
<script type="text/javascript" src="${pageContext.request.contextPath}/script/js/date.js"></script>
</body>
</html>

