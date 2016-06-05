package cn.sise.oa.action.bean;

import java.io.File;
import java.util.Date;

public class MyTask {

	private String taskId;
	private String name;
	private String assignee;
	private Date createTimel;
	
	private String nextUserName; //下一个任务执行者
	private String outcome; //完成任务后的走向
	private String comment; //审批意见
	private File attachment; //附件
	private String attachmentDescription; //
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public Date getCreateTimel() {
		return createTimel;
	}
	public void setCreateTimel(Date createTimel) {
		this.createTimel = createTimel;
	}
	public String getNextUserName() {
		return nextUserName;
	}
	public void setNextUserName(String nextUserName) {
		this.nextUserName = nextUserName;
	}
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public File getAttachment() {
		return attachment;
	}
	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}
	public String getAttachmentDescription() {
		return attachmentDescription;
	}
	public void setAttachmentDescription(String attachmentDescription) {
		this.attachmentDescription = attachmentDescription;
	}
	
	
	
}
