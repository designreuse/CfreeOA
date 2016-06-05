<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>文件夹属性</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>文件夹属性</strong></span> 
</div>

<!--显示表单内容-->
<div id=MainArea>
    <s:form action="resource_addFolder">
    	<s:hidden name="parentDirectory"></s:hidden>
    	<s:hidden name="currentPath"></s:hidden>
    
        <!-- <div class="ItemBlock_Title1">信息说明<DIV CLASS="ItemBlock_Title1">
        	<IMG BORDER="0" WIDTH="4" HEIGHT="7" SRC="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 文件夹属性</DIV> 
        </div> -->
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table  class="table table-hover table-bordered TableStyle" style="width:40%;margin-top:20px;margin-left:5px;table-border:0px solid #000">
                    <tr>
                        <td width="100" style="font-size:13px;height:20px">文件夹名称</td>
                        <td><s:textfield name="name" class="form-control" style="width:300px"/></td>
                    </tr>                   
					<tr>
                        <td style="font-size:13px;height:40px">上级文件夹</td>
                        <td><s:property value="%{currentPath}"/></td>
                    </tr>
                </table>
            </div>
        </div>
        <div style="height:10px"></div>
        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <s:submit class="btn btn-default otherButton" value="保存" style="height:30px;margin-left:10%;"/>
            <%-- <input type="image" src="${pageContext.request.contextPath}/style/images/save.png"/> --%>
            <a href="javascript:history.go(-1);" class="btn btn-default newButton" style="height:30px;margin-left:5px">返回</a>
        </div>
    </s:form>
</div>
<div style="height:30px"></div>
<div class="Description" style="margin-left:5px">
	说明：<br />
	文件夹的名称，注意不要与本文件夹中已有的文件夹重名。
</div>

</body>
</html>
