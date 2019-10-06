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

public class JavaCodeMian {

	public static void main(String[] args) throws Exception {
		Map<String, List<String>> map = new HashMap<>();
		File dirFile = new File("D:/java");
		dirFile.mkdirs();
		DataSource dataSource = DruidDataSourceFactory.createDataSource(getProperties());
		String sql = "select table_name,column_name,data_type from information_schema.COLUMNS where table_schema = 'k12' order by table_name";
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
				temp_list.add(column_name);
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
		Set<String> tableNames = map.keySet();
		for (String tableName : tableNames) {
			List<String> columnList = map.get(tableName);
			StringBuffer sb = new StringBuffer();
			String serviceName = toJavaClassName(tableName) + "Service";
			String className = serviceName + "Test";
			String idCloumnName = columnList.get(0);
			String idJavaName = toJava(idCloumnName);
			sb.append("import org.junit.Test;");
			sb.append("import IceRequest;");
			sb.append("import IceRespose;");
			sb.append("import TestClientUtils;");
			sb.append("public class " + className + " {" + "\n");
			sb.append("@Test" + "\n");
			sb.append("public void testAdd() {");
			sb.append("IceRequest iceRequest = new IceRequest();" + "\n");
			sb.append("iceRequest.setService(\"" + serviceName + "\");\n");
			sb.append("iceRequest.setMethod(\"add\");\n");
			for (int i = 1; i < columnList.size(); i++) {
				String cloumnName = columnList.get(i);
				String javaName = toJava(cloumnName);
				sb.append("iceRequest.addAttr(\"");
				sb.append(javaName);
				sb.append("\",\"\");" + "\n");
			}
			sb.append("IceRespose rs = TestClientUtils.doService(iceRequest, new String[] {});" + "\n");
			sb.append("System.out.println(rs.getCode());" + "\n");
			sb.append("System.out.println(rs.getMsg());" + "\n");
			sb.append("System.out.println(rs.getData());" + "\n");
			sb.append("}");
			//修改
			sb.append("@Test" + "\n");
			sb.append("public void testModify() {");
			sb.append("IceRequest iceRequest = new IceRequest();" + "\n");
			sb.append("iceRequest.setService(\"" + serviceName + "\");\n");
			sb.append("iceRequest.setMethod(\"modify\");\n");
			for (int i = 1; i < columnList.size(); i++) {
				String cloumnName = columnList.get(i);
				String javaName = toJava(cloumnName);
				sb.append("iceRequest.addAttr(\"");
				sb.append(javaName);
				sb.append("\",\"\");" + "\n");
			}
			sb.append("IceRespose rs = TestClientUtils.doService(iceRequest, new String[] {});" + "\n");
			sb.append("System.out.println(rs.getCode());" + "\n");
			sb.append("System.out.println(rs.getMsg());" + "\n");
			sb.append("System.out.println(rs.getData());" + "\n");
			sb.append("}");
			//删除
			sb.append("@Test\n");
			sb.append("public void testRemove() { ");
			sb.append("	IceRequest iceRequest = new IceRequest(); ");
			sb.append("iceRequest.setService(\"" + serviceName + "\");\n");
			sb.append("iceRequest.setMethod(\"remove\");\n");
			sb.append("iceRequest.addAttr(\""+idJavaName+"\",\"\");\n");
			sb.append("IceRespose rs = TestClientUtils.doService(iceRequest, new String[] {}); ");
			sb.append("	System.out.println(rs.getCode()); ");
			sb.append("	System.out.println(rs.getMsg()); ");
			sb.append("System.out.println(rs.getData()); ");
			sb.append("} ");
			//查询
			sb.append("@Test\n");
			sb.append("public void testDtl() { ");
			sb.append("	IceRequest iceRequest = new IceRequest(); ");
			sb.append("iceRequest.setService(\"" + serviceName + "\");\n");
			sb.append("iceRequest.setMethod(\"dtl\");\n");
			sb.append("iceRequest.addAttr(\""+idJavaName+"\",\"\");\n");
			sb.append("IceRespose rs = TestClientUtils.doService(iceRequest, new String[] {}); ");
			sb.append("	System.out.println(rs.getCode()); ");
			sb.append("	System.out.println(rs.getMsg()); ");
			sb.append("System.out.println(rs.getData()); ");
			sb.append("} ");
			//查询分页
			sb.append("@Test\n");
			sb.append("public void testList() { ");
			sb.append("	IceRequest iceRequest = new IceRequest(); ");
			sb.append("iceRequest.setService(\"" + serviceName + "\");\n");
			sb.append("iceRequest.setMethod(\"list\");\n");
			sb.append("iceRequest.addAttr(\"page\",\"1\");\n");
			sb.append("iceRequest.addAttr(\"pagesize\",\"10\");\n");
			sb.append("iceRequest.addAttr(\""+idJavaName+"\",\"\");\n");
			sb.append("IceRespose rs = TestClientUtils.doService(iceRequest, new String[] {}); ");
			sb.append("	System.out.println(rs.getCode()); ");
			sb.append("	System.out.println(rs.getMsg()); ");
			sb.append("System.out.println(rs.getData()); ");
			sb.append("} ");
			//结束
			sb.append("}");
			FileWriter fw = new FileWriter(new File("D:/java/" + className + ".java"));
			fw.write(sb.toString());
			fw.close();
		}
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

	private static String toJavaClassName(String tableName) {
		tableName = tableName.toLowerCase();
		String[] strs = tableName.split("_");
		StringBuffer sb = new StringBuffer();
		for (String string : strs) {
			if (string.length() > 1) {
				String f = string.substring(0, 1).toUpperCase();
				sb.append(f);
				sb.append(string.substring(1));
			} else {
				sb.append(string.toUpperCase());
			}
		}

		return sb.toString();
	}
}
