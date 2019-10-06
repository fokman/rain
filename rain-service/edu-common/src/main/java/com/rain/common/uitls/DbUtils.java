package com.rain.common.uitls;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC工具类
 *
 */
public final class DbUtils {
	/**
	 * 关闭结果集
	 * @param rs 结果集
	 * @throws SQLException SQL异常
	 */
	public static void close(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
	}

	/**
	 * 关闭Statement
	 * @param stmt Statement
	 * @throws SQLException SQL异常
	 */
	public static void close(Statement stmt) throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
	}

	/**
	 * 关闭连接
	 * @param conn 连接
	 * @throws SQLException SQL异常
	 */
	public static void close(Connection conn) throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

	/**
	 * 关闭结果集
	 * @param rs 结果集
	 */
	public static void closeQuietly(ResultSet rs) {
		try {
			close(rs);
		} catch (SQLException e) {
			// quiet
		}
	}

	/**
	 * 关闭Statement
	 * @param stmt Statement
	 */
	public static void closeQuietly(Statement stmt) {
		try {
			close(stmt);
		} catch (SQLException e) {
			// quiet
		}
	}

	/**
	 * 关闭连接
	 * @param conn 连接
	 */
	public static void closeQuietly(Connection conn) {
		try {
			close(conn);
		} catch (SQLException e) {
		}
	}

	/**
	 * 关闭
	 * @param conn 连接
	 * @param stmt Statement
	 * @param rs 结果集
	 */
	public static void closeQuietly(Connection conn, Statement stmt, ResultSet rs) {
		try {
			closeQuietly(rs);
		} finally {
			try {
				closeQuietly(stmt);
			} finally {
				closeQuietly(conn);
			}
		}
	}

	/**
	 * 提交事物
	 * @param conn 连接
	 * @throws SQLException SQL异常
	 */
	public static void commit(Connection conn) throws SQLException {
		if (conn != null) {
			conn.commit();
		}
	}

	/**
	 * 提交事物
	 * @param conn 连接
	 */
	public static void commitQuietly(Connection conn) {
		try {
			commit(conn);
		} catch (SQLException e) {
		}
	}

	/**
	 * 提交事物并且关闭连接
	 * @param conn 连接
	 * @throws SQLException SQL异常
	 */
	public static void commitAndClose(Connection conn) throws SQLException {
		if (conn != null) {
			try {
				conn.commit();
			} finally {
				conn.close();
			}
		}
	}

	/**
	 * 提交事物并且关闭连接
	 * @param conn 连接
	 */
	public static void commitAndCloseQuietly(Connection conn) {
		try {
			commitAndClose(conn);
		} catch (SQLException e) {
			// quiet
		}
	}

	/**
	 * 回滚事物
	 * @param conn 连接
	 * @throws SQLException SQL异常
	 */
	public static void rollback(Connection conn) throws SQLException {
		if (conn != null) {
			conn.rollback();
		}
	}

	/**
	 * 回滚事物
	 * @param conn 连接
	 * @throws SQLException SQL异常
	 */
	public static void rollbackQuietly(Connection conn) {
		try {
			rollback(conn);
		} catch (SQLException e) {
		}
	}

	/**
	 * 回滚事物并且关闭连接
	 * @param conn 连接
	 * @throws SQLException SQL异常
	 */
	public static void rollbackAndClose(Connection conn) throws SQLException {
		if (conn != null) {
			try {
				conn.rollback();
			} finally {
				conn.close();
			}
		}
	}

	/**
	 * 回滚事物并且关闭连接
	 * @param conn 连接
	 */
	public static void rollbackAndCloseQuietly(Connection conn) {
		try {
			rollbackAndClose(conn);
		} catch (SQLException e) {
			// quiet
		}
	}
}
