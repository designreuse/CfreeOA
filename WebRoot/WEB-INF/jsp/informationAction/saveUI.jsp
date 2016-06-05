<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
	<title>信息设置</title>
   	<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>信息设置</strong></span> 
</div>

<!--显示表单内容-->
<div id=MainArea>

    <s:form action="information_add">
    	<s:hidden name="id" />
        
        <%-- <div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
        	<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 信息设置 
        		<s:fielderror></s:fielderror>
        	</div> 
        </div> --%>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table style="margin-top:20px">                   
                    <tr><td style="width:60px;padding-left:60px;font-size:14px"><b>标题:</b></td>
                        <td style="width:300px"><s:textfield name="title" cssClass="form-control"/></td>
                    </tr>
                    <%-- <tr><td>结束时间</td>
                        <td><s:textfield name="endTime" placeholder="请输入日期"
                        		 class="laydate-icon" cssClass="form-control"
								onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/></td>
                    </tr>  --%>                                
                    <tr style="height:150px"><td style="width:60px;padding-left:60px;font-size:14px"><b>接收人:</b></td>
                        <td><s:select name="userIds" cssClass="SelectStyle"
                        		multiple="true" size="10" 
                        		list="#userList" listKey="id" listValue="name" style="width:300px;height:130px"/></td>
                    </tr>
                    <tr><td style="width:60px;padding-left:60px;font-size:14px"><b>内容:</b></td>
                        <td><s:textarea name="content" cssClass="form-control"></s:textarea></td>
                    </tr>
                </table>
            </div>
        </div>
        <!-- 表单操作 -->
        <div style="height:20px"></div>
        <div id="InputDetailBar" style="margin-left:170px">
        	<s:submit class="btn btn-default otherButton" value="保存" style="width:70px;height:30px"/>
           
            <a class="btn btn-default newButton" href="javascript:history.go(-1);" style="margin-left:10px;width:70px;height:30px">返回</a>
        </div>
	
    </s:form>
</div>
<!-- 日期计算(需放在最后) -->
<script type="text/javascript" src="${pageContext.request.contextPath}/script/js/date.js"></script>

</body>
</html>
