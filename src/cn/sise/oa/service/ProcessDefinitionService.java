package cn.sise.oa.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.repository.Deployment;

import cn.sise.oa.action.bean.ProcessOV;
import cn.sise.oa.base.DaoSupport;

public interface ProcessDefinitionService extends DaoSupport<ProcessOV>{

	/**
	 * 将activiti中的deployment对象装换成processOV对象
	 * @param deployments
	 * @return
	 */
	List<ProcessOV> deploymentTransformProcessOV(List<Deployment> deployments);

	/**
	 * 查询部署对象
	 * @return
	 */
	List<Deployment> findDeployments();

	/**
	 * 部署流程定义文件
	 * @param resource 
	 * @param deplomentName
	 */
	void deployProcessDefinition(File resource,String deplomentName);

	/**
	 *  获取图片输入流
	 * @param deploymentId
	 * @param processDedinitionImageName
	 * @return
	 */
	InputStream findImageInputStream(String deploymentId,
			String processDedinitionImageName);

	

}
