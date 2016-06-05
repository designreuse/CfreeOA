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
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 审批文件申请
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id=MainArea>
    <s:form  action="task_sumbit.action" enctype="multipart/form-data">
		<s:hidden name="id"/>
		<s:hidden name="taskId"/>		
		
		<div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
        	<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 审批处理 </div> 
        </div>
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">                 
                   	<tr>
                   		<td>审批的文件</td>                  		
                   		<td>
                   		<s:if test="applicationFileUrl != null">
							<a href="javascript:openWindow('loading.jsp?id=<s:property value='id'/>');">点击预览</a>
						</s:if></td>                 		
                   	</tr>                  	                                    
					<tr>
                        <td width="130">审批意见</td>
                        <td><s:textarea name="comment" cols="30" rows="5"/> </td>                   	
                    	<td width="130">下一个审批人</td>
                    	<td><s:select name="nextUserName" cssClass="SelectStyle" size="5" 
                        		list="#users" listKey="name" listValue="name"/></td>
                    </tr>
                    <tr>
                    	<td>附件上传</td>
                    	<td><s:file name="taskAttachment"/></td>
                    	<td>附件描述</td>
                    	<td><s:textfield name="attachmentDescription"/></td>
                    </tr> 
                </table>
            </div>
        </div>
		
        <!-- 表单操作 -->
        <div id="InputDetailBar">
	        <s:if test="#outcomes!=null && #outcomes.size()>0">
	        	<s:iterator value="#outcomes" var = "outcomeValue">	        	
	        		<s:if test='"返回" != #outcomeValue'>	    
	        			<input name="outcome" type="submit" value="<s:property/>" cssClass="FuncBtnMemo"  />    			
	        		</s:if>	
	        		<s:elseif test='"admin" == #session.user.loginName'>
	        			<input name="outcome" type="submit" value="<s:property/>" cssClass="FuncBtnMemo"  />
	        		</s:elseif>	      		       		
	        	</s:iterator>		        	
	        </s:if>           
			<a href="javascript:history.go(-1);"><img src="${pageContext.request.contextPath}/style/images/goBack.png"/></a>
        </div>
    </s:form>
</div>

<!-- 审批记录 -->

<div id="Description">
	审批记录：<br />
	<table cellspacing="0" cellpadding="0" class="TableStyle">
		<!-- 表头-->
        <thead>
		      <tr align="CENTER" valign="MIDDLE" id="TableTitle">
		          <td width="230">时间</td>	    
		          <td width="300">审批人</td>	      
		          <td width="430">审批意见</td>  
		          <td width="300">附件</td>       
		      </tr>  
		 </thead>         
		 <tbody id="TableData" class="dataContainer" datakey="formList">
		 <!-- 文件流转记录显示 -->
		 	<s:if test="#commentOVs!=null && #commentOVs.size()>0">
		 		<s:iterator value="#commentOVs">
		 			<tr class="TableDetail1 template">
			 			<td><s:date name="comment.time" format="yyyy-MM-dd HH:mm:ss"/></td>
			 			<td><s:property value="comment.userId"/></td>
			 			<td><s:property value="comment.fullMessage"/></td>			 				 			
		 				<td>
			 				<s:if test="haveAttachment">
			 					<a href="javascript:openWindow('viewTaskAttachment.jsp?taskId=<s:property value="comment.taskId"/>');">附件预览</a>			 							 						
			 				</s:if>			 							 					 									
					</tr>					
		 		</s:iterator>
		 	</s:if>
			 
		 </tbody>
    </table>
</div>

</body>
</html>