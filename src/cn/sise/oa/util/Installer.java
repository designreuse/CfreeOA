package cn.sise.oa.util;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.sise.oa.domain.Privilege;
import cn.sise.oa.domain.User;

@Component
public class Installer {

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 执行安装
	 */
	@Transactional
	public void install() {
		Session session = sessionFactory.getCurrentSession();

		// ==============================================================
		// 保存超级管理员用户
		User user = new User();
		user.setLoginName("admin");  //登录名
		user.setName("超级管理员");
		user.setPassword(DigestUtils.md5Hex("admin"));  //登录密码
		session.save(user); // 保存

		// ==============================================================
		// 保存权限数据
		Privilege menu, menu1, menu2, menu3, menu4, menu5, menu6;
		
						
		//================流程管理权限
		menu = new Privilege("流程管理", null, null);
		menu1 = new Privilege("流程定义部署", "/process_list", menu);	
		menu2 = new Privilege("流程模型管理", "/model_modelList", menu);

		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		
		/*session.save(new Privilege("流程定义列表", "/process_list", menu1));
		session.save(new Privilege("流程定义删除", "/process_delete", menu1));*/
		session.save(new Privilege("流程定义部署", "/process_deploy", menu1));	
		
		
		
		//=================表单管理
		menu = new Privilege("表单管理", null, null);
		menu1 = new Privilege("申请模板管理", "/fileTemplate_list", menu);
		
		session.save(menu);
		session.save(menu1);
		
		session.save(new Privilege("文档表单模板添加", "/fileTemplate_add", menu1));
		session.save(new Privilege("文档表单模板修改", "/fileTemplate_edit", menu1));
		session.save(new Privilege("自定义表单添加", "/fileTemplate_customAdd", menu1));
		session.save(new Privilege("自定义表单修改", "/fileTemplate_customEdit", menu1));
		session.save(new Privilege("表单模板删除", "/fileTemplate_delete", menu1));
		
		//=================申请管理
		menu = new Privilege("申请管理", null, null);		
		menu1 = new Privilege("起草申请", "/myApplication_templateList", menu);
		menu2 = new Privilege("个人审批任务", "/task_list", menu);
		menu3 = new Privilege("个人申请查询", "/myApplication_list", menu);				
				
		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		session.save(menu3);		
		
		/*session.save(new Privilege("提交申请", "/myApplication_start", menu1));*/
		
        /*session.save(new Privilege("所有申请列表", "/myApplication_list", menu3));*/
		session.save(new Privilege("申请删除-管理员", "/myApplication_delete", menu3));
		session.save(new Privilege("申请删除-个人", "/myApplication_hide", menu3));
		session.save(new Privilege("取消申请", "/myApplication_cancelApplication", menu3));				
		
		//===============会议室预约
		menu = new Privilege("会议室预约", null, null);
		/*menu1 = new Privilege("会议室预约管理", "/reservation_list", menu);
		menu2 = new Privilege("会议室预约处理", "/reservation_listAll", menu);*/
		menu3 = new Privilege("会议室管理", "/meetingRoom_list", menu);
		menu4 = new Privilege("个人预约", "/calendarReservation_show", menu);
		menu5 = new Privilege("预约处理", "/calendarReservation_toShowHandel", menu);
		
		session.save(menu);
		/*session.save(menu1);
		session.save(menu2);*/
		session.save(menu3);
		session.save(menu4);
		session.save(menu5);
		
		/*session.save(new Privilege("会议室预约列表-个人", "/reservation_list", menu1));
		session.save(new Privilege("会议室预约删除-个人", "/reservation_hide", menu1));
		session.save(new Privilege("会议室预约申请", "/reservation_add", menu1));	
		
		session.save(new Privilege("会议室预约删除-管理员", "/reservation_delete", menu2));
		session.save(new Privilege("会议室预约修改", "/reservation_edit", menu2));
		session.save(new Privilege("会议室预约审批", "/reservation_handleAppoint", menu2));*/
		
		session.save(new Privilege("会议室列表", "/meetingRoom_list", menu3));
		session.save(new Privilege("会议室删除", "/meetingRoom_delete", menu3));
		session.save(new Privilege("会议室添加", "/meetingRoom_add", menu3));
		session.save(new Privilege("会议室修改", "/meetingRoom_edit", menu3));
		
		
		session.save(new Privilege("预约管理列表(new)", "/calendarReservation_list", menu4));
		session.save(new Privilege("预约添加(new)", "/calendarReservation_add", menu4));
		
		session.save(new Privilege("预约处理列表(new)", "/calendarReservation_handelList", menu5));
		session.save(new Privilege("预约处理(new)", "/calendarReservation_handel", menu5));
		session.save(new Privilege("预约处理列表-单个会议室(new)", "/calendarReservation_setSessionMeetingRoomId", menu5));
		
		
		
		//===============个人办公模块权限
		menu = new Privilege("个人办公", null, null);
		/*menu1 = new Privilege("日程管理", "/schedule_list", menu);*/
		menu2 = new Privilege("日程管理", "/calendar_show", menu);
		menu3 = new Privilege("短消息管理", "/information_list", menu);
		menu4 = new Privilege("短消息查看", "/information_lookInbox", menu);
		menu5 = new Privilege("密码修改", "/person_editUserPasswordUI", menu);
		menu6 = new Privilege("信息维护", "/person_editUsrInfoUI", menu);
		
		
		session.save(menu);
		/*session.save(menu1);*/
		session.save(menu2);
		session.save(menu3);
		session.save(menu4);
		session.save(menu5);
		session.save(menu6);
			
		/*session.save(new Privilege("日程列表", "/schedule_list", menu1));
		session.save(new Privilege("日程删除", "/schedule_delete", menu1));
		session.save(new Privilege("日程添加", "/schedule_add", menu1));
		session.save(new Privilege("日程修改", "/schedule_edit", menu1));*/
		
		session.save(new Privilege("日程列表", "/calendar_list", menu2));
		session.save(new Privilege("日程删除", "/calendar_delete", menu2));
		session.save(new Privilege("日程添加", "/calendar_add", menu2));
		session.save(new Privilege("日程修改", "/calendar_edit", menu2));
		session.save(new Privilege("拖动修改", "/calendar_eventDrop", menu2));
		session.save(new Privilege("拖动修改时间", "/calendar_eventResize", menu2));
		session.save(new Privilege("选中时间段添加日程", "/calendar_select", menu2));
		
		
		session.save(new Privilege("个人短消息查询", "/information_list", menu3));
		session.save(new Privilege("个人短消息删除", "/information_delete", menu3));
		session.save(new Privilege("个人短消息发送", "/information_add", menu3));
		
		
		//=======资源管理
		menu = new Privilege("资源管理", null, null);
		menu1 = new Privilege("文件列表", "/resource_list", menu);
		menu2 = new Privilege("通讯录", "/user_statisticsDepart", menu);
		

		session.save(menu);
		session.save(menu1);
		session.save(menu2);		
		
		session.save(new Privilege("文件列表", "/resource_list", menu1));
		session.save(new Privilege("文件删除", "/resource_delete", menu1));
		session.save(new Privilege("文件夹创建", "/resource_addFolder", menu1));
		session.save(new Privilege("文件上传", "/resource_uploadFile", menu1));
		session.save(new Privilege("文件重命名", "/resource_rename", menu1));				
			
		// =====系统管理模块权限
		menu = new Privilege("系统管理", null, null);
		menu1 = new Privilege("用户管理", "/user_list", menu);
		menu2 = new Privilege("部门管理", "/department_list", menu);
		menu3 = new Privilege("角色管理", "/role_list", menu);

		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		session.save(menu3);


		/*session.save(new Privilege("用户列表", "/user_list", menu1));*/
		session.save(new Privilege("用户删除", "/user_delete", menu1));
		session.save(new Privilege("用户添加", "/user_add", menu1));
		session.save(new Privilege("用户修改", "/user_edit", menu1));
		session.save(new Privilege("初始化密码", "/user_initPassword", menu1));

		/*session.save(new Privilege("部门列表", "/department_list", menu2));*/
		session.save(new Privilege("部门删除", "/department_delete", menu2));
		session.save(new Privilege("部门添加", "/department_add", menu2));
		session.save(new Privilege("部门修改", "/department_edit", menu2));
		
		/*session.save(new Privilege("岗位列表", "/role_list", menu3));*/
		session.save(new Privilege("岗位删除", "/role_delete", menu3));
		session.save(new Privilege("岗位添加", "/role_add", menu3));
		session.save(new Privilege("岗位修改", "/role_edit", menu3));
		session.save(new Privilege("系统访问设置", "/role_setPrivilege", menu3));
		
		//======论坛
	/*	menu = new Privilege("网上交流", null, null);
		menu1 = new Privilege("版块管理", "/forum_list", menu);

		session.save(menu);
		session.save(menu1);
		
		session.save(new Privilege("版块列表", "/forum_list", menu1));
		session.save(new Privilege("版块删除", "/forum_delete", menu1));
		session.save(new Privilege("版块添加", "/forum_add", menu1));
		session.save(new Privilege("版块修改", "/forum_edit", menu1));
		session.save(new Privilege("版块上移", "/forum_moveUp", menu1));
		session.save(new Privilege("版块下移", "/forum_moveDown", menu1));*/
				
		
	}

	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		Installer installer = (Installer) ac.getBean("installer");
		installer.install();
	}
}
