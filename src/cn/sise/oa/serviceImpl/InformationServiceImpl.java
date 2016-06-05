package cn.sise.oa.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sise.oa.base.DaoSupportImpl;
import cn.sise.oa.domain.Information;
import cn.sise.oa.domain.User;
import cn.sise.oa.service.InformationService;

@Service
@Transactional
public class InformationServiceImpl extends DaoSupportImpl<Information> implements InformationService{

	@Override
	public void deleteByIds(Integer[] ids) {	
		if (ids != null && ids.length != 0) {
			for(Integer id : ids){
				delete(id);
			}
		} 
	}

	@Override
	public int countUnread(User user) { 
		Integer sum = null;
		
		sum =  (Integer) getSession().createQuery(//
				"SELECT COUNT(i) FROM Information i WHERE i.senderId=? AND i.status=?")//
				.setParameter(0, user.getId())//
				.setParameter(1, 0)
				.uniqueResult();
		
		if(sum == null){
			sum = 0;
		}
		
		return sum;
	}

}
