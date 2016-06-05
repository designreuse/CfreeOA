import java.util.Map;

import cn.sise.oa.util.JsonUtil;


public class JsonTest {

	public static void main(String[] args) {
		String jsonString = "{\"id\":\"1\",\"days\":\"3\",\"season\":\"生病\",\"textarea-1464313243654\":\"6767h\"}";

		Map my = JsonUtil.jsonStringData(jsonString);
		System.out.println(my.get("days1"));
	}

}
