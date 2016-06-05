<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
	<title>配置权限</title>
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
    <script language="javascript" src="${pageContext.request.contextPath}/script/jquery_treeview/jquery.treeview.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/file.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/script/jquery_treeview/jquery.treeview.css" />
	<script type="text/javascript">
		$(function(){
			// 指定事件处理函数
			$("[name=privilegeIds]").click(function(){
				
				// 当选中或取消一个权限时，也同时选中或取消所有的下级权限
				$(this).siblings("ul").find("input").attr("checked", this.checked);
				
				// 当选中一个权限时，也要选中所有的直接上级权限
				if(this.checked == true){
					$(this).parents("li").children("input").attr("checked", true);
				}
				
			});
		});
	</script>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar" style="height:30px;line-height:13px;padding-top:8px">
   <img border="0" width="13" height="13" style="margin-bottom:2px;" src="${pageContext.request.contextPath}/style/images/title_arrow.gif" /> <span style="font-size:14px;valign:middle"><strong>配置权限</strong></span> 
</div>
<div style="height:20px"></div>
<!--显示表单内容-->
<div id=MainArea>
    <s:form action="role_setPrivilege.action">
    	<s:hidden name="id"></s:hidden>
    
        <div class="ItemBlock_Title1"><!-- 信息说明 -->
        <div class="ItemBlock_Title1" style="font-size:14px;margin-left:8px">
        	 <b>正在为【${name}】配置权限</b> 
        	 </div> 
        </div>
        <HR style="filter: alpha(opacity=100,finishopacity=0,style=3)" width="25%" color=#987cb9 SIZE=3 align="left">
        <div></div>
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table  class="mainForm">
					<!--表头-->
					<thead>
						<tr align="LEFT" valign="MIDDLE" id="TableTitle">
							<td width="300px" style="padding-left: 7px;">
								<!-- 如果把全选元素的id指定为selectAll，并且有函数selectAll()，就会有错。因为有一种用法：可以直接用id引用元素 -->
								<input type="checkbox" id="cbSelectAll" onClick="$('[name=privilegeIds]').attr('checked', this.checked)"/>
								<label for="cbSelectAll">全选</label>
							</td>
						</tr>
					</thead>
                   
			   		<!--显示数据列表-->
					<tbody id="TableData" class="dataContainer" datakey="userList">
						<tr class="TableDetail1">
							<!-- 显示权限树 -->
							<td>

<!-- 显示树状结构内容 -->
<ul id="tree">
<s:iterator value="#application.topPrivilegeList">
	<li>
		<input type="checkbox" name="privilegeIds" value="${id}" id="cb_${id}" <s:property value="%{id in privilegeIds ? 'checked' : ''}"/> />
		<label for="cb_${id}"><span class="folder">${name}</span></label>
		<ul>
		<s:iterator value="children">
			<li>
				<input type="checkbox" name="privilegeIds" value="${id}" id="cb_${id}" <s:property value="%{id in privilegeIds ? 'checked' : ''}"/> />
				<label for="cb_${id}"><span class="folder">${name}</span></label>
				<ul>
				<s:iterator value="children">
					<li>
						<input type="checkbox" name="privilegeIds" value="${id}" id="cb_${id}" <s:property value="%{id in privilegeIds ? 'checked' : ''}"/> />
						<label for="cb_${id}"><span class="folder">${name}</span></label>
					</li>
				</s:iterator>
				</ul>
			</li>		
		</s:iterator>
		</ul>
	</li>
</s:iterator>
</ul>


							</td>
						</tr>
					</tbody>
                </table>
            </div>
        </div>
        
        <script language="javascript">
        	$("#tree").treeview();
        </script>
        
        <!-- 表单操作 -->
        <div class="footer" style="padding-bottom:30px">
            <button type="submit" class="btn btn-default otherButton" style="height:30px;margin-left:5%">保存</button>
            <a class="btn btn-default newButton" href="javascript:history.go(-1);" style="height:30px;margin-left:5px">返回</a>
        </div>
    </s:form>
   
</div>


</body>
</html>
    