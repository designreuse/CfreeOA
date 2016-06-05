package cn.sise.oa.serviceImpl;

import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sise.oa.base.DaoSupportImpl;
import cn.sise.oa.domain.FileTemplate;
import cn.sise.oa.service.FileTemplateService;



@Service
@Transactional
public class FileTemplateServiceImpl extends DaoSupportImpl<FileTemplate> implements FileTemplateService{

	@Override
	public List<ProcessDefinition> findProcessDefinitions() {
		List<ProcessDefinition> processDefinitions = repositoryService//
				.createProcessDefinitionQuery()//
				.list();
		return processDefinitions;
	}


	
}
