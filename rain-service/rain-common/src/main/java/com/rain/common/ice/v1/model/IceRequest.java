package com.rain.common.ice.v1.model;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class IceRequest {
    //服务名称
    private String service;
    //方法名称
    private String method;
    //扩展参数. 例如：客户端IP. 客户端User-Agent
    private final Map<String, String> extData = new LinkedHashMap<>();
    //业务参数
    private Map<String, String> attr = new HashMap<>();
    /**
     * 业务参数,支持object，为了解决在mybatis中使用in
     * 如果把in中条件用字符串传进去，mybatis会在参数中加\转义，导致查询不出来
     * 例子: iceRequest.addAttr("paramTypes", "'org_user_status','org_dept_status'");
     * where param_type in ('\'org_user_status\',\'org_dept_status\'')
     */
    private final Map<String, Object> attrBiz = new HashMap<>();

    public IceRequest() {

    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void addAttr(String key, String value) {
        this.attr.put(key, value);
    }

    // 放入新的参数，需要把attr中的相同参数删除，否则查询数据库的时候会覆盖
    public void addAttr(String key, Object value) {
        this.attr.remove(key);
        this.attrBiz.put(key, value);
    }

    public void addAttr(Map<String, String> attr) {
        if (attr == null) {
            return;
        }
        this.attr.putAll(attr);
    }

    public Object removeAttr(String key) {
        return this.attr.remove(key);
    }

    public void clearAttr() {
        this.attr.clear();
    }

    //获取输入参数
    public Map<String, String> getAttr() {
        return attr;
    }

    /**
     * 转换参数，并去掉为空值的参数
     */
    public Map<String, Object> getAttrMap() {
        if (attr != null && !attr.isEmpty()) {
            attr.forEach((key, value) -> {
                String v = attr.get(key);
                if (StringUtils.isNotBlank(v)) {
                    attrBiz.put(key, v);
                }
            });
        }
        return attrBiz;
    }

    /**
     * 根据key获取输入参数
     *
     */
    public String getAttr(String key) {
        return this.attr.get(key);
    }

    public void setAttr(Map<String, String> attr) {
        if (attr == null) {
            return;
        }
        this.attr = attr;
    }

    public void setAttr(String key, String value) {
        this.attr.put(key, value);
    }

    /**
     * 获取输入参数，并转化为整形。如果变量不存在，则返回-1
     *
     */
    public Integer getAttrAsInt(String key) {
        return getAttrAsInt(key, -1);
    }

    public Integer getAttrAsInt(String key, Integer defaultValue) {
        Integer value = toInt(getAttr(key));
        return value == null ? defaultValue : value;
    }

    public Integer getAttrAsInt(Object object) {
        return object == null ? -1 : getAttrAsInt(object.toString());
    }

    /**
     * 获取输入参数，并转化为整形。如果变量不存在，则返回-1L
     *
     */
    public Long getAttrAsLong(String key) {
        return getAttrAsLong(key, -1L);
    }

    public Long getAttrAsLong(String key, Long defaultValue) {
        Long value = toLong(getAttr(key));
        return value == null ? defaultValue : value;
    }

    /**
     * 获取业务参数 不存在到返回, 不存在返回0.0
     *
     */
    public Double getAttrAsDouble(String key) {
        return getAttrAsDouble(key, 0.0);
    }

    public Double getAttrAsDouble(String key, Double defaultValue) {
        Double value = toDouble(getAttr(key));
        return value == null ? defaultValue : value;
    }

    /**
     * 获取业务参数，并转为字符串。如果不存在或为空，则返回""
     *
     */
    public String getAttrAsStr(String key) {
        return getAttrAsStr(key, "");
    }

    public String getAttrAsStr(String key, String defaultValue) {
        Object value = getAttr(key);
        return value == null ? defaultValue : value.toString();
    }

    /**
     * 扩展参数。 如ip,User-Agent
     *
     */
    public Map<String, String> getExtData() {
        return extData;
    }

    /**
     * 获取扩展参数.
     *
     */
    public Object getExtra(String key) {
        return extData.get(key);
    }

    public void setExtra(String key, String value) {
        this.extData.put(key, value);
    }

    /**
     * 获取扩展参数并且转化为Integer对象
     *
     */
    public Integer getExtraAsInt(String key) {
        return toInt(getExtra(key));
    }

    public Integer getExtraAsInt(String key, Integer defaultValue) {
        Integer value = toInt(getExtra(key));
        return value == null ? defaultValue : value;
    }

    /**
     * 获取扩展参数. 不存在返回null
     *
     */
    public Double getExtraAsDouble(String key) {
        return toDouble(getExtra(key));
    }

    public Double getExtraAsDouble(String key, Double defaultValue) {
        Double value = toDouble(getExtra(key));
        return value == null ? defaultValue : value;
    }

    /**
     * 获取扩展参数，并且转为字符串. 参数不存在，返回 "";
     *
     */
    public String getExtraAsStr(String key) {
        Object value = getExtra(key);
        return value == null ? "" : value.toString();
    }

    /**
     * 获取查询字符串
     *
     */
    public String getQueryString() {
        return getExtraAsStr("queryString");
    }

    /**
     * 设置查询字符串
     *
     */

    public void setQueryString(String queryString) {
        getExtData().put("queryString", queryString);
    }

    private static Double toDouble(Object obj) {
        if (obj == null || "".equals(obj.toString())) {
            return null;
        } else {
            try {
                return Double.parseDouble(obj.toString());
            } catch (Exception e) {
                return null;
            }
        }
    }

    private static Integer toInt(Object obj) {
        if (obj == null || "".equals(obj.toString())) {
            return null;
        } else {
            try {
                return Integer.parseInt(obj.toString());
            } catch (Exception e) {
                return null;
            }
        }
    }

    private static Long toLong(Object obj) {
        if (obj == null || "".equals(obj.toString())) {
            return null;
        } else {
            try {
                return Long.parseLong(obj.toString());
            } catch (Exception e) {
                return null;
            }
        }
    }

    /**
     * 清除输入参数
     */
    public void clearInputData() {
        this.clearAttr();
        this.extData.clear();
    }

    public String getClientIp() {
        return (String) getExtra("remoteAddr");
    }

    public String getUserAgent() {
        return (String) getExtra("userAgent");
    }

    public Map<String, Object> getAttrBiz() {
        return attrBiz;
    }

    public final static String STRING_01 = "";

    public final static String STRING_02 = " ";

}
