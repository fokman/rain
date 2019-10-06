package com.rain.common.uitls;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CamelNameUtils {
	
	/**
	 * convert camel name to Underline name
	 * @return
	 */
	public static String toUnderlineName(String camelName){
		//先把第一个字母大写
		camelName = capitalize(camelName);
		
		String regex = "([A-Z][a-z]+)";
		String replacement = "$1_";

		String underscoreName = camelName.replaceAll(regex, replacement);
		//output: Pur_Order_Id_ 接下来把最后一个_去掉，然后全部改小写
		
		underscoreName = underscoreName.toLowerCase().substring(0, underscoreName.length()-1);
		
		return underscoreName;
	}
	
	/**
	 * convert underscore name to camel name
	 * @param underscoreName
	 * @return
	 */
	public static String toCamelCase(String underscoreName){
		String[] sections = underscoreName.split("_");
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<sections.length;i++){
			String s = sections[i];
			if(i==0){
				sb.append(s);
			}else{
				sb.append(capitalize(s));
			}
		}
		return sb.toString();
	}
	
	/**
	 * capitalize the first character
	 * @param str
	 * @return
	 */
	public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen)
            .append(Character.toTitleCase(str.charAt(0)))
            .append(str.substring(1))
            .toString();
    }
	
	public static Map<String, Object> mapToCamel(Map<String, Object>data){
		Map<String, Object> rowMap = new HashMap<String, Object>();
		if (data != null && !data.isEmpty()){
		  Set set = data.keySet();	
		  for(Iterator iter = set.iterator(); iter.hasNext();){
			String key = (String)iter.next();			
			if (key.endsWith("_id") || key.endsWith("_user") ) {//字段以_id结尾的转字符串
				rowMap.put(CamelNameUtils.toCamelCase(key), String.valueOf(data.get(key)));	
			}
			else {//日期类型格式化
			  Object parameter=data.get(key);	
			  if(parameter instanceof java.util.Date){
				rowMap.put(CamelNameUtils.toCamelCase(key), DateUtils.formatTime(parameter));
			  }else if(parameter instanceof Date){
				rowMap.put(CamelNameUtils.toCamelCase(key), DateUtils.formatTime(parameter));
			  }else if(parameter instanceof Timestamp){
				rowMap.put(CamelNameUtils.toCamelCase(key), DateUtils.formatTime(parameter));
			  }
			  else {
			    rowMap.put(CamelNameUtils.toCamelCase(key), data.get(key));
			  }
			}
		  }	
		}
		return rowMap;		
	}
	
	public static List<Map<String, Object>> listToCamel(List<Map<String, Object>> data){
		List<Map<String, Object>> rows =new ArrayList<Map<String, Object>>();
		 for(int    i=0;  i<data.size();  i++)  {
			 rows.add(mapToCamel(data.get(i)));
		 }
		return rows;		
	}
	
    public static void main(String[] args) {
        System.out.println(CamelNameUtils.toUnderlineName("ISOCertifiedStaff"));
        System.out.println(CamelNameUtils.toUnderlineName("CertifiedStaff"));
        System.out.println(CamelNameUtils.toUnderlineName("UserId"));
        System.out.println(CamelNameUtils.toCamelCase("iso_certified_staff"));
        System.out.println(CamelNameUtils.toCamelCase("certified_staff"));
        System.out.println(CamelNameUtils.toCamelCase("user_id"));
    }	
}

