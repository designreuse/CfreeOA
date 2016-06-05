<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>文档模板信息</title>
<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>


</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>文档模板信息</strong></span> 
</div>

<!--显示表单内容-->
<div id=MainArea>
    <s:form action="fileTemplate_%{id == null ? 'add' : 'edit'}" enctype="multipart/form-data">
    	<s:hidden name="id"></s:hidden>
    
        <%-- <div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
        	<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 模板基本信息 </div> 
        </div> --%>
        <div style="height:5px"></div>
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table style="margin-top:15px">
				   	<tr>
                        <td style="font-size:13px;width:100px;text-align:center"><b>模板名称:</b></td>
                        <td><s:textfield name="name" class="form-control" /></td>
                    </tr>
                    <tr style="height:60px">
                        <td style="font-size:13px;width:100px;text-align:center"><b>所用流程:</b></td>
                        <td>
                        <div class="form-group">
                       	 	<s:select name="flowKey" class="form-control" 
                        		list="#processOVs"
                        		listKey="processDedinitionKey" listValue="deploymentName" />
                        </div>
						</td>
                    </tr>
                     <tr style="padding-top:5px">
                <td style="font-size:13px;width:100px;text-align:center"><b>模板文件:</b>
                </td>
                <td>
                <form>
    <input type="file" name="" size="" maxlength="" accept=".doc">
                </form>
                </td>
                </tr>
 
 
                </table>
                <div style="height:10px"></div>
                <span style="margin-left:100px">*请上传doc格式的文件</span>
            </div>
        </div>
		

        

        
        <br/><br/>
        <!-- 表单操作 -->
        <div id="InputDetailBar" style="margin-left:30px">
       		 <s:submit class="btn btn-default otherButton" value="保存" style="height:30px;margin-left:6%"/>           
            <a class="btn btn-default newButton" href="javascript:history.go(-1);" style="height:30px;margin-left:10px">返回</a>
        </div>
    </s:form>
</div>

<br/><br/><br/>
<div class="Description" style="margin-left:20px">
	说明:<br />
	1.模板文件是doc扩展名的文件（Word文档）;<br />
	2.如果是添加：必须要选择模板文件;<br />
	3.如果是修改：只是在 需要更新模板文件时 才选择一个文件。
</div>

</body>
</html>
