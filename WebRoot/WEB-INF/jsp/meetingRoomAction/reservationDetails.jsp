<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
   	<title>会议室预约详情</title>
   	<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>	
   	<script type="text/javascript">
		function onSortByChange( selectedValue ){
			if(selectedValue == 0){
				$("select[name=asc]").attr("disabled", "disabled");	
			}else{
				$("select[name=asc]").removeAttr("disabled");	
			}
		}

		$(function(){
			if($("select[name=orderBy]").val() == '0'){
				$("select[name=asc]").attr("disabled", "disabled");		
			}
		});
	</script>
  </head>
  
  <body>
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>会议室预约详情</strong></span> 
</div>
<div style="height:10px"></div>
<s:form action="meetingRoom_reservationDetails">
	<s:hidden name="meetingRoomId"></s:hidden>
<div id="QueryArea">
	<div style="height: 30px">
			
		<!-- 申请使用结束时间查询 ，默认 没有 -->
		<s:textfield name="endUseTime" cssStyle="width:250px;font-size:13px;height:28px;margin-left:5px" 
		  class="laydate-icon"
         onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
		
		<!-- 按申请状态过滤 ，默认 no,表示不按此条件过滤 -->
		<s:select list="#{0:'预约中', 1:'预约成功', 2:'预约失败'}" name="applicationState" class="btn btn-default otherButton" style="height:30px;margin-left:5px"/>
		
		<!-- 按申请时间排序  -->
		<s:select name="orderBy" onchange="onSortByChange(this.value)" class="btn btn-default otherButton"
						list="#{0:'请选择排序条件', 1:'提交申请时间', 2:'使用开始时间', 3:'使用结束时间'}" style="height:30px;margin-left:5px"/>
		
		<s:select name="asc" list="#{false:'降序', true:'升序'}"  class="btn btn-default otherButton" style="height:30px;margin-left:5px"/>
		
		<s:submit class="btn btn-default otherButton" value="查询" style="height:30px;margin-left:5px"/>
	</div>
</div>

</s:form>

<div style="width:100%">
    <table class="table table-hover table-bordered TableStyle">
        <!-- 表头-->
        <thead>
            <tr>
				<td style="background:#6fb0c0;font-size:14px"><b>申请人</b></td>			
				<td style="background:#6fb0c0;font-size:14px"><b>申请人联系方式</b></td>			
				<td width="150" style="background:#6fb0c0;font-size:14px"><b>提交申请日期</b></td>
				<td width="300" style="background:#6fb0c0;font-size:14px"><b>申请使用时间段</b></td>
				<td style="background:#6fb0c0;font-size:14px"><b>申请状态</b></td>
				<td style="background:#6fb0c0;font-size:14px"><b>相关操作</b></td>
			</tr>
		</thead>		
		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="userList">
        <s:if test="recordList != null">
			<s:iterator value="recordList">
				<tr class="TableDetail1 template">
					<td>${user.name }</td>			
					<td>${user.phoneNumber }</td>			
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>				
					<td>
						<s:date name="startTime" format="yyyy-MM-dd HH:mm:ss"/> 至 
						<s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/>			
					</td>
					<td>
						<s:if test="state == 0">预约中</s:if>
						<s:elseif test="state == 1">预约成功</s:elseif>
						<s:elseif test="state == 2">预约失败</s:elseif>
					</td>						
				</tr>
			</s:iterator>
			</s:if>
        </tbody>
    </table>
    <div style="height:40px"></div>
	<!--分页信息-->
	<div class="hint">
    	<%@ include  file="/WEB-INF/jsp/public/pageView.jspf"%>    
    </div>
   
   <div style="height:60px"></div>
	<div class="description">
		说明：<br />
		&nbsp;按申请时间降序排列，第1页看到的是最近的申请。
	</div>
	
	
</div>
<!-- 日期计算(需放在最后) -->
<script type="text/javascript" src="${pageContext.request.contextPath}/script/js/date.js"></script>  
</body>
</html>
