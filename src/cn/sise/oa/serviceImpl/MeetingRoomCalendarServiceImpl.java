package cn.sise.oa.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sise.oa.action.bean.CalendarOV;
import cn.sise.oa.base.DaoSupportImpl;
import cn.sise.oa.domain.Reservation;
import cn.sise.oa.service.MeetingRoomCalendarService;



@Service
@Transactional
public class MeetingRoomCalendarServiceImpl extends DaoSupportImpl<Reservation> implements MeetingRoomCalendarService{

	@Override
	public List<CalendarOV> transformOV(List<Reservation> reservations) {
		List<CalendarOV> calendarOVs = new ArrayList<CalendarOV>();
		for(Reservation reservation : reservations){
			CalendarOV calendarOV = new CalendarOV();
			
			String title = reservation.getTitle();
			calendarOV.setId(reservation.getId());
			calendarOV.setTitle(title);
			calendarOV.setStart(reservation.getStartTime().toString());
			calendarOV.setEnd(reservation.getEndTime().toString());
			String color = "";
			int state = reservation.getState();
			if(0 == state){
				color = "#EFD7A7";
			}else if(1 == state){
				color = "#51C332";
			}
			calendarOV.setColor(color);
			calendarOV.setDesc(reservation.getSummary());
			calendarOV.setPrincipal(reservation.getUser().getName());
			calendarOV.setFeedback(reservation.getFeedback());
			
			calendarOVs.add(calendarOV);
		}
		return calendarOVs;
	}


}
