package com.rain.ice.service;

import com.rain.ice.dao.Dao;
import com.rain.ice.dao.DaoMybatisImpl;
import com.rain.ice.model.IceRequest;
import com.rain.ice.model.IceResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class BaseService {

    private static final String QUERY = "query";
    private static final String COUNT = "count";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String INSERT = "insert";
    private  static final String PAGE = "page";
    private static final String PAGE_SIZE = "pageSize";

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


    public IceResponse query(IceRequest context, String namespace) {
        IceResponse iceResponse = new IceResponse();
        try {
            if (context.getAttr().size() == 0) {
                iceResponse.setData(getDao().query(getDs(), namespace, QUERY));
            } else {
                iceResponse.setData(getDao().query(getDs(), namespace, QUERY, context.getAttrMap()));
            }
            iceResponse.setMsg("查询到" + iceResponse.getTotal() + "条记录!");
            iceResponse.setCode(0);
        } catch (Exception e) {
            log.error("{}", e);
            iceResponse.setCode(9);
        }
        return iceResponse;
    }

    public IceResponse queryByPage(IceRequest context, String namespace) {
        IceResponse IceResponse = new IceResponse();
        try {
            // 每页显示记录条数
            int limit = context.getAttrAsInt(PAGE_SIZE, 10);
            // 第几页
            int pageNum = context.getAttrAsInt(PAGE, 1);
            if (context.getAttr().size() == 0) {
                IceResponse.setData(getDao().query(getDs(), namespace, QUERY, limit, pageNum));
                IceResponse.setTotal(getDao().count(getDs(), namespace, COUNT));
            } else {
                IceResponse.setData(getDao().query(getDs(), namespace, QUERY, context.getAttrMap(), limit, pageNum));
                IceResponse.setTotal(getDao().count(getDs(), namespace, COUNT, context.getAttrMap()));
            }
            IceResponse.setMsg("查询到" + IceResponse.getTotal() + "条记录!");
            IceResponse.setCode(0);
        } catch (Exception e) {
            log.error("{}", e);
            IceResponse.setCode(1);
        }
        return IceResponse;
    }

    public IceResponse get(IceRequest context, String namespace, String statement) {
        IceResponse IceResponse = new IceResponse();
        try {
            if (statement == null || statement.length() == 0) {
                statement = QUERY;
            }
            IceResponse.setData(getDao().get(getDs(), namespace, statement, context.getAttrMap()));
            IceResponse.setMsg("查询到" + IceResponse.getTotal() + "条记录!");
            IceResponse.setCode(0);
        } catch (Exception e) {
            log.error("{}", e);
            IceResponse.setCode(9);
        }
        return IceResponse;
    }

    public IceResponse query(IceRequest context, String namespace, String statement) {
        IceResponse IceResponse = new IceResponse();
        try {
            if (statement == null || statement.length() == 0) {
                statement = QUERY;
            }
            IceResponse.setData(getDao().query(getDs(), namespace, statement, context.getAttrMap()));
            IceResponse.setMsg("查询到" + IceResponse.getTotal() + "条记录!");
            IceResponse.setCode(0);
        } catch (Exception e) {
            log.error("{}", e);
            IceResponse.setCode(9);
        }
        return IceResponse;
    }

    public IceResponse queryByPage(IceRequest context, String namespace, String queryStatement, String countStatement) {
        IceResponse IceResponse = new IceResponse();
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
            IceResponse
                    .setData(getDao().query(getDs(), namespace, queryStatement, context.getAttrMap(), limit, pageNum));
            IceResponse.setTotal(getDao().count(getDs(), namespace, countStatement, context.getAttrMap()));
            IceResponse.setMsg("查询到" + IceResponse.getTotal() + "条记录!");
            IceResponse.setCode(0);
        } catch (Exception e) {
            log.error("{}", e);
            IceResponse.setCode(9);
        }
        return IceResponse;
    }

    public IceResponse queryCombox(IceRequest context, String namespace, String queryStatement, String countStatement) {
        IceResponse IceResponse = new IceResponse();
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
            IceResponse = queryByPage(context, namespace, queryStatement, countStatement);
        } catch (Exception e) {
            log.error("{}", e);
            IceResponse.setCode(1);
        }
        return IceResponse;
    }

    public IceResponse insert(IceRequest context, String namespace) {
        IceResponse IceResponse = new IceResponse();
        try {
            getDao().insert(getDs(), namespace, INSERT, context.getAttrMap());
            IceResponse.setMsg("新增成功!");
            IceResponse.setCode(0);
        } catch (Exception e) {
            log.error("{}", e);
            IceResponse.setCode(9);
        }
        return IceResponse;
    }

    public IceResponse update(IceRequest context, String namespace) {
        IceResponse IceResponse = new IceResponse();
        try {
            int count = getDao().update(getDs(), namespace, UPDATE, context.getAttrMap());
            IceResponse.setMsg("成功修改" + count + "条记录!");
            IceResponse.setCode(0);
        } catch (Exception e) {
            log.error("{}", e);
            IceResponse.setCode(9);
        }
        return IceResponse;
    }

    public IceResponse delete(IceRequest context, String namespace) {
        IceResponse IceResponse = new IceResponse();
        try {
            getDao().delete(getDs(), namespace, DELETE, context.getAttrMap());
            IceResponse.setMsg("成功删除");// + context.getRows().size() + "条记录!");
            IceResponse.setCode(0);
        } catch (Exception e) {
            log.error("{}", e);
            IceResponse.setCode(9);
        }
        return IceResponse;
    }

    public IceResponse setInfo(int code, String msg) {
        IceResponse respose = new IceResponse();
        respose.setCode(code);
        respose.setMsg(msg);
        return respose;
    }

    public IceResponse setErrInfo(int code, String msg) {
        IceResponse respose = new IceResponse();
//        msg = SysCodeUtils.getInstance().getProperty(code, msg);
        respose.setCode(code);
        respose.setMsg(msg);
        return respose;
    }

    /**
     * 方法描述直接设置错误码
     *
     * @param errCode
     * @return
     * @创建日期 2016年7月7日
     */
    public IceResponse setErr(int errCode) {
        return setErrInfo(errCode, "");
    }

}
