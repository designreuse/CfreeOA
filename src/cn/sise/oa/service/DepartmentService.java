package cn.sise.oa.service;

import java.util.List;

import cn.sise.oa.base.DaoSupport;
import cn.sise.oa.domain.Department;

public interface DepartmentService extends DaoSupport<Department>{

	/**
	 * 查询顶级部门列表
	 * 
	 * @return
	 */
	List<Department> findTopList();

	/**
	 * 查询子部门列表
	 * 
	 * @param parentId
	 * @return
	 */
	List<Department> findChildren(Integer parentId);

}
