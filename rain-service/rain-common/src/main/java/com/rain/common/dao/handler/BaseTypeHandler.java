
package com.rain.common.dao.handler;

import org.apache.ibatis.session.Configuration;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class BaseTypeHandler<T> extends TypeReference<T> implements TypeHandler<T> {

  protected Configuration configuration;

  public void setConfiguration(Configuration c) {
    this.configuration = c;
  }

  public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
    if (parameter == null) {
      if (jdbcType == null) {
        throw new TypeException("JDBC requires that the JdbcType must be specified for all nullable parameters.");
      }
      try {
        ps.setNull(i, jdbcType.TYPE_CODE);
      } catch (SQLException e) {
        throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . " +
        		"Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. " +
        		"Cause: " + e, e);
      }
    } else {
      setNonNullParameter(ps, i, parameter, jdbcType);
    }
  }

  public T getResult(ResultSet rs, String columnName) throws SQLException {
    T result = getNullableResult(rs, columnName);
    if (rs.wasNull()) {
      return null;
    } else {
      return result;
    }
  }

  public T getResult(ResultSet rs, int columnIndex) throws SQLException {
    T result = getNullableResult(rs, columnIndex);
    if (rs.wasNull()) {
      return null;
    } else {
      return result;
    }
  }

  public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
    T result = getNullableResult(cs, columnIndex);
    if (cs.wasNull()) {
      return null;
    } else {
      return result;
    }
  }

  public abstract void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;

  public abstract T getNullableResult(ResultSet rs, String columnName) throws SQLException;

  public abstract T getNullableResult(ResultSet rs, int columnIndex) throws SQLException;

  public abstract T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException;

}