package cn.sise.oa.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sise.oa.action.bean.CalendarOV;
import cn.sise.oa.base.DaoSupportImpl;
import cn.sise.oa.domain.Calendar;
import cn.sise.oa.domain.User;
import cn.sise.oa.service.CalendarService;

@Service
@Transactional
public class CalendarServiceImpl extends DaoSupportImpl<Calendar> implements CalendarService {

	@Override
	public List<Calendar> findByUser(User user) {
		return  getSession().createQuery(//
				"FROM Calendar c WHERE c.user=?")//
				.setParameter(0, user)//
				.list();
	}

	@Override
	public List<CalendarOV> transformToOV(List<Calendar> calendars) {
		List<CalendarOV> calendarOVs = new ArrayList<CalendarOV>();
		for(Calendar calendar : calendars){
			CalendarOV calendarOV = new CalendarOV();
			calendarOV.setAllDay(calendar.getAllDay());
			calendarOV.setColor(calendar.getColor());
			calendarOV.setEnd(calendar.getEnd());
			calendarOV.setStart(calendar.getStart());
			calendarOV.setTitle(calendar.getTitle());
			calendarOVs.add(calendarOV);
		}
		return calendarOVs;
	}

}
