package cn.sise.oa.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.sise.oa.action.bean.FileOV;
import cn.sise.oa.base.BaseAction;

@Controller
@Scope("prototype")
public class ResourceManagementAction extends BaseAction<FileOV>{

	private String currentPath; //当前目录（从resourceRoot开始）
	private String parentDirectory;//上级目录（全路径）
	private String newName;//新文件名称
	
	private File upload; 
	private String uploadFileName;
	private String uploadContentType;
	private String downPath;
	
	private String dirExists;
	//获取请求响应对象
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	//得到当前项目在服务器中的位置,把所有\替换成/
	private String systemPath = request.getRealPath("").replaceAll("\\\\", "/") + "/resourceRoot";
			
	/**
	 * 罗列文件夹内的内容
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String list() throws UnsupportedEncodingException{
		String realPath = "";//最终查询路径
		String fileName = model.getName();
		
		if(StringUtils.isNotBlank(fileName)){//如果有文件夹名称，则拼接文件路径
			//路径转码开始
			fileName = new String(fileName.getBytes("iso-8859-1"),"utf-8");
			currentPath = new String(currentPath.getBytes("iso-8859-1"),"utf-8");
			systemPath = new String(systemPath.getBytes("iso-8859-1"),"utf-8");
			//路径转码结束
			currentPath += "/" + fileName;
			realPath = systemPath + currentPath;
			parentDirectory = realPath.substring(0, realPath.lastIndexOf('/'));
		}else{ //首次访问				
			realPath = systemPath ; //文件全路径
			parentDirectory = realPath;
		}
		//获取文件和文件夹
		List<File> files = resourceManagementService.getAllFileInFolder(realPath);
		List<File> folder = resourceManagementService.getAllFolder(realPath);
		//将数据包装成界面类
		List<FileOV> fileOVs = resourceManagementService.transformToOV(files);
		List<FileOV> folders = resourceManagementService.transformToOV(folder);
		
		ActionContext.getContext().put("fileOVs", fileOVs);
		ActionContext.getContext().put("folders", folders);
		ActionContext.getContext().put("parentDirectory", parentDirectory);
		ActionContext.getContext().getValueStack().push(currentPath);
		return "list";
	}
	
	/**
	 * action转发查询文件列表
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String toList() throws UnsupportedEncodingException{
		//路径转码开始
		currentPath = new String(currentPath.getBytes("iso-8859-1"),"utf-8");
		parentDirectory = new String(parentDirectory.getBytes("iso-8859-1"),"utf-8");
		systemPath = new String(systemPath.getBytes("iso-8859-1"),"utf-8");
		//路径转码结束
		
		String realPath = systemPath + currentPath;
		//获取文件和文件夹	
		List<File> files = resourceManagementService.getAllFileInFolder(realPath);
		List<File> folder = resourceManagementService.getAllFolder(realPath);
		//将数据包装成界面类
		List<FileOV> fileOVs = resourceManagementService.transformToOV(files);
		List<FileOV> folders = resourceManagementService.transformToOV(folder);
		
		ActionContext.getContext().put("fileOVs", fileOVs);
		ActionContext.getContext().put("folders", folders);
		ActionContext.getContext().put("parentDirectory", parentDirectory);
		ActionContext.getContext().getValueStack().push(currentPath);
		return "list";
	}
	
	/**
	 * 创建文件夹界面
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String addFolderUI() throws UnsupportedEncodingException{
		currentPath = new String(currentPath.getBytes("iso-8859-1"),"utf-8");
		parentDirectory = new String(parentDirectory.getBytes("iso-8859-1"),"utf-8");
		ActionContext.getContext().put("parentDirectory", parentDirectory);
		ActionContext.getContext().getValueStack().push(currentPath);
		return "addFolderUI";
	}
	
	/**
	 * 创建文件夹
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String addFolder() throws UnsupportedEncodingException{
		String fileName = model.getName();
		//路径转码开始
		//fileName = new String(fileName.getBytes("iso-8859-1"),"utf-8");
		//currentPath = new String(currentPath.getBytes("iso-8859-1"),"utf-8");
		//systemPath = new String(systemPath.getBytes("iso-8859-1"),"utf-8");
		//路径转码结束	
		String filePath = systemPath + currentPath + "/" + fileName;
		//创建文件
		resourceManagementService.createFolderByPath(filePath);

		ActionContext.getContext().put("parentDirectory", parentDirectory);
		ActionContext.getContext().put("currentPath", currentPath);
		
		return "toList";
	}
	
	/**
	 * 删除文件
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String delete() throws UnsupportedEncodingException{
		String fileName = model.getName();		
		//路径转码开始
		fileName = new String(fileName.getBytes("iso-8859-1"),"utf-8");
		if (currentPath != null) {
			currentPath = new String(currentPath.getBytes("iso-8859-1"),"utf-8");
		}
		systemPath = new String(systemPath.getBytes("iso-8859-1"),"utf-8");
		//路径转码结束
		String delRealPath = systemPath + currentPath + "/" + fileName;		
		//删除文件（及其目录下所有文件）
		resourceManagementService.deleteDirByPath(delRealPath);
		ActionContext.getContext().put("parentDirectory", parentDirectory);
		ActionContext.getContext().put("currentPath", currentPath);
		
		return "toList";
	}
	
	/**
	 * 重名名界面
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String renameUI() throws UnsupportedEncodingException{
		
		currentPath = new String(currentPath.getBytes("iso-8859-1"),"utf-8");
		parentDirectory = new String(parentDirectory.getBytes("iso-8859-1"),"utf-8");
		model.setName(new String(model.getName().getBytes("iso-8859-1"),"utf-8"));
		
		ActionContext.getContext().put("parentDirectory", parentDirectory);
		ActionContext.getContext().getValueStack().push(currentPath);
		ActionContext.getContext().getValueStack().push(model);
		return "renameUI";
	}
	
	/**
	 * 重名名
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String rename() throws UnsupportedEncodingException{
		String oldName = model.getName();
		//转码开始
		systemPath = new String(systemPath.getBytes("iso-8859-1"),"utf-8");
		//currentPath = new String(currentPath.getBytes("iso-8859-1"),"utf-8");
		//转码结束
		String realPath = systemPath + currentPath ;
		//重命名
		resourceManagementService.renameFile(realPath,oldName,newName);
		ActionContext.getContext().put("parentDirectory", parentDirectory);
		ActionContext.getContext().put("currentPath", currentPath);
		return "toList";
	}
	
	/**
	 * 上传文件界面
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String uploadFileUI() throws UnsupportedEncodingException{
		currentPath = new String(currentPath.getBytes("iso-8859-1"),"utf-8");
		parentDirectory = new String(parentDirectory.getBytes("iso-8859-1"),"utf-8");		
		
		ActionContext.getContext().put("parentDirectory", parentDirectory);
		ActionContext.getContext().getValueStack().push(currentPath);
		return "uploadFileUI";
	}
	
	/**
	 * 上传文件
	 * @return
	 * @throws IOException 
	 */
	public String uploadFile() throws IOException{
		//将文件保存进系统
		if(upload != null){		
			String uploadPath = systemPath + currentPath;
			File saveFile = new File(uploadPath + "/" + uploadFileName);
			if(!saveFile.exists() && !saveFile.isDirectory()){//不存在，则保存
				FileUtils.copyFile(upload, saveFile);
			}
		}
		
		ActionContext.getContext().put("parentDirectory", parentDirectory);
		ActionContext.getContext().put("currentPath", currentPath);
		
		return "toList";
	}

	/**
	 * 文件下载
	 * @throws Exception 
	 */
	public void downFile() throws Exception{
		String fileName = model.getName();
		//转码开始
		fileName = new String(fileName.getBytes("iso-8859-1"),"utf-8");
		systemPath = new String(systemPath.getBytes("iso-8859-1"),"utf-8");
		if (currentPath != null) {
			currentPath = new String(currentPath.getBytes("iso-8859-1"),"utf-8");
		}
		//转码结束
		downPath = systemPath + currentPath + "/" +fileName;
		//下载文件
		resourceManagementService.downFileByPath(downPath,response);
	}
	
	/**
	 * 获取上级目录
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String getParentDir() throws UnsupportedEncodingException{
		
		String realPath = "";//最终查询路径
		realPath = parentDirectory;
		if(currentPath.indexOf("/") != currentPath.lastIndexOf("/")){
			parentDirectory = realPath.substring(0, realPath.lastIndexOf('/'));
			currentPath = currentPath.substring(0, currentPath.lastIndexOf('/'));
			currentPath = new String(currentPath.getBytes("iso-8859-1"),"utf-8");
		}else{
			parentDirectory = systemPath;
			currentPath = null;
		}
		
		//路径转码开始
		realPath = new String(realPath.getBytes("iso-8859-1"),"utf-8");
		parentDirectory = new String(parentDirectory.getBytes("iso-8859-1"),"utf-8");
		//路径转码结束	

		//获取文件和文件夹
		List<File> files = resourceManagementService.getAllFileInFolder(realPath);
		List<File> folder = resourceManagementService.getAllFolder(realPath);
		//将数据包装成界面类
		List<FileOV> fileOVs = resourceManagementService.transformToOV(files);
		List<FileOV> folders = resourceManagementService.transformToOV(folder);
		
		ActionContext.getContext().put("fileOVs", fileOVs);
		ActionContext.getContext().put("folders", folders);
		ActionContext.getContext().put("parentDirectory", parentDirectory);
		ActionContext.getContext().getValueStack().push(currentPath);
		return "list";
	}
	
	//==================

	public String getCurrentPath() {
		return currentPath;
	}

	public void setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
	}

	public String getParentDirectory() {
		return parentDirectory;
	}

	public void setParentDirectory(String parentDirectory) {
		this.parentDirectory = parentDirectory;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getDownPath() {
		return downPath;
	}

	public void setDownPath(String downPath) {
		this.downPath = downPath;
	}

	public String getDirExists() {
		return dirExists;
	}

	public void setDirExists(String dirExists) {
		this.dirExists = dirExists;
	}
	
	
}
