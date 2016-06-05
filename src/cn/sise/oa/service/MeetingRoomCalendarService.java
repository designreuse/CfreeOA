package cn.sise.oa.service;

import java.util.List;

import cn.sise.oa.action.bean.CalendarOV;
import cn.sise.oa.base.DaoSupport;
import cn.sise.oa.domain.Reservation;

public interface MeetingRoomCalendarService extends DaoSupport<Reservation> {

	List<CalendarOV> transformOV(List<Reservation> reservations);

}
