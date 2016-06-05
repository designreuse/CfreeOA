<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>申请模板选择</title>
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

    <div id="Title_bar" style="margin-bottom: 20px;">
      <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 申请模板选择      
    </div>
    
    <div class="pegtitle">请选择申请模板</div><br>
                                        
    <table>				
            <tr>
                <td background="#e0eff2">
                    <!-- 显示表单模板列表 -->
                    <s:iterator value="recordList">
                        <div id="DetailBlock" class="template"> 
                            <img width="16" height="16" src="${pageContext.request.contextPath}/style/images/FileType/doc.gif"/> 
                            <s:a action="myApplication_startUI?templateId=%{id}">
                            ${name}</s:a> 
                         </div>
                    </s:iterator>
                </td>
            </tr>
    </table>
    
    <!-- 分页信息 -->
	<div class="hint">
	   <%@ include  file="/WEB-INF/jsp/public/pageView.jspf"%>
	   <s:form action="role_list"></s:form>
    </div>
    
    
    <br><br>
    <div class="Description">
       	 说明：此页面是列出所有的表单模板记录<br />
    </div>

</body>
</html>
