package cn.sise.oa.action.bean;

import org.activiti.engine.task.Comment;


/**
 * 历史评论
 *
 */
public class CommentOV {

	private Comment comment;
	private boolean haveAttachment = false; //是否有附件
	
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public boolean isHaveAttachment() {
		return haveAttachment;
	}
	public void setHaveAttachment(boolean haveAttachment) {
		this.haveAttachment = haveAttachment;
	}
	
	
}
