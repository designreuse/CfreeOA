package cn.sise.oa.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.Deployment;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.sise.oa.action.bean.ProcessOV;
import cn.sise.oa.base.BaseAction;
import cn.sise.oa.domain.FileTemplate;
import cn.sise.oa.util.QueryHelper;

@Controller
@Scope("prototype")
public class FileTemplateAction extends BaseAction<FileTemplate>{

	private File resource; //上传的文件
	private String resourceFileName;
	
	//获取请求响应对象
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	//得到当前项目在服务器中的位置,把所有\替换成/
	private String fileSavePath = request.getRealPath("").replaceAll("\\\\", "/") + "/file/fileTemplate";
	
	public String list(){
		new QueryHelper(FileTemplate.class,"ft")//
		.preparePageBean(fileTemplateService, pageNum, 15);
		
		return "list";
	}
	
	//================文件表单=======================
	/**
	 * 文件表单界面
	 * @return
	 */
	public String addUI(){
		//准备流程数据
		//查询流程部署对象deployment，对应表act_re_deployment
		List<Deployment> deployments = processDefinitionService.findDeployments();
		//将部署对象和流程定义封装成界面类
		List<ProcessOV> processOVs = processDefinitionService.deploymentTransformProcessOV(deployments);
		ActionContext.getContext().put("processOVs", processOVs);
		return "addUI";
	}
	
	public String add() throws IOException{
		//将文件保存进系统
		if(resource != null){		
			File saveFile = new File(fileSavePath + "/" + resourceFileName);
			if(!saveFile.exists() && !saveFile.isDirectory()){//不存在，则保存
				FileUtils.copyFile(resource, saveFile);
			}	
			model.setTemplateFileUrl(fileSavePath+"/"+resourceFileName);
		}
		//设置对象信息
		model.setFlowName(model.getName() + "流程");
		fileTemplateService.save(model);
		return "toList";
	}
	
	public String editUI(){
		//准备流程数据
		//查询流程部署对象deployment，对应表act_re_deployment
		List<Deployment> deployments = processDefinitionService.findDeployments();
		//将部署对象和流程定义封装成界面类
		List<ProcessOV> processOVs = processDefinitionService.deploymentTransformProcessOV(deployments);
		//准备业务数据
		FileTemplate fileTemplate = fileTemplateService.getById(model.getId());
		System.out.println(processOVs.get(0).getProcessDedinitionKey());
		ActionContext.getContext().put("processOVs", processOVs);
		ActionContext.getContext().getValueStack().push(fileTemplate);
		
		return "addUI";
	}
		
	public String edit() throws IOException{
		FileTemplate fileTemplate = fileTemplateService.getById(model.getId());
		//将文件保存进系统
		if(resource != null){
			//如果有存在文件，先删除之前保存的模板文件
			if(fileTemplate.getTemplateFileUrl() != null){
				String fileUrl = fileTemplate.getTemplateFileUrl();
				File file = new File(fileUrl);
				file.delete();
			}
			//保存新文件
			File saveFile = new File(fileSavePath + "/" + resourceFileName);
			if(!saveFile.exists() && !saveFile.isDirectory()){//不存在，则保存
				FileUtils.copyFile(resource, saveFile);
			}	
			fileTemplate.setTemplateFileUrl(fileSavePath+"/"+resourceFileName);
		}
		//设置流程名称
		fileTemplate.setName(model.getName());
		fileTemplate.setFlowName(model.getName() + "流程");
		fileTemplate.setFlowKey(model.getFlowKey());
		
		fileTemplateService.update(fileTemplate);
		
		return "toList";
	}

	/**
	 * 下载模板文件
	 * @throws UnsupportedEncodingException 
	 */
	public void downTemplateFile() throws UnsupportedEncodingException{
		String templateFileUrl = model.getTemplateFileUrl();
		templateFileUrl = new String(templateFileUrl.getBytes("iso-8859-1"),"utf-8");
		resourceManagementService.downFileByPath(templateFileUrl,response);
	}
	
	//====================自定义表单==============================
	
	/**
	 * 自定义表单添加页面
	 * @return
	 */
	public String customAddUI(){
		//准备流程数据
		//查询流程部署对象deployment，对应表act_re_deployment
		List<Deployment> deployments = processDefinitionService.findDeployments();
		//将部署对象和流程定义封装成界面类
		List<ProcessOV> processOVs = processDefinitionService.deploymentTransformProcessOV(deployments);
		ActionContext.getContext().put("processOVs", processOVs);
		return "customAddUI";
	}
	
	public String customAdd(){
		//设置对象信息
		model.setFlowName(model.getName() + "流程");
		model.setType(1);
		fileTemplateService.save(model);
		
		return "toList";
	}
	
	public String customEditUI(){
		//准备流程数据
		//查询流程部署对象deployment，对应表act_re_deployment
		List<Deployment> deployments = processDefinitionService.findDeployments();
		//将部署对象和流程定义封装成界面类
		List<ProcessOV> processOVs = processDefinitionService.deploymentTransformProcessOV(deployments);
		//准备业务数据
		FileTemplate fileTemplate = fileTemplateService.getById(model.getId());
		System.out.println(processOVs.get(0).getProcessDedinitionKey());
		ActionContext.getContext().put("processOVs", processOVs);
		ActionContext.getContext().getValueStack().push(fileTemplate);
		return "customEditUI";
	}
	
	public String customEdit(){
		model.setFlowName(model.getName() + "流程");
		fileTemplateService.update(model);
		return "toList";
	}
	
	public void preview() throws IOException{
		FileTemplate fileTemplate = fileTemplateService.getById(model.getId());
		String formDate = fileTemplate.getHtml();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(formDate);
		printWriter.flush();
		printWriter.close();
	}
	
	//=================公有操作========================
	
	public String delete(){
		//删除文件
		FileTemplate fileTemplate = fileTemplateService.getById(model.getId());
		if(fileTemplate.getTemplateFileUrl() != null){
			String fileUrl = fileTemplate.getTemplateFileUrl();
			File file = new File(fileUrl);
			file.delete();
		}
		//删除业务数据
		fileTemplateService.delete(model.getId());
		return "toList";
	}
		
	/**
	 * 设置为使用状态
	 * @throws IOException 
	 */
	public void setUsed() throws IOException{
		FileTemplate fileTemplate = fileTemplateService.getById(model.getId());
		fileTemplate.setUsed(1);
		fileTemplateService.update(fileTemplate);
		
		//返回修改成功状态
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		String success = "1";
		printWriter.write(success);
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * 设置为未使用状态
	 * @throws IOException 
	 */
	public void setUnUsed() throws IOException{
		FileTemplate fileTemplate = fileTemplateService.getById(model.getId());
		fileTemplate.setUsed(0);
		fileTemplateService.update(fileTemplate);
		
		//返回修改成功状态
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		String success = "1";
		printWriter.write(success);
		printWriter.flush();
		printWriter.close();
	}
	
	
	//==========================
	public File getResource() {
		return resource;
	}

	public void setResource(File resource) {
		this.resource = resource;
	}

	public String getResourceFileName() {
		return resourceFileName;
	}

	public void setResourceFileName(String resourceFileName) {
		this.resourceFileName = resourceFileName;
	}
	
	
	
}
