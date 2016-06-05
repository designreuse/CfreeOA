package cn.sise.oa.action.bean;

public class CalendarOV {

	private Integer id;// 事件id
	private String title;// 事件标题
	private String start;// 事件开始时间
	private String end;// 结束时间
	private Integer allDay;// 是否为全天事件
	private String color;// 事件的背景色
	private String principal;//负责人
	private String desc;//描述
	private String _;//缓存
	private String feedback;//反馈
	
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
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public Integer getAllDay() {
		return allDay;
	}
	public void setAllDay(Integer allDay) {
		this.allDay = allDay;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String get_() {
		return _;
	}
	public void set_(String _) {
		this._ = _;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	
	
}
