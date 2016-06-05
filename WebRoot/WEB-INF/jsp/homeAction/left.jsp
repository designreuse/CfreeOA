<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>OA - 导航菜单</title>
<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
<script type="text/javascript">
function changeColor(id){
	var cs = document.getElementById('accordion').childNodes;
	for(var i = 0; i < cs.length; i++)
	{
	 	if(cs[i].id!=undefined)
		{
	 		var csid = document.getElementById(cs[i].id);
	 		if ('panel'+id != cs[i].id.toString()){
				csid.className="panel myPanel";
			} else {
				if (csid.className == "panel myPanelChange")
					csid.className="panel myPanel";
				else
					csid.className="panel myPanelChange";
			}
		}
	}
}

</script>
</head>

<body style="margin: 0;background: #4d4d4d;">
   <div class="panel-group " id="accordion">
  	 
  	 <s:iterator value="#application.topPrivilegeList">
     <s:if test="#session.user.hasPrivilegeByName(name)">
     <!-- 菜单显示 -->
     <div class="panel myPanel " id="panel${id }">     
       <!-- 显示一级菜单 -->
       <div class="panel-heading">
         <a data-parent="#accordion" data-toggle="collapse" href="#collapseOne${id }" onclick="changeColor(${id })">
           <h4 class="panel-title">
               <!----导航名称和图标----->
               <img src="${pageContext.request.contextPath}/style/images/MenuIcon/${id }.gif" class="Icon"/>
               <span class="MenuFontColor">${name }</span>
           </h4>
         </a>
       </div>
       
       <!-- 显示二级菜单 -->
       <div id="collapseOne${id }" class="panel-collapse collapse">
         <div class="panel-body" style="padding:0">
           <!-----内容放置----->
           <ul class="MenuLevel2">
       			<s:iterator value="children">
      			<s:if test="#session.user.hasPrivilegeByName(name)">
                <li class="level2">
                    <a href="${pageContext.request.contextPath }${url}.action" target="right"><div class="level2Style">${name }</div></a>
                </li>
               </s:if>
               </s:iterator>
            </ul>           
         </div>
       </div>
     </div>
  	</s:if>
  	</s:iterator>
  	
  	
   </div>
  
     
</body>
</html>
