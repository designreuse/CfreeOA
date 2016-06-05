package cn.sise.oa.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sise.oa.action.bean.StatisticsDepart;
import cn.sise.oa.base.BaseAction;
import cn.sise.oa.domain.Department;
import cn.sise.oa.domain.Role;
import cn.sise.oa.domain.User;
import cn.sise.oa.util.DepartmentUtils;
import cn.sise.oa.util.QueryHelper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;


@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> implements Preparable {

	private Integer departmentId;
	private Integer[] roleIds;
	private Integer parentId;
	private String departmentname;
	//private final Log logger = LogFactory.getLog(getClass());
	//private static Logger logger = Logger.getLogger(UserAction.class);
	/** 
	 * 列表 
	 * */
	@SkipValidation
	public String list() throws Exception {
		// 准备分页信息
		new QueryHelper(User.class, "u")//
			.preparePageBean(userService, pageNum, 8);

		return "list";
	}
	
	/** 
	 * 根据部门名称查询用户联系信息 
	 * */
	@SkipValidation
	public String findConnection() throws Exception {
		// 准备分页信息		
		departmentname = new String(departmentname.getBytes("iso-8859-1"),"utf-8");
		System.out.println("--------"+departmentname);		
		new QueryHelper(User.class, "u")//
			.addCondition("u.department.name=?", departmentname)
			.preparePageBean(userService, pageNum, 10);
		return "findConnection";
	}

	/**
	 * 部门人数统计
	 * @return
	 */
	@SkipValidation
	public String statisticsDepart(){
		List<StatisticsDepart> statisticsDeparts = userService.statistics();
		ActionContext.getContext().put("statisticsDeparts", statisticsDeparts);
		
		return "statisticsDepart";
	}
	
	/** 删除 */
	@SkipValidation
	public String delete() throws Exception {
		userService.delete(model.getId());
		
		return "toList";
	}

	/** 添加页面 */
	@SkipValidation
	public String addUI() throws Exception {

		// 准备数据, departmentList
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);

		// 准备数据, roleList
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);

		//准备数据,userList
		List<User> userList = userService.findAll();
		ActionContext.getContext().put("userList", userList);
		
		return "saveUI";
	}

	/** 添加 */
	
	public String add() throws Exception {
		// 封装到对象中（当model是实体类型时，也可以使用model，但要设置未封装的属性）		
		// >> 设置所属部门
		model.setDepartment(departmentService.getById(departmentId));
		// >> 设置关联的岗位
		List<Role> roleList = roleService.getByIds(roleIds);
		model.setRoles(new HashSet<Role>(roleList));
		//>> 设置上级
		model.setParent(userService.getById(parentId));
		
		// >> 设置默认密码为1234（要使用MD5摘要）
		String md5Digest = DigestUtils.md5Hex("1234");
		model.setPassword(md5Digest);

		// 保存到数据库
		userService.save(model);		
		
		return "toList";
	}

	/** 修改页面 */
	@SkipValidation
	public String editUI() throws Exception {
		// 准备数据, departmentList
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		
		// 准备数据, roleList
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);

		//准备数据,userList
		List<User> userList = userService.findAll();
		ActionContext.getContext().put("userList", userList);
		
		// 准备回显的数据
		User user = userService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(user);
		//准备系统角色
		if (user.getDepartment() != null) {
			departmentId = user.getDepartment().getId();
		}
		if (user.getRoles() != null) {
			roleIds = new Integer[user.getRoles().size()];
			int index = 0;
			for (Role role : user.getRoles()) {
				roleIds[index++] = role.getId();
			}
		}
		//准备上级
		if(user.getParent() != null){
			parentId = user.getParent().getId();
		}
		
		return "saveUI";
	}

	/** 修改 */
	public String edit() throws Exception {
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
		List<Role> roleList = roleService.getByIds(roleIds);
		user.setRoles(new HashSet<Role>(roleList));				
		//>> 设置上级
		user.setParent(userService.getById(parentId));

		// 3，更新到数据库
		userService.update(user);

		return "toList";
	}

	/** 初始化密码为1234 */
	@SkipValidation
	public String initPassword() throws Exception {
		// 1，从数据库中取出原对象
		User user = userService.getById(model.getId());		
		
		// 2，设置要修改的属性（要使用MD5摘要）
		String md5Digest = DigestUtils.md5Hex("1234");
		user.setPassword(md5Digest);				
		
		// 3，更新到数据库
		userService.update(user);				
		
		return "toList";
	}

	/** 登录页面 */
	@SkipValidation
	public String loginUI() throws Exception {
		return "loginUI";
	}

	/** 登录 */
	@SkipValidation
	public String login() throws Exception {
		User user = userService.findByLoginNameAndPassword(model.getLoginName(), model.getPassword());
		if (user == null) {
			addFieldError("login", "用户名或密码不正确！");
			return "loginUI";
		} else {
			// 登录用户
			ActionContext.getContext().getSession().put("user", user);
			return "toIndex";
		}
		
		//logger.info("登录");
		//System.out.println("--------------");
	}

	/** 注销 */
	@SkipValidation
	public String logout() throws Exception {
		ActionContext.getContext().getSession().remove("user");
		return "logout";
	}

	/**
	 *校验失败时，返回视图前执行的函数
	 * 一般是准备数据
	 */
	@Override
	public void prepare() throws Exception {
		addUI();		
	}
	
	
	public void checkUser() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String loginName = request.getParameter("loginName");
		boolean haveUser = userService.findUserByLoginName(loginName);
		PrintWriter printWriter = response.getWriter();
		String msg = "";
		if(haveUser){
			//此登录名存在
			msg = "exist";
			printWriter.write(msg);
		}else{
			msg = "notExist";
			printWriter.write(msg);
		}
	}
	
	// ---
	public Integer[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	
	

}
