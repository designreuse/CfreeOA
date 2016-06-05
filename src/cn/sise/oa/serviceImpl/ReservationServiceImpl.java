package cn.sise.oa.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sise.oa.base.DaoSupportImpl;
import cn.sise.oa.domain.MeetingRoom;
import cn.sise.oa.domain.Reservation;
import cn.sise.oa.service.ReservationService;

@Service
@Transactional
public class ReservationServiceImpl extends DaoSupportImpl<Reservation> implements ReservationService {

	@Override
	public List<Reservation> findAllByMeetRoomObj(MeetingRoom meetRoom) {

		return getSession().createQuery(//
				"FROM Reservation r WHERE r.meetingRoom=?")//
				.setParameter(0, meetRoom)//
				.list();
	}

	@Override
	public List<Reservation> findAllByMeetRoomObjAndState(
			MeetingRoom meetingRoom) {
		
		return getSession().createQuery(//
				"FROM Reservation r WHERE r.meetingRoom=? AND r.state=?")//
				.setParameter(0, meetingRoom)//
				.setParameter(1, 0)//
				.list();
	}
	

}
