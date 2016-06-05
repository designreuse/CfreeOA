<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <link href="${pageContext.request.contextPath}/style/bootstrap/bootstrap.min.css" rel="stylesheet">
  <link type="text/css" rel="stylesheet" href="style/blue/menu.css" />
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/pageCommon.css" />
    <%@ include file="/common/global.jsp"%>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include-base-styles.jsp" %>
	<%@ include file="/common/include-jquery-ui-theme.jsp" %>
	<%@ include file="/common/include-custom-styles.jsp" %>
	    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
	<title>流程定义模型列表</title>

	<script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(function() {
    	$('#create').click(function() {
    		$('#createModelTemplate').dialog({
    			modal: true,
    			width: 500,
    			buttons: [{
    				text: '创建',
    				click: function() {
    					if (!$('#name').val()) {
    						alert('请填写名称！');
    						$('#name').focus();
    						return;
    					}
                        setTimeout(function() {
                            location.reload();
                        }, 1000);
    					$('#modelForm').submit();
    				}
    			}]
    		});
    	});
    });
    </script>
     
</head>
<body>
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>流程定义模型</strong></span> 
</div>
<div style="height:0px;width:100%"></div>
	<div style="width:100%">
	<table width="100%" class="table table-hover table-bordered TableStyle">
		<thead>
			<tr>
				<th style="background:#6fb0c0">ID</th>
				<th style="background:#6fb0c0">KEY</th>
				<th style="background:#6fb0c0">Name</th>
				<th style="background:#6fb0c0">Version</th>
				<th style="background:#6fb0c0">创建时间</th>
				<th style="background:#6fb0c0">最后更新时间</th>
				<th style="background:#6fb0c0">元数据</th>
				<th style="background:#6fb0c0">操作</th>
			</tr>
		</thead>
		<tbody>			
			<s:iterator value="#list">
				<tr>
					<td>${id }</td>
					<td>${key }</td>
					<td>${name}</td>
					<td>${version}</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:date name="lastUpdateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${metaInfo}</td>
					<td>
						<a href="${ctx}/service/editor?id=${id}" target="_blank">编辑</a>
						<a href="model_deploy.action?id=${id}">部署</a>
						<a href="model_export.action?id=${id}" target="_blank">导出</a>
                        <a href="model_delete.action?id=${id}">删除</a>
					</td>
				</tr>
			</s:iterator>			
		</tbody>
	</table>
		<div style="text-align:left"><button id="create" class="btn" style="background:#9BBCB6;color:#004779;height:30px;width:60px">创建</button></div>
	<div id="createModelTemplate" title="创建模型" class="template">
        <form id="modelForm" action="model_create.action" target="_blank" method="post">
		<table>
			<tr>
				<td>名称：</td>
				<td>
					<input id="name" name="name" type="text" />
				</td>
			</tr>
			<tr>
				<td>KEY：</td>
				<td>
					<input id="key" name="key" type="text" />
				</td>
			</tr>
			<tr>
				<td>描述：</td>
				<td>
					<textarea id="description" name="description" style="width:300px;height: 50px;">						
					</textarea>
				</td>
			</tr>
		</table>
        </form>
	</div>
	
<div style="height:30px"></div>
<div class="Description">
	说明：<br />
	1、创建时key 不能含有中文<br />
	2、在流程设计器内设计，流程id不能有中文<br />
	3、导出文件名称是：流程id.bpmn20.xml<br />
	4、注意：流程设计器内并不能修改已设定好的key和流程id<br />
	5、节点名称不能有中文。如有中文部署会失败（待解决）<br />					
</div>
</body>
</html>