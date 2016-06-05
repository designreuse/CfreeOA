<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>

    <title>会议室预约详情</title>
    	
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/fullcalendar/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/fullcalendar/fullcalendar2.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/fullcalendar/skins/default.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/fullcalendar/skins/blue.css">
<style type="text/css">

#calendar {
	width: 960px;
	margin: 20px auto 10px auto
}

.fancy {
	width: 450px;
	height: auto
}

.fancy h3 {
	height: 30px;
	line-height: 30px;
	border-bottom: 1px solid #d3d3d3;
	font-size: 14px
}

.fancy form {
	padding: 10px
}

.fancy p {
	height: 28px;
	line-height: 28px;
	padding: 4px;
	color: #999
}

.input {
	height: 20px;
	line-height: 20px;
	padding: 2px;
	border: 1px solid #d3d3d3;
	width: 100px
}

.btn {
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	padding: 5px 12px;
	cursor: pointer
}

.btn_ok {
	background: #360;
	border: 1px solid #390;
	color: #fff
}

.btn_cancel {
	background: #f0f0f0;
	border: 1px solid #d3d3d3;
	color: #666
}

.btn_del {
	background: #f90;
	border: 1px solid #f80;
	color: #fff
}

.sub_btn {
	height: 32px;
	line-height: 32px;
	padding-top: 6px;
	border-top: 1px solid #f0f0f0;
	text-align: right;
	position: relative
}

.sub_btn .del {
	position: absolute;
	left: 2px
}

/********************************************** 鼠标悬停tip提示  ************************************************/  
    #tip{  
        position: absolute;  
        width: 250px;  
        max-width: 400px;  
        text-align: left;  
        padding: 4px;  
        border: #87CEEB solid 7px;  
        border-radius: 5px;  
        background: #00BFFF;  
        z-index: 1000;  
        behavior: url('/css/css3/pie.htc');  
    }  
    #tip ul{  
        margin: 0;  
        padding: 0;  
    }  
    #tip ul li{  
        font-family:'Microsoft YaHei', 微软雅黑, 'Microsoft JhengHei', 宋体;  
        font-size:15px;  
        list-style: none;  
        padding-left: 40px;  
    }  
    .clock{  
        /*line-height: 60px;*/  
        background: url('./fullcalendar/images/clock.png') no-repeat;  
        background-size:40px 40px;  
    }  
    .postmessage{  
        /*line-height: 60px; 
        font-size: 12px;*/  
        background: url('./fullcalendar/images/message.png') no-repeat;  
        background-size:40px 40px;  
    }  
    .message{  
        /*margin-top: 5px;*/  
        /*line-height: 60px;*/  
        background: url('./fullcalendar/images/text.png') no-repeat;  
        background-size:40px 40px;  
    } 
/****** 事件显示样式  ******/
.showEvent{
	color:red;
}
</style>
<script src='${pageContext.request.contextPath}/script/fullcalendar/jquery-1.9.1.js'></script>
<script src='${pageContext.request.contextPath}/script/fullcalendar/jquery-ui.js'></script>
<script src='${pageContext.request.contextPath}/script/fullcalendar/fullcalendar.min.js'></script>

<script src='${pageContext.request.contextPath}/script/fullcalendar/fullcalendar.js'></script>
<script src='${pageContext.request.contextPath}/script/fullcalendar/artDialog.js'></script>

<script src='${pageContext.request.contextPath}/script/fullcalendar/meetingRoomFullcalendarConfig.js'></script>
  </head>
  
  <body>
    <div id="main" style="width:1060px">
		<h2 class="top_title">
			<a>会议室预约情况</a>
		</h2>
		<div id="meetRoom">
			<s:iterator value="#meetingRooms">
				<span><input type="hidden" value="<s:property value='id'/>"/><button>${name }</button></span>　　
			</s:iterator>
		</div>
		
		<div id='calendar'></div>
	</div>
	
  </body>
  <script type="text/javascript">
  /** 默认会议室id */
  var meetRoomId = $('div#meetRoom span :first input').val();
  
  /**
	 * 获取项目路径
	 */
	function getRootPath(){

	    //获取当前网址，如： http://localhost:8083/maintain/share/meun.jsp

	    var curWwwPath=window.document.location.href;

	    //获取主机地址之后的目录，如： maintain/share/meun.jsp

	    var pathName=window.document.location.pathname;

	    var pos=curWwwPath.indexOf(pathName);

	    //获取主机地址，如： http://localhost:8083

	    var localhostPaht=curWwwPath.substring(0,pos);

	    //获取带"/"的项目名，如：/maintain

	    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

	    return(localhostPaht+projectName);

	}

	var rootPath = getRootPath();
	$(document).ready(function(){
		
			/**
			 * 获取会议室预约情况
			 */
			$('div#meetRoom span').click(function(){
				meetRoomId = $(this).children('input').val();
				$.ajax({
					type:'post',
					url:rootPath + '/calendarReservation_show.action',
					data:{meetRoomId:meetRoomId}
				});
				//window.location.reload();
			});

	});


  </script>
  
</html>
