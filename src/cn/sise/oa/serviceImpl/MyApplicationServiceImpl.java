package cn.sise.oa.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import cn.sise.oa.action.bean.CommentOV;
import cn.sise.oa.base.DaoSupportImpl;
import cn.sise.oa.dao.MyApplicationDao;
import cn.sise.oa.domain.MyApplication;
import cn.sise.oa.domain.User;
import cn.sise.oa.service.MyApplicationService;

@Service
@Transactional
public class MyApplicationServiceImpl extends DaoSupportImpl<MyApplication> implements MyApplicationService{

	@Resource 
	private MyApplicationDao applicationDao;
	
	/**
	 *1:根据请假单ID查询请假单对象
	 *2：更新请假单的状态（0：初始录入-->1：审核中）
	 *3：根据流程key启动流程
	 *4：设置当前任务便办理人（session中取当前任务办理人，放置流程变量）
	 *5：将业务数据与流程实例关联（BUSINESS_KEY字段存储标识）
	 * @throws IOException 
	 */
	@Override
	public void stratProcess(Integer applicationId, String startProcessKey, File applicationFile, String fileSavePath, String applicationFileFileName) {
				
		//1:根据ID查询对象
		MyApplication application = getById(applicationId);
		//2：更新请假单的状态（0：初始录入-->1：审核中）
		application.setState(1);
		//将文件保存进系统
		String fileName = startProcessKey + applicationId +applicationFileFileName;
		File saveFile = new File(fileSavePath + "/" + fileName);
		if(!saveFile.exists() && !saveFile.isDirectory()){//不存在，则保存
			try {
				FileUtils.copyFile(applicationFile, saveFile);
			} catch (IOException e) {			
				e.printStackTrace();
			}
		}
		application.setApplicationFileUrl(fileSavePath + "/" + fileName);
		
		//设置当前任务便办理人（session中取当前任务办理人，放置流程变量）,将业务数据与流程实例关联（BUSINESS_KEY字段存储标识）
		Map<String, Object> variables = new HashMap<String, Object>();
		User user = (User) ActionContext.getContext().getSession().get("user");
		variables.put("inputUser", user.getLoginName()); //名称要唯一，使用登录名
		//BUSINESS_KEY字段存储标识(格式：类名.id)
		String objId = startProcessKey+"."+applicationId;
		//4:启动流程，同时设置BUSINESS_KEY字段
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(startProcessKey, objId, variables);
		//将流程实例与请假单关联
		application.setProcessInstanceId(instance.getId());
		update(application);
		
	}

	/**
	 *取消流程
	 */
	@Override
	public void cancelApplicationByProcessInstanceId(String processInstanceId) {
		runtimeService.deleteProcessInstance(processInstanceId, "");
		
	}

	/**
	 *查询业务数据
	 */
	@Override
	public MyApplication findApplicationByTaskId(String taskId) {
		// * 1：使用任务id查询任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//
				.singleResult();
		// * 2：使用任务对象获取流程实例id
		String processInstanceId = task.getProcessInstanceId();
		// * 3：使用流程实例id查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)//
				.singleResult();
		// * 4：使用流程实例对象获取BUSINESS_KEY
		String businessKey = pi.getBusinessKey();
		// * 5：从BUSINESS_KEY获取业务数据主键值，查询业务对象
		String appliactionId = "";
		if (StringUtils.isNotBlank(businessKey)) {
			// 截取字符串第二个字符
			appliactionId = businessKey.split("\\.")[1];
		}
		MyApplication application = applicationDao.getById(Integer.valueOf(appliactionId));
		return application;
	}

	/**
	 *获得业务数据id
	 */
	@Override
	public String findMyApplicationIdProcessInstanceId(String processInstanceId) {
		 //* 1：使用流程实例id查询正在执行的执行对象表，返回流程实例对象
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()//
								.processInstanceId(processInstanceId)
								.singleResult();
		 //* 2：使用流程实例对象获取BUSINESS_KEY
		String businessKey = hpi.getBusinessKey();
		 //* 3：从BUSINESS_KEY获取业务数据主键值，查询业务对象
		String myApplicationId = "";
		if(StringUtils.isNotBlank(businessKey)){
			//截取字符串第二个字符
			myApplicationId = businessKey.split("\\.")[1];
		}
		return myApplicationId;
	}

	/**
	 *根据业务数据主键查询历史评论
	 */
	@Override
	public List<CommentOV> findCommentByApplicationId(Integer applicationId) {
		List<CommentOV> commentOVs = new ArrayList<CommentOV>();
		
		//通过id得请假单对象
		MyApplication application = getById(applicationId);
		//通过组合得到BUSINESS_KEY值
		String businessKey = application.getProcessInstanceKey()+"."+applicationId;
		//通过BUSINESS_KEY找到流程实例对象
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()//
							.processInstanceBusinessKey(businessKey)//
							.singleResult();
		//获取流程实例id
		String processInstanceId = hpi.getId();
		//使用流程实例id查询评论
		List<Comment> comments = taskService.getProcessInstanceComments(processInstanceId);
		//包装成界面对象（有附件）
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
		//包装成界面对象（有附件）
		for(Comment commentfirst : comments){									
			CommentOV currentCommentOV = new CommentOV();
			currentCommentOV.setComment(commentfirst);		
			commentOVs.add(currentCommentOV);
		}
		return commentOVs;
	}

	/**
	 * 通过任务id查询附件文件名称 文件名.任务id（任务id作为在线预览的文件名称） 只需任务id，因为在线预览文件名不能含有中文
	 */
	@Override
	public Attachment findAttachmentByTaskId(String taskId) {
		// 获取任务的所有附件
		List<Attachment> attachments = taskService.getTaskAttachments(taskId);
		Attachment attachment = null;
		if (attachments.get(0) != null) {// 只获取第一个
			attachment = attachments.get(0);
		}
		return attachment;
	}

	@Override
	public void startProcess(Integer applicationId, String startProcessKey,
			Map parameterMap) {
		
		//1:根据ID查询对象
		MyApplication application = getById(applicationId);
		//2：更新请假单的状态（0：初始录入-->1：审核中）
		application.setState(1);		
		
		//设置当前任务便办理人（session中取当前任务办理人，放置流程变量）,将业务数据与流程实例关联（BUSINESS_KEY字段存储标识）
		//Map<String, Object> variables = new HashMap<String, Object>();
		User user = (User) ActionContext.getContext().getSession().get("user");
		parameterMap.put("inputUser", user.getLoginName()); //名称要唯一，使用登录名
		//BUSINESS_KEY字段存储标识(格式：类名.id)
		String objId = startProcessKey+"."+applicationId;
		//4:启动流程，同时设置BUSINESS_KEY字段
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(startProcessKey, objId, parameterMap);
		//将流程实例与请假单关联
		application.setProcessInstanceId(instance.getId());
		update(application);
	}

	
	

}
