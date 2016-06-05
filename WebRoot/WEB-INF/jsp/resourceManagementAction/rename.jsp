<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<HTML>
<HEAD>
<TITLE>文件夹重命名</TITLE>
    <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8" />
	<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</HEAD>
<BODY>

<!-- 标题显示 -->
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>文件夹重命名</strong></span> 
</div>

<!--显示表单内容-->
<DIV ID=MainArea>
    <s:form action="resource_rename">
    	<s:hidden name="parentDirectory"></s:hidden>
    	<s:hidden name="currentPath"></s:hidden>
    
        <%-- <DIV CLASS="ItemBlock_Title1">信息说明<DIV CLASS="ItemBlock_Title1">
        	<IMG BORDER="0" WIDTH="4" HEIGHT="7" SRC="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 
        	<s:fielderror name="fileExist"></s:fielderror>
        	</DIV> 
        </DIV> --%>
        
        <!-- 表单内容显示 -->
        <DIV CLASS="ItemBlockBorder" style="margin-left:5px;margin-top:20px">
            <DIV CLASS="ItemBlock">
                <TABLE>
                    <TR HEIGHT="50">
						<TD WIDTH="120" style="font-size:14px">
							<b>原文件夹名称:</b>
						</TD>
						<TD><s:textfield name="name" readonly="true" CLASS="form-control" style="font-size:13px;height:30px"/></TD>
					</TR>
					<TR HEIGHT="25">
						<TD WIDTH="120" style="font-size:14px">
							<b>新文件夹名称:</b>
						</TD>
						<TD><s:textfield name="newName" cssClass="form-control"  style="font-size:13px;height:30px"/></TD>
						
					</TR>
                </TABLE>
            </DIV>
        </DIV>
        <div style="height:20px"></div>
        <!-- 表单操作 -->
        <DIV ID="InputDetailBar">
            <s:submit class="btn btn-default otherButton" value="保存" style="height:28px;margin-left:6%"/>
            <%-- <INPUT TYPE="image" SRC="${pageContext.request.contextPath}/style/images/save.png"/> --%>
             <a class="btn btn-default newButton" href="javascript:history.go(-1);" style="margin-left:10px;height:28px">返回</a>
        </DIV>
    </s:form>
</DIV>

<DIV CLASS="Description" style="margin-top:20px">
	说明：<BR />
	&nbsp;新的文件夹名称，注意不要与本文件夹中已有的文件夹重名
</DIV>

</BODY>
</HTML>
