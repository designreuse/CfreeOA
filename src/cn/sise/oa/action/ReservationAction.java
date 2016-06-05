package cn.sise.oa.action;


import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sise.oa.base.BaseAction;
import cn.sise.oa.domain.MeetingRoom;
import cn.sise.oa.domain.Reservation;
import cn.sise.oa.domain.User;
import cn.sise.oa.util.QueryHelper;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ReservationAction extends BaseAction<Reservation> {

	private String reservationMeetingRoomId;//会议室的id
	
	/** 列表-个人 */
	public String list() throws Exception {		
		User user = (User) ActionContext.getContext().getSession().get("user");
		new QueryHelper(Reservation.class,"r")//
		.addCondition("r.user = ?", user)//只查询当前用户的预约申请
		.addCondition("r.showInPage = ?", 0) //只查询用户为删除的预约申请
		.preparePageBean(reservationService, pageNum, 15);
		
		return "list";
	}

	/** 列表-会议室管理员 */
	public String listAll() throws Exception {		
		new QueryHelper(Reservation.class,"r")//
		.addCondition("r.state = ?", 0)//只查找预约中的预约
		.preparePageBean(reservationService, pageNum, 15);
		
		return "listAll";
	}

	
	/** 删除 */
	public String delete() throws Exception {
		reservationService.delete(model.getId());
		
		return "toList";
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		//准备会议室
		List<MeetingRoom> meetingRooms = meetingRoomService.findAll();
		ActionContext.getContext().put("meetingRooms", meetingRooms);
		return "saveUI";
	}

	/** 添加 */
	public String add() throws Exception {	
		Reservation reservation = new Reservation();
		reservation.setTitle(model.getTitle());
		reservation.setEmergencyLevel(model.getEmergencyLevel());
		reservation.setStartTime(model.getStartTime());
		reservation.setEndTime(model.getEndTime());
		
		//》》设置用户
		User user = (User) ActionContext.getContext().getSession().get("user");
		model.setUser(user);
		//》》设置申请的会议室
		if(!"on".equals(reservationMeetingRoomId)){
			MeetingRoom meetingRoom = meetingRoomService.getById(Integer.valueOf(reservationMeetingRoomId));
			model.setMeetingRoom(meetingRoom);
		}
		
		reservationService.save(model);

		return "toList";
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		// 准备回显的数据
		Reservation reservation = reservationService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(reservation);
        reservationMeetingRoomId = reservation.getMeetingRoom().getId().toString();
        ActionContext.getContext().getValueStack().push(reservationMeetingRoomId);;
        
		//准备会议室
		List<MeetingRoom> meetingRooms = meetingRoomService.findAll();
		ActionContext.getContext().put("meetingRooms", meetingRooms);
		
		return "saveUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		//》》设置申请的会议室		
		if(!"on".equals(reservationMeetingRoomId)){
			MeetingRoom meetingRoom = meetingRoomService.getById(Integer.valueOf(reservationMeetingRoomId));
			model.setMeetingRoom(meetingRoom);
		}
		reservationService.update(model);

		return "toList";
	}
	
	/**
	 * 设置用户界面不可查询
	 * @return
	 */
	public String hide(){
		Reservation reservation = reservationService.getById(model.getId());
		reservation.setShowInPage(1);
		reservationService.update(reservation);
		return "toList";
	}
	
	/**
	 * 处理预约界面
	 * @return
	 */
	public String handelAppointUI(){
		//准备数据
		Reservation reservation = reservationService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(reservation);
		return "handelAppointUI";
	}
	
	/**
	 * 处理预约
	 * @return
	 */
	public String handelAppoint(){
		Reservation reservation = reservationService.getById(model.getId());
		
		User user = (User) ActionContext.getContext().getSession().get("user");
		//设置预约处理人
		reservation.setHandelUser(user.getName());
		//设置为已处理
		reservation.setHandle(1);
		//设置反馈
		reservation.setFeedback(model.getFeedback());
		//设置申请状态
		reservation.setState(model.getState());
		
		reservationService.update(reservation);
		return "toListAll";
	}

	/**
	 * 预约详情
	 * @return
	 */
	public String detailsUI(){
		Reservation reservation = reservationService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(reservation);
		return "detailsUI";
	}
	
	// ---
	public String getReservationMeetingRoomId() {
		return reservationMeetingRoomId;
	}

	public void setReservationMeetingRoomId(String reservationMeetingRoomId) {
		this.reservationMeetingRoomId = reservationMeetingRoomId;
	}

	
	

}
