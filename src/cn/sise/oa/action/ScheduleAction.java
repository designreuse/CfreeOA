package cn.sise.oa.action;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sise.oa.base.BaseAction;
import cn.sise.oa.domain.Schedule;
import cn.sise.oa.domain.User;
import cn.sise.oa.util.QueryHelper;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ScheduleAction extends BaseAction<Schedule> {
	/** 日程列表 */
	public String list() throws Exception{
		//准备数据
		User user = (User)ActionContext.getContext().getSession().get("user");
		//分页查询
		new QueryHelper(Schedule.class,"r")//
		// 过滤条件
		.addCondition(true,"r.user=?", user)//当前登录人
		.preparePageBean(scheduleService, pageNum, 15);
		return "list";
	}
	
	/** 日程删除 */
	public String delete() throws Exception{
		scheduleService.delete(model.getId());
		return "toList";
	}
	
	/** 日程添加页面 */
	public String addUI() throws Exception{
		return "saveUI";
	}
	
	/** 日程添加 */
	public String add() throws Exception{
		User user = (User)ActionContext.getContext().getSession().get("user");
		model.setUser(user);
		model.setCreateTime(new Date());
		scheduleService.save(model);
		return "toList";
	}
	
	/** 日程修改页面 */
	public String editUI() throws Exception{
		//准备数据
		Schedule schedule = scheduleService.getById(model.getId());
		//回显
		ActionContext.getContext().getValueStack().push(schedule);;
		return "saveUI";
	}
	
	/** 日程修改  */
	public String edit() throws Exception{
		//更新数据
		scheduleService.update(model);
		return "toList";
	}
	
	public String viewDetail() throws Exception{
		//准备数据
		Schedule schedule = scheduleService.getById(model.getId());
		//回显
		ActionContext.getContext().getValueStack().push(schedule);
		return "viewDetail";
	}
}
