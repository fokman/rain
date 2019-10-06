package com.rain.common.uitls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 源文件名：SqlUtils.java<br>
 * 文件版本：1.0.0<br>
 * 创建作者：liuzf<br>
 * 创建日期：2016年5月18日<br>
 * 修改作者：liuzf<br>
 * 修改日期：2016年5月18日<br>
 * 文件描述：SQL工具类<br>
 * 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.<br>
 */
public class SqlUtils {
	/**
	 * 获取执行sql
	 * 
	 * @param sql
	 * @param list
	 *            参数
	 * @return sql 执行sql
	 */
	public static String calculationSql(String sql, List<Object> list) {
		if (list == null) {
			list = new ArrayList<Object>(0);
		}
		return calculationSql(sql, list.toArray());
	}

	/**
	 * 获取执行SQL
	 * 
	 * @param sql
	 *            SQL
	 * @param params
	 *            参数
	 * @return SQL 执行SQL
	 */
	public static String calculationSql(String sql, Object[] params) {
		int paramNum = 0;
		if (params != null) {
			paramNum = params.length;
		}
		if (paramNum == 0) {
			return sql;
		}
		StringBuffer returnSQL = new StringBuffer();
		String[] subSQL = sql.split("\\?");
		for (int i = 0; i < paramNum && i < subSQL.length; i++) {
			if (params[i] instanceof String) {
				returnSQL.append(subSQL[i]).append(" '").append(params[i]).append("' ");
			} else {
				returnSQL.append(subSQL[i]).append(params[i]);
			}
		}
		if (subSQL.length > params.length) {
			returnSQL.append(subSQL[subSQL.length - 1]);
		}
		return returnSQL.toString();
	}

	/**
	 * 创建预处理语句段
	 * 
	 * @param num
	 *            个数
	 * @return 预处理语句段
	 */
	public static String createPrepared(int num) {
		StringBuffer sb = new StringBuffer(num * 2 + 10);
		for (int i = 0; i < num; i++) {
			sb.append("?,");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		return sb.toString();
	}

	/**
	 * 创建预处理语句段
	 * 
	 * @param fieldName
	 *            字段名称
	 * @param num
	 *            个数
	 * @return 创建预处理语句段
	 */
	public static String createInPrepared(String fieldName, int num) {
		return createInPrepared(fieldName, num, true);
	}

	/**
	 * 创建预处理语句段
	 * 
	 * @param fieldName
	 *            字段名称
	 * @param num
	 *            个数
	 * @param b
	 *            是否包含where
	 * @return 预处理语句段
	 */
	public static String createInPrepared(String fieldName, int num, boolean b) {
		StringBuffer sb = new StringBuffer(num * 2 + 40);
		sb.append(b ? " WHERE " : "");
		sb.append(fieldName).append(" IN(");
		sb.append(createPrepared(num));
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 获取字段IN的条件
	 * 
	 * @param list
	 *            条件列表
	 * @param fieldName
	 *            字段名称
	 * @return 字段IN的条件
	 */
	public static String createInCondition(List<?> list, String fieldName) {
		return createInCondition(list, fieldName, true);
	}

	/**
	 * 获取字段IN的条件
	 * 
	 * @param list
	 *            条件列表
	 * @param fieldName
	 *            字段名称
	 * @param b
	 *            生成WHERE
	 * @return 字段IN的条件
	 */
	public static String createInCondition(Collection<?> list, String fieldName, boolean b) {
		if (list == null || list.size() == 0)
			return "";
		StringBuffer sb = new StringBuffer(list.size() * 8);
		sb.append(b ? " WHERE " : "");
		if (list.size() == 1) {
			sb.append(fieldName).append(" = ");
		} else {
			sb.append(fieldName).append(" IN(");
		}
		for (Object object : list) {
			if (object instanceof Number) {
				sb.append(String.valueOf(object));
			} else {
				sb.append("'");
				sb.append(object.toString());
				sb.append("'");
			}
			sb.append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		if (list.size() > 1) {
			sb.append(")");
		}
		return sb.toString();
	}

	public static String columnToField(String columnName) {
		String strs[] = columnName.toLowerCase().split("_");
		if (strs != null) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < strs.length; i++) {
				String str = strs[i];
				if (i != 0) {
					str = str.substring(0, 1).toUpperCase() + str.substring(1);
				}
				sb.append(str);
			}
			return sb.toString();
		}
		return columnName;
	}

	public static String fieldToColumn(String fieldName) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fieldName.length(); i++) {
			char c = fieldName.charAt(i);
			String cStr = String.valueOf(c);
			if (c >= 65 && c <= 95) {
				sb.append("_");
				sb.append(cStr);
			} else {
				sb.append(cStr);
			}
		}
		return sb.toString().toUpperCase();
	}

	public static List<Object> objsToList(Object... params) {
		List<Object> list = new ArrayList<Object>();
		if (params != null) {
			for (Object t : params) {
				list.add(t);
			}
		}
		return list;
	}

	public static void parseQueryCondition(String columnCode, String op, Object value, StringBuffer conditionSql,
			List<Object> rsList) {
		conditionSql.append(" AND ");
		if (!"99".equals(op)) {
			conditionSql.append(columnCode);
		}
		if ("1".equals(op)) {
			conditionSql.append(" = ? ");
			rsList.add(value);
		} else if ("2".equals(op)) {
			conditionSql.append(" <> ? ");
			rsList.add(value);
		} else if ("3".equals(op)) {
			conditionSql.append(" > ?");
			rsList.add(value);
		} else if ("4".equals(op)) {
			conditionSql.append(" >= ? ");
			rsList.add(value);
		} else if ("5".equals(op)) {
			conditionSql.append(" < ?");
			rsList.add(value);
		} else if ("6".equals(op)) {
			conditionSql.append(" <= ? ");
			rsList.add(value);
		} else if ("7".equals(op)) {
			conditionSql.append(" LIKE ?");
			rsList.add(StringUtils.likeTrim((String) value));
		} else if ("8".equals(op)) {
			conditionSql.append(" NOT LIKE ?");
			rsList.add(StringUtils.likeTrim((String) value));
		} else if ("9".equals(op)) {
			conditionSql.append(" LIKE ?");
			rsList.add(StringUtils.likeLTrim((String) value));
		} else if ("10".equals(op)) {
			conditionSql.append(" NOT LIKE ");
			rsList.add(StringUtils.likeLTrim((String) value));
		} else if ("11".equals(op)) {
			conditionSql.append(" LIKE ? ");
			rsList.add(StringUtils.likeRTrim((String) value));
		} else if ("12".equals(op)) {
			conditionSql.append(" NOT LIKE ?");
			rsList.add(StringUtils.likeRTrim((String) value));
		} else if ("99".equals(op)) {
			List<String> values = CollectionUtils.valueOfList((String) value);
			if (values.size() > 0) {
				conditionSql.append(columnCode);
				conditionSql.append(" IN (");
				conditionSql.append(SqlUtils.createPrepared(values.size()));
				conditionSql.append(')');
				rsList.addAll(values);
			}
		}
	}

	
}
