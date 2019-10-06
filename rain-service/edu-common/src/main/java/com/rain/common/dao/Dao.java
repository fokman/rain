package com.rain.common.dao;

import java.util.List;
import java.util.Map;

public abstract interface Dao {

//    public SqlSession getSqlSession();

    public static final String MYBATIS_DAO = "daoMybatis";

    public abstract List<Map<String, Object>> query(String namespace, String statement);

    public abstract List<Map<String, Object>> query(String namespace, String statement, int limit, int offset);

    public abstract List<Map<String, Object>> query(String namespace, String statement, Map<String, Object> paramData);

    public abstract List<Map<String, Object>> query(String namespace, String statement, Map<String, Object> paramData,
            int limit, int offset);

    public abstract int count(String namespace, String statement);

    public abstract int count(String namespace, String statement, Map<String, Object> paramData);

    public abstract Map<String, Object> get(String namespace, String statement, Map<String, Object> paramData);

    public abstract Map<String, Object> load(String namespace, String key, String value);

    public abstract void insert(String namespace, String statement, Map<String, Object> paramData);

    public abstract int update(String namespace, String statement, Map<String, Object> paramData);

    public abstract int delete(String namespace, String statement, Map<String, Object> paramData);

    public abstract String getSql(String namespace, String statement, Map<String, Object> paramData);

    public abstract List<Map<String, Object>> query(String ds, String namespace, String statement);

    public abstract List<Map<String, Object>> query(String ds, String namespace, String statement, int limit,
            int pageNum);

    public abstract List<Map<String, Object>> query(String ds, String namespace, String statement,
            Map<String, Object> paramData);

    public abstract List<Map<String, Object>> query(String ds, String namespace, String statement,
            Map<String, Object> paramData, int limit, int pageNum);

    public abstract int count(String ds, String namespace, String statement);

    public abstract int count(String ds, String namespace, String statement, Map<String, Object> paramData);

    public abstract Map<String, Object> get(String ds, String namespace, String statement,
            Map<String, Object> paramData);

    public abstract Map<String, Object> load(String ds, String namespace, String key, String value);

    public abstract Map<String, Object> load(String ds, String namespace, Map<String, Object> paramData);

    public abstract void insert(String ds, String namespace, String statement, Map<String, Object> paramData);

    public abstract int update(String ds, String namespace, String statement, Map<String, Object> paramData);

    public abstract int delete(String ds, String namespace, String statement, Map<String, Object> paramData);

    public abstract String getSql(String ds, String namespace, String statement, Map<String, Object> paramData);

}