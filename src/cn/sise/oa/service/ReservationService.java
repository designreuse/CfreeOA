package cn.sise.oa.service;

import java.util.List;

import cn.sise.oa.base.DaoSupport;
import cn.sise.oa.domain.MeetingRoom;
import cn.sise.oa.domain.Reservation;

public interface ReservationService extends DaoSupport<Reservation> {

	List<Reservation> findAllByMeetRoomObj(MeetingRoom meetRoom);

	List<Reservation> findAllByMeetRoomObjAndState(MeetingRoom meetingRoom);

}
