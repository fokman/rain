package com.rain.ice.dao;

import java.util.List;
import java.util.Map;

public interface Dao {


    List<Map<String, Object>> query(String namespace, String statement);

    List<Map<String, Object>> query(String namespace, String statement, int limit, int offset);

    List<Map<String, Object>> query(String namespace, String statement, Map<String, Object> paramData);

    List<Map<String, Object>> query(String namespace, String statement, Map<String, Object> paramData,
                                    int limit, int offset);

    int count(String namespace, String statement);

    int count(String namespace, String statement, Map<String, Object> paramData);

    Map<String, Object> get(String namespace, String statement, Map<String, Object> paramData);

    Map<String, Object> load(String namespace, String key, String value);

    void insert(String namespace, String statement, Map<String, Object> paramData);

    int update(String namespace, String statement, Map<String, Object> paramData);

    int delete(String namespace, String statement, Map<String, Object> paramData);

    String getSql(String namespace, String statement, Map<String, Object> paramData);

    List<Map<String, Object>> query(String ds, String namespace, String statement);

    List<Map<String, Object>> query(String ds, String namespace, String statement, int limit,
                                    int pageNum);

    List<Map<String, Object>> query(String ds, String namespace, String statement,
                                    Map<String, Object> paramData);

    List<Map<String, Object>> query(String ds, String namespace, String statement,
                                    Map<String, Object> paramData, int limit, int pageNum);

    int count(String ds, String namespace, String statement);

    int count(String ds, String namespace, String statement, Map<String, Object> paramData);

    Map<String, Object> get(String ds, String namespace, String statement,
                            Map<String, Object> paramData);

    Map<String, Object> load(String ds, String namespace, String key, String value);

    Map<String, Object> load(String ds, String namespace, Map<String, Object> paramData);

    void insert(String ds, String namespace, String statement, Map<String, Object> paramData);

    int update(String ds, String namespace, String statement, Map<String, Object> paramData);

    int delete(String ds, String namespace, String statement, Map<String, Object> paramData);

    String getSql(String ds, String namespace, String statement, Map<String, Object> paramData);

}