package cn.sise.oa.service;

import cn.sise.oa.base.DaoSupport;
import cn.sise.oa.domain.Information;
import cn.sise.oa.domain.User;

public interface InformationService extends DaoSupport<Information>{
	
	//批量删除
	public void deleteByIds(Integer[] ids);

	/**
	 * 查询未读消息数量
	 * @param user
	 * @return 未读消息个数
	 */
	public int countUnread(User user);
}
