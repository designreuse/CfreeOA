<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>部署流程定义</title>
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %> 
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>部署流程定义</strong></span> 
</div>

<!--显示表单内容-->
<div id=MainArea>
    <s:form action="process_deploy.action" enctype="multipart/form-data">
    
        <!-- <div class="ItemBlock_Title1">信息说明<DIV CLASS="ItemBlock_Title1">
        	<IMG BORDER="0" WIDTH="4" HEIGHT="7" SRC="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 部署流程定义 </DIV> 
        </div> -->
        <div style="height:20px;width:100%"></div>
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table style="margin-left:5px">
                	<tr>
                		<td width="100px;font-size:14px"><b>文件部署名称:</b></td>
                		<td><s:textfield name="deployName" cssClass="form-control" style="width:250px"/></td>
                	</tr>
                	<tr style="height:5px"></tr>
                    <tr>
						<td width="100px;font-size:14px"><b>流程定义文档:</b></td>
                        <td><s:file name="resource" style="width:250px;" accept=".zip"/></td>
                    </tr>   
            
                </table>
            </div>
        </div>
        <div style="height:5px"></div>
       <span style="margin-left:140px;"> *请上传zip格式的文件 </span>  
        <!-- 表单操作 -->
        <div style="height:25px"></div>
        <div id="InputDetailBar">
        	<s:submit class="btn btn-default otherButton" value="保存" style="height:30px;margin-left:7%" />
            
            <a class="btn btn-default newButton" href="javascript:history.go(-1);" style="height:30px;margin-left:5px">返回</a>
        </div>
    </s:form>
</div>



</body>
</html>
