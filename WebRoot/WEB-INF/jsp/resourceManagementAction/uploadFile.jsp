<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>文件上传</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>文件上传</strong></span> 
</div>

<!--显示表单内容-->
<div id=MainArea>
    <s:form action="resource_uploadFile" enctype="multipart/form-data">
    	<s:hidden name="parentDirectory"></s:hidden>
    	<s:hidden name="currentPath"></s:hidden>
    
        <!-- <div class="ItemBlock_Title1">信息说明<DIV CLASS="ItemBlock_Title1">
        	<IMG BORDER="0" WIDTH="4" HEIGHT="7" SRC="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 文件夹属性</DIV> 
        </div> -->
        <div style="height:10px"></div>
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder" style="margin-left:5px">
            <div class="ItemBlock">
                <table>
                    <tr>
                        <td width="80" style="font-size:14px"><b>修改名称:</b></td>
                        <td><s:textfield name="name" class="form-control" style="height:28px;width:180px"/></td>
                    </tr> 
                    <tr>
                    <td></td>
                    </tr>                  
					<tr>
                        <td style="font-size:14px"><b>选择文件:</b></td>
                        <td><s:file name="upload" /></td>
                    </tr>
                </table>
            </div>
        </div>
        
        <!-- 表单操作 -->
        <div id="InputDetailBar" style="margin-top:20px">
        	<s:submit class="btn btn-default otherButton" value="保存" style="height:28px;margin-left:5%"/>
            <%-- <input type="image" src="${pageContext.request.contextPath}/style/images/save.png"/> --%>
           <a class="btn btn-default newButton" href="javascript:history.go(-1);" style="height:28px;margin-left:10px">返回</a>
        </div>
    </s:form>
</div>

<div class="Description" style="margin:5px">
	<div>说明：</div>
	<div style="margin-top:3px">1.文件夹的名称，注意不要与本文件夹中已有的文件夹重名;</div>
	<div style="margin-top:3px">2.如果需要修改文件名称可重新命名，如果不填写则不改变文件名称</div>
</div>

</body>
</html>
