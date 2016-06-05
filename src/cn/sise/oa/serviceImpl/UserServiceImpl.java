package cn.sise.oa.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sise.oa.action.bean.StatisticsDepart;
import cn.sise.oa.base.DaoSupportImpl;
import cn.sise.oa.domain.User;
import cn.sise.oa.service.UserService;

@Service
@Transactional
public class UserServiceImpl extends DaoSupportImpl<User> implements UserService {

	@Resource
	private IdentityService identityService;
	
	public User findByLoginNameAndPassword(String loginName, String password) {
		// 使用密码的MD5摘要进行对比
		String md5Digest = DigestUtils.md5Hex(password);
		return (User) getSession().createQuery(//
				"FROM User u WHERE u.loginName=? AND u.password=?")//
				.setParameter(0, loginName)//
				.setParameter(1, md5Digest)//
				.uniqueResult();
	}

	//设置activiti中的用户与组的关系
	public void createUserAndGroupsShip(String userId, Integer[] roleIds) {
		for(Integer roleId : roleIds){
			identityService.createMembership(userId, roleId.toString());
		}
		
	}

	//解除绑定
	public void deleteUserAndGroupsShip(String userId, List<Group> groups) {
		for(Group group : groups){
			identityService.deleteMembership(userId, group.getId());
		}
		
	}

	@Override
	public List<User> findAllByDepartmentName(String departmentName) {
		List<User> users = getSession().createQuery(//
							"FROM User u WHERE u.department.name=?")//
							.setParameter(0, departmentName)//
							.list();
		return users;
	}

	@Override
	public List<StatisticsDepart> statistics() {
		List<Object[]> objects = getSession().createQuery(//
				"SELECT u.department.name, count(u.id) FROM User u "//
				+ "GROUP BY u.department.name")//
				.list();
		List<StatisticsDepart> departs  = new ArrayList<StatisticsDepart>();
		
		for(Object[] obj : objects){
			StatisticsDepart depart = new StatisticsDepart();
			depart.setDepartName(obj[0].toString());
			depart.setAmount(Integer.valueOf(obj[1].toString()));
			departs.add(depart);
		}
		
		return departs;
	}

	
	/**
	 * 通过登录名查询用户
	 */
	@Override
	public User getByLoginName(String nextUserName) {		
		return (User) getSession().createQuery(//
				"FROM User u WHERE u.loginName=?")//
				.setParameter(0, nextUserName)//				
				.uniqueResult();
	}

	@Override
	public boolean findUserByLoginName(String loginName) {
		User user = (User) getSession().createQuery(//
				"FROM User u WHERE u.loginName=?")//
				.setParameter(0, loginName)//				
				.uniqueResult();
		
		if(user != null){
			return true;
		}
		return false;
	}

}
