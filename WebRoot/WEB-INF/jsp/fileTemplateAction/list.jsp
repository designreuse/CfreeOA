<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>表单模板列表</title>
   <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
   	<script type="text/javascript" src="script/artdialog/artDialog.js?skin=blue"></script>
	<script type="text/javascript" src="script/artdialog/jquery.artDialog.js"></script>
</head>
<body>

<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>模板信息列表</strong></span> 
</div>

<div style="width:100%">
    <table class="table table-hover table-bordered TableStyle">       
        <!-- 表头-->
        <thead>
            <tr >
            		<th width="250px" style="background:#6fb0c0">模板名称</th>
				<th width="250px" style="background:#6fb0c0">所用流程</th>
				<th width="250px" style="background:#6fb0c0">流程KEY</th>			
				<th width="250px" style="background:#6fb0c0">表单类型</th>				
				<th width="250px" style="background:#6fb0c0">表单状态</th>				
                <th style="background:#6fb0c0">相关操作</th>
            </tr>
        </thead>
		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="userList">
			<s:iterator value="recordList">
				<tr class="TableDetail1 template">
					<input type="hidden" id="id" name="id"/>
					<td>${name}&nbsp;</td>
					<td>${flowName}&nbsp;</td>
					<td>${flowKey}&nbsp;</td>
					<td>
						<s:if test="%{type == 0}">文件表单</s:if>
						<s:elseif test="%{type == 1}">自定义表单</s:elseif>
					&nbsp;</td>
					<td>
						<s:if test="%{used == 0}"><span id="<s:property value='id'/>_">未使用</span></s:if>
						<s:elseif test="%{used == 1}"><span id="<s:property value='id'/>_">正在使用</span></s:elseif>
					&nbsp;</td>
					
					<td style="width: 30%">
						<s:a onClick="return delConfirm('确定删除数据！')" action="fileTemplate_delete?id=%{id}">删除</s:a>						
						
						<s:if test="%{used == 0}">
							<span id="<s:property value='id'/>"><a onclick="setUsed(<s:property value='id'/>)">设置为使用状态</a></span></s:if>
						<s:elseif test="%{used == 1}">
							<span id="<s:property value='id'/>"><a onclick="setUnUsed(<s:property value='id'/>)">设置为未使用状态</a></span></s:elseif>
						
						<s:if test="%{type == 0}">
							<a href="fileTemplate_downTemplateFile?templateFileUrl=%{templateFileUrl}">下载</a>
							<s:a action="fileTemplate_editUI?id=%{id}">修改</s:a>
						</s:if>
						<s:elseif test="%{type == 1}">
							<a onclick="preview(<s:property value='id'/>)">预览</a>
							
							<s:a action="fileTemplate_customEditUI?id=%{id}">修改</s:a>
						</s:elseif>
					</td>
					
				</tr>
			</s:iterator>
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div class="footer">      
		<s:a class="btn btn-default newButton" action="fileTemplate_addUI">新建文件模板</s:a>
		<s:a class="btn btn-default newButton" action="fileTemplate_customAddUI">新建自定义表单模板</s:a>
    </div>

	<!-- 分页信息 -->
	<div class="hint">
	    <%@ include  file="/WEB-INF/jsp/public/pageView.jspf"%>
	    <s:form action="template_list"></s:form>
    </div>
    
    
	<br><br>
    <div class="description">
		说明：<br />
		1，删除时，相应的文件也被删除。<br />		
	</div>

</div>

<script type="text/javascript">
		function getRootPath(){

	    //获取当前网址，如： http://localhost:8083/maintain/share/meun.jsp

	    var curWwwPath=window.document.location.href;

	    //获取主机地址之后的目录，如： maintain/share/meun.jsp

	    var pathName=window.document.location.pathname;

	    var pos=curWwwPath.indexOf(pathName);

	    //获取主机地址，如： http://localhost:8083

	    var localhostPaht=curWwwPath.substring(0,pos);

	    //获取带"/"的项目名，如：/maintain

	    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

	    return(localhostPaht+projectName);

	}

	var rootPath = getRootPath();	
	
	function preview(id){
		$.ajax({
			type:'post',
			url: rootPath + '/fileTemplate_preview.action?id=' + id,
			success:function(formDate){
				art.dialog({
					title : '表单',
					content: formDate					
				});
			}
		});
	}

	function setUsed(id){
		$.ajax({
			type:'post',
			url:rootPath + '/fileTemplate_setUsed.action?id=' + id,
			success:function(success){
				
				if("1" == success){//成功返回
					//修改连接内容
					var a = '<a onclick="setUnUsed(' + id +')">设置为不使用</a>';
					var statue = '正在使用';
					var spanId = 'span#'+id;
					var staueId = 'span#'+ id + '_';
					$(spanId).html(a);
					$(staueId).text(statue);
				}
			}
		});
	}
	
	function setUnUsed(id){
		$.ajax({
			type:'post',
			url:rootPath + '/fileTemplate_setUnUsed.action?id=' + id,
			success:function(success){
				if("1" == success){//成功返回
					//修改连接内容
					var a = '<a onclick="setUsed(' + id +')">设置为使用</a>';
					var statue = '未使用';
					
					var spanId = 'span#'+id;
					var staueId = 'span#'+ id + '_';
					
					$(spanId).html(a);
					$(staueId).text(statue);
				}
			}
		});
	}

</script>

</body>
</html>
