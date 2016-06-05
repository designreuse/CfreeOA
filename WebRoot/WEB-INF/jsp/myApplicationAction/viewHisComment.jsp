<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>审批文件</title>
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>
 
<!-- 标题显示 -->
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>审批文件申请</strong></span> 
</div>

<!--显示表单内容-->
<div id=MainArea>		
<div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
   	<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 审批信息 </div> 
   </div>
   <div class="ItemBlockBorder">
       <div class="ItemBlock">
           <table cellpadding="0" cellspacing="0" class="table table-hover table-bordered TableStyle">          
              	<!-- 文件类型数据 -->
              	<s:if test="%{#fileTemplateId == null}">
              		<tr>
              		<td>审批的文件</td>
              		<td>
              		<a href="javascript:openWindow('readOnLine/myApplicationAction/loading.jsp?id=<s:property value='id'/>');">点击预览</a>
              		　　
              		<s:a action="myApplication_downTemplateFile?templateFileUrl=%{applicationFileUrl}">下载文件</s:a>
              		</td> 
              		<td></td>                		
              	</tr>       
              	</s:if>  
              	<s:else>
              	<s:iterator value="#th" status="index">              		
              		<tr>
              			<td style="width: 30%"><s:property/></td>
              			<td><s:property value="#formDataMap[#fileds[#index.index]]"/></td>
              		</tr>
              	</s:iterator>
              	
              	</s:else>         	                                                        
           </table>
       </div>
   </div>
</div>

<!-- 审批记录 -->

<div id="Description">
	审批记录：<br />
	<table cellspacing="0" cellpadding="0" class="table table-hover table-bordered TableStyle">
		<!-- 表头-->
        <thead>
		      <tr align="CENTER" valign="MIDDLE" id="TableTitle">
		          <td width="230">时间</td>	    
		          <td width="300">审批人</td>	      
		          <td width="430">审批意见</td>  
		          <td width="300">附件</td>       
		      </tr>  
		 </thead>         
		 <tbody id="TableData" class="dataContainer" datakey="userList">
		 <!-- 文件流转记录显示 -->
		 	<s:if test="#commentOVs!=null && #commentOVs.size()>0">
		 		<s:iterator value="#commentOVs">
		 			<tr class="TableDetail1 template">
			 			<td><s:date name="comment.time" format="yyyy-MM-dd HH:mm:ss"/></td>
			 			<td><s:property value="comment.userId"/></td>
			 			<td><s:property value="comment.fullMessage"/></td>			 				 			
		 				<td>
			 				<s:if test="haveAttachment">
			 					<a href="javascript:openWindow('readOnLine/myApplicationAction/viewTaskAttachment.jsp?taskId=<s:property value="comment.taskId"/>');">附件预览</a>		 						
			 				</s:if>	
		 							 					 									
					</tr>					
		 		</s:iterator>
		 	</s:if>
			 
		 </tbody>
    </table>
</div>

</body>
</html>