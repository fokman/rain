package com.rain.common.servcie;

import com.rain.common.dao.Dao;
import com.rain.common.dao.impl.DaoMybatisImpl;
import com.rain.common.ice.v1.exception.SysException;
import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.ice.v1.model.IceRespose;
import org.apache.commons.lang3.StringUtils;
//import javax.inject.Inject;
//import javax.inject.Named;
//import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rain.common.uitls.SysCodeUtils;

/**
 * 源文件名：BaseService.java 文件版本：1.0.0 创建作者：冰风影 创建日期：2016-05-6 修改作者：冰风影
 * 修改日期：2016-05-16 文件描述：BaseService 是Java的基础服务类，封装的mybaits的CRUD操作 package
 * com.zjhz.common.dao 是封装的mybaits包
 * 
 * 新增 insert(context, NAMESPACE); 查询 query(context, NAMESPACE); 更新
 * update(context, NAMESPACE); 删除 delete(context, NAMESPACE); 分页
 * queryByPage(context, NAMESPACE); 参考：edu-org-auth的服务端代码OrgUserDiyService.java
 * 客户端调用代码edu-org-auth的edu-org-auth的Client.java 版权所有：Copyright 2016 zjhz, Inc.
 * All Rights Reserved.
 */

public class BaseService {
    public static final Logger logger = LogManager.getLogger(BaseService.class);
    private static final String QUERY = "query";
    private static final String COUNT = "count";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String INSERT = "insert";
    public static final String IS_PAGE = "isPage";
    public static final String PAGE = "page";
    public static final String PAGE_SIZE = "pageSize";
    public static final String DTL = "dtl";
    public static final String LIST = "list";
    public static final String LIST_ALL = "listAll";

    // @Inject
    // @Named("daoMybatis")
    private Dao dao;

    public Dao getDao() {

        if (this.dao == null) {
            dao = new DaoMybatisImpl();
        }

        return dao;
    }

    private String getDs() {
        return "";
    }

    /*
     * protected Map<String, Object> mapStrtoObj(Map<String, String> paramData)
     * { Map<String, Object> params = new HashMap<String, Object>(); if
     * (paramData != null && !paramData.isEmpty()){ Set set =
     * paramData.keySet(); for(Iterator iter = set.iterator(); iter.hasNext();){
     * String key = (String)iter.next(); String value=paramData.get(key).trim();
     * if (!StringUtils.isBlank(value)){ params.put(key, value); } } }
     * //params.putAll(paramData); return params; }
     */
    public IceRespose query(IceRequest context, String namespace) {
        IceRespose iceRespose = new IceRespose();
        try {
            if (context.getAttr().size() == 0) {
                iceRespose.setData(getDao().query(getDs(), namespace, QUERY));
            } else {
                iceRespose.setData(getDao().query(getDs(), namespace, QUERY, context.getAttrMap()));
            }
            iceRespose.setMsg("查询到" + iceRespose.getTotal() + "条记录!");
            iceRespose.setCode(0);
        } catch (Exception e) {
            logger.error(e.getCause());
            iceRespose.setCode(9);
            throw new SysException("查询失败,系统异常!case:" + e.getMessage(), e.getCause());
        }
        return iceRespose;
    }

    public IceRespose queryByPage(IceRequest context, String namespace) {
        IceRespose iceRespose = new IceRespose();
        try {
            // 每页显示记录条数
            int limit = context.getAttrAsInt(PAGE_SIZE, 10);
            // 第几页
            int pageNum = context.getAttrAsInt(PAGE, 1);
            if (context.getAttr().size() == 0) {
                iceRespose.setData(getDao().query(getDs(), namespace, QUERY, limit, pageNum));
                iceRespose.setTotal(getDao().count(getDs(), namespace, COUNT));
            } else {
                iceRespose.setData(getDao().query(getDs(), namespace, QUERY, context.getAttrMap(), limit, pageNum));
                iceRespose.setTotal(getDao().count(getDs(), namespace, COUNT, context.getAttrMap()));
            }
            iceRespose.setMsg("查询到" + iceRespose.getTotal() + "条记录!");
            iceRespose.setCode(0);
        } catch (Exception e) {
            logger.error(e.getCause());
            iceRespose.setCode(1);
            throw new SysException("查询失败,系统异常!case:" + e.getMessage(), e.getCause());
        }
        return iceRespose;
    }

    public IceRespose get(IceRequest context, String namespace, String statement) {
        IceRespose iceRespose = new IceRespose();
        try {
            if (statement == null || statement.length() == 0) {
                statement = QUERY;
            }
            iceRespose.setData(getDao().get(getDs(), namespace, statement, context.getAttrMap()));
            iceRespose.setMsg("查询到" + iceRespose.getTotal() + "条记录!");
            iceRespose.setCode(0);
        } catch (Exception e) {
            logger.error(e.getCause());
            iceRespose.setCode(9);
            throw new SysException("查询失败,系统异常!case:" + e.getMessage(), e.getCause());
        }
        return iceRespose;
    }

    public IceRespose query(IceRequest context, String namespace, String statement) {
        IceRespose iceRespose = new IceRespose();
        try {
            if (statement == null || statement.length() == 0) {
                statement = QUERY;
            }
            iceRespose.setData(getDao().query(getDs(), namespace, statement, context.getAttrMap()));
            iceRespose.setMsg("查询到" + iceRespose.getTotal() + "条记录!");
            iceRespose.setCode(0);
        } catch (Exception e) {
            logger.error(e.getCause());
            iceRespose.setCode(9);
            throw new SysException("查询失败,系统异常!case:" + e.getMessage(), e.getCause());
        }
        return iceRespose;
    }

    public IceRespose queryByPage(IceRequest context, String namespace, String queryStatement, String countStatement) {
        IceRespose iceRespose = new IceRespose();
        try {
            if (queryStatement == null || queryStatement.length() == 0) {
                queryStatement = QUERY;
            }
            if (countStatement == null || countStatement.length() == 0) {
                countStatement = COUNT;
            }
            // 每页显示记录条数
            int limit = context.getAttrAsInt(PAGE_SIZE, 10);
            // 第几页
            int pageNum = context.getAttrAsInt(PAGE, 1);
            iceRespose
                    .setData(getDao().query(getDs(), namespace, queryStatement, context.getAttrMap(), limit, pageNum));
            iceRespose.setTotal(getDao().count(getDs(), namespace, countStatement, context.getAttrMap()));
            iceRespose.setMsg("查询到" + iceRespose.getTotal() + "条记录!");
            iceRespose.setCode(0);
        } catch (Exception e) {
            logger.error(e.getCause());
            iceRespose.setCode(9);
            throw new SysException("查询失败,系统异常!case:" + e.getMessage(), e.getCause());
        }
        return iceRespose;
    }

    public IceRespose queryCombox(IceRequest context, String namespace, String queryStatement, String countStatement) {
        IceRespose iceRespose = new IceRespose();
        try {
            if (queryStatement == null || queryStatement.length() == 0) {
                queryStatement = QUERY;
            }
            if (countStatement == null || countStatement.length() == 0) {
                countStatement = COUNT;
            }
            String value = (String) context.getAttr("q");
            if (value != null && !value.trim().isEmpty()) {
                if (StringUtils.isNumeric(value)) {
                    context.addAttr("id", value);
                } else {
                    context.addAttr("name", value);
                }
            }
            iceRespose = queryByPage(context, namespace, queryStatement, countStatement);
        } catch (Exception e) {
            logger.error(e.getCause());
            iceRespose.setCode(1);
            throw new SysException("查询失败,系统异常!case:" + e.getMessage(), e.getCause());
        }
        return iceRespose;
    }

    public IceRespose insert(IceRequest context, String namespace) {
        IceRespose iceRespose = new IceRespose();
        try {
            getDao().insert(getDs(), namespace, INSERT, context.getAttrMap());
            iceRespose.setMsg("新增成功!");
            iceRespose.setCode(0);
        } catch (Exception e) {
            logger.error(e.getCause());
            iceRespose.setCode(9);
            throw new SysException("新增失败,系统异常!case:" + e.getMessage(), e.getCause());
        }
        return iceRespose;
    }

    public IceRespose update(IceRequest context, String namespace) {
        IceRespose iceRespose = new IceRespose();
        try {
            int count = getDao().update(getDs(), namespace, UPDATE, context.getAttrMap());
            iceRespose.setMsg("成功修改" + count + "条记录!");
            iceRespose.setCode(0);
        } catch (Exception e) {
            logger.error(e.getCause());
            iceRespose.setCode(9);
            throw new SysException("修改失败,系统异常!case:" + e.getMessage(), e.getCause());
        }
        return iceRespose;
    }

    public IceRespose delete(IceRequest context, String namespace) {
        IceRespose iceRespose = new IceRespose();
        try {
            // for(Map<String,Object> map : context.getRows()){
            getDao().delete(getDs(), namespace, DELETE, context.getAttrMap());
            // }
            iceRespose.setMsg("成功删除");// + context.getRows().size() + "条记录!");
            iceRespose.setCode(0);
        } catch (Exception e) {
            logger.error(e.getCause());
            iceRespose.setCode(9);
            throw new SysException("删除失败,系统异常!case:" + e.getMessage(), e.getCause());
        }
        return iceRespose;
    }

    public IceRespose setInfo(int code, String msg) {
        IceRespose respose = new IceRespose();
        respose.setCode(code);
        respose.setMsg(msg);
        return respose;
    }

    /**
    * 方法描述:设置错误代码及方法
    * @param code
    * @param msg
    * @return
    * @创建日期 2016年7月7日
     */
    public IceRespose setErrInfo(int code, String msg) {
        IceRespose respose = new IceRespose();
        msg = SysCodeUtils.getInstance().getProperty(code, msg);
        respose.setCode(code);
        respose.setMsg(msg);
        return respose;
    }

    /**
     * 
    * 方法描述直接设置错误码
    * @param errCode
    * @return
    * @创建日期 2016年7月7日
     */
    public IceRespose setErr(int errCode) {
        return setErrInfo(errCode, "");
    }

    /**
     * 设置错误码,并返回对应的业务数据
    * 方法描述
    * @param errCode
    * @param data
    * @return
    * @创建日期 2016年7月7日
     */
    public IceRespose setErr(int errCode, Object data) {
        IceRespose respose = new IceRespose();
        String msg = SysCodeUtils.getInstance().getProperty(errCode, "");
        respose.setCode(errCode);
        respose.setMsg(msg);
        respose.setData(data);
        return respose;
    }

}
