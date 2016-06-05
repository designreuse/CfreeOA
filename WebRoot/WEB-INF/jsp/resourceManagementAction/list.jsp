<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<HTML>
<HEAD>
	<TITLE>文件管理</TITLE>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8" />
	<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</HEAD>
<BODY STYLE="background-color: #F7FFFF;">	

<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>文件列表</strong></span> 
</div>



<!--目录列表-->
<DIV ID="dirListArea" STYLE="width: 260px; float: left;">
	<DIV CLASS="Menu_head_Table" ID="dirListTitle"> 		
		<!-- 错误信息提示 -->
		<s:property value="%{dirExists }"/>		
	</DIV>
</DIV>
<!--目录内容列表显示-->
<DIV style="width:100%">
	<TABLE class="table table-hover table-bordered TableStyle">
		<!-- 表头-->
		<THEAD>
			<TR ALIGN="CENTER" VALIGN="MIDDLE" ID="TableTitle">
				<th WIDTH="20%" style="background:#6fb0c0">名称</th>
				<th WIDTH="15%" style="background:#6fb0c0">大小</th>
				<th WIDTH="30%" style="background:#6fb0c0">当前路径</th>
				<th WIDTH="20%" style="background:#6fb0c0">创建时间</th>
				<th width="20%" style="background:#6fb0c0">相关操作</th>
			</TR>
		</THEAD>

		<!--显示文件夹列表-->
		<tbody id="TableData" class="dataContainer" datakey="userList">
			<s:iterator value="#folders">
				<TR CLASS="TableDetail1 template">
					<TD width="500"><IMG SRC="${pageContext.request.contextPath}/style/images/FileType/folder.gif"/>
						<s:a action="resource_list?name=%{name}&parentDirectory=%{parentDirectory}&currentPath=%{currentPath}">${name}</s:a>&nbsp;
					</TD>
					<TD width="150">&nbsp;</TD>
					<td style="width: 300px;">${currentPath }</td>
					<TD width="200"><s:date name="lastModified" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</TD>
					<TD width="200">
						<s:a action="resource_renameUI?name=%{name}&parentDirectory=%{parentDirectory}&currentPath=%{currentPath}">重命名</s:a>
						<s:a onClick="return delConfirm('确定要删除文件夹及其下的所有文件!')"
							action="resource_delete?name=%{name}&parentDirectory=%{parentDirectory}&currentPath=%{currentPath}">删除</s:a>
					</TD>
				</TR>
			</s:iterator>
		</TBODY>
		
		<!--显示文件列表-->
		<TBODY >
			<s:iterator value="#fileOVs">
				<TR CLASS="TableDetail1 template">
					<TD width="500"><IMG SRC="${pageContext.request.contextPath}/style/images/FileType/${fileType}.gif"/>
						<s:a action="resource_downFile?name=%{name}&parentDirectory=%{parentDirectory}&currentPath=%{currentPath}">${name}</s:a>&nbsp;
					</TD>
					<TD width="150" ALIGN="right" STYLE="padding-right: 15px;">${fileSize} KB&nbsp;</TD>
					<td style="width: 300px;">${currentPath }</td>
					<TD width="200"><s:date name="lastModified" format="yyyy-MM-dd HH:mm:ss"/></TD>
					<TD width="200">
						<s:a action="resource_renameUI?name=%{name}&parentDirectory=%{parentDirectory}&currentPath=%{currentPath}">重命名</s:a>
						<s:a onClick="return delConfirm('确定要删除文件夹及其下的所有文件')" 
							action="resource_delete?name=%{name}&parentDirectory=%{parentDirectory}&currentPath=%{currentPath}">删除</s:a>
					</TD>
				</TR>
			</s:iterator>
		</TBODY>
	</TABLE>
	
	<!-- 其他功能超链接 -->
	<DIV class="footer">		
		<s:a  class="btn btn-default newButton"
		  action="resource_addFolderUI?parentDirectory=%{parentDirectory}&currentPath=%{currentPath}" style="height:28px">新建</s:a>
		  
		<s:a  class="btn btn-default newButton"
		  action="resource_uploadFileUI?parentDirectory=%{parentDirectory}&currentPath=%{currentPath}" style="height:28px;margin-left:10px">上传文件</s:a>
		<s:if test='%{currentPath != null }'>
		<%-- <A HREF="#"><IMG BORDER="0" ALT="返回上级文件夹" SRC="${pageContext.request.contextPath}/style/images/folder_up.gif" /></A> --%>
		<s:a class="btn btn-default newButton"
		action="resource_getParentDir?parentDirectory=%{parentDirectory}&currentPath=%{currentPath}" style="height:28px;margin-left:10px">返回上层</s:a>
		</s:if>
	
	</DIV>
	<div style="height:40px"></div>
	<DIV CLASS="Description">		
		说明：<br/>
		&nbsp;1.单击文件名，可以下载这个文件;<br/>
		&nbsp;2.下载时指定存储的名称为文件名。<BR />
	</DIV>
	
	
</DIV>

</BODY>
</HTML>
