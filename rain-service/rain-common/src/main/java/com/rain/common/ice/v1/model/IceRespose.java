package com.rain.common.ice.v1.model;

import java.util.List;
import java.util.Map;

public class IceRespose {

    /**
     * 服务返回消息
     */
    private String msg;

    /**
     * 是否执行成功 0:成功 1:失败
     */
    private Integer code = new Integer(0);

    /**
     * 总数
     */
    private int total = 0;

    /**
     * 业务数据 没有使用attr的原因是，attr主要是用来放置查询条件的
     */
    private Object data = null;

    public void setCode(Integer code, String msg) {
        this.setCode(code);
        this.msg = msg;
    }

    /**
     * 设置状态码。0-成功。非0-不成功.<br>
     * 当设置为0是，success会被设置为true。 当设置为false是，success会被设置为false。
     * 
     * @param code
     */
    public void setCode(Integer code) {
        if (code == null) {
            code = -1;
        }
        this.code = code;
    }

    public void setCode(int code, String msg) {
        setCode(code);
        setMsg(msg);
    }

    public Integer getCode() {
        return code;

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
        if (data instanceof List) {
            this.total = ((List) data).size();
        } else if (data instanceof Map) {
            if (data != null && !((Map) data).isEmpty()) {
                this.total = 1;
            }
        }
    }

    /**
     * 此方法仅设置data信息，不改变 tot的信息
    * 方法描述
    * @param data
    * @创建日期 2016年8月9日
    */
    public void setDataOnly(Object data) {
        this.data = data;
    }

}
