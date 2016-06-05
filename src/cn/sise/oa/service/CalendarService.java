package cn.sise.oa.service;

import java.util.List;

import cn.sise.oa.action.bean.CalendarOV;
import cn.sise.oa.base.DaoSupport;
import cn.sise.oa.domain.Calendar;
import cn.sise.oa.domain.User;

public interface CalendarService extends DaoSupport<Calendar>{

	List<Calendar> findByUser(User user);

	List<CalendarOV> transformToOV(List<Calendar> calendars);

}
