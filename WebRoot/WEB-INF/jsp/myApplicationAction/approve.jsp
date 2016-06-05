<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>审批处理</title>
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/OpenWindow.js"></script>
</head>
<body>
 
<!-- 标题显示 -->
<div id="Title_bar">
   <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 审批处理
</div>

<!--显示表单内容-->
<div id=MainArea>
    <s:form  action="task_sumbit.action">
		<s:hidden name="id"/>
		<s:hidden name="taskId"/>		
		
		<%-- <div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
        	<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 申请信息 </div> 
        </div> --%>
        
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table style="margin:15px">
                	<!-- 业务数据 显示-->
                    <s:if test="%{#fileTemplateId == null}">
                    	<tr>
	                        <td width="70%" class="pegtitle">审批的文件　　
	                       		<a href="javascript:openWindow('readOnLine/myApplicationAction/loading.jsp?id=<s:property value='id'/>');">点击可预览</a>　
		                        <a href="myApplication_downTemplateFile.action?templateFileUrl=<s:property value='applicationFileUrl'/>">下载文件</a>
	                        </td>
	                        <td  width="20%"></td>
	                        <td width="10%"></td>
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
                    
                    
                    
                    <!-- 提交数据 -->  
                    <tr>
                    	<td colspan="3"><hr/></td>
                    </tr>                                                      
					<tr>
                        <td class="pegtitle">审批意见</td>
                       <s:if test="%{#users != null && #users.size() != 0}">                     
                    		<td class="pegtitle">下一个审批人</td>                     	
                        </s:if>
                        <td></td>
                    </tr> 
                    <tr>
                    	<td><s:textarea name="comment" class="form-control" id="description"
                    				rows="8" cols="15"  placeholder="审批意见"/> </td> 
                    				                  	
                    	<s:if test="%{#users != null && #users.size() != 0}">
                    		<td valign="top">
                    		<s:select name="nextUserName" class="form-control" 
                        		list="#users" listKey="loginName" listValue="name" />
                    	</td>
                    	</s:if>
                    	<td></td>
                    </tr>
                    
                    <!-- 通知方式 -->
                    <s:if test="%{#users != null && #users.size() != 0}">
                    <tr>
                    	<td>发送通知　　
                    	<s:checkboxlist name="notifieds"
                    			list="#{'1':'OA消息', '2':'邮件通知' }" listKey="key" listValue="value"/>      </td>
                    	<td></td>
                    	<td></td>
                    </tr>
                    </s:if>
                    
                    <!-- 流程方向选择 -->
                    <tr>
                    	<td>
                    		 <s:if test="#outcomes!=null && #outcomes.size()>0">
	        					<s:iterator value="#outcomes" var = "outcomeValue">      	
	      							<s:if test='"返回" != #outcomeValue'>
										<input name="outcome" type="submit" value="<s:property/>" class="btn btn-default otherButton"/>	
	        						</s:if>	
	        						<s:elseif test='"admin" == #session.user.loginName'>
	        							<input name="outcome" type="submit" value="<s:property/>" class="btn btn-default otherButton"  />
	        						</s:elseif>	 
	        					</s:iterator>	        	
	        				</s:if>           
							<a class="btn btn-default newButton" href="javascript:history.go(-1);">返回</a>
                    	</td>
                    	<td></td>
                    </tr>
                    
                    
                </table>
            </div>
        </div>
		      
    </s:form>
</div>
<br><br><br>

<!-- 审批记录 -->
<div class="pegtitle">审批记录</div>
 <div style="width:100%">
   <table class="table table-hover table-bordered TableStyle">
      <thead>
         <tr>
            <th width="20%">时间</th>
            <th width="20%">审批人</th>
            <th>审批意见</th>
         </tr>
      </thead>
      <tbody>
         <!-- 文件流转记录显示 -->
		 	<s:if test="#commentOVs!=null && #commentOVs.size()>0">
		 		<s:iterator value="#commentOVs">
		 			<tr >
			 			<td><s:date name="comment.time" format="yyyy-MM-dd HH:mm:ss"/></td>
			 			<td><s:property value="comment.userId"/></td>
			 			<td><s:property value="comment.fullMessage"/></td>			 			
					</tr>
		 		</s:iterator>
		 	</s:if>
      </tbody>
   </table>
</div>

</body>
</html>