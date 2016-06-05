
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
        events: rootPath + '/calendarReservation_list.action?state=0',

        eventMouseover: function(event, jsEvent, view){                
            showDetail(event, jsEvent);                   
        },  
        eventMouseout: function(event, jsEvent, view){  
            $('#tip').remove();  
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
        		title:'预约处理',
        		content : '<p>日程内容：'+ calEvent.title +'</p>'
					+'<p>开始时间：'+ start +'</p>'
					+'<p>结束时间：'+ end +'</p>'									
					+'<p>简单说明：'+ calEvent.desc +'</p>'
					+'<p>反馈：<textarea id="desc"></textarea></p>'
					,
				button:[{
					value:'同意',
					callback:function(){
						var desc = $('textarea#desc').val();
						alert(calEvent.id);
						$.ajax({
							type:'post',
							url:rootPath + "/calendarReservation_handel.action",
							cache:false,
							data:{id:calEvent.id, desc:desc, state:1}													
						});
						$('#calendar').fullCalendar('updateEvent',calEvent);
						return true;
					}
					},{
					value:'不同意',
					callback:function(){						
						var desc = $('textarea#desc').val();
		        		var color = '#ED1C24';
		        		
		        		$.ajax({
		        			type : "post",
		        			url : rootPath + "/calendarReservation_handel.action",
		        			cache:false,
		        			data : {id:calEvent.id, desc:desc, state:2}		        			
		        		});

		        		calEvent.color = color;
		        		
		        		$('#calendar').fullCalendar('updateEvent',calEvent);  //显示添加的内容			        		
		        		
					}
						
					},{
					value:'取消'
				}]
        	});
        	
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