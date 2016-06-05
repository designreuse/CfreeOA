package cn.sise.oa.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import cn.sise.oa.action.bean.CommentOV;
import cn.sise.oa.action.bean.MyTask;
import cn.sise.oa.base.DaoSupport;

public interface MyTaskService extends DaoSupport<MyTask> {

	/**
	 * 根据当前登录人name查找当前任务
	 * @param assigneeName
	 * @return
	 */
	List<Task> findMyTaskByName(String assigneeName);

	/**
	 * 根据任务id查询当前任务的之后的连线名称
	 * @param taskId
	 * @return
	 */
	List<String> findOutcomesByTaskId(String taskId);

	/**
	 * 使用当前任务id查询历史任务评论数据
	 * @param taskId
	 * @return
	 */
	List<CommentOV> findCommentByTaskId(String taskId);

	/**
	 * 通过任务id查找流程定义对象
	 * @param taskId
	 * @return
	 */
	ProcessDefinition findProcessInstanceByTaskId(String taskId);

	/**
	 * 通过任务id查找当前活动坐标信息
	 * @param taskId
	 * @return
	 */
	Map<String, Object> findCoordinateByTaskId(String taskId);

	/**
	 * 通过任务id找到任务节点的formkey
	 * @param taskId
	 * @return
	 */
	String findFormKeyByTaskId(String taskId);

	/**
	 * 查询下一个节点是否为用户节点
	 * @param taskId
	 * @return
	 */
	boolean findNextUserTaskByTaskId(String taskId);


}
