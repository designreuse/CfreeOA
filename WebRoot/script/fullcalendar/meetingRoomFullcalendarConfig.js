
$(function() {
	
	
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
	

	
	//页面加载完初始化日历 
	$('#calendar').fullCalendar({
		//设置日历头部信息
		header: {
			left: 'prev,next today',
			center: 'prevYear,title,nextYear',
			right: 'month,agendaWeek,agendaDay'
		},
		contentHeight: 500,
		buttonText:{
			prev:     '<',
			next:     '>',
			prevYear: '<<',
			nextYear: '>>',
			today:    '今天',
			month:    '月',
			week:     '周',
			day:      '日'
		},
		
		firstDay:1,//每行第一天为周一 
		allDaySlot:false,//是否显示全天
		slotEventOverlap:false,//是否可重叠
        events: rootPath + '/calendarReservation_list.action',

        eventMouseover: function(event, jsEvent, view){                
            showDetail(event, jsEvent);                   
        },  
        eventMouseout: function(event, jsEvent, view){  
            $('#tip').remove();  
        }, 
        
      //点击某一天时促发
		dayClick: function(date, allDay, jsEvent, view) {
			var obj = new Object();
			var selDate =$.fullCalendar.formatDate(date,'yyyy-MM-dd HH:mm:ss');	
			art.dialog({
				title : '申请会议室',
				
				content : '<p>会议主题：<input type="text" name="event" id="event"/></p>'
					+'<p>开始时间：<input type="text" name="startTime" id="start" value="'+ selDate +'"/></p>'
					+'<p>结束时间：<input type="text" name="endTime" id="end" /></p>'								    	
					+'<p>简单描述：<textarea rows="3" cols="3" id="desc"></textarea></p>'								    	

					,
				 button : [{
		        	  value : '提交',
		        	  callback : function(){
		        		  var title = $("input#event").val();
		        		  var start = $("input#start").val();
		        		  var end = $("input#end").val();
		        		  var desc = $("textarea#desc").val();

		        		  if ('' == title) {  
	                            alert("主题不能为空");  
	                            return false;                          
	                      }else if('' == end || end == start) {  
	                    	  alert("结束时间有误");  
	                          return false;  
	                      }
		        		  $.ajax({
		        			  type : "post",
		        			  url : rootPath + "/calendarReservation_add.action",
		        			  dataType : "json",
		        			  cache:false,
		        			  data : {title:title,start:start,end:end,desc:desc},
		        			  success : function(backValue){
		        				  obj.title = title;
		        				  obj.start = start;
		        				  obj.end = end;
		        				  obj.color = '#EFD7A7';
		        				  // TODO 换页时会显示两记录
		        				  $('#calendar').fullCalendar('renderEvent',obj,true);  //显示添加的内容			        		
		        			  }
		        	  		});
		        		  return true;
		        	  }
		          }, {
		        	  value : '取消',
		          }],
			});
    	},
		
		selectable: true, //允许用户拖动 
		select: function( startDate, endDate, allDay, jsEvent, view ){ 
			
	    	var start =$.fullCalendar.formatDate(startDate,'yyyy-MM-dd HH:mm:ss'); 
	    	var end =$.fullCalendar.formatDate(endDate,'yyyy-MM-dd HH:mm:ss');
	    	if(start != end){	    		
	    		art.dialog({
	    			title : '申请会议室',
					
					content : '<p>会议主题：<input type="text" name="event" id="event" placeholder="记录你将要做的一件事..."/></p>'
						+'<p>开始时间：<input type="text" name="startdate" id="startdate" value="'+ start +'"/></p>'
						+'<p>结束时间：<input type="text" name="enddate" id="enddate" value="'+ end +'"/></p>'								    	
						+'<p>简单描述：<textarea id="desc"></textarea></p>',							    	

					 button : [{
			        	  value : '提交',
			        	  callback : function(){
			        		  var selectObj = new Object();
			        		  var title = $("input#event").val();
			        		  var start = $("input#startdate").val();
			        		  var end = $("input#enddate").val();
			        		  var desc = $("textarea#desc").val();
			        		  if ('' == title) {  
		                            alert("主题不能为空");  
		                            return false;                          
		                      }
			        		  
			        		  $.ajax({
			        			  type : "post",
			        			  url : rootPath + "/calendarReservation_add.action",
			        			  dataType : "json",
			        			  cache:false,
			        			  data : {title:title,start:start,end:end,desc:desc},
			        			  success : function(){
			        				  selectObj.title = title;
			        				  selectObj.start = start;
			        				  selectObj.end = end;
			        				  selectObj.color = "#EFD7A7";
			        				  		        		
			        				  // TODO 换页时会显示两记录
			        				  $('#calendar').fullCalendar('renderEvent', selectObj, true);  //显示添加的内容	
			        			  }
			        	  		});
			        		  
			        		  return true;
			        	  }
			          }, {
			        	  value : '取消',
			          }],
				});
	    	}
		} 
	});
});

function showDetail(obj, e){  
    var str;  
    
    var eInfo = '<div id="tip"><ul>';  
    eInfo += '<li class="clock">' + '开始：'+$.fullCalendar.formatDate(obj.start,"yyyy-MM-dd HH:mm:ss") 
    			+'</br>结束：'+$.fullCalendar.formatDate(obj.end,"yyyy-MM-dd HH:mm:ss")+ '</li>';  
    eInfo += '<li class="message">' +'负责人：'+ obj.principal + '<br/> </li>';  
    eInfo += '<li class="message">' +'申请详情：'+ obj.desc + '<br/> </li>'; 
    eInfo += '<li class="message">' +'审批反馈：'+ obj.feedback + '<br/> </li>';
    //eInfo += '<li>分类：' + obj.title + '</li>';  
    //eInfo += '<li class="postmessage">' + str + '<br/> </li>';  
    eInfo += '</ul></div>';  
    $(eInfo).appendTo($('body'));  
    $('#tip').css({"opacity":"0.4", "display":"none", "left":(e.pageX + 20) + "px", "top":(e.pageY + 10) + "px"}).fadeTo(600, 0.9);  
    //鼠标移动效果  
    $('.fc-event-inner').mousemove(function(e){  
        $('#tip').css({'top': (e.pageY + 10), 'left': (e.pageX + 20)});  
    });  
} 

//======================riqi
