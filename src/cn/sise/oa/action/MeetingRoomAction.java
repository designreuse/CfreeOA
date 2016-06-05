package cn.sise.oa.action;


import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sise.oa.base.BaseAction;
import cn.sise.oa.domain.MeetingRoom;
import cn.sise.oa.domain.MyApplication;
import cn.sise.oa.domain.Reservation;
import cn.sise.oa.util.DateUtil;
import cn.sise.oa.util.QueryHelper;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class MeetingRoomAction extends BaseAction<MeetingRoom> {

	private String meetingRoomId;
	
	/**
	 * 申请使用结束时间查询，默认没有
	 */
	private String endUseTime = "大于预约使用结束时间查询";
	
	/**
	 * 按申请状态过滤
	 * 0：预约中
	 * 1:预约成功
	 * 2：预约失败
	 */
	private int applicationState = 1;
	
	/**
	 * 排序条件
	 * 0：不排序
	 * 1：提交申请时间
	 * 2：使用开始时间
	 * 3：使用结束时间
	 */
	private int orderBy = 0;
	
	/**
	 * true 表示升序<br>
	 * false 表示降序
	 */
	private boolean asc = false;
	
	/** 列表 */
	public String list() throws Exception {		
		new QueryHelper(MeetingRoom.class,"m")//
		.preparePageBean(meetingRoomService, pageNum, 15);
		
		return "list";
	}

	/** 删除 */
	public String delete() throws Exception {
		meetingRoomService.delete(model.getId());
		
		return "toList";
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		return "saveUI";
	}

	/** 添加 */
	public String add() throws Exception {		
		meetingRoomService.save(model);
		
		return "toList";
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		// 准备回显的数据
		MeetingRoom meetingRoom = meetingRoomService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(meetingRoom);

		return "saveUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		meetingRoomService.update(model);

		return "toList";
	}
	
	/**
	 * 会议室预约详情
	 * @return
	 * @throws ParseException 
	 */
	public String reservationDetails() throws ParseException{
		
		MeetingRoom meetingRoom = meetingRoomService.getById(Integer.valueOf(meetingRoomId));
		
		Timestamp endtime = null;
		if(!"大于预约使用结束时间查询".equals(endUseTime) && !"".equals(endUseTime)){
			endtime = Timestamp.valueOf(endUseTime);
		}
		//分页查询
		new QueryHelper(Reservation.class,"r")//
		// 过滤条件
		.addCondition("r.meetingRoom = ?", meetingRoom)//当前会议室	
		.addCondition(!"大于预约使用结束时间查询".equals(endUseTime) && !"".equals(endUseTime), "r.endTime > ?", endtime)//
		.addCondition(applicationState != 1, "r.state = ?", applicationState)//
		//排序条件
		.addOrderProperty(orderBy == 1, "r.createTime", asc)//
		.addOrderProperty(orderBy == 2, "r.startTime", asc)//
		.addOrderProperty(orderBy == 3, "r.endTime", asc)//
		//默认按使用结束时间降序
		.addOrderProperty((orderBy == 0),
				"(CASE r.endTime WHEN 2 THEN 2 ELSE 0 END)", false)//
		.addOrderProperty((orderBy == 0), "r.endTime", false) // 
		
		.preparePageBean(meetingRoomService, pageNum, 15);
		
		ActionContext.getContext().getValueStack().push(meetingRoomId);
		
		return "reservationDetails";
	}

	// --------------------------
	public String getMeetingRoomId() {
		return meetingRoomId;
	}

	public void setMeetingRoomId(String meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}

	public String getEndUseTime() {
		return endUseTime;
	}

	public void setEndUseTime(String endUseTime) {
		this.endUseTime = endUseTime;
	}

	public int getApplicationState() {
		return applicationState;
	}

	public void setApplicationState(int applicationState) {
		this.applicationState = applicationState;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	
	
	

}
