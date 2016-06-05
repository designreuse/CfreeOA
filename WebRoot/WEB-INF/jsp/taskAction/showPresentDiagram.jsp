<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  
  <body>
    <!-- 获取图片 -->
    <img style="position:absolute; top:0px; left: 0px;" 
    	src="task_viewImage.action?deploymentId=<s:property value="deploymentId"/>&imageName=<s:property value="imageName"/>">
    	
    <!-- 根据当前活动的左边绘制div -->
    <div style="position: absolute; border: 1px solid red; 
 				top: <s:property value='#acs.y'/>;
 				left: <s:property value='#acs.x'/>;
 				width: <s:property value='#acs.width'/>;
 				height: <s:property value='#acs.height'/>;"></div> 				
    
  </body>
</html>
