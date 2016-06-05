package cn.sise.oa.action;

import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sise.oa.base.BaseAction;
import cn.sise.oa.domain.Role;
import cn.sise.oa.domain.User;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class PersonConfigAction extends BaseAction<User>{

	private String newPassword;
	private String confirmPassword;
	private String oldPassword;
	private Integer departmentId;
	
	/** 
	 *修改个人信息界面
	 *  
	 *  */
	@SkipValidation
	public String editUsrInfoUI(){		
		//从session获取用户
		User user = (User) ActionContext.getContext().getSession().get("user");
		//重新查询用户
		User u = userService.getById(user.getId());
		Set<Role> roles = u.getRoles();
		String roleNames = "";
		int i = 0 ;
		for(Role role : roles){//遍历岗位，并把最后一个逗号去掉
			roleNames = roleNames+
					(i>0 ? "," : "")					
					+role.getName();
			i++;
		}
		//将用户放进值栈顶
		ActionContext.getContext().getValueStack().push(u);		
		ActionContext.getContext().put("roleNames",roleNames);		
		return "editUsrInfoUI";
	}
	
	/**
	 * 修改个人信息
	 * @return
	 */
	public String editUsrInfo(){
		
		// 1，从数据库中取出原对象
		User user = userService.getById(model.getId());
		
		// 2，设置要修改的属性
		user.setLoginName(model.getLoginName());
		user.setName(model.getName());
		user.setGender(model.getGender());
		user.setPhoneNumber(model.getPhoneNumber());
		user.setEmail(model.getEmail());
		user.setDescription(model.getDescription());
		
		// >> 设置所属部门
		user.setDepartment(departmentService.getById(departmentId));
		// >> 设置关联的岗位
		Set<Role> roleList = user.getRoles();
		user.setRoles(roleList);
		
		// 3，更新到数据库
		userService.update(user);
		
		ActionContext.getContext().put("success", "修改成功！");
		
		return "editUsrInfoUI";
	}
	
	/**
	 * 修改密码界面
	 * @return
	 */
	@SkipValidation
	public String editUserPasswordUI(){				
		return "editUserPasswordUI";
	}
	
	
	/**
	 * 修改密码
	 * @return
	 */
	@SkipValidation
	public String editUserPassword(){
		User user = (User) ActionContext.getContext().getSession().get("user");		
		String md5OldPassword = DigestUtils.md5Hex(oldPassword);
		if(user.getPassword().equals(md5OldPassword)){ //原密码与用户密码是否相同
			if(!"".equals(newPassword.trim()) //
					&& !"".equals(confirmPassword.trim())//
					&& newPassword.equals(confirmPassword)){ //新密码与确认密码
				//加密保存
				user.setPassword(DigestUtils.md5Hex(newPassword));
				userService.update(user);
				ActionContext.getContext().put("success", "修改成功！");
				return "editUserPasswordUI";
			}
			ActionContext.getContext().put("error", "确认密码有误！");
			return "editUserPasswordUI";
		}
		ActionContext.getContext().put("error", "密码错误，请重新输入！");
		return "editUserPasswordUI";
	}

	//=================================
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	
}
