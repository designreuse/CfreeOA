package cn.sise.oa.action.bean;

import java.util.Date;

/**
 * 界面类
 * 流程定义相关信息，对应表act_re_procdef 和 act_re_deployment 
 * @author yzh
 *
 */
public class ProcessOV {

	//-----------流程部署数据 ，对应表 act_re_deployment------------------
	private String deploymentId; //deployment的ID
	private String deploymentName; 
	private Date deploymentTime; //流程部署时间
	
	//---------流程定义数据 act_re_procdef --------------
	private String processDedinitionId;
	private String processDedinitionKey; //查找主键 ---> 对应key
	private String processDedinitionName;  //流程定义的名称 --> 对应 name
	private int version; //流程定义的版本-->对一个rev_
	private String processDedinitionFileName; //流程定义文件名称
	private String processDedinitionImageName;//流程定义图片名称
	private String description; //流程定义的描述-->对应description
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public Date getDeploymentTime() {
		return deploymentTime;
	}
	public void setDeploymentTime(Date deploymentTime) {
		this.deploymentTime = deploymentTime;
	}
	public String getProcessDedinitionId() {
		return processDedinitionId;
	}
	public void setProcessDedinitionId(String processDedinitionId) {
		this.processDedinitionId = processDedinitionId;
	}
	public String getProcessDedinitionKey() {
		return processDedinitionKey;
	}
	public void setProcessDedinitionKey(String processDedinitionKey) {
		this.processDedinitionKey = processDedinitionKey;
	}
	public String getProcessDedinitionName() {
		return processDedinitionName;
	}
	public void setProcessDedinitionName(String processDedinitionName) {
		this.processDedinitionName = processDedinitionName;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getProcessDedinitionFileName() {
		return processDedinitionFileName;
	}
	public void setProcessDedinitionFileName(String processDedinitionFileName) {
		this.processDedinitionFileName = processDedinitionFileName;
	}
	public String getProcessDedinitionImageName() {
		return processDedinitionImageName;
	}
	public void setProcessDedinitionImageName(String processDedinitionImageName) {
		this.processDedinitionImageName = processDedinitionImageName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDeploymentName() {
		return deploymentName;
	}
	public void setDeploymentName(String deploymentName) {
		this.deploymentName = deploymentName;
	}

	
}
