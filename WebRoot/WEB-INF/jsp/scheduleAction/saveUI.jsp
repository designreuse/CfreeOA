<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
	<title>日程信息</title>
   	<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
     <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 日程信息
</div>

<!--显示表单内容-->
<div id=MainArea>

    <s:form action="schedule_%{id == null ? 'add' : 'edit'}">
    	<s:hidden name="id" />
        
        <%-- <div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
        	<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 日程信息 
        		<s:fielderror></s:fielderror>
        	</div> 
        </div> --%>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table >                   
                    <tr><td>标题</td>
                        <td><s:textfield name="title" cssClass="form-control"/></td>
                    </tr>                   
                    <tr class="inline">
                    	<td width="100">使用开始时间</td>
                        <td><s:textfield name="startTime" placeholder="请输入日期" id="start"
                        		 class="inline laydate-icon" cssClass="form-control"/></td>
						<td width="100">使用结束时间</td>
                        <td><s:textfield name="endTime" placeholder="请输入日期" id="end"
                        		class="inline laydate-icon" cssClass="form-control"/></td>
                    </tr>                   
                    <tr><td>内容</td>
                        <td><s:textarea name="content" cssClass="form-control"></s:textarea></td>
                    </tr>
                </table>
            </div>
        </div>
        <!-- 表单操作 -->
        <div id="InputDetailBar">
        	<s:submit class="btn btn-default otherButton" value="保存"/>
           
            <a class="btn btn-default newButton" href="javascript:history.go(-1);">返回</a>
        </div>
	
    </s:form>
</div>
<!-- 日期计算(需放在最后) -->
<script type="text/javascript" src="${pageContext.request.contextPath}/script/js/date.js"></script>
</body>
</html>
