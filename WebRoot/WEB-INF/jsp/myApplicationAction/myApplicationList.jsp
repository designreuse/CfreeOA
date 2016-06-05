<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>申请列表</title>
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/OpenWindow.js"></script>
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

<div id="Title_bar">
    <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 我的申请列表
</div>

<s:form action="myApplication_list">

<div id="QueryArea">
	<div style="height: 30px">
		<!-- 按输入关键字对标题模糊查询 ，默认 没有 -->
		<s:textfield name="likeSearch" cssStyle="width:150px" class="btn btn-default search"/>
				
		<!-- 按申请状态过滤 ，默认 no,表示不按此条件过滤 -->
		<s:select list="#{0:'请选择申请状态', 1:'审批中', 2:'审批结束'}" name="applicationState" class="btn btn-default search"/>
		
		<!-- 按申请时间排序  -->
		<s:select name="orderBy" onchange="onSortByChange(this.value)" class="btn btn-default search"
						list="#{0:'请选择排序条件', 1:'提交申请时间'}"/>
		
		<s:select name="asc" list="#{false:'降序', true:'升序'}"  class="btn btn-default search"/>
		
		<s:submit class="btn btn-default otherButton" value="查询"/>
	</div>
</div>

</s:form>

<div style="width:100%">
    <table class="table table-hover table-bordered TableStyle">
        <!-- 表头-->
        <thead>
            <tr>
				<td width="450">主题</td>			
				<td width="200">申请日期</td>
				<td width="100">当前状态</td>
				<td>相关操作</td>
			</tr>
		</thead>		
		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="userList">
        <s:if test="recordList != null">
			<s:iterator value="recordList">
				<tr class="TableDetail1 template">
					<td><%-- <a href="javascript:openWindow('readOnLine/myApplicationAction/loading.jsp?id=<s:property value='id'/>');"> --%>${title}<!-- </a> --></td>			
					<td><s:date name="createTime" format="yyyy-MM-dd hh:mm:ss"/>&nbsp;</td>				
					<td>
						<s:if test="state == 1"><span style="color:red">审核中</span></s:if>
               			<s:elseif test="state == 2"><span  style="color:green">审核结束</span></s:elseif></td>            			
					
					<!-- 操作 -->
					<td>
						<s:if test="state == 1">
							<s:a action="myApplication_cancelApplication?id=%{id}&processInstanceId=%{processInstanceId}" 
								onclick="return delConfirm('确定取消申请吗？流程将会停止，所有相关数据均被删除！')">取消申请</s:a>
						</s:if>
						<s:if test="state == 2">
							<s:a action="myApplication_delete?id=%{id}" onclick="return delConfirm('确定删除吗！')">删除</s:a>
						</s:if>
						<a href="myApplication_viewHisComment.action?id=%{id}">查看流转记录</a>
						<s:if test="%{fileTempliateId != null}">						
							<a action="myApplication_downTemplateFile.action?templateFileUrl=%{applicationFileUrl}">下载文件</a>						
						</s:if>
					</td>
				</tr>
			</s:iterator>
			</s:if>
        </tbody>
    </table>
    
	<!--分页信息-->
	<div class="hint">
  		 <%@ include  file="/WEB-INF/jsp/public/pageView.jspf"%>  
    </div>  
   
   <br><br>
	<div class="description">
		说明：<br />
		1，按申请时间降序排列，第1页看到的是最近的申请。
	</div>
	
	
</div>


</body>
</html>
