package cn.sise.oa.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sise.oa.base.BaseAction;
import cn.sise.oa.domain.Information;
import cn.sise.oa.domain.User;
import cn.sise.oa.util.QueryHelper;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class InformationAction extends BaseAction<Information>{
	
	private Integer[] userIds;
	
	private Integer[] infoIds;
	
	/** 列表 */
	public String list() throws Exception{
		//准备数据
		User user = (User)ActionContext.getContext().getSession().get("user");
		//分页查询
		new QueryHelper(Information.class,"r")//
		// 过滤条件
		.addCondition(true,"r.senderId=?", user.getId())//当前登录人
		.preparePageBean(informationService, pageNum, 15);
		
		List<Information> informationList = informationService.findAll();
		ActionContext.getContext().put("informationList", informationList);
		
		return "list";
	}
	
	/**
	 * 查询未读消息个数
	 * @throws IOException 
	 */
	public void countUnread() throws IOException{
		User user = (User)ActionContext.getContext().getSession().get("user");
		String sum = "2";
		int num = 0;
		//num = informationService.countUnread(user);
		num = user.getInformations().size();	
		sum = Integer.toString(num);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(sum);
		printWriter.flush();
		printWriter.close();
	}
	
	/** 批量删除 */
	public String batchDelete() throws Exception{
		System.out.println(infoIds);
		informationService.deleteByIds(infoIds);
		return "toList";
	}
	
	/** 删除 */
	public String delete() throws Exception{
		informationService.delete(model.getId());
		return "toList";
	}
	
	/** 添加页面 */
	public String addUI() throws Exception{
		List<User> userList = userService.findAll();
		ActionContext.getContext().put("userList", userList);
		return "saveUI";
	}
	
	/** 添加 */
	public String add() throws Exception{
		Information information = new Information();
		//设置发送人
		User user = (User)ActionContext.getContext().getSession().get("user"); 
		information.setSenderId(user.getId());
		//设置接收人
		List<User> userList = userService.getByIds(userIds);
		information.setSendee(new HashSet<User>(userList));
		
		information.setTitle(model.getTitle());
		information.setEndTime(model.getEndTime());
		information.setContent(model.getContent());
		
		informationService.save(information);
		
		return "toList";
	}
	
	/** 查看信息详情 */
	public String viewDetail() throws Exception{
		//准备数据
		Information information = informationService.getById(model.getId());
		//回显
		ActionContext.getContext().getValueStack().push(information);
		return "viewDetail";
	}
	
	/** 待看消息列表*/
	public String lookInbox() throws Exception{
		User user = (User)ActionContext.getContext().getSession().get("user"); 
		user = userService.getById(user.getId());
		Set<Information> informationList = user.getInformations();
		ActionContext.getContext().put("informationList", informationList);
		return "lookInbox";
	}

	public Integer[] getUserIds() {
		return userIds;
	}

	public void setUserIds(Integer[] userIds) {
		this.userIds = userIds;
	}

	public Integer[] getInfoIds() {
		return infoIds;
	}

	public void setInfoIds(Integer[] infoIds) {
		this.infoIds = infoIds;
	}
	
}
