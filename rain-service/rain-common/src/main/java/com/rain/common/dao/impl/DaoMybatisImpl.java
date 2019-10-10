package com.rain.common.dao.impl;

import com.rain.common.dao.Dao;
import com.rain.common.dao.TxUtils;
import com.rain.common.ice.v1.exception.SysException;
import com.rain.common.uitls.CamelNameUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoMybatisImpl implements Dao {


    private SqlSession getDao(String ds) {
        return TxUtils.getSqlSession();
    }

    public List<Map<String, Object>> query(String namespace, String statement) {
        return query(null, namespace, statement);
    }

    public List<Map<String, Object>> query(String ds, String namespace, String statement) {
    	SqlSession sqlSession = null;
        try {
        	sqlSession = getDao(ds);
        	List<Map<String, Object>> dataList = sqlSession
                .selectList(changeStatement(namespace, statement));
        	return CamelNameUtils.listToCamel(dataList);
        }finally{
        	TxUtils.close(sqlSession);
        }
      
    }

    public List<Map<String, Object>> query(String namespace, String statement, int limit, int offset) {
        return query(null, namespace, statement, limit, offset);
    }

    public List<Map<String, Object>> query(String ds, String namespace, String statement, int limit, int offset) {
    	SqlSession sqlSession = null;
        try {
        	sqlSession = getDao(ds);
	        RowBounds rowBounds = new RowBounds((offset - 1) * limit, limit);
	        List<Map<String, Object>> dataList =sqlSession
	                .<Map<String, Object>> selectList(changeStatement(namespace, statement), null, rowBounds);
	        return CamelNameUtils.listToCamel(dataList);
	    }finally{
	    	TxUtils.close(sqlSession);
	    }
    }

    public List<Map<String, Object>> query(String namespace, String statement, Map<String, Object> paramData) {
        return query(null, namespace, statement, paramData);
    }

    public List<Map<String, Object>> query(String ds, String namespace, String statement,
            Map<String, Object> paramData) {
    	SqlSession sqlSession = null;
        try {
        	sqlSession = getDao(ds);
        	List<Map<String, Object>> dataList = sqlSession.<Map<String, Object>> selectList(changeStatement(namespace, statement), paramData);
        	return CamelNameUtils.listToCamel(dataList);
        }finally{
	    	TxUtils.close(sqlSession);
	    }
    
    }

    public List<Map<String, Object>> query(String namespace, String statement, Map<String, Object> paramData, int limit,
            int offset) {
        return query(null, namespace, statement, paramData, limit, offset);
    }

    public List<Map<String, Object>> query(String ds, String namespace, String statement, Map<String, Object> paramData,
            int limit, int offset) {
    	SqlSession sqlSession = null;
        try {
        	sqlSession = getDao(ds);
        	RowBounds rowBounds = new RowBounds((offset - 1) * limit, limit);
        	List<Map<String, Object>> dataList = sqlSession
                .<Map<String, Object>> selectList(changeStatement(namespace, statement), paramData, rowBounds);
            return CamelNameUtils.listToCamel(dataList);
        }finally{
	    	TxUtils.close(sqlSession);
	    }
    }

    public int count(String namespace, String statement) {
        return count(null, namespace, statement);
    }

    public int count(String ds, String namespace, String statement) {
    	SqlSession sqlSession = null;
        try {
        	sqlSession = getDao(ds);
    		return sqlSession.selectOne(changeStatement(namespace, statement));
        
	    }finally{
	    	TxUtils.close(sqlSession);
	    }
    }

    public int count(String namespace, String statement, Map<String, Object> paramData) {
        return count(null, namespace, statement, paramData);
    }

    public int count(String ds, String namespace, String statement, Map<String, Object> paramData) {
    	SqlSession sqlSession = null;
        try {
        	sqlSession = getDao(ds);
        	return sqlSession.selectOne(changeStatement(namespace, statement), paramData);
        }finally{
	    	TxUtils.close(sqlSession);
	    }
    }

    public Map<String, Object> get(String namespace, String statement, Map<String, Object> paramData) {
        return get(null, namespace, statement, paramData);
    }

    public Map<String, Object> get(String ds, String namespace, String statement, Map<String, Object> paramData) {
    	SqlSession sqlSession = null;
        try {
        	sqlSession = getDao(ds);
	        Map<String, Object> dataMap = sqlSession.<Map<String, Object>> selectOne(changeStatement(namespace, statement),
	                paramData);
	        return CamelNameUtils.mapToCamel(dataMap);
        }finally{
	    	TxUtils.close(sqlSession);
	    }
    }

    public Map<String, Object> load(String namespace, String key, String value) {
        return load(null, namespace, key, value);
    }

    public Map<String, Object> load(String ds, String namespace, String key, String value) {
    	SqlSession sqlSession = null;
        try {
        	sqlSession = getDao(ds);
	        Map<String, String> param = new HashMap<String, String>();
	        param.put(key, value);
	        Map<String, Object> dataMap = sqlSession.<Map<String, Object>> selectOne(changeStatement(namespace, "load"),
	                param);
	        return CamelNameUtils.mapToCamel(dataMap);
	    }finally{
	    	TxUtils.close(sqlSession);
	    }
        
    }

    public Map<String, Object> load(String ds, String namespace, Map<String, Object> param) {
        SqlSession sqlSession = null;
        try {
            sqlSession = getDao(ds);
            Map<String, Object> dataMap = sqlSession.<Map<String, Object>> selectOne(changeStatement(namespace, "load"),
                    param);
            return CamelNameUtils.mapToCamel(dataMap);
        }finally{
            TxUtils.close(sqlSession);
        }

    }
    public void insert(String namespace, String statement, Map<String, Object> paramData) {
        insert(null, namespace, statement, paramData);
    }

    public void insert(String ds, String namespace, String statement, Map<String, Object> paramData) {
    	SqlSession sqlSession = null;
        try {
        	sqlSession = getDao(ds);
        	sqlSession.insert(changeStatement(namespace, statement), paramData);
        } catch (Exception e) {
            String msg = "Error Case:" + e.getMessage() + ";paramData:"
                    + (paramData != null ? paramData.toString() : "");
            throw new SysException(msg, e.getCause());
        }finally{
	    	TxUtils.close(sqlSession);
	    }
    }

    public int update(String namespace, String statement, Map<String, Object> paramData) {
        return update(null, namespace, statement, paramData);
    }

    public int update(String ds, String namespace, String statement, Map<String, Object> paramData) {
    	SqlSession sqlSession = null;
        try {
        	sqlSession = getDao(ds);
            return sqlSession.update(changeStatement(namespace, statement), paramData);
        } catch (Exception e) {
            String msg = "Error Case:" + e.getMessage() + ";paramData:"
                    + (paramData != null ? paramData.toString() : "");
            throw new SysException(msg, e.getCause());
        }finally{
	    	TxUtils.close(sqlSession);
	    }
    }

    public int delete(String namespace, String statement, Map<String, Object> paramData) {
        return delete(null, namespace, statement, paramData);
    }

    public int delete(String ds, String namespace, String statement, Map<String, Object> paramData) {
    	SqlSession sqlSession = null;
        try {
        	sqlSession = getDao(ds);
            return getDao(ds).delete(changeStatement(namespace, statement), paramData);
        } catch (Exception e) {
            String msg = "Error Case:" + e.getMessage() + ";paramData:"
                    + (paramData != null ? paramData.toString() : "");
            throw new SysException(msg, e.getCause());
        }finally {
			TxUtils.close(sqlSession);
		}
    }

    private String changeStatement(String namespace, String statement) {
        return namespace + "." + statement;
    }

    public String getSql(String namespace, String statement, Map<String, Object> paramData) {
        return getSql(null, namespace, statement, paramData);
    }

    @Override
    public String getSql(String ds, String namespace, String statement, Map<String, Object> paramData) {
    	SqlSession sqlSession = null;
        try {
        	sqlSession = getDao(ds);
            MappedStatement ms = sqlSession.getConfiguration().getMappedStatement(namespace + "." + statement);
            BoundSql boundSql = ms.getBoundSql(paramData);
            return boundSql.getSql();
        } catch (Exception e) {
            return namespace + "." + statement + "is error!";
        }finally {
        	TxUtils.close(sqlSession);
		}
    }

}
