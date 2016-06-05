<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>文件在线预览</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/flexpaper_flash_debug.js"></script>
	<script type="text/javascript" src="js/flexpaper_flash.js"></script>
	<style type="text/css" media="screen"> 
			html, body	{ height:100%; }
			body { margin:0; padding:0; overflow:auto; }   
			#flashContent { display:none; }
    </style>
  </head>
  
  <body>
    	<div id="div_readonline" style="position:absolute;left:50%;top:10px;">
	        <a id="viewerPlaceHolder" style="width:1000px;height:800px;display:block"></a>
	        
	        <script type="text/javascript">
	     		var fp = new FlexPaperViewer(	
						 'FlexPaperViewer',
						 'viewerPlaceHolder', { config : {
						 //swf文件不能有中文名
						 SwfFile : escape('<%=request.getParameter("swfPath")%>'),
						 Scale : 0.8, 
						 ZoomTransition : 'easeOut',
						 ZoomTime : 0.5,
						 ZoomInterval : 0.1,
						 FitPageOnLoad : true,
						 FitWidthOnLoad : true,
						 PrintEnabled : true,
						 FullScreenAsMaxWindow : false,
						 ProgressiveLoading : true,
						 MinZoomSize : 0.1,
						 MaxZoomSize : 5,
						 SearchMatchAll : true,
						 InitViewMode : 'Portrait',
						 
						 ViewModeToolsVisible : true,
						 ZoomToolsVisible : true,
						 NavToolsVisible : true,
						 CursorToolsVisible : true,
						 SearchToolsVisible : false,
  						
  						 localeChain: 'zh_CN'
						 }});
	        </script>
        </div> 	
        <script type="text/javascript">
        	$(document).ready(function(){
        		$("#div_readonline").css("margin-left",-500);
        	});
        </script>
  </body>
</html>
