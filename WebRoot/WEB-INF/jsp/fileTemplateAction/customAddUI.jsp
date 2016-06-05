<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
	<link rel="stylesheet" type="text/css" href="style/formBulider/form-builder.min.css">
	<link rel="stylesheet" type="text/css" href="style/formBulider/form-render.min.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/pageCommon.css" />
		
  	<script type="text/javascript" src="script/jquery/jquery-2.2.4.min.js"></script>
	<script type="text/javascript" src="script/jquery/jquery-ui.min.js"></script>
	
	<script type="text/javascript" src="script/artdialog/artDialog.js?skin=blue"></script>
	<script type="text/javascript" src="script/artdialog/jquery.artDialog.js"></script>
	<script type="text/javascript" src="script/artdialog/plugins/iframeTools.js"></script>
	
	
	<script type="text/javascript" src="script/formBuilder/form-builder.min.js"></script>
	<script type="text/javascript" src="script/formBuilder/form-render.min.js"></script>
	<script type="text/javascript" src="script/formBuilder/formBuilderConfig.js"></script>
	
<%-- 	<script type="text/javascript" src="script/formBuilder/formBuild.js"></script> --%>
	
  </head>
  
  <body>
  	<s:form action="fileTemplate_customAdd" name="cdForm" id="cdForm" method="post" enctype="multipart/form-data" onsubmit="save()">
		<div id="zhongxin">
		<table width="90%">
			<tr>
				<td width="20%" align="right">标题：</td>
				<td><s:textfield type="text" name="name" style="width: 95%;height: 32px;" class="form-control" placeholder="这里输入标题" /></td>
			</tr>
			<tr>
                <td width="20%" align="right">所用流程：</td>
                 <td><!-- <div class="form-group"> -->
                  	 	<s:select name="flowKey" class="form-control" style="width: 95%;height: 32px;"
                   		list="#processOVs" listKey="processDedinitionKey" listValue="deploymentName" />
                     <!-- </div> -->
				</td>
            </tr>			
			<tr>
				<td width="20%" align="right">简介：</td>
				<td>
					<s:textarea rows="8" cols="8" name="summary" style="width: 95%;height: 120px;" class="form-control"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<!-- <a class="btn btn-mini btn-primary" onclick="save();">保存</a> -->
					<button type="submit" class="btn btn-default otherButton"  style="height:30px;margin-left:9%">保存</button>
		            <a class="btn btn-default newButton" href="javascript:history.go(-1);" style="height:30px;margin-left:8px">返回</a>				
					
				</td>
			</tr>
		</table>
		<s:textfield type="hidden" id="code" name="code" />
		<s:textfield type="hidden" id="add_html" name="html" />
		<s:textfield type="hidden" id="filed" name="filed" />
		<s:textfield type="hidden" id="text" name="text" />
		<s:textfield type="hidden" id="flowVar" name="flowVar" />
		<div style="display: none;" id="add_my_form">
		</div>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="${pageContext.request.contextPath}/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</s:form>
  
  
  <hr style="border: 1px dashed #CCCCCC;"/>
  
 	<!--  编辑的视图 -->
	<div id="edit-form">
		 <textarea id="fb-template">
		 	${code }
		 </textarea>
	</div>
	<!-- 生成表单的代码 -->
	<div id="rendered-form">
		<form action="#" id="add_save_html"></form>
	</div>
  
   
  </body>

<script type="text/javascript">

			function save(){
				var bt = $("#bt").val();
				//var btnum = getByteLen(bt);
				
				if(bt == ""){
					art.dialog("请输入标题。");
					return false;
				}else{					
					//操作html
					var fbTemplate = document.getElementById('fb-template'),					
				    formContainer = document.getElementById('rendered-form'),				    				   				    
				    formRenderOpts = {
				      container: $('form', formContainer)
				    };
				    
				    
			    	$(fbTemplate).formRender(formRenderOpts);
					$("#add_html").val($("#add_save_html").html());
					$("#add_my_form").html($("#add_save_html").html());
					
				    
					//获取全部
					var fileds = [];
					var texts = [];
					$("form#add_save_html label[for]").each(function(){						
							texts.push($(this).html());
							fileds.push($(this).attr("for"));
					});
							
					//获取用户设置的流程参数（name自定义）					
					var flowVars = [];
					//流程需要设置变量
					$("form#add_save_html label[for]").each(function(){						
						if($(this).attr("for").indexOf('-')==-1){										  
							flowVars.push($(this).attr("for"));
						}
					});
					
					$("input#filed").val(fileds.join(","));									
					$("input#text").val(texts.join(","));									
					$("input#flowVar").val(flowVars.join(","));
					$("input#code").val($("textarea#fb-template").val());
						
					return true;									
				}
				
			}			
		</script>

</html>
