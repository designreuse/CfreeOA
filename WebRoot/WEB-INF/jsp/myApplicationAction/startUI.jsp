<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>提交申请</title>
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>
 
<!--显示表单内容-->
<div style="width:100%">
    <form action="myApplication_start.action" enctype="multipart/form-data">
    	<s:hidden name="templateName"></s:hidden>
    	<s:hidden name="startProcessKey"></s:hidden>
    
        <%-- <div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
        	<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 下载文档模板 </div> 
        </div> --%>
        
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table class="table table-hover table-bordered TableStyle">
					<tr>
						<td><a href="myApplication_downTemplateFile.action?templateFileUrl=%{templateFileUrl}" 
						style="text-decoration: underline">[点击下载文档模板文件]</a></td>
					</tr>
                </table>
            </div>
        </div>
		
		<div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
        	<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 申请信息 </div> 
        </div>
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table class="table table-hover table-bordered TableStyle">                 
                    <tr>
                        <td width="130">请选择填写好的文档</td>
                        <td><s:file name="applicationFile"  style="width:450px;" /></td>
                        <td>*</td>
                    </tr>
                </table>
            </div>
        </div>
		
        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <s:submit class="btn btn-default otherButton" value="提交申请"/>           
			<a class="btn btn-default newButton" href="javascript:history.go(-1);">返回</a>
        </div>
    </form>
</div>

<br>
<div class="Description">
	使用说明：<br />
	1，下载模板文件。<br />
	2，填写文档中的表单。<br />
	3，选择这个填写好的文件进行提交。<br />
	<br />
	说明2：提交表单后，就会按照 模板对应的流程 开始流转。
</div>

</body>
</html>
