
$(function() {

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
	//alert(${pageContext.request.contextPath});
	
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
        editable: true,//启用拖动 
        events: rootPath + '/calendar_list.action',

        eventMouseover: function(event, jsEvent, view){                
            showDetail(event, jsEvent);                   
        },  
        eventMouseout: function(event, jsEvent, view){  
            $('#tip').remove();  
        }, 
        
      //点击某一天时促发
		dayClick: function(date, allDay, jsEvent, view) {
			var obj = new Object();
			var selDate =$.fullCalendar.formatDate(date,'yyyy-MM-dd');	
			art.dialog({
				title : '新建日程',
				
				content : '<p>日程内容：<input type="text" name="event" id="event" placeholder="记录你将要做的一件事..."/></p>'
					+'<p>开始时间：<input type="text" name="startdate" id="startdate" value="'+ selDate +'"/>'
					+'<p>结束时间：<input type="text" name="enddate" id="enddate" />'					
					+'<p>紧急程度：<span id="sel_start"><select name="color" id="color">'
			    	+'<option value="#06c">普通</option>'
			    	+'<option value="#f30">重要</option>'	
			    	+'<select/>'
					+'<p><input type="checkbox" id="isallday" name="isallday"> 全天'	
					,
				 button : [{
		        	  value : '保存',
		        	  callback : function(){
		        		  var title = $("input#event").val();
		        		  var start = $("input#startdate").val();
		        		  var end = $("input#enddate").val();
		        		  var color = $("#color").val();
		        		  var isallday = 1;		
		        		  if(!$("input#isallday").prop('checked')){	        			  
		        			  isallday = 0;
		        		  }

		        		  if ('' == title) {  
	                            alert("标题不能为空");  
	                            return false;                          
	                      }else if('' == end || end == start) {  
	                            isallday = 1;  
	                      }
		        		  $.ajax({
		        			  type : "post",
		        			  url : rootPath + "/calendar_add.action",
		        			  dataType : "json",
		        			  cache:false,
		        			  data : {title:title,start:start,end:end,color:color,isallday:isallday},
		        			  success : function(backValue){
		        				  obj.title = title;
		        				  obj.start = start;
		        				  obj.end = end;
		        				  obj.color = color;
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
		
		//单击事件项时触发 
        eventClick: function(calEvent, jsEvent, view) {
        	var obj = new Object();
        	var start =$.fullCalendar.formatDate(calEvent.start,'yyyy-MM-dd HH:mm:ss');
        	var end =$.fullCalendar.formatDate(calEvent.end,'yyyy-MM-dd HH:mm:ss');
        	if(end == ''){
        		end = start;
        	}
        	art.dialog({
        		title:'日程信息',
        		content : '<p>日程内容：<input type="text" name="event" id="event" value="'+ calEvent.title +'"/></p>'
					+'<p>开始时间：<input type="text" name="startdate" id="startdate" value="'+ start +'"/>'
					+'<p>结束时间：<input type="text" name="enddate" id="enddate" value="'+ end +'"/>'
					+'<p>紧急程度：<span id="sel_start"><select name="color" id="color">'
			    	+'<option value="#06c">普通</option>'
			    	+'<option value="#f30">重要</option>'	
			    	+'<select/>'
					+'<p><input type="checkbox" id="isallday" name="isallday" > 全天',
					
				button:[{
					value:'移除',
					callback:function(){
						$.ajax({
							type:'post',
							url:rootPath + "/calendar_delete.action",
							dataType:'json',
							cache:false,
							data:{id:calEvent.id}													
						});
						$('#calendar').fullCalendar('removeEvents',calEvent.id);
						return true;
					}
					},{
					value:'修改',
					callback:function(){						
						var title = $("input#event").val();
		        		var start = $("input#startdate").val();
		        		var end = $("input#enddate").val();
		        		var color = $("#color").val();
		        		var isallday = 1;
		        		if(!$("input#isallday").prop('checked')){	        			  
		        		  isallday = 0;
		        		}

		        		if ('' == title) {  
	                          alert("标题不能为空");  
	                          return false;                          
	                    }else if('' == end || end == start) {  
	                    	isallday = 1; 
	                    }
		        		$.ajax({
		        			type : "post",
		        			url : rootPath + "/calendar_edit.action",
		        			dataType : "json",
		        			cache:false,
		        			data : {id:calEvent.id,title:title,start:start,end:end,color:color,isallday:isallday}		        			
		        		});
		        		calEvent.title = title;
		        		calEvent.start = start;
		        		calEvent.end = end;
		        		calEvent.color = color;
		        		
		        		$('#calendar').fullCalendar('updateEvent',calEvent);  //显示添加的内容			        		
		        		
					}
						
					},{
					value:'取消'
				}]
        	});
        	
        },
		
		//拖动事件 
		eventDrop: function(event,dayDelta,minuteDelta,allDay,revertFunc) { 
        	$.ajax({
        		type:'post',
        		url:rootPath + "/calendar_eventDrop.action",
        		data:{id:event.id,dayDelta:dayDelta,minuteDelta:minuteDelta,allDay:allDay},
        		error:function(){
        			revertFunc(); //恢复原状 
        		}
        	});
    	},
		
		//日程事件的缩放
		eventResize: function(event,dayDelta,minuteDelta,revertFunc) { 
			$.ajax({
        		type:'post',
        		url:rootPath + "/calendar_eventResize.action",
        		cache:false,
        		data:{id:event.id,dayDelta:dayDelta,minuteDelta:minuteDelta},
        		error:function(){
        			revertFunc(); //恢复原状 
        		}
        	});
		},
		
		selectable: true, //允许用户拖动 
		select: function( startDate, endDate, allDay, jsEvent, view ){ 
			
	    	var start =$.fullCalendar.formatDate(startDate,'yyyy-MM-dd HH:mm:ss'); 
	    	var end =$.fullCalendar.formatDate(endDate,'yyyy-MM-dd HH:mm:ss');
	    	if(start != end){	    		
	    		art.dialog({
					title : '新建日程',
					
					content : '<p>日程内容：<input type="text" name="event" id="event" placeholder="记录你将要做的一件事..."/></p>'
						+'<p>开始时间：<input type="text" name="startdate" id="startdate" value="'+ start +'"/>'
						+'<p>结束时间：<input type="text" name="enddate" id="enddate" value="'+ end +'"/>'					
						+'<p>紧急程度：<span id="sel_start"><select name="color" id="color">'
				    	+'<option value="#06c">普通</option>'
				    	+'<option value="#f30">重要</option>'	
				    	+'<select/>'
						+'<p><input type="checkbox" id="isallday" name="isallday" checked> 全天'	
						,
					 button : [{
			        	  value : '保存',
			        	  callback : function(){
			        		  var globalObj = new Object();
			        		  var title = $("input#event").val();			        		  
			        		  var color = $("#color").val();
			        		  var isallday = 1;
			        		  if(!$("input#isallday").prop('checked')){	        			  
			        			  isallday = 0;
			        		  }

			        		  if ('' == title) {  
		                            alert("标题不能为空");  
		                            return false;                          
		                      }
			        		  $.ajax({
			        			  type : "post",
			        			  url : rootPath + "/calendar_add.action",
			        			  dataType : "json",
			        			  cache:false,
			        			  data : {title:title,start:start,end:end,color:color,isallday:isallday},
			        			  success : function(){
			        				  globalObj.title = title;
			        				  globalObj.start = start;
			        				  globalObj.end = end;
			        				  globalObj.color = color;
			        				  		        		
			        				  // TODO 换页时会显示两记录
			        				  $('#calendar').fullCalendar('renderEvent',globalObj,true);  //显示添加的内容	
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
    //eInfo += '<li class="message">' +'日志：'+ obj.description + '<br/> </li>';  
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