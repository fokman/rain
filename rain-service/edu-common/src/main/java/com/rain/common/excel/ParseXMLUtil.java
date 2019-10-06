package com.rain.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 解析xml工具类
 * @author PCCW-80352891
 *
 */
@SuppressWarnings("rawtypes")
public class ParseXMLUtil {

    /**entity map对象，key:name ,value:entity的属性map集**/
    public Map<String, Map<String, String>> entityMap;

    /**column map 对象，key:entityName_colName , value:column的属性map集 **/
    public Map<String, Map<String, String>> columnMap;

    /**rule map 对象，key:entityName_colName_ruleName, value: rule 的map集：找到一行rule**/
    public Map<String, Map<String, String>> ruleMap;

    /**rules map 对象, key:entityName_colName, value: rules 的map集:找到该column下所有的rule**/
    public Map<String, List<Map<String, String>>> columnRulesMap;

    /**entity--column map: key:entityName, value: column list:根据实体类名得到所有的列**/
    public Map<String, List> columnListMap;

    /**column list**/
    public List<Map<String, String>> columnList;

    /**开始解析xml文件**/
    public ParseXMLUtil(File xmlFilePath) {
        FileInputStream in = null;
        try {
            if (xmlFilePath == null) {
                throw new FileNotFoundException();
            }
            SAXReader reader = new SAXReader();
            in = new FileInputStream(xmlFilePath);
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            Iterator itEntity = root.elements("entity").iterator();
            while (itEntity.hasNext()) {
                Element entity = (Element) itEntity.next();
                parseEntity(entity);
            }

            /**测试entityMap 是否正确**/
            /* Map enMap = (Map) this.getEntityMap().get("用户表");
            Set<?> set = enMap.keySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                String uu = (String) it.next();
                System.out.println("entity properties:" + uu + " = " + enMap.get(uu));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**开始解析entity**/
    public void parseEntity(Element entity) {
        if (entity != null) {
            columnListMap = new HashMap<String, List>();
            columnMap = new HashMap<String, Map<String, String>>();
            entityMap = new HashMap<String, Map<String, String>>();
            ruleMap = new HashMap<String, Map<String, String>>();
            columnRulesMap = new HashMap<String, List<Map<String, String>>>();
            columnList = new ArrayList<Map<String, String>>();

            setEntityMap(entity);
            String entityName = entity.attributeValue("name");
            Iterator itColumn = entity.elements("column").iterator();
            while (itColumn.hasNext()) {
                Element column = (Element) itColumn.next();
                setColumnMap(entityName, column);
            }
            columnListMap.put(entityName, columnList);
        }
    }

    /**将entity放入entityMap中**/
    public void setEntityMap(Element entity) {
        Map<String, String> ent = new HashMap<String, String>();
        String name = entity.attributeValue("name");
        String code = entity.attributeValue("code");
        ent.put("name", name);
        ent.put("code", code);
        entityMap.put(name, ent);
    }

    /**
    * 方法描述
    * 将column放入columnMap中
    * @param entityName
    * @param column
    * @创建日期 2016年6月29日
    */
    public void setColumnMap(String entityName, Element column) {
        if (column != null) {
            Map<String, String> col = new HashMap<String, String>();
            String name = column.attributeValue("name");
            String code = column.attributeValue("code");
            String type = column.attributeValue("type");
            col.put("name", name);
            col.put("code", code);
            col.put("type", type);
            String columnMapKey = entityName + "_" + name; //eg:  用户表_用户名
            columnMap.put(columnMapKey, col);
            columnList.add(col);
            Iterator ruleIt = column.elements("rules").iterator(); //获得rules
            while (ruleIt.hasNext()) {
                Element rules = (Element) ruleIt.next();
                Iterator rule = rules.elements("rule").iterator(); //获得 rule
                while (rule.hasNext()) {
                    Element ruleValid = (Element) rule.next(); //获得每一行rule
                    setRuleMap(entityName, name, ruleValid);
                }
            }
        }
    }

    /**
    * 方法描述
    * 将 rule 验证规则放入ruleMap中
    * @param entityName
    * @param columnName
    * @param ruleValid
    * @创建日期 2016年6月29日
    */
    public void setRuleMap(String entityName, String columnName, Element ruleValid) {
        if (ruleValid != null) {
            String ruleName = ruleValid.attributeValue("name");
            String ruleMsg = ruleValid.attributeValue("message");
            Map<String, String> ruleValidMap = new HashMap<String, String>();
            ruleValidMap.put("name", ruleName);
            ruleValidMap.put("message", ruleMsg);
            String ruleStrKey = entityName + "_" + columnName + "_" + ruleName;
            String colStrKey = entityName + "_" + columnName;
            if (this.getColumnRulesMap().containsKey(colStrKey)) {
                List<Map<String, String>> valids = (List<Map<String, String>>) this.getColumnRulesMap().get(colStrKey);
                valids.add(ruleValidMap);
            } else {
                List<Map<String, String>> valids = new ArrayList<Map<String, String>>();
                valids.add(ruleValidMap);
                this.columnRulesMap.put(colStrKey, valids); //将每个column下的所有rules存入该map中
            }
            ruleMap.put(ruleStrKey, ruleValidMap); //将每个column下的一条rule存入该map中
        }
    }

    public Map<String, Map<String, String>> getEntityMap() {
        return entityMap;
    }

    public void setEntityMap(Map<String, Map<String, String>> entityMap) {
        this.entityMap = entityMap;
    }

    public Map<String, Map<String, String>> getColumnMap() {
        return columnMap;
    }

    public void setColumnMap(Map<String, Map<String, String>> columnMap) {
        this.columnMap = columnMap;
    }

    public Map<String, Map<String, String>> getRuleMap() {
        return ruleMap;
    }

    public void setRuleMap(Map<String, Map<String, String>> ruleMap) {
        this.ruleMap = ruleMap;
    }

    public Map<String, List<Map<String, String>>> getColumnRulesMap() {
        return columnRulesMap;
    }

    public void setColumnRulesMap(Map<String, List<Map<String, String>>> columnRulesMap) {
        this.columnRulesMap = columnRulesMap;
    }

    public Map<String, List> getColumnListMap() {
        return columnListMap;
    }

    public void setColumnListMap(Map<String, List> columnListMap) {
        this.columnListMap = columnListMap;
    }

}
