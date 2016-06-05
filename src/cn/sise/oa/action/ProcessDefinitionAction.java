package cn.sise.oa.action;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.sise.oa.action.bean.ProcessOV;
import cn.sise.oa.base.BaseAction;

@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends BaseAction<ProcessOV>{

	private File resource;
	private String deployName;
	
	/**
	 * 查询所有部署对象
	 * @return
	 */
	public String list(){
		//查询流程部署对象deployment，对应表act_re_deployment
		List<Deployment> deployments = processDefinitionService.findDeployments();
		//将部署对象和流程定义封装成界面类
		List<ProcessOV> processOVs = processDefinitionService.deploymentTransformProcessOV(deployments);
		
		ActionContext.getContext().put("processOVs",processOVs);
		return "list";
	}
	
	/**
	 * 流程部署界面
	 * @return
	 */
	public String deployUI(){		
		return "deployUI";
	}
	
	/**
	 * 部署流程定义
	 * @return
	 */
	public String deploy(){
		processDefinitionService.deployProcessDefinition(resource,deployName);
		return "toList";
	}
	
	/**
	 * 删除流程部署
	 * @return
	 */
	public String delete(){
		//删除流程部署
		repositoryService.deleteDeployment(model.getDeploymentId(), true);
		return "toList";
	}
	
	// 显示流程图
	public String showDiagram() throws Exception {
		//获取部署ID
		String deploymentId = model.getDeploymentId();
		//获取图片名称
		String processDedinitionImageName = model.getProcessDedinitionImageName();
		//获取资源文件表act_ge_bytearray中图片的输入流
		processDedinitionImageName = new String(processDedinitionImageName.getBytes("iso-8859-1"),"utf-8");
		InputStream in = processDefinitionService.findImageInputStream(deploymentId,processDedinitionImageName);
		//从response获取输出流
		OutputStream out = ServletActionContext.getResponse().getOutputStream();
		//将输入流中的数据读出来
		for(int b=-1; (b=in.read())!=-1;)
			out.write(b);
		out.close();
		in.close();
		return null;
	}
	
	//==========================

	public File getResource() {
		return resource;
	}

	public void setResource(File resource) {
		this.resource = resource;
	}

	public String getDeployName() {
		return deployName;
	}

	public void setDeployName(String deployName) {
		this.deployName = deployName;
	}
	
	
}
