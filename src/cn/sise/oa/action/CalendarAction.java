package cn.sise.oa.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.sise.oa.action.bean.CalendarOV;
import cn.sise.oa.base.BaseAction;
import cn.sise.oa.domain.Calendar;
import cn.sise.oa.domain.User;
import cn.sise.oa.util.DateUtil;


@Controller
@Scope("prototype")
public class CalendarAction extends BaseAction<Calendar>{

	private HttpServletResponse response = ServletActionContext.getResponse();
	private HttpServletRequest request = ServletActionContext.getRequest();	
	
	private String start;
	private String end;
	
	/**
	 * 跳转到日程管理界面
	 * @return
	 */
	public String show(){
		return "show";
	}
	
	/**
	 * 查询所有日历信息
	 * @throws IOException 
	 */
	public void list() throws IOException{
		User user = (User)ActionContext.getContext().getSession().get("user");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		List<Calendar> calendars = calendarService.findByUser(user);
		List<CalendarOV> calendarOVs = calendarService.transformToOV(calendars);
		System.out.println(calendars.size());
		String json = JSONSerializer.toJSON(calendarOVs).toString();
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
		
	} 
	
	public void add() throws IOException{
		User user = (User)ActionContext.getContext().getSession().get("user");
		String title = request.getParameter("title");
		String isallday = request.getParameter("isallday");
		System.out.println(isallday);
		if("0".equals(isallday)){
			isallday = "0";
		}else{
			isallday = "1";
		}

		//title = new String(title.getBytes("iso-8859-1"),"utf-8");
		Calendar calendar = new Calendar();
		calendar.setTitle(title);
		calendar.setStart(request.getParameter("start"));
		calendar.setEnd(request.getParameter("end"));
		calendar.setAllDay(Integer.valueOf(isallday));
		calendar.setColor(request.getParameter("color"));
		calendar.setUser(user);
		
		Integer id = calendarService.save(calendar);
		
		Map<String, String> backValue = new HashMap<String, String>();
		backValue.put("success", "1");
		PrintWriter writer = response.getWriter();
		writer.print(JSONSerializer.toJSON(backValue));
		writer.flush();
		writer.close();
	}
	
	public void delete(){
		String id = request.getParameter("id");
		calendarService.delete(Integer.valueOf(id));
	}

	public void edit() throws UnsupportedEncodingException{
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String isallday = request.getParameter("isallday");
		if("0".equals(isallday)){
			isallday = "0";
		}else{
			isallday = "1";
		}
		//title = new String(title.getBytes("iso-8859-1"),"utf-8");
		System.out.println(id);
		Calendar calendar = calendarService.getById(Integer.valueOf(id));
		calendar.setTitle(title);
		calendar.setStart(request.getParameter("start"));
		calendar.setEnd(request.getParameter("end"));
		calendar.setAllDay(Integer.valueOf(isallday));
		calendar.setColor(request.getParameter("color"));
		
		calendarService.update(calendar);
	}
	
	/**
	 * 拖动时间（只在月视图有效）
	 */
	public void eventDrop(){
		//获取参数
		Integer id =  Integer.parseInt(request.getParameter("id"));
		Integer daydiff = Integer.valueOf(request.getParameter("dayDelta"));
		daydiff *= 24 * 60 * 60;
		Integer minudiff = Integer.valueOf(request.getParameter("minuteDelta"));
		minudiff *= 60;
		String allday = request.getParameter("allday");
		
		Calendar calendar = calendarService.getById(id);
		String start = calendar.getStart();
		long lstart = DateUtil.string2long(start);		
		String end = calendar.getEnd();
		long lend = 0;
		if(!"".equals(end)){
			lend = DateUtil.string2long(end);
		}
		
		if("true".equals(allday)){//全天
			if("".equals(end)){//没有结束时间,只更新开始时间
				lstart += daydiff;				
			}else {//
				lstart += daydiff;
				lend = lstart + daydiff;
			}
		}else {//不是全天
			Integer difftime = daydiff + minudiff;
			if("".equals(end)){//没有结束时间
				lstart += difftime;				
			}else {				
				lstart += difftime;
				lend += difftime;				
			}
		}
		
		//更新时间
		calendar.setStart(DateUtil.long2string(lstart));
		if(lend != 0){			
			calendar.setEnd(DateUtil.long2string(lend));
		}
		calendarService.update(calendar);
		
	}
	
	/**
	 * 修改日程长短
	 */
	public void eventResize(){
		//获取参数并转换成long型
		Integer id =  Integer.parseInt(request.getParameter("id"));
		Integer daydiff = Integer.valueOf(request.getParameter("dayDelta"));
		daydiff *= 24 * 60 * 60;
		Integer minudiff = Integer.valueOf(request.getParameter("minuteDelta"));
		minudiff *= 60;
		
		//获取实例参数并转换成long型
		Calendar calendar = calendarService.getById(id);
		String start = calendar.getStart();
		long lstart = DateUtil.string2long(start);		
		String end = calendar.getEnd();
		long lend = 0;
		
		Integer difftime = daydiff + minudiff;
		if("".equals(end)){//如果没有结束时间，就把增加时间与开始时间相加作为结束时间
			lend = lstart + difftime;		
		}else {//如果有结束时间，就把增加时间与结束时间相加			
			lend += difftime;			
		}
		
		if(lend != 0){			
			calendar.setEnd(DateUtil.long2string(lend));
		}else if(lend == lstart){//开始时间与结束时间相同
			calendar.setEnd("");
		}
		calendarService.update(calendar);		
		
	}
	
	/**
	 * 选中时间段添加日程
	 * @throws UnsupportedEncodingException 
	 */
	public void select() throws UnsupportedEncodingException{
		User user = (User)ActionContext.getContext().getSession().get("user");
		String title = request.getParameter("title");
		String isallday = request.getParameter("isallday");
		if("0".equals(isallday)){
			isallday = "0";
		}else{
			isallday = "1";
		}
		//title = new String(title.getBytes("iso-8859-1"),"utf-8");
		Calendar calendar = new Calendar();
		calendar.setTitle(title);
		calendar.setStart(request.getParameter("start"));
		calendar.setEnd(request.getParameter("end"));
		calendar.setAllDay(Integer.valueOf(isallday));
		calendar.setColor(request.getParameter("color"));
		calendar.setUser(user);
		
		Integer id = calendarService.save(calendar);
	}
	
	//=========================

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	
	
	
	
}
