package cn.sise.oa.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

/**
 * json数据转换成map
 * @author yzh
 *
 */
public class JsonUtil {

	public static Map jsonStringData(String jsonString){
		Map returnMap = new HashMap();
		
		//转换成json对象
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		
		Map<String, Object> tmpMap = (Map<String, Object>) JSONObject.toBean(jsonObject,Map.class);
		for(Entry<String, Object> entry : tmpMap.entrySet()){
			returnMap.put(entry.getKey(), entry.getValue());			
		}
		
		return returnMap;
	}
}
