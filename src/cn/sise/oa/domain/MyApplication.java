package cn.sise.oa.domain;

import java.util.Date;

/**
 * 个人申请
 * @author yzh
 *
 */
public class MyApplication {

	private Integer id;
	private String title;
	private Date createTime = new Date();
	private Integer state = 0; //请假单状态 0：初始录入，1：审核中，2：审核完毕
	private String processInstanceId; //流程实例ID
	private String processInstanceKey; //流程实例启动key
	private Integer fileTemplateId;//模板id
	
	private String applicationFileUrl;//文件表单数据
	private String jsonData;//自定义表单数据
	
	
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
	public String getApplicationFileUrl() {
		return applicationFileUrl;
	}
	public void setApplicationFileUrl(String applicationFileUrl) {
		this.applicationFileUrl = applicationFileUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getProcessInstanceKey() {
		return processInstanceKey;
	}
	public void setProcessInstanceKey(String processInstanceKey) {
		this.processInstanceKey = processInstanceKey;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public Integer getFileTemplateId() {
		return fileTemplateId;
	}
	public void setFileTemplateId(Integer fileTemplateId) {
		this.fileTemplateId = fileTemplateId;
	}
	
	
}
