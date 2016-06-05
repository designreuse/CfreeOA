package cn.sise.oa.action;

import java.util.HashSet;
import java.util.List;

import org.activiti.engine.identity.Group;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sise.oa.base.BaseAction;
import cn.sise.oa.domain.Privilege;
import cn.sise.oa.domain.Role;
import cn.sise.oa.util.QueryHelper;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

	private Integer[] privilegeIds;

	/** 列表 */
	public String list() throws Exception {		
		new QueryHelper(Role.class,"r")//
		.preparePageBean(roleService, pageNum, 8);
		
		return "list";
	}

	/** 删除 */
	public String delete() throws Exception {
		roleService.delete(model.getId());
		//删除activiti的组
		identityService.deleteGroup(model.getId().toString());
		return "toList";
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		return "saveUI";
	}

	/** 添加 */
	public String add() throws Exception {
		//将用户组保存进数据库(返回生成的主键)
		Integer id = roleService.save(model);
		//将用户组信息保存进activiti用户组中
		Group group = identityService.newGroup(id.toString()); //new一个组构造方法必须指定主键
		group.setName(model.getName());
		identityService.saveGroup(group); //保存
		return "toList";
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		// 准备回显的数据
		Role role = roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role);

		return "saveUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		roleService.update(model);

		return "toList";
	}

	/** 设置权限页面 */
	public String setPrivilegeUI() throws Exception {
		// 准备回显的数据
		Role role = roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role);

		if (role.getPrivileges() != null) {
			privilegeIds = new Integer[role.getPrivileges().size()];
			int index = 0;
			for (Privilege priv : role.getPrivileges()) {
				privilegeIds[index++] = priv.getId();
			}
		}

		// 准备数据 privilegeList
		List<Privilege> privilegeList = privilegeService.findAll();
		ActionContext.getContext().put("privilegeList", privilegeList); 

		return "setPrivilegeUI";
	}

	/** 设置权限 */
	public String setPrivilege() throws Exception {
		// 1，从数据库中获取原对象
		Role role = roleService.getById(model.getId());

		// 2，设置要修改的属性
		List<Privilege> privilegeList = privilegeService.getByIds(privilegeIds);
		role.setPrivileges(new HashSet<Privilege>(privilegeList));

		// 3，更新到数据库
		roleService.update(role);

		return "toList";
	}

	public Integer[] getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(Integer[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

	// ---



}
