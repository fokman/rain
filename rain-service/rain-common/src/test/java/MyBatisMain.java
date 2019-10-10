import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.rain.common.servcie.StartupUtils;

public class MyBatisMain {

	public static void main(String[] args) throws Exception {
		File dirFile = new File("D:/xml");
		dirFile.mkdirs();
		DataSource dataSource = DruidDataSourceFactory.createDataSource(getProperties());
		Map<String, List<String>> map = new HashMap<String, List<String>>();    
		String sql = "select table_name,column_name,data_type from information_schema.COLUMNS where  table_schema = 'k12' order by table_name";
		                                                           //table_name in('push_msg','push_msg_link') and
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			String temp_table = null;
			List<String> temp_list = null;
			while (rs.next()) {
				String table_name = rs.getString("table_name");
				String column_name = rs.getString("column_name");
				column_name = column_name.toLowerCase();
				String data_type = rs.getString("data_type");
				if ("int".equals(data_type) || "bigint".equals(data_type)) {
					data_type = "NUMERIC";
				} else if ("datetime".equals(data_type)) {
					data_type = "TIMESTAMP";
				} else {
					data_type = "VARCHAR";
				}
				boolean b = false;
				if (temp_table == null) {
					temp_table = table_name;
					b = true;
				}
				if (b || !table_name.equals(temp_table)) {
					temp_list = new ArrayList<>();
					map.put(table_name, temp_list);
					temp_table = table_name;
				}
				temp_list.add(column_name + "#" + data_type);
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}

		Set<String> set = map.keySet();
		for (String tableName : set) {
			StringBuffer sb = new StringBuffer();
			List<String> tcolumnList = map.get(tableName);
			String idColumnInfo = tcolumnList.get(0);
			String idCoumnName = idColumnInfo.split("#")[0];
			String idJavaName = toJava(idCoumnName);
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \n");
			sb.append("\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
			sb.append("<mapper namespace=\"" + tableName.toUpperCase() + "\"> \n");
			// 添加
			sb.append("<select id=\"insert\" parameterType=\"map\">\n");
			sb.append("insert into " + tableName + " \n");
			sb.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
			for (int i = 0; i < tcolumnList.size(); i++) {
				String columnInfo = tcolumnList.get(i);
				String coumnName = columnInfo.split("#")[0];
				String javaName = toJava(coumnName);
				// String typeName = columnInfo.split("#")[1];
				sb.append("<if test=\"" + javaName + " != null\">\n");
				if(!"updateUser".equals(javaName) && !"updateTime".equals(javaName)){
    				sb.append(coumnName);
    				sb.append(",\n");
				}
				sb.append("</if>\n");
			}
			sb.append("</trim>\n");
			sb.append("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
			for (int i = 0; i < tcolumnList.size(); i++) {
				String columnInfo = tcolumnList.get(i);
				String coumnName = columnInfo.split("#")[0];
				String javaName = toJava(coumnName);
				String typeName = columnInfo.split("#")[1];
			    if(!"updateUser".equals(javaName) && !"updateTime".equals(javaName)){
    				sb.append("<if test=\"" + javaName + " != null\">\n");
    				sb.append("#{" + javaName + ",jdbcType=" + typeName + "}");
    				if (i != tcolumnList.size() - 1) {
    					sb.append(",");
    				}
			    }
				sb.append("\n</if>\n");
			}
			sb.append("</trim>\n");
			sb.append("</select>\n");
			// 修改
			sb.append("<update id=\"update\" parameterType=\"map\">\n");
			sb.append("update ");
			sb.append(tableName);
			sb.append("<set>\n");
			for (int i = 0; i < tcolumnList.size(); i++) {
				String columnInfo = tcolumnList.get(i);
				String coumnName = columnInfo.split("#")[0];
				String javaName = toJava(coumnName);
				String typeName = columnInfo.split("#")[1];
				sb.append("<if test=\"" + javaName + " != null\">\n");
				sb.append(" " + coumnName + " = #{" + javaName + ",jdbcType=" + typeName + "},\n");
				sb.append("</if>\n");
			}
			sb.append("</set>\n");
			sb.append("where ");
			sb.append(idCoumnName + "=#{" + idJavaName + "}   and school_id=#{schoolId} \n");
			sb.append("</update>\n");
			// 查询
			sb.append("<select id=\"query\" parameterType=\"map\" resultType=\"map\">\n");
			sb.append("select ");
			for (int i = 0; i < tcolumnList.size(); i++) {
				String columnInfo = tcolumnList.get(i);
				String coumnName = columnInfo.split("#")[0];
				// String javaName = toJava(coumnName);
				// String typeName = columnInfo.split("#")[1];
				sb.append(coumnName);
				if (i != tcolumnList.size() - 1) {
					sb.append(",\n");
				}
			}
			sb.append(" \n from  ");
			sb.append(tableName);
			sb.append("\n<where>\n");
			for (int i = 0; i < tcolumnList.size(); i++) {
				String columnInfo = tcolumnList.get(i);
				String coumnName = columnInfo.split("#")[0];
				String javaName = toJava(coumnName);
				String typeName = columnInfo.split("#")[1];
				sb.append("<if test=\"" + javaName + " != null\">\n");
				sb.append("and " + coumnName + " = #{" + javaName + ",jdbcType=" + typeName + "}\n");
				sb.append("</if>\n");
			}
			sb.append("</where>\n");
			sb.append("</select>\n");
			// 统计
			sb.append("<select id=\"count\" resultType=\"int\" parameterType=\"map\">\n");
			sb.append(" select count(*) from ");
			sb.append(tableName);
			sb.append("\n<where>\n");
			for (int i = 0; i < tcolumnList.size(); i++) {
				String columnInfo = tcolumnList.get(i);
				String coumnName = columnInfo.split("#")[0];
				String javaName = toJava(coumnName);
				String typeName = columnInfo.split("#")[1];
				sb.append("<if test=\"" + javaName + " != null\">\n");
				sb.append("and " + coumnName + " = #{" + javaName + ",jdbcType=" + typeName + "}\n");
				sb.append("</if>\n");
			}
			sb.append("</where>\n");
			sb.append("</select>\n");
			// 删除
			sb.append("<delete id=\"delete\" parameterType=\"map\">\n");
			sb.append("delete from  ");
			sb.append(tableName);
			sb.append("\n<where>\n");
			sb.append(idCoumnName + "=#{" + idJavaName + "}   and school_id=#{shoolId} \n");
			sb.append("</where>\n");
			sb.append("</delete>\n");

			sb.append("</mapper>");

			FileWriter fw = new FileWriter(new File("D:/xml/" + tableName + "Mapper.xml"));
			fw.write(sb.toString());
			fw.close();
		}
	}

	private static String toJava(String str) {
		String[] strs = str.split("_");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			if (i == 0) {
				sb.append(strs[i]);
			} else {
				sb.append(strs[i].substring(0, 1).toUpperCase());
				sb.append(strs[i].substring(1));
			}
		}
		return sb.toString();
	}

	private static Properties getProperties() {
		Properties prop = new Properties();
		InputStream in = null;
		String config = "/test.properties";
		try {
			in = StartupUtils.class.getResourceAsStream(config);
			if (in == null) {
				in = StartupUtils.class.getClassLoader().getResourceAsStream(config);
			}
			if (in == null) {
				in = Thread.currentThread().getContextClassLoader().getResourceAsStream(config);
			}
			prop.load(in);

		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
}
