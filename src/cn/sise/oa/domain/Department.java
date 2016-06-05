package cn.sise.oa.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 部门详细信息
 * @author yzh
 *
 */
public class Department implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4342154371600375250L;
	private Integer id;
	private String name;
	private String description;

	private Set<User> users = new HashSet<User>();
	private Department parent; //上级部门
	private Set<Department> children = new HashSet<Department>(); //下级部门
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public Set<Department> getChildren() {
		return children;
	}

	public void setChildren(Set<Department> children) {
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
