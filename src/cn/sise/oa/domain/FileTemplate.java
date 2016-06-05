package cn.sise.oa.domain;

/**
 * 表单模板
 * @author yzh
 *
 */
public class FileTemplate {

	private Integer id;
	private String name;
	private String summary;//简介
	private int type; //0:文件表单,1：自定义表单
	private int used; //0:用户不可以使用，1：用户可使用
	private String flowName; //流程名称
	private String flowKey; //流程key,用于启动流程
	
	//文件表单
	private String templateFileUrl; //模板文件地址
	
	//自定义表单
	private String code; //自定义表单模型数据
	private String html; //表单HTML数据
	private String filed;//表单的name元素
	private String text; //表单文本内容
	private String flowVar;//流程需要设置的参数
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public String getFlowKey() {
		return flowKey;
	}
	public void setFlowKey(String flowKey) {
		this.flowKey = flowKey;
	}
	public String getTemplateFileUrl() {
		return templateFileUrl;
	}
	public void setTemplateFileUrl(String templateFileUrl) {
		this.templateFileUrl = templateFileUrl;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getFiled() {
		return filed;
	}
	public void setFiled(String filed) {
		this.filed = filed;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getFlowVar() {
		return flowVar;
	}
	public void setFlowVar(String flowVar) {
		this.flowVar = flowVar;
	}
	public int getUsed() {
		return used;
	}
	public void setUsed(int used) {
		this.used = used;
	}
	
	
}
