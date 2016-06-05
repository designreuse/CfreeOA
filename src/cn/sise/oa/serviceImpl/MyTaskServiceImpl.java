package cn.sise.oa.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sise.oa.action.bean.CommentOV;
import cn.sise.oa.action.bean.MyTask;
import cn.sise.oa.base.DaoSupportImpl;
import cn.sise.oa.service.MyTaskService;

@Service
@Transactional
public class MyTaskServiceImpl extends DaoSupportImpl<MyTask> implements MyTaskService {

	/**
	 *查找当前任务
	 */
	@Override
	public List<Task> findMyTaskByName(String assigneeName) {
		List<Task> tasks = taskService.createTaskQuery()//
						.taskAssignee(assigneeName)//
						.orderByTaskCreateTime().desc()
						.list();
		return tasks;
	}


	/**
	 *查询当前任务的之后的连线名称
	 */
	@Override
	public List<String> findOutcomesByTaskId(String taskId) {
		List<String> outcomes = new ArrayList<String>();
		//* 1：使用任务id查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//
					.singleResult();
		 //* 2：使用任务对象获取流程定义实例id
		String processDefinitionId = task.getProcessDefinitionId();
		 //* 3：使用流程实例id返回流程实例对象
		ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);									
		//获取流程实体id
		String processInstanceId = task.getProcessInstanceId();
		//获取当前活动id
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)//
				.singleResult();
		String activityId = pi.getActivityId();
		
		//4:获取当前活动实例
		ActivityImpl activityImpl = definitionEntity.findActivity(activityId);
		
		//5：获取连线名称
		List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();
		if(pvmTransitions != null && pvmTransitions.size()>0){
			for(PvmTransition pvmTransition : pvmTransitions){
				String name = (String) pvmTransition.getProperty("name");
				/*//测试
				PvmActivity ac = pvmTransition.getDestination();
				System.out.println("下一个节点名称"+ac.getProperty("name"));
				//测试结束
*/				if(StringUtils.isNotBlank(name)){
					if(name.indexOf("于") == -1){						
						outcomes.add(name);
					}					
				}else{
					outcomes.add("默认提交");
				}
			}
		}
		
		if(outcomes.size() == 0){
			outcomes.add("默认提交");
		}
		
		return outcomes;
	}

	/**
	 *使用当前任务id查询历史任务评论数据
	 */
	@Override
	public List<CommentOV> findCommentByTaskId(String taskId) {
		List<CommentOV> commentOVs = new ArrayList<CommentOV>();
		//根据当前任务id获取任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//
				.singleResult();
		//根据当前任务对象获取流程实例id
		String processInstanceId = task.getProcessInstanceId();
		
		//使用流程实例查询
		List<Comment> comments = taskService.getProcessInstanceComments(processInstanceId);
		//将表ACT_HI_COMMENT同一个任务type类型为comment和event数据合并(有附件)
		/*for(Comment commentfirst : comments){		
			if("comment".equals(commentfirst.getType())){
				String currentTaskId = commentfirst.getTaskId();
				CommentOV currentCommentOV = new CommentOV();
				currentCommentOV.setComment(commentfirst);
				//再寻找taskId一样并且type为event是否存在
				for(Comment commentSeconed : comments){
					if("event".equals(commentSeconed.getType()) 
							&& currentTaskId.equals(commentSeconed.getTaskId())){
						currentCommentOV.setHaveAttachment(true);
						break;
					}
				}
				commentOVs.add(currentCommentOV);
			}
		}*/
		//将表ACT_HI_COMMENT(无附件)
		for(Comment commentfirst : comments){					
			CommentOV currentCommentOV = new CommentOV();
			currentCommentOV.setComment(commentfirst);
			commentOVs.add(currentCommentOV);
		}
		return commentOVs;
	}

	/**
	 *通过任务id查找流程定义对象
	 */
	@Override
	public ProcessDefinition findProcessInstanceByTaskId(String taskId) {
		//查找任务对象
		Task task = taskService.createTaskQuery()//
						.taskId(taskId)//
						.singleResult();
		//获取流程定义id
		String processDefinitionId = task.getProcessDefinitionId();
		//获取流程定义对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//
							.processDefinitionId(processDefinitionId)
							.singleResult();
		return pd;
	}

	/**
	 *通过任务id查找当前活动坐标信息
	 */
	@Override
	public Map<String, Object> findCoordinateByTaskId(String taskId) {
		//存放当前活动坐标信息
		Map<String, Object> acs = new HashMap<String, Object>();
		//查询任务对象
		Task task = taskService.createTaskQuery()//
						.taskId(taskId)//
						.singleResult();
		//获取流程定义id
		String processDefinitionId = task.getProcessDefinitionId();
		//获取流程定义实体
		ProcessDefinitionEntity pde = (ProcessDefinitionEntity) repositoryService
										.getProcessDefinition(processDefinitionId);
		//流程实例id
		String processInstanceId = task.getProcessInstanceId();
		//获取流程实例
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
								.processInstanceId(processInstanceId)//
								.singleResult();
		//获取当前活动id
		String activityId = pi.getActivityId();
		//获取当前或实例
		ActivityImpl activityImpl = pde.findActivity(activityId);
		//获取坐标信息
		acs.put("x", activityImpl.getX());
		acs.put("y", activityImpl.getY());
		acs.put("width", activityImpl.getWidth());
		acs.put("height", activityImpl.getHeight());
		return acs;
	}

	/**
	 *通过任务id找到任务节点的formkey
	 */
	@Override
	public String findFormKeyByTaskId(String taskId) {
		TaskFormData formData = formService.getTaskFormData(taskId);
		String url = formData.getFormKey();
		return url;
	}

	/**
	 * 查询下一个节点是否为用户节点
	 */
	@Override
	public boolean findNextUserTaskByTaskId(String taskId) {
		boolean canFind = true;
		//* 1：使用任务id查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//
					.singleResult();
		 //* 2：使用任务对象获取流程定义实例id
		String processDefinitionId = task.getProcessDefinitionId();
		 //* 3：使用流程实例id返回流程实例对象
		ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);									
		//获取流程实体id
		String processInstanceId = task.getProcessInstanceId();
		//获取当前活动id
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)//
				.singleResult();
		String activityId = pi.getActivityId();
		
		//4:获取当前活动实例
		ActivityImpl presentActivityImpl = definitionEntity.findActivity(activityId);
		
		//获取下一个活动实例
		List<PvmTransition> pvmTransitions = presentActivityImpl.getOutgoingTransitions();
		if(pvmTransitions != null && pvmTransitions.size()>0){
			for(PvmTransition pvmTransition : pvmTransitions){
				PvmActivity pvmActivity = pvmTransition.getDestination();
				if("userTask".equals(pvmActivity.getProperty("type"))){
					canFind = true;
					break;
				}
			}
		}
		return canFind;
	}


}
