package cn.sise.oa.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;



import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.sise.oa.action.bean.MyTask;
import cn.sise.oa.base.BaseAction;
import cn.sise.oa.domain.Information;
import cn.sise.oa.domain.MyApplication;
import cn.sise.oa.domain.User;
import cn.sise.oa.util.ManagerTaskHander;


@Controller
@Scope("prototype")
public class MyTaskAction extends BaseAction<MyTask>{
	
	private Integer id;  //请假单id
	private String imageName;
	private String deploymentId;
	
	private File taskAttachment; 
	private String taskAttachmentFileName;
	private String taskAttachmentContentType;
	
	/**
	 * 通知方式
	 * 	1：OA系统消息通知
	 * 	2:邮件通知
	 */
	private int[] notifieds;
	
	/**
	 * 查询当前登录人任务详细信息
	 * @return
	 */
	public String list(){
		User user = (User) ActionContext.getContext().getSession().get("user");
		String assigneeName = user.getLoginName();//使用登录名
		//查找当前登录人任务
		List<Task> tasks = myTaskService.findMyTaskByName(assigneeName);
		ActionContext.getContext().put("tasks", tasks);
		return "list";
	}
	
	/**
	 * 查询当前登录人任务个数
	 * @throws IOException 
	 */
	public void countTask() throws IOException{
		User user = (User) ActionContext.getContext().getSession().get("user");
		String assigneeName = user.getLoginName();//使用登录名
		//查找当前登录人任务
		List<Task> tasks = myTaskService.findMyTaskByName(assigneeName);
		
		String sum = null;
		if(tasks != null){		
			sum = Integer.toString(tasks.size());
		}else{
			sum = "0";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(sum);
		printWriter.flush();
		printWriter.close();
		
	}
	
	/**
	 * 打开任务表单
	 * 		
	 * @return
	 */
	public String viewTaskFrom(){
		//获取任务id
		String taskId = model.getTaskId();
		String action = "myApplication_approveUI.action"; //跳转action名字
		String deparName = "无";//根据部门查询下一个审批人
		
		//获取任务表单任务节点的key值（部门）
		String formKey = myTaskService.findFormKeyByTaskId(taskId);
		//如果流程中form key 未设值则使用默认值(即直接已指定处理人)
		if(formKey != null){				
			deparName = formKey;
		}
		if(formKey != null && formKey.contains("*")){
			deparName = formKey.substring(formKey.indexOf('*')+1);
		}

		ActionContext.getContext().put("action", action);
		ActionContext.getContext().put("taskId", taskId);
		ActionContext.getContext().put("deparName", deparName);
		
		return "viewTaskFrom";
	}	
	
	/**
	 * 提交任务
	 * 1：完成任务之前要保存审批意见（保存在act_hi_comment表）
	 * 2:如果连线名称是"默认提交"，不需要设置流程变量，如果不是，需在完成任务之前设置流程变量，让流程按指定方向走
	 * 		变量名称：outcome
	 * 		变量值：连线名称
	 * 3：完成任务后，指定下一个任务执行者
	 * 4:如果有附件，将附件保存进activiti的ACT_HI_ATTACHMENT表
	 * 5：使用任务id，完成当前任务 
	 * 6：完成任务后判断流程是否结束，如果结束，更新流程状态（使用监听器自动完成）
	 * @return
	 * @throws IOException 
	 */
	public String sumbit() throws IOException{
		
		//获取任务id
		String taskId = model.getTaskId();
		//获取连线名称
		String outcome = model.getOutcome();
		//获取审批意见
		String comment = model.getComment();
		//下一个任务执行者
		String nextUserName = model.getNextUserName();
		//* 1：完成任务之前要保存当前用户审批意见（保存在act_hi_comment表）
		Task task = taskService.createTaskQuery()//
						.taskId(taskId)//
						.singleResult();
		String processInstanceId = task.getProcessInstanceId();
		//注意：因为activiti底层设置用户是使用自己的表（act_id_user）来设置USER_ID字段的，所有我们需要重新设置
		User user = (User) ActionContext.getContext().getSession().get("user");
		//使用登录名
		Authentication.setAuthenticatedUserId(user.getLoginName());
		taskService.addComment(taskId, processInstanceId, comment);
		
		// * 2:如果连线名称是"默认提交"，不需要设置流程变量，如果不是，需在完成任务之前设置流程变量，让流程按指定方向走
		Map<String, Object> variables = new HashMap<String, Object>();
		System.out.println(outcome.indexOf("于")==-1);
		if(outcome != null && !"默认提交".equals(outcome) && outcome.indexOf("于")==-1)
			variables.put("outcome", outcome);		
		
		// * 3：在完成任务前，使用静态成员变量指定下一个任务执行者
		if(StringUtils.isNotBlank(nextUserName)){
			
			ManagerTaskHander.nextUserName = nextUserName;
		}
		/*if(outcome.contains("结束")){//如果是结束流程，则不设
			ManagerTaskHander.nextUserName = null;		
		}else{
			if(StringUtils.isNotBlank(nextUserName)){ //如果下一个用户处理人不为空则设置用户
				ManagerTaskHander.nextUserName = nextUserName;
			}else { //如果未设置用户，则交给系统管理员
				ManagerTaskHander.nextUserName = "超级管理员";			
			}
		}*/
		
		//* 4:如果有附件，将附件url保存进activiti的ACT_HI_ATTACHMENT表
		if(taskAttachment != null){		
			//将文件保存进系统file/taskFile目录下
			HttpServletRequest request=ServletActionContext.getRequest();
			//得到当前项目在服务器中的位置,把所有\替换成/
			String realPath=request.getRealPath("").replaceAll("\\\\", "/") + "/file/taskFile";		
			File saveFile = new File(new File(realPath), taskAttachmentFileName);
			if(!saveFile.getParentFile().exists())
				saveFile.mkdirs();
			FileUtils.copyFile(taskAttachment, saveFile);	
			//将附件信息保存进activiti的ACT_HI_ATTACHMENT表
			taskService.createAttachment(taskAttachmentContentType, 
					taskId, processInstanceId, taskAttachmentFileName, 
					model.getAttachmentDescription(), realPath + "\\" + taskAttachmentFileName);
		}		
		// * 5：使用任务id，完成当前任务
		taskService.complete(taskId, variables);			
		// * 6_1：完成任务后判断流程是否结束，如果结束，更新流程状态，（结束节点使用监听器） 		
		// * 6_2：完成任务后判断流程是否结束，如果结束，更新流程状态（查询act_ru_execution表是否存在流程实例）
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
								.processInstanceId(processInstanceId)
								.singleResult();
		if(pi == null){//不存在流程则结束，修改申请状态
			HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
										.processInstanceId(processInstanceId)
										.singleResult();
			String businessKey = hpi.getBusinessKey();
			String myApplicationID = businessKey.substring(businessKey.indexOf('.')+1);
			System.out.println(myApplicationID);
			MyApplication application = myApplicationService.getById(Integer.valueOf(myApplicationID));
			application.setState(2);
			myApplicationService.update(application);
		}
		//消息通知		
		if(notifieds != null){
			for(int i=0;i<notifieds.length;i++){
				if(notifieds[i] == 1){
					Information information = new Information();
					information.setTitle(user.getName() +"为您分配一个审批任务!请及时处理");
					information.setContent(user.getName() +"为您分配一个审批任务!请及时处理");
					//设置接收人（单个）
					//TODO 有待改进接受人设置 
					//按登录名查找用户
					User receiver = userService.getByLoginName(nextUserName);
					Integer[] userIds = new Integer[]{receiver.getId()};
					List<User> userList = userService.getByIds(userIds);
					information.setSendee(new HashSet<User>(userList));
					//发送消息
					informationService.save(information);
				}
				if(notifieds[i] == 2){
					//TODO 未完成
					System.out.println("邮件发送");
				}
			}
		}
		
		
		return "toList";
	}

	/**
	 * 准备显示图片的数据
	 * 		deploymentId
	 * 		imageName
	 * @return
	 */
	public String showPresentDiagramUI(){
		String taskId = model.getTaskId();
		//通过任务id查询流程定义对象
		ProcessDefinition pd = myTaskService.findProcessInstanceByTaskId(taskId);
		
		ActionContext.getContext().put("deploymentId", pd.getDeploymentId());
		ActionContext.getContext().put("imageName", pd.getDiagramResourceName());
		//根据任务对象获取获取当前节点的坐标
		Map<String, Object> acs = myTaskService.findCoordinateByTaskId(taskId);
		ActionContext.getContext().put("acs", acs);
		return "showPresentDiagramUI";
	}
	
	/**
	 * 显示图片
	 * @return
	 * @throws Exception
	 */
	public String viewImage() throws Exception{		
		//获取资源文件表act_ge_bytearray中图片的输入流
		imageName = new String(imageName.getBytes("iso-8859-1"),"utf-8");
		InputStream in = processDefinitionService.findImageInputStream(deploymentId,imageName);
		//从response获取输出流
		OutputStream out = ServletActionContext.getResponse().getOutputStream();
		//将输入流中的数据读出来
		for(int b=-1; (b=in.read())!=-1;)
			out.write(b);
		out.close();
		in.close();
		return null;
	}
	
	//=======================
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public File getTaskAttachment() {
		return taskAttachment;
	}

	public void setTaskAttachment(File taskAttachment) {
		this.taskAttachment = taskAttachment;
	}

	public String getTaskAttachmentFileName() {
		return taskAttachmentFileName;
	}

	public void setTaskAttachmentFileName(String taskAttachmentFileName) {
		this.taskAttachmentFileName = taskAttachmentFileName;
	}

	public String getTaskAttachmentContentType() {
		return taskAttachmentContentType;
	}

	public void setTaskAttachmentContentType(String taskAttachmentContentType) {
		this.taskAttachmentContentType = taskAttachmentContentType;
	}

	public int[] getNotifieds() {
		return notifieds;
	}

	public void setNotifieds(int[] notifieds) {
		this.notifieds = notifieds;
	}





	
}
