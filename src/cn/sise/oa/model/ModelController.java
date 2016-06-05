package cn.sise.oa.model;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import sun.misc.BASE64Encoder;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 流程模型控制器
 * 
 * @author henryyan
 */
@Controller
public class ModelController extends ActionSupport {

	private String name;
	private String key;
	private String description;
	private String id;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	RepositoryService repositoryService;

	/**
	 * 模型列表
	 */
	public String modelList() {
		List<Model> list = repositoryService.createModelQuery().list();
		ActionContext.getContext().put("list", list);
		return "modelList";
	}

	/**
	 * 创建模型
	 */
	public void create() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace",
					"http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.put("stencilset", stencilSetNode);
			Model modelData = repositoryService.newModel();

			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			description = StringUtils.defaultString(description);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
					description);
			modelData.setMetaInfo(modelObjectNode.toString());
			modelData.setName(name);
			modelData.setKey(key);
			System.out.println(key);

			repositoryService.saveModel(modelData);
			repositoryService.addModelEditorSource(modelData.getId(),
					editorNode.toString().getBytes("utf-8"));

			response.sendRedirect(request.getContextPath()
					+ "/service/editor?id=" + modelData.getId());
		} catch (Exception e) {
			logger.error("创建模型失败：", e);
		}
	}

	/**
	 * 根据Model部署流程
	 */
	
	public String deploy() {		
		try {
			Model modelData = repositoryService.getModel(id);
			JsonNode modelNode = new ObjectMapper()
				.readTree(repositoryService.getModelEditorSource(modelData.getId()));
					
			/*ObjectNode modelNode = (ObjectNode) new ObjectMapper()
					.readTree(repositoryService.getModelEditorSource(modelData.getId()));*/
			byte[] bpmnBytes = null;

			BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			bpmnBytes = new BpmnXMLConverter().convertToXML(model);

			//转换成utf-8
			String strBpmnBytes = new String(bpmnBytes);
			String gbk=new String(strBpmnBytes.getBytes("GBK"));  
	        String iso = new String(gbk.getBytes("UTF-8"),"ISO-8859-1");  
	        String utf8=new String(iso.getBytes("ISO-8859-1"),"UTF-8");  
	        byte[]  json_xml=new BASE64Encoder().encode(utf8.getBytes("UTF-8")).getBytes();
			
			String processName = modelData.getName() + ".bpmn20.xml";
			Deployment deployment = repositoryService.createDeployment()
					.name(modelData.getName())
					.addString(processName, utf8).deploy();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据模型部署流程失败：modelId={}", id, e);
		}
		return "toModelList";
	}

	/**
	 * 导出model的xml文件
	 */
	public void export() {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			Model modelData = repositoryService.getModel(id);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper()
					.readTree(repositoryService
					.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			IOUtils.copy(in, response.getOutputStream());
			String filename = bpmnModel.getMainProcess().getId()
					+ ".bpmn20.xml";
			response.setHeader("Content-Disposition", "attachment; filename="
					+ filename);
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("导出model的xml文件失败：modelId={}", id, e);
		}
	}

	public String delete() {
		repositoryService.deleteModel(id);
		return "toModelList";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	// ===========

}
