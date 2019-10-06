package com.rain.common.validation.support;

import com.rain.common.exception.ValidationException;
import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.uitls.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationSupport {
	private static final Logger logger = LoggerFactory.getLogger(ValidationSupport.class);
	private static boolean b = false;
	private static final Map<String, List<Map<String, String>>> validataionMap = new HashMap<String, List<Map<String, String>>>();

	public static final void init() throws Exception {
		logger.info("ValidationSupport ini time: {}", DateUtils.getStrCurrtTime());
		if (!b) {
			String eduHome = AppUtils.getEduHome();
			String fileDir = UrlUtils.joinUrl(eduHome, "config/validation");
			File file = new File(fileDir);
			if(file.exists()){
				initFile(file);
			}
			else {
			  logger.info("ValidationSupport config error path: {}",fileDir);	
			}
			b = true;
		}
	}

	@SuppressWarnings({ "unchecked" })
	private static final void initFile(File fileDir) throws Exception {
		File[] files = fileDir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				initFile(file);
			} else {
				String json = FileUtils.readFileToString(file,"UTF-8");
				logger.info("ValidationSupport load file: {}",file.getName());
				Map<String, Object> map = (Map<String, Object>) JsonUtils.toMap(json);
				String service = (String) map.get("service");
				List<?> list = (List<?>) map.get("validation");
				for (Object object : list) {
					Map<String, Object> rsMap = (Map<String, Object>) object;
					String method = (String) rsMap.get("method");
					List<Map<String, String>> fields = (List<Map<String, String>>) rsMap.get("fields");
					String key = service + "#" + method;
					
					validataionMap.put(key, fields);
					//System.out.println(key);
					//System.out.println(fields);
					logger.info("ValidationSupport load: {} {}",service,method);
				}
				
			}
		}
	}

	public static List<Map<String, String>> getValidataion(String service, String method) {
		String key = service + "#" + method;
		return validataionMap.get(key);
	}

	public static void doValid(String service, String method, IceRequest request) {
		String key = service + "#" + method;
		List<Map<String, String>> rsList = validataionMap.get(key);
		if (rsList==null) return;
		for (Map<String, String> map : rsList) {
			// 字段编号
			String fieldCode = map.get("fieldCode");
			// 字段名称
			String fieldName = map.get("fieldName");
			// 是否为空
			String notNull = map.get("notNull");
			// 字段类型
			String fieldType = map.get("fieldType");
			// 字段长度
			String fieldLenghtStr = map.get("fieldLenght");
			int fieldLength = LangUtils.parseInt(fieldLenghtStr);
			// 字段范围,字典类型数据的范围。
			String fieldDictStr = map.get("fieldDict");
			List<String> fieldDicts = CollectionUtils.valueOfList(fieldDictStr);
			// 数据范围
			// String fieldRangeStr = map.get("fieldRange");
			// 校验类型
			// String validType = map.get("validType");
			// 校验Class
			// String validClass = map.get("validClass");
			// 转换类型
			// String convertType = map.get("convertType");
			// 转换Class
			// String convertClass = map.get("convertClass");

			// 空类型
			String value = StringUtils.trimNull(request.getAttr(fieldCode));
			if ("1".equals(notNull) && StringUtils.isEmpty(value)) {
				throw new ValidationException(fieldName + "不能为空！");
			}
			// 字段长度
			if ("string".equals(fieldType.toLowerCase())) {
				if (fieldLength > 0 && value.length() > fieldLength) {
					throw new ValidationException(fieldName + "超过" + fieldLength + "字数的最大限制！");
				}
			}
			// 字典范围
			if (fieldDicts.size() > 0 && !fieldDicts.contains(value)) {
				throw new ValidationException(fieldName + "不能识别的数据类型" + value);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		System.out.println();
	}

}
