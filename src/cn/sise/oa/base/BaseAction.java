package cn.sise.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.ExecutionListener;

import cn.sise.oa.domain.Reservation;
import cn.sise.oa.service.CalendarService;
import cn.sise.oa.service.DepartmentService;
import cn.sise.oa.service.FileTemplateService;
import cn.sise.oa.service.InformationService;
import cn.sise.oa.service.MeetingRoomCalendarService;
import cn.sise.oa.service.MeetingRoomService;
import cn.sise.oa.service.MyApplicationService;
import cn.sise.oa.service.MyTaskService;
import cn.sise.oa.service.PrivilegeService;
import cn.sise.oa.service.ProcessDefinitionService;
import cn.sise.oa.service.ReservationService;
import cn.sise.oa.service.ResourceManagementService;
import cn.sise.oa.service.RoleService;
import cn.sise.oa.service.ScheduleService;
import cn.sise.oa.service.UserService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	// =============== ModelDriven的支持==================

	protected T model;

	public BaseAction() {
		try {
			// 通过反射获取model的真实类型
			ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
			Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
			// 通过反射创建model的实例
			model = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public T getModel() {
		return model;
	}

	// =============== 权限相关服务组件==================
	
	@Resource
	protected RoleService roleService;
	@Resource
	protected UserService userService;
	@Resource
	protected DepartmentService departmentService;
	@Resource
	protected PrivilegeService privilegeService;
	
	//=============== avtiviti 相关服务组件====
	@Resource
	protected IdentityService identityService;
	@Resource
	protected RepositoryService repositoryService;
	@Resource
	protected TaskService taskService;
	@Resource
	protected RuntimeService runtimeService;
	@Resource
	protected HistoryService historyService;
	
	//================ 其他服务组件  ===========
	@Resource
	protected ProcessDefinitionService processDefinitionService;
	@Resource
	protected MyTaskService myTaskService;
	@Resource
	protected ResourceManagementService resourceManagementService;
	@Resource 
	protected FileTemplateService fileTemplateService;
	@Resource
	protected MyApplicationService myApplicationService;
	@Resource
	protected ScheduleService scheduleService;
	@Resource
	protected InformationService informationService;
	@Resource
	protected MeetingRoomService meetingRoomService;
	@Resource
	protected ReservationService reservationService;
	@Resource
	protected CalendarService calendarService;
	@Resource
	protected MeetingRoomCalendarService meetingRoomCalendarService;
	// ============== 分页用的参数 =============

		protected int pageNum = 1; // 当前页
		protected int pageSize = 10; // 每页显示多少条记录

		public int getPageNum() {
			return pageNum;
		}

		public void setPageNum(int pageNum) {
			this.pageNum = pageNum;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}


}
