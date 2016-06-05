package cn.sise.oa.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 消息详细信息
 * @author yzh
 *
 */
public class Information {
	
	private Integer id;
	private String title;	
	private String content;	
	private Date sendTime = new Date();	
	private Date endTime;	
	private int status;	//0：未读，1：已读
	private Integer senderId; //发送者id
	
	private Set<User> sendee = new HashSet<User>();

	
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Set<User> getSendee() {
		return sendee;
	}

	public void setSendee(Set<User> sendee) {
		this.sendee = sendee;
	}

	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
