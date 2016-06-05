package cn.sise.oa.action;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sise.oa.action.bean.CalendarOV;
import cn.sise.oa.base.BaseAction;
import cn.sise.oa.domain.MeetingRoom;
import cn.sise.oa.domain.Reservation;
import cn.sise.oa.domain.User;
import cn.sise.oa.util.DateUtil;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class MeetingRoomCalendarAction extends BaseAction<CalendarOV> {

	private HttpServletResponse response = ServletActionContext.getResponse();
	private HttpServletRequest request = ServletActionContext.getRequest();	
	
	private String meetRoomId;
	private String defaultMeetRoomId;
	private String state;
	/**
	 * 跳转到会议室预约情况界面
	 * @return
	 */
	public String show(){
		setSessionMeetingRoomId();
		return "show";
	}
	
	/** 
	 * 通过session中的会议室id查询所有申请数据
	 * */
	public void list() throws Exception {
		//查询预约		
		String sessionMeetingRoomId = (String)ActionContext.getContext().getSession().get("meetRoomId");
		List<Reservation> reservations = new ArrayList<Reservation>();

		//预约情况查询  指定id
		MeetingRoom meetingRoom = meetingRoomService.getById(Integer.valueOf(sessionMeetingRoomId));
		if(StringUtils.isNotBlank(state)){//预约处理查询（state=0）
			reservations = reservationService.findAllByMeetRoomObjAndState(meetingRoom);
		}else{//查询所有预约		
			reservations = reservationService.findAllByMeetRoomObj(meetingRoom);			
		}
		
		//转换成页面数据	
		List<CalendarOV> calendarOVs = meetingRoomCalendarService.transformOV(reservations);

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String json = JSONSerializer.toJSON(calendarOVs).toString();
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
		
		System.out.println("--list--"+"sessionMeetingRoomId:"+sessionMeetingRoomId);
	}
	
	/**
	 * 将会议室id放置session中
	 * @return
	 */
	public String toList(){
		if(meetRoomId != null){			
			ActionContext.getContext().getSession().put("meetRoomId", meetRoomId);
		}
		System.out.println("--------tolist---meetRoomId-"+meetRoomId);
		System.out.println("--------tolist---smeetRoomId-"+ActionContext.getContext().getSession().get("meetRoomId"));
		return "toList";
	}
	
	
	/**
	 * 添加一个申请
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	public void add() throws UnsupportedEncodingException, ParseException{
		User user = (User)ActionContext.getContext().getSession().get("user");
		String sessionMeetingRoomId = (String) ActionContext.getContext().getSession().get("meetRoomId");
		MeetingRoom meetingRoom = meetingRoomService.getById(Integer.valueOf(sessionMeetingRoomId));

		String title = request.getParameter("title");
		
		String desc = request.getParameter("desc");
		String startTime = request.getParameter("start");
		String endTime = request.getParameter("end"); 
		//title = new String(title.getBytes("iso-8859-1"),"utf-8");
		//desc = new String(desc.getBytes("iso-8859-1"),"utf-8");
		
		Reservation reservation = new Reservation();
		reservation.setTitle(title);
		reservation.setSummary(desc);
		System.out.println(startTime);
		reservation.setStartTime(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", startTime));
		reservation.setEndTime(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", endTime));
		reservation.setUser(user);
		reservation.setMeetingRoom(meetingRoom);
		
		reservationService.save(reservation);
	}
	
	//===================处理预约============
	/**
	 * 处理界面
	 * @return
	 */
	public String toShowHandel(){
		setSessionMeetingRoomId();
		return "toShowHandel";
	}
	
	/**
	 * 查询需处理的预约(state=0)
	 * @throws IOException 
	 */
	public void handelList() throws IOException{
		//查询预约(state=0)
		String sessionMeetingRoomId = (String) ActionContext.getContext().getSession().get("meetRoomId");
		List<Reservation> reservations = new ArrayList<Reservation>();
		
		if(StringUtils.isNotBlank(sessionMeetingRoomId)){	//是空使用默认id（第一个）		
			String defaultMeetingRoomId = (String) ActionContext.getContext().getSession().get("defaultMeetRoomId");
			MeetingRoom meetingRoom = meetingRoomService.getById(Integer.valueOf(defaultMeetingRoomId));
			reservations = reservationService.findAllByMeetRoomObjAndState(meetingRoom);
		}else{
			MeetingRoom meetingRoom = meetingRoomService.getById(Integer.valueOf(sessionMeetingRoomId));
			reservations = reservationService.findAllByMeetRoomObjAndState(meetingRoom);
			
		}
		//转换成页面数据		
		List<CalendarOV> calendarOVs = meetingRoomCalendarService.transformOV(reservations);
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String json = JSONSerializer.toJSON(calendarOVs).toString();
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
		
		System.out.println("--list--"+"handel:"+sessionMeetingRoomId);
	}
	
	public void handel() throws UnsupportedEncodingException{
		User user = (User)ActionContext.getContext().getSession().get("user");
		String id = request.getParameter("id");
		String desc = request.getParameter("desc");
		String state = request.getParameter("state");
		//desc = new String(desc.getBytes("iso-8859-1"),"utf-8");
		
		Reservation reservation = reservationService.getById(Integer.valueOf(id));
		reservation.setFeedback(desc);
		reservation.setState(Integer.valueOf(state));
		reservation.setHandelUser(user.getName());
		
		reservationService.update(reservation);
	}
	
	//======
	/**
	 * 设置meetroom查询id或默认id
	 * 	如果界面有传id，记住，以后查询子查询此id会议室申请
	 *  如果没有，设置一个默认id
	 */
	private void setSessionMeetingRoomId(){
		if(meetRoomId != null){	//界面传有有id，记住		
			ActionContext.getContext().getSession().put("meetRoomId", meetRoomId);
		}else{//没有，就设置默认				
			List<MeetingRoom> meetingRooms = meetingRoomService.findAll();
			ActionContext.getContext().put("meetingRooms", meetingRooms);
			if(meetingRooms.size()>0){
				String meetRoomId = meetingRooms.get(0).getId().toString();
				//设置默认id
				ActionContext.getContext().getSession().put("meetRoomId", meetRoomId);
				System.out.println("---smeetRoomId--"+meetRoomId);
			}
		}
		
	}
	
	// --------------------------
	public String getMeetRoomId() {
		return meetRoomId;
	}

	public void setMeetRoomId(String meetRoomId) {
		this.meetRoomId = meetRoomId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDefaultMeetRoomId() {
		return defaultMeetRoomId;
	}

	public void setDefaultMeetRoomId(String defaultMeetRoomId) {
		this.defaultMeetRoomId = defaultMeetRoomId;
	}


	

}
