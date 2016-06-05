package cn.sise.oa.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sise.oa.base.BaseAction;
import cn.sise.oa.domain.User;

@Controller
@Scope("prototype")
public class HomeAction extends BaseAction<User> {

	public String index() throws Exception {
		return "index";
	}

	public String top() throws Exception {
		return "top";
	}

	public String bottom() throws Exception {
		return "bottom";
	}

	public String left() throws Exception {
		return "left";
	}

	public String right() throws Exception {
		return "right";
	}
	
	public String userState() throws Exception {
		return "userState";
	}
	
}
