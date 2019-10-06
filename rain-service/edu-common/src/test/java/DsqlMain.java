import java.io.File;
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

public class DsqlMain {

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
			for (int i = 0; i < columnList.size(); i++) {
                sb.append(columnList.get(i));
                if(i!= columnList.size()-1){
                    sb.append(",");
                }
            }
			System.out.println(tableName);
			System.out.println(sb.toString());
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
	
}
