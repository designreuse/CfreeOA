package cn.sise.oa.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * 文件在线预览类
 * @author yzh
 *
 */
public class OfficeConvert
{
	private File sourceFile;		//转换源文件
	private File pdfFile;			//PDF目标文件
	private File swfFile;			//SWF目标文件
	private String projectPath;		//web项目路径
	private int exceptionStatus=0;    //记录异常状态，初始值为0代表正常，1代表不正常

	
	public OfficeConvert(String projectPath,File sourceFile,String fileName){
		this.projectPath=projectPath;
		this.sourceFile=sourceFile;
		this.pdfFile=new File(projectPath+"/file/swf/"+fileName+".pdf");//
		this.swfFile=new File(projectPath+"/file/swf/"+fileName+".swf");//
	}

	
	public int getExceptionStatus() {
		return exceptionStatus;
	}


	public void setExceptionStatus(int exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}


	/**
	 *@作用：office文件格式转换
	 *
	 */
	public void convert(){
		office2pdf();
		pdf2swf();
	}

    /**
	 *@作用：office->pdf
	 *
	 */
  	private void office2pdf(){
  		if (!pdfFile.getParentFile().exists()) {
			pdfFile.getParentFile().mkdirs();
		}
  		if(sourceFile.exists()) {
  			if(!pdfFile.exists()) {
				System.out.println("源文件正在转换成pdf文件，请稍后。。。");
				//开启OpenOffice服务
				startupOpenOffice();
  				OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);

  				try {
					//打开与OpenOffice服务的连接
  					connection.connect();
  					DocumentConverter converter = new OpenOfficeDocumentConverter(connection);   
					//转换文件office->pdf
  					converter.convert(sourceFile, pdfFile);
  					pdfFile.createNewFile();
  					connection.disconnect();  
  					System.out.println("office->pdf 转换成功！");
  				} catch (java.net.ConnectException e) {
  					exceptionStatus=1;//记录异常状态
  					e.printStackTrace();
  					System.out.println("OpenOffice服务连接异常");
  				} catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
  					exceptionStatus=1;//记录异常状态
  					e.printStackTrace();
  					System.out.println("读取文件失败");
  				} catch (Exception e){
  					exceptionStatus=1;//记录异常状态
  					e.printStackTrace();
  					System.out.println("doc->pdf转换出错");
  				}

  			} else {
  				System.out.println("源文件已转换为PDF，无需再次转换");
  			}
  		} else {
  			System.out.println("源文件不存在，无法转换");
  		} 
  	}
	/**
	 *@作用：pdf-swf
	 *
	 */
	private void pdf2swf(){
		Runtime r = Runtime.getRuntime();
		if(!swfFile.exists()){
			if(pdfFile.exists()) {
				try {
					//Process p = r.exec("E:\\Project\\Java\\JavaEE\\Tools\\SWFTools\\gpdf2swf.exe " + pdfFile.getPath() + " -o " + swfFile.getPath() + " -T 9");
					Process p = r.exec(projectPath+"/Tools/SWFTools/pdf2swf.exe "+" -t \""+ pdfFile.getPath() + "\" -s flashversion=9 -o \""+ swfFile.getPath() + "\"");  
					p.waitFor();
					swfFile.createNewFile();
					System.out.println("office->pdf 转换成功！");
					//把pdf中间文件删除
					if(pdfFile.exists()) {
						pdfFile.delete();
					}
				} catch (Exception e) {
					exceptionStatus=1;//记录异常状态
					e.printStackTrace();
					System.out.println("pdf-swf转换出错");
				}
			} else {
				System.out.println("PDF文件不存在，无法转换");
			}
		} else {
			System.out.println("PDF文件已经转为SWF文件，无需再次转换");
		}
	}

	/**
	 *@作用：开启OpenOffice服务
	 *
	 */
	private void startupOpenOffice(){
		System.out.println("正在开启OpenOffice服务。。。");
		//启动OpenOffice服务命令
		String command=projectPath+"/Tools/OpenOffice 4/program/soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard";
		try {
			//开启服务
			Process process=Runtime.getRuntime().exec(command);
			System.out.println("OpenOffice服务已开启");
		} catch (IOException e) {
			exceptionStatus=1;//记录异常状态
			System.out.println("OpenOffice服务开启失败");
		}
	}
	
	public static void deleteSWF(String fileName){
		HttpServletRequest request = ServletActionContext.getRequest();		
		//得到当前项目在服务器中的位置,把所有\替换成/
		String fileSavePath = request.getRealPath("").replaceAll("\\\\", "/") + "/file/swf/";
		File fileSWF = new File(fileSavePath+fileName + ".swf");
		File filePDF = new File(fileSavePath+fileName + ".pdf");
		if(fileSWF != null && fileSWF.isFile()){
			fileSWF.delete();
		}
		if(filePDF != null && filePDF.isFile()){
			filePDF.delete();
		}
	}
	
}