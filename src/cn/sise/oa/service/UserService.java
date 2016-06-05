package cn.sise.oa.service;

import java.util.List;

import org.activiti.engine.identity.Group;

import cn.sise.oa.action.bean.StatisticsDepart;
import cn.sise.oa.base.DaoSupport;
import cn.sise.oa.domain.User;


public interface UserService extends DaoSupport<User> {

	/**
	 * 根据登录名与密码查询用户
	 * 
	 * @param loginName
	 * @param password
	 *            明文密码
	 * @return
	 */
	User findByLoginNameAndPassword(String loginName, String password);

	/**
	 * 绑定activiti 用户与组的关系
	 * @param userId
	 * @param roleIds
	 */
	void createUserAndGroupsShip(String userId, Integer[] roleIds);

	/**
	 * 解除activiti 用户与组的关系
	 * @param userId
	 * @param groups
	 */
	void deleteUserAndGroupsShip(String userId, List<Group> groups);

	/**
	 * 通过部门名称查询在此部门下的所有用户
	 * @param departmentName
	 * @return
	 */
	List<User> findAllByDepartmentName(String departmentName);

	/**
	 * 统计每个部门下的人数
	 * @return
	 */
	List<StatisticsDepart> statistics();

	/**
	 * 通过登录名查询用户
	 * @param nextUserName
	 * @return
	 */
	User getByLoginName(String nextUserName);

	/**
	 * 查询是否存在此登录名
	 * @param loginName
	 * @return
	 */
	boolean findUserByLoginName(String loginName);

}
