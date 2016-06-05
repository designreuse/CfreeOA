package cn.sise.oa.domain;

import java.util.Date;

/**
 * 预约详情
 * @author yzh
 *
 */
public class Reservation {

	private Integer id;
	private String title;//会议主题
	private String summary; //预约说明
	private String emergencyLevel;//紧急程度
	private Date createTime = new Date(); //预约时间
	private Date startTime; 
	private Date endTime;
	private int showInPage; //是否在页面可查询 0:可以，1：不可以
	
	//处理人填写
	private String feedback;//预约反馈
	private String handelUser; //预约处理人
	private int handle; //是否已处理 0:未处理 ，1：已处理
	private int state; //预约状态 0：预约中(黄色)，1预约成功(绿色)，2：预约失败
	
	private MeetingRoom meetingRoom;
	private User user;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getEmergencyLevel() {
		return emergencyLevel;
	}
	public void setEmergencyLevel(String emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getShowInPage() {
		return showInPage;
	}
	public void setShowInPage(int showInPage) {
		this.showInPage = showInPage;
	}
	public MeetingRoom getMeetingRoom() {
		return meetingRoom;
	}
	public void setMeetingRoom(MeetingRoom meetingRoom) {
		this.meetingRoom = meetingRoom;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getHandle() {
		return handle;
	}
	public void setHandle(int handle) {
		this.handle = handle;
	}
	public String getHandelUser() {
		return handelUser;
	}
	public void setHandelUser(String handelUser) {
		this.handelUser = handelUser;
	}
	
	
}
