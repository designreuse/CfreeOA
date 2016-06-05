package cn.sise.oa.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sise.oa.action.bean.ProcessOV;
import cn.sise.oa.base.DaoSupportImpl;
import cn.sise.oa.service.ProcessDefinitionService;

@Service
@Transactional
public class ProcessDefinitionServiceImpl extends DaoSupportImpl<ProcessOV> implements ProcessDefinitionService{

	@Resource
	private RepositoryService repositoryService;
	
	/* 
	 * 将流程信息(流程部署信息和流程定义信息)封装成界面类
	*/
	public List<ProcessOV> deploymentTransformProcessOV(
			List<Deployment> deployments) {
		List<ProcessOV> processOVs = new ArrayList<ProcessOV>();				
		for(Deployment deployment : deployments){ //封装界面类
			//根据流程部署ID查找流程定义
			ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
							.deploymentId(deployment.getId())//更具流程部署ID查找流程定义对象
							.singleResult();
			
			ProcessOV processOV = new ProcessOV();
			processOV.setDeploymentId(deployment.getId());
			processOV.setDeploymentName(deployment.getName());
			processOV.setDeploymentTime(deployment.getDeploymentTime());
			processOV.setVersion(definition.getVersion());
			processOV.setDescription(definition.getDescription());
			processOV.setProcessDedinitionFileName(definition.getResourceName());
			processOV.setProcessDedinitionImageName(definition.getDiagramResourceName());
			processOV.setProcessDedinitionKey(definition.getKey());
			processOV.setProcessDedinitionName(definition.getName());
			processOV.setProcessDedinitionId(definition.getId());
			
			processOVs.add(processOV);
		}
		return processOVs;
	}

	
	/**
	 * 查询部署对象
	 */
	public List<Deployment> findDeployments() {
		List<Deployment> deployments = repositoryService.createDeploymentQuery()//创建部署查询对象
							.list();
		return deployments;
	}

	/**
	 *部署流程定义文件
	 */
	public void deployProcessDefinition(File resource,String deploymentName) {
		try {
			if(resource != null){ //上传文件不为空
				//创建流程存储实例				
				FileInputStream fis = new FileInputStream(resource);
				ZipInputStream zi = new ZipInputStream(fis);
				repositoryService.createDeployment()
						.addZipInputStream(zi)
						.name(deploymentName)			
						.deploy();//部署
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * 获取图片输入流
	 */
	public InputStream findImageInputStream(String deploymentId,
			String processDedinitionImageName) {
		return repositoryService.getResourceAsStream(deploymentId, processDedinitionImageName);
	}


}
