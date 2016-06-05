package cn.sise.oa.util;


import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.opensymphony.xwork2.ActionContext;

import cn.sise.oa.domain.User;

@SuppressWarnings("serial")
public class ManagerTaskHander implements TaskListener{

	public static String nextUserName;

	public void notify(DelegateTask arg0) {
		//设置任务执行者
		arg0.setAssignee(nextUserName);
		
	}
	
/*	@Override
	public void notify(DelegateTask delegateTask) {
		
		//设置任务执行者
		delegateTask.setAssignee(nextUserName);
		
		*//**从新查询当前用户，再获取当前用户对应的领导*//*
		User sessionUser = (User) ActionContext.getContext().getSession().get("user");
		//使用当前用户名查询用户的详细信息
		//从web中获取spring容器
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
		UserService userService =  (UserService) ac.getBean("userService");
		User user = userService.getById(sessionUser.getId());
		//设置个人任务的办理人
		if(user.getParent() != null)
			delegateTask.setAssignee(user.getParent().getName());
		if(sessionUser.getParent() != null)
			delegateTask.setAssignee(sessionUser.getParent().getName());
	}*/


	
}
