package cn.sise.oa.service;

import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;

import cn.sise.oa.base.DaoSupport;
import cn.sise.oa.domain.FileTemplate;

public interface FileTemplateService extends DaoSupport<FileTemplate>{

	/**
	 * 查询所有的流程定义对象
	 * @return
	 */
	List<ProcessDefinition> findProcessDefinitions();

}
