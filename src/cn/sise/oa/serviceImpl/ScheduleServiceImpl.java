package cn.sise.oa.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sise.oa.base.DaoSupportImpl;
import cn.sise.oa.domain.Schedule;
import cn.sise.oa.service.ScheduleService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class ScheduleServiceImpl extends DaoSupportImpl<Schedule> implements ScheduleService{

}
