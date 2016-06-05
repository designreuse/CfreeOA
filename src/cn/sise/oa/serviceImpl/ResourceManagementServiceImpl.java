package cn.sise.oa.serviceImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sise.oa.action.bean.FileOV;
import cn.sise.oa.base.DaoSupportImpl;
import cn.sise.oa.service.ResourceManagementService;

@Service
@Transactional
public class ResourceManagementServiceImpl extends DaoSupportImpl<FileOV> implements ResourceManagementService{

	@Override
	public List<File> getAllFileInFolder(String realPath) {
		File file=new File(realPath);
		File[] tempList = file.listFiles();
		List<File> files = new ArrayList<File>();
		for (int i = 0; i < tempList.length; i++) {	
			
			if (tempList[i].isFile()) {
				files.add(tempList[i]);
			}
		}		
		return files;
	}

	@Override
	public List<File> getAllFolder(String realPath) {
		File file=new File(realPath);
		File[] tempList = file.listFiles();
		List<File> files = new ArrayList<File>();
		for (int i = 0; i < tempList.length; i++) {			
			if (tempList[i].isDirectory()) {
				files.add(tempList[i]);
			}
		}		
		return files;
	}
	
	@Override
	public List<FileOV> transformToOV(List<File> files) {
		List<FileOV> fileOVs = new ArrayList<FileOV>();
		for(File file : files){
			FileOV fileOV = new FileOV();
			fileOV.setName(file.getName());
			fileOV.setLastModified(new Date(file.lastModified()));
			fileOV.setFileSize(file.length()/1024);
			if(file.isFile()){
				String fileName = file.getName();
				fileOV.setFileType(fileName.substring(fileName.indexOf('.')+1));
			}
			fileOVs.add(fileOV);
		}
		return fileOVs;
	}

	@Override
	public void createFolderByPath(String filePath) {
		File file =new File(filePath);    		  
		if(!file.exists()  && !file.isDirectory()){//如果文件夹不存在则创建    		      
		    file .mkdir();    
		}else{  
		    System.out.println("文件夹已存在！");
		}		
	}

	@Override
	public boolean deleteDirByPath(String delRealPath) {
		File dir = new File(delRealPath);
		return deleteDir(dir);
	}

	private boolean deleteDir(File dir) {
		if(dir.isDirectory()){
			String[] children = dir.list();
			for(String childrenDir : children){
				boolean success = deleteDir(new File(dir,childrenDir)); //递归删除
				if (!success) {
					System.out.println("失败");
					return false;
				}
			}
		}
		//此时为空，可以删除
		return dir.delete();
	}

	@Override
	public void renameFile(String folderPath, String oldName, String newName) {
		//新的文件名和以前文件名不同时,才有必要进行重命名
		if(!"".equals(newName.trim()) && !oldName.equals(newName)){
			//获得后缀名
			String fileType = oldName.substring(oldName.indexOf('.'));
			File oldFile = new File(folderPath + "/" + oldName);
			File newFile = new File(folderPath + "/" + newName + fileType);
			if(!oldFile.exists()){
				System.out.println("文件不已存在");
			}
			if(newFile.exists()){//若在该目录下已经有一个文件和新文件名相同，则不允许重命名				
				System.out.println("文件文件已存在");
			}else{
				oldFile.renameTo(newFile);
			}
		}else{
			System.out.println("文件已存在");
		}
		
	}

	@Override
	public void downFileByPath(String downPath, HttpServletResponse response) {
		try {
			File file = new File(downPath);//
			String fileName = file.getName();// 获取文件名称
			InputStream fis = new BufferedInputStream(new FileInputStream(downPath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();
			// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
			response.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.replaceAll(" ", "").getBytes("utf-8"),"iso8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream os = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			os.write(buffer);// 输出文件
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	

}
