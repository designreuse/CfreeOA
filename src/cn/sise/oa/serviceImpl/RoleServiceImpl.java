package cn.sise.oa.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sise.oa.base.DaoSupportImpl;
import cn.sise.oa.domain.Role;
import cn.sise.oa.service.RoleService;


@Service
@Transactional
public class RoleServiceImpl extends DaoSupportImpl<Role> implements RoleService {


}
