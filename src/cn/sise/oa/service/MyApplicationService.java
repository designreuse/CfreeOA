package cn.sise.oa.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Attachment;

import cn.sise.oa.action.bean.CommentOV;
import cn.sise.oa.base.DaoSupport;
import cn.sise.oa.domain.MyApplication;

public interface MyApplicationService extends DaoSupport<MyApplication>{

	/**
	 * 开启流程
	 * @param applicationId 申请对象id
	 * @param startProcessKey 流程key
	 * @param applicationFileFileName  上传的文件
	 * @param fileSavePath  文件保存的文件夹路径
	 * @param applicationFile  上传文件的文件名
	 */
	void stratProcess(Integer applicationId, String startProcessKey, File applicationFile, String fileSavePath, String applicationFileFileName);

	/**
	 * 根据流程实例取消流程
	 * @param processInstanceId
	 */
	void cancelApplicationByProcessInstanceId(String processInstanceId);

	/**
	 * 查询Application对象
	 * @param taskId
	 * @return
	 */
	MyApplication findApplicationByTaskId(String taskId);

	/**
	 * 通过流程实例id查询BUSINESS_KEY(类名.id)中业务数据id
	 * @param processInstanceId
	 * @return
	 */
	String findMyApplicationIdProcessInstanceId(String processInstanceId);

	/**
	 * 通过业务数据id查询评论信息
	 * @param applicationId
	 * @return
	 */
	List<CommentOV> findCommentByApplicationId(Integer applicationId);

	/**
	 * 通过任务id查询评论数据
	 * @param taskId
	 * @return
	 */
	Attachment findAttachmentByTaskId(String taskId);

	/**
	 * 自定义表单流程启动
	 * @param applicationId 申请对象id
	 * @param startProcessKey 启动流程key
	 * @param parameterMap 流程参数（整个表单）
	 */
	void startProcess(Integer applicationId, String startProcessKey,
			Map parameterMap);

	

}
