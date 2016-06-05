package cn.sise.oa.action.bean;

/**
 * 部门信息统计界面类
 * @author yzh
 *
 */
public class StatisticsDepart {

	private Integer id;
	private int amount; //部门人数
	private String departName; //部门名称
	
	public StatisticsDepart(){}
	
	public StatisticsDepart(String departName, int amount){
		this.departName = departName;
		this.amount = amount;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	
	
}
