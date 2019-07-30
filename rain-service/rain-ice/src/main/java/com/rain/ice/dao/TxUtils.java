package com.rain.ice.dao;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

@Slf4j
public class TxUtils {

    private static final ThreadLocal<SqlSession> THREAD_LOCAL = new ThreadLocal<SqlSession>();

    public static SqlSession getSqlSession() {
        SqlSession sqlSession = THREAD_LOCAL.get();
        if (sqlSession == null) {
            sqlSession = DruidDataSourceFactory.sqlSessionFactory.openSession(true);
        }
        return sqlSession;
    }

    public static void close(SqlSession session) {
        SqlSession sqlSession = THREAD_LOCAL.get();
        if (sqlSession != session) {
            log.info("TxUtils.close开始释放连接..." + session);
            session.close();
            log.info("TxUtils.close释放连接成功!" + session);
        }
    }


    public static void commit() {
        SqlSession conn = THREAD_LOCAL.get();
        if (conn != null) {
            conn.commit();
        }
    }

    public static void rollback() {
        SqlSession conn = THREAD_LOCAL.get();
        if (conn != null) {
            conn.rollback();
        }
    }

    public static void beginTransaction() {
        SqlSession conn = DruidDataSourceFactory.sqlSessionFactory.openSession(false);
        THREAD_LOCAL.set(conn);
    }

    public static void endTransaction() {
        SqlSession conn = null;
        try {
            conn = THREAD_LOCAL.get();
        } finally {
            if (conn != null) {
                conn.close();
            }
            THREAD_LOCAL.remove();
        }
    }

}
