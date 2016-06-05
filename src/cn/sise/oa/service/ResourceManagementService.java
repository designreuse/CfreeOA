package cn.sise.oa.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cn.sise.oa.action.bean.FileOV;
import cn.sise.oa.base.DaoSupport;

public interface ResourceManagementService extends DaoSupport<FileOV>{

	/**
	 * 获取路径下的所有文件
	 * @param realPath
	 * @return
	 */
	List<File> getAllFileInFolder(String realPath);
	
	/**
	 * 获取路径下的所有文件夹
	 * @param realPath
	 * @return
	 */
	List<File> getAllFolder(String realPath);

	/**
	 * 将数据转换成界面数据
	 * @param files
	 * @return
	 */
	List<FileOV> transformToOV(List<File> files);

	/**
	 * 根据路径创建文件夹
	 * @param filePath
	 */
	void createFolderByPath(String filePath);

	/**
	 * 根据路径删除目录及其所有子目录
	 * @param delRealPath
	 */
	boolean deleteDirByPath(String delRealPath);

	/**
	 * 重命名
	 * @param folderPath 文件夹路径
	 * @param oldName 文件名称
	 * @param newName 文件新名称
	 */
	void renameFile(String folderPath, String oldName, String newName);

	/**
	 * 更具路径下载文件
	 * @param downPath
	 * @param response 
	 */
	void downFileByPath(String downPath, HttpServletResponse response);


}
