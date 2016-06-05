package cn.sise.oa.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sise.oa.base.DaoSupportImpl;
import cn.sise.oa.domain.MeetingRoom;
import cn.sise.oa.domain.User;
import cn.sise.oa.service.MeetingRoomService;



@Service
@Transactional
public class MeetingRoomServiceImpl extends DaoSupportImpl<MeetingRoom> implements MeetingRoomService {


}
