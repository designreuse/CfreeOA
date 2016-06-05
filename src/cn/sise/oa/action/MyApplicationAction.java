package cn.sise.oa.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Attachment;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.sise.oa.action.bean.CommentOV;
import cn.sise.oa.base.BaseAction;
import cn.sise.oa.domain.FileTemplate;
import cn.sise.oa.domain.Information;
import cn.sise.oa.domain.MyApplication;
import cn.sise.oa.domain.User;
import cn.sise.oa.util.DateUtil;
import cn.sise.oa.util.JsonUtil;
import cn.sise.oa.util.OfficeConvert;
import cn.sise.oa.util.PageDate;
import cn.sise.oa.util.QueryHelper;

@Controller
@Scope("prototype")
public class MyApplicationAction extends BaseAction<MyApplication> implements ExecutionListener{

	private String taskId; //任务id
	private String deparName; //部门名称
	
	private String startProcessKey;
	private String templateFileUrl;
	private String templateName;//申请模板名称
	private String type;//申请模板类型
	private String templateId;//申请模板id
	
	private File applicationFile;
	private String applicationFileFileName;
	
	//获取请求响应对象
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	//得到当前项目在服务器中的位置,把所有\替换成/
	private String fileSavePath = request.getRealPath("").replaceAll("\\\\", "/") + "/file/applicationFile";

	/**
	 * 按输入关键字对标题模糊查询 ，默认没有
	 */
	private String likeSearch = "按关键字查询";
	
	/**
	 * 按申请状态过滤
	 * 0：不按此条件过滤
	 * 1:表示审批中
	 * 2：表示审核结束
	 */
	private int applicationState = 0;
	
	/**
	 * 排序条件
	 * 0：不排序
	 * 1：按申请时间
	 */
	private int orderBy = 0;
	
	/**
	 * true 表示升序<br>
	 * false 表示降序
	 */
	private boolean asc = false;
	
	
	/**
	 * 查询所有申请模板(用户可用：used=1)
	 * @return
	 */
	public String templateList(){
		
		new QueryHelper(FileTemplate.class,"f")//
		// 过滤条件
		.addCondition(true,"f.used=?", 1)//
		.preparePageBean(myApplicationService, pageNum, 15);
		
		/*List<FileTemplate> fileTemplates = fileTemplateService.findAll();
		ActionContext.getContext().put("fileTemplates", fileTemplates);*/
		return "templateList";
	}

	/**
	 * 当前用户申请记录
	 * @return
	 */
	public String list(){
		//准备数据
		User user = (User)ActionContext.getContext().getSession().get("user");
		//分页查询
		new QueryHelper(MyApplication.class,"m")//
		// 过滤条件
		.addCondition(true,"m.user=?", user)//当前登录人
		.addCondition(!("按关键字查询".equals(likeSearch) || 
				"".equals(likeSearch)),"m.title LIKE ?", "%"+likeSearch+"%")//关键字
		.addCondition(applicationState != 0, "m.state = ?", applicationState)//
		//排序条件
		.addOrderProperty(orderBy == 1, "m.createTime", asc)//
		//默认按提交申请时间降序
		.addOrderProperty((orderBy == 0),
				"(CASE m.createTime WHEN 2 THEN 2 ELSE 0 END)", false)//
		.addOrderProperty((orderBy == 0), "m.createTime", false) // 
		.preparePageBean(myApplicationService, pageNum, 15);
		
		/*List<MyApplication> applications = myApplicationService.findAll();
		ActionContext.getContext().put("applications", applications);*/
		return "list";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		
		MyApplication application = myApplicationService.getById(model.getId());		
		//删除在线转换的文件swf(命名：流程key+myApplicationId)
		String fileName = application.getProcessInstanceKey() + application.getId();
		OfficeConvert.deleteSWF(fileName);
		//删除申请的文件
		if(application != null && application.getApplicationFileUrl() != null){
			File file = new File(application.getApplicationFileUrl());
			file.delete();
		}
		myApplicationService.delete(model.getId());
		return "toList";
	}
	
	/**
	 * 下载模板文件
	 * @throws UnsupportedEncodingException 
	 */
	public void downTemplateFile() throws UnsupportedEncodingException{
		templateFileUrl = new String(templateFileUrl.getBytes("iso-8859-1"),"utf-8");
		resourceManagementService.downFileByPath(templateFileUrl,response);
	}
	
	/**
	 * 申请界面
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String startUI() throws UnsupportedEncodingException{
		FileTemplate fileTemplate = fileTemplateService.getById(Integer.valueOf(templateId));
		
		if(fileTemplate.getType() == 1){//自定义表单类型
			String formHtml = fileTemplate.getHtml();
			startProcessKey = fileTemplate.getFlowKey();
			
			ActionContext.getContext().put("formHtml", formHtml);;
			ActionContext.getContext().getValueStack().push(startProcessKey);
			ActionContext.getContext().getValueStack().push(templateId);
			
			return "customStartUI";
		}
			
		/*templateFileUrl = new String(templateFileUrl.getBytes("iso-8859-1"),"utf-8");
		templateName = new String(templateName.getBytes("iso-8859-1"),"utf-8");*/
		templateFileUrl = fileTemplate.getTemplateFileUrl();
		templateName = fileTemplate.getName();
		startProcessKey = fileTemplate.getFlowKey();
		
		ActionContext.getContext().getValueStack().push(startProcessKey);
		ActionContext.getContext().getValueStack().push(templateFileUrl);
		ActionContext.getContext().getValueStack().push(templateName);
		
		return "startUI";
	}
	
	//==================activiti操作
	/**
	 * 开启一个流程并保存业务数据
	 * @return
	 * @throws IOException 
	 */
	public String start() throws IOException{		
		
		if(applicationFile != null){	//申请文件不为空，则启动申请	
			MyApplication application = new MyApplication();			
			//设置相关申请信息
			//获取当前用户
			User user = (User) ActionContext.getContext().getSession().get("user");
			//当前时间 	
			String date = DateUtil.dateToString("yyyy-MM-dd", new Date());
			application.setTitle(templateName + "_" + user.getName() + "_" + date);
			application.setProcessInstanceKey(startProcessKey);
			application.setUser(user);
			Integer applicationId = myApplicationService.save(application);
			//开启流程
			myApplicationService.stratProcess(applicationId
					,startProcessKey,applicationFile,fileSavePath,applicationFileFileName);
			
		}else if(StringUtils.isNotBlank(templateId)){//只定义表单
			FileTemplate fileTemplate = fileTemplateService.getById(Integer.valueOf(templateId));
			//1、获取表单数据
			HttpServletRequest request = ServletActionContext.getRequest();
			Map parameterMap = PageDate.getParameterMap(request);
			//将集合转换成json格式
			String jsonData = JSONSerializer.toJSON(parameterMap).toString();
			
			//2、保存申请信息
			MyApplication application = new MyApplication();			
			//设置相关申请信息
			//获取当前用户
			User user = (User) ActionContext.getContext().getSession().get("user");
			//当前时间 	
			String date = DateUtil.dateToString("yyyy-MM-dd", new Date());
			application.setTitle(fileTemplate.getName() + "_" + user.getName() + "_" + date);
			application.setProcessInstanceKey(startProcessKey);
			application.setUser(user);
			application.setJsonData(jsonData);
			application.setFileTemplateId(fileTemplate.getId());
			Integer applicationId = myApplicationService.save(application);
			
			//3、开启流程
			myApplicationService.startProcess(applicationId,startProcessKey,parameterMap);
			
		}		
		return "toTaskList";
	}
	
	/**
	 * 取消申请
	 * @return
	 */
	public String cancelApplication(){
		//删除运行时流程
		myApplicationService.cancelApplicationByProcessInstanceId(model.getProcessInstanceId());
		//删除业务数据
		MyApplication application = myApplicationService.getById(model.getId());
		
		//删除在线转换的文件swf(命名：流程key+myApplicationId)
		String fileName = application.getProcessInstanceKey() + application.getId();
		OfficeConvert.deleteSWF(fileName);
		//删除申请的文件
		if(application != null && application.getApplicationFileUrl() != null){
			File file = new File(application.getApplicationFileUrl());
			file.delete();
		}
	
		myApplicationService.delete(model.getId());
		return "toList";
	}
	
	/**
	 * A:获取任务连线名称，作为按钮，指示流程走向
	 * B：使用当前任务id查询历史任务记录，获取评论信息
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String approveUI() throws UnsupportedEncodingException{
		/**A：准备业务数据*/		
		//查询所有用户
		String deparName = URLDecoder.decode(getDeparName(), "utf-8");
		List<User> users = userService.findAllByDepartmentName(deparName);
		ActionContext.getContext().put("users", users);		
		
		/** 准备业务数据 */
		MyApplication application = myApplicationService.findApplicationByTaskId(taskId);
		if(application.getFileTemplateId() != null){//自定义调单数据			
			//获取表单模板
			FileTemplate fileTemplate = fileTemplateService.getById(application.getFileTemplateId());
			//获取表单标题
			String text = fileTemplate.getText();
			String filed = fileTemplate.getFiled();
			
			List<String> th = Arrays.asList(text.split(","));
			List<String> fileds = Arrays.asList(filed.split(","));
			
			//获取json数据
			String jsonData = application.getJsonData();
			//装换成map集合
			Map formDataMap = JsonUtil.jsonStringData(jsonData);
			
			//设置到页面
			Integer fileTemplateId = application.getFileTemplateId();
			ActionContext.getContext().put("th", th);
			ActionContext.getContext().put("fileds", fileds);
			ActionContext.getContext().put("formDataMap", formDataMap);
			ActionContext.getContext().put("fileTemplateId", fileTemplateId);

		}else{//文件表单数据
			Integer fileTemplateId = application.getFileTemplateId();
			ActionContext.getContext().put("fileTemplateId", fileTemplateId);
			ActionContext.getContext().getValueStack().push(application);

		}
		
		
		/**B:获取任务连线名称，作为按钮，指示流程走向*/
		List<String> outcomes = myTaskService.findOutcomesByTaskId(taskId);
		ActionContext.getContext().put("outcomes", outcomes);
		
		/**C：使用当前任务id查询历史任务记录，获取评论信息*/
		List<CommentOV> commentOVs = myTaskService.findCommentByTaskId(taskId);
		ActionContext.getContext().put("commentOVs", commentOVs);
		
		return "approveUI";
	}
		
	/**
	 * 查看审核记录
	 * @return
	 */
	public String viewHisComment(){
		//准备数据
		MyApplication application = myApplicationService.getById(model.getId());
		
		if(application.getFileTemplateId() != null){//自定义表单数据
			//获取表单模板
			FileTemplate fileTemplate = fileTemplateService.getById(application.getFileTemplateId());
			//获取表单标题
			String text = fileTemplate.getText();
			String filed = fileTemplate.getFiled();
			
			List<String> th = Arrays.asList(text.split(","));
			List<String> fileds = Arrays.asList(filed.split(","));
			
			//获取json数据
			String jsonData = application.getJsonData();
			//装换成map集合
			Map formDataMap = JsonUtil.jsonStringData(jsonData);
			
			//设置到页面
			ActionContext.getContext().put("th", th);
			ActionContext.getContext().put("fileds", fileds);
			ActionContext.getContext().put("formDataMap", formDataMap);
			Integer fileTemplateId = application.getFileTemplateId();
			ActionContext.getContext().put("fileTemplateId", fileTemplateId);
		}else{//文件数据			
			Integer fileTemplateId = application.getFileTemplateId();
			ActionContext.getContext().put("fileTemplateId", fileTemplateId);
			ActionContext.getContext().getValueStack().push(application);
		}
		//使用id，查询评论信息（BUSINESS_KEY字段格式：类名.id）
		Integer applicationId = model.getId();
		List<CommentOV> commentOVs = myApplicationService.findCommentByApplicationId(applicationId);
		ActionContext.getContext().put("commentOVs", commentOVs);		
		
		return "viewHisComment";
	}
	
	
	@Override
	public void notify(DelegateExecution arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 流程结束时，修改表单状态（在流程结束活动节点使用流程监听器的expression）
	 * @param execution 执行流的实例
	 */
	@SkipValidation
	public void changeStatus(Execution execution){
		//流程定义id
		String processInstanceId = execution.getProcessInstanceId();
		//通过流程定义id查找BUSINESS_KEY得到MyApplication的Id
		String myApplicationId = myApplicationService.findMyApplicationIdProcessInstanceId(processInstanceId);
		//修改状态
		MyApplication application = myApplicationService.getById(Integer.valueOf(myApplicationId));
		application.setState(2);
		myApplicationService.update(application);
	}
	
	//================文件预览
	/**
	 * 查看任务中附件（ACT_HI_ATTACHMENT表url获取附件）
	 * @throws IOException 
	 * 	
	 */
	public void viewAttachment() throws IOException{
		//文件名称
		String fileName = taskId;
		//从activiti的ACT_HI_ATTACHMENT表url获取附件
		Attachment attachment = myApplicationService.findAttachmentByTaskId(taskId);
		String fileUrl = attachment.getUrl();
		File onLineFile = new File(fileUrl);
		
		//>>>>>>>>>>>>>>>>>>>>>>>>>在线预览<<<<<<<<<<<<<<<<<<<<<<<<<<
		//获取请求响应对象
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		//得到当前项目在服务器中的位置,把所有\替换成/
		String projectPath = request.getRealPath("").replaceAll("\\\\", "/");
		//文件转换
		try{
			OfficeConvert officeConvert = new OfficeConvert(projectPath,onLineFile,fileName);
			officeConvert.convert();
			if(officeConvert.getExceptionStatus()==1){//生成失败，重新生成目标文件
				OfficeConvert officeConvert1 = new OfficeConvert(projectPath,onLineFile,fileName);
				officeConvert1.convert();
			}						
		}catch(Exception exception){
			System.out.println("抱歉，目标文件生成失败！FileFlowAction.viewAttachment");
			exception.printStackTrace();
		}
		//请求转发到在线预览页面
		response.sendRedirect("readOnLine/myApplicationAction/ReadOnline.jsp?swfPath=file/swf/"+fileName+".swf");
	}
	
	/**
	 * 查看MyApplication对象中的附件（在表myapplication中applicationFileUrl获取路径）
	 * 		fileOnLine: 需要file对象
	 * 		fileName: 文件名称要唯一
	 * 
	 * @return
	 * @throws Exception
	 */
	public void view() throws Exception{		
		MyApplication application = myApplicationService.getById(model.getId());
		//需要在线预览的文件对象
		File fileOnLine = new File(application.getApplicationFileUrl());	
		//获取文件名
		String fileName = application.getProcessInstanceKey() + String.valueOf(application.getId());	
		
		//>>>>>>>>>>>>>>>>>>>>>>>>>在线预览<<<<<<<<<<<<<<<<<<<<<<<<<<
		//获取请求响应对象
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();		
		//得到当前项目在服务器中的位置,把所有\替换成/
		String projectPath = request.getRealPath("").replaceAll("\\\\", "/");
		//从对象获取URL查看文件
		OfficeConvert officeConvert = new OfficeConvert(projectPath,fileOnLine,fileName);
		try{
			officeConvert.convert();
			if(officeConvert.getExceptionStatus()==1){
				officeConvert.convert();
			}
		}catch(Exception exception){
			System.out.println("抱歉，目标文件生成失败！");
		}
		//请求转发到在线预览页面
		response.sendRedirect("readOnLine/myApplicationAction/ReadOnline.jsp?swfPath=file/swf/"+fileName+".swf");
	}
	
	//============
	public String getStartProcessKey() {
		return startProcessKey;
	}

	public void setStartProcessKey(String startProcessKey) {
		this.startProcessKey = startProcessKey;
	}

	public String getTemplateFileUrl() {
		return templateFileUrl;
	}

	public void setTemplateFileUrl(String templateFileUrl) {
		this.templateFileUrl = templateFileUrl;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public File getApplicationFile() {
		return applicationFile;
	}

	public void setApplicationFile(File applicationFile) {
		this.applicationFile = applicationFile;
	}

	public String getApplicationFileFileName() {
		return applicationFileFileName;
	}

	public void setApplicationFileFileName(String applicationFileFileName) {
		this.applicationFileFileName = applicationFileFileName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getDeparName() {
		return deparName;
	}

	public void setDeparName(String deparName) {
		this.deparName = deparName;
	}

	public String getLikeSearch() {
		return likeSearch;
	}

	public void setLikeSearch(String likeSearch) {
		this.likeSearch = likeSearch;
	}

	public int getApplicationState() {
		return applicationState;
	}

	public void setApplicationState(int applicationState) {
		this.applicationState = applicationState;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	
	
	
	
}
