<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     
    <title>预约详情</title>
  
  </head>
  
  <body>
    <div id="main">
     	<table border="1">
     		<tr>
     			<td colspan="4" align="center">${title }</td>
     		</tr>
     		<tr>
     			<td>申请时间</td>
     			<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
     			<td>使用时间</td>
     			<td><s:date name="startTime" format="yyyy-MM-dd HH:mm:ss"/> 至
     			 	<s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/></td>
     		</tr>
     		
     		<tr>
     			<td>会议室名称</td>
     			<td>${meetingRoom.name }</td>
     			<td>位置</td>
     			<td>${meetingRoom.position }</td>
     		</tr>
     		<tr>
     			<td>会议室容纳人数</td>
     			<td>${meetingRoom.capacity }</td>
     			<td>会议室设备情况</td>
     			<td>${meetingRoom.equipmentCases }</td>
     		</tr>
     		<tr>
     			<td>预约处理人</td>
     			<td>${handelUser }</td>
     			<td>预约情况</td>
     			<td>
     				<s:if test="state == 0">预约中</s:if>
     				<s:elseif test="state == 1">预约成功</s:elseif>
     				<s:elseif test="state == 2">预约失败</s:elseif>
     			</td>
     		</tr>
     		<tr>
     			<td>预约说明</td>
     			<td colspan="3">${summary }</td>    			
     		</tr>
     		<tr>
     			<td>预约反馈意见</td>
     			<td colspan="3">${feedback }</td>    			
     		</tr>
     	</table>
    
    </div>
  </body>
</html>
