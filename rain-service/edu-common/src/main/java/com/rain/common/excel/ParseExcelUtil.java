package com.rain.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


/**
 *
 */
public class ParseExcelUtil {
    public FileInputStream fis;
    public HSSFWorkbook workBook;
    public ParseXMLUtil parseXmlUtil;
    public StringBuffer errorString;

    /**当前实体类的code**/
    public String curEntityCode;
    /**表头map对象：key:entityCode, value:headMap(index,headTitle)**/
    public Map<String, Map<Integer, String>> curEntityHeadMap;
    /**字段的必填：key:entityCode+headTitle, value:true(必填),false(不必填)**/
    public Map<String, Boolean> curEntityColRequired;
    /**存放每一行的数据**/
    public List<Map<String, Object>> listDatas;

    public ParseExcelUtil(File excelFile, File xmlFile) {
        try {
            if (excelFile == null) {
                throw new FileNotFoundException();
            }
            fis = new FileInputStream(excelFile);
            workBook = new HSSFWorkbook(fis);
            parseXmlUtil = new ParseXMLUtil(xmlFile);
            errorString = new StringBuffer();
            readExcelData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ParseExcelUtil(URL url, File xmlFile) {
        try {
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(180 * 1000);
            InputStream inStream = conn.getInputStream();
            workBook = new HSSFWorkbook(inStream);
            parseXmlUtil = new ParseXMLUtil(xmlFile);
            errorString = new StringBuffer();
            readExcelData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * 方法描述
    * 开始从excel读取数据
    * @创建日期 2016年6月29日
    */
    public void readExcelData() {
        int sheetSize = workBook.getNumberOfSheets();
        for (int i = 0; i < sheetSize; i++) {
            HSSFSheet sheet = workBook.getSheetAt(i);
            String entityName = workBook.getSheetName(i);
            int rowNumbers = sheet.getPhysicalNumberOfRows();
            Map<String, String> ent = (Map<String, String>) parseXmlUtil.getEntityMap().get(entityName);
            this.setCurEntityCode(String.valueOf(ent.get("code")));
            if (rowNumbers == 0) {
                errorString.append(ParseConstans.ERROR_EXCEL_NULL);
            }
            List colList = (List) parseXmlUtil.getColumnListMap().get(entityName);
            int xmlRowNum = colList.size();
            HSSFRow excelRow = sheet.getRow(0);
            int excelFirstRow = excelRow.getFirstCellNum();
            int excelLastRow = excelRow.getLastCellNum();
            if (xmlRowNum != (excelLastRow - excelFirstRow)) {
                errorString.append(ParseConstans.ERROR_EXCEL_COLUMN_NOT_EQUAL);
            }
            readSheetHeadData(sheet);
            readSheetColumnData(sheet, entityName);
        }
    }

    /**
    * 方法描述
    * 读取sheet页中的表头信息
    * @param sheet
    * @创建日期 2016年6月29日
    */
    public void readSheetHeadData(HSSFSheet sheet) {
        Map<Integer, String> headMap = new HashMap<Integer, String>();
        curEntityHeadMap = new HashMap<String, Map<Integer, String>>();
        curEntityColRequired = new HashMap<String, Boolean>();
        HSSFRow excelheadRow = sheet.getRow(0);
        int excelLastRow = excelheadRow.getLastCellNum();
        String headTitle = "";
        for (int i = 0; i < excelLastRow; i++) {
            HSSFCell cell = excelheadRow.getCell(i);
            headTitle = getStringCellValue(cell).trim();
            if (headTitle.endsWith("*")) {
                curEntityColRequired.put(this.getCurEntityCode() + "_" + headTitle, true);
            } else {
                curEntityColRequired.put(this.getCurEntityCode() + "_" + headTitle, false);
            }
            headMap.put(i, headTitle);
        }
        curEntityHeadMap.put(this.getCurEntityCode(), headMap);
    }

    /**
    * 方法描述
    * 读取sheet页里面的数据
    * @param sheet
    * @param entityName
    * @创建日期 2016年6月29日
    */
    public void readSheetColumnData(HSSFSheet sheet, String entityName) {
        HSSFRow excelheadRow = sheet.getRow(0);
        int excelLastcell = excelheadRow.getLastCellNum(); // excel总列数
        int excelRowNum = sheet.getLastRowNum(); // excel总行数

        for (int j = 0; j < excelRowNum; j++) {
            HSSFRow row = sheet.getRow(j);
            if (isBlankRow(row)) {
                excelRowNum = j;
            }

        }
        Map headMap = (Map) this.getCurEntityHeadMap().get(this.getCurEntityCode());
        Map colMap = parseXmlUtil.getColumnMap();
        listDatas = new ArrayList<Map<String, Object>>();
        for (int i = 1; i <= excelRowNum; i++) {// 行循环
            HSSFRow columnRow = sheet.getRow(i);
            if (columnRow != null) {
                Map<String, Object> curRowCellMap = new HashMap<String, Object>();
                for (int j = 0; j < excelLastcell; j++) { // 列循环
                    int cout = String.valueOf(headMap.get(j)).indexOf("*");
                    String headTitle = "";
                    if (cout == -1) {
                        headTitle = String.valueOf(headMap.get(j));
                    } else {
                        headTitle = String.valueOf(headMap.get(j)).substring(0, cout);
                    }
                    Map curColMap = (Map) colMap.get(entityName + "_" + headTitle);
                    String curColCode = String.valueOf(curColMap.get("code"));
                    String curColType = String.valueOf(curColMap.get("type"));
                    HSSFCell colCell = columnRow.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    String value = this.getStringCellValue(colCell);
                    if (value != null) {
                        value = value.trim();
                    } else {
                        value = "";
                    }

                    String xmlColType = String.valueOf(curColMap.get("type"));
                    if (xmlColType.equals("int")) {
                        int intVal = Integer.valueOf(value);
                        curRowCellMap.put(curColCode, intVal); // 将这一行的数据以code-value的形式存入map
                    } else {
                        curRowCellMap.put(curColCode, value);
                    }
                    /**验证cell数据**/
                    validateCellData(i + 1, j + 1, colCell, entityName, headTitle, curColType);
                }
                listDatas.add(curRowCellMap);
            }
        }
        /*        if (this.getErrorString().length() == 0) {// 如果没有任何错误，就保存
            saveExcelData(entityName);
            System.out.println("导入数据成功！");
        } else {
            // 清理所有的缓存clearMap();现在暂时未清理
            String[] strArr = errorString.toString().split("<br>");
            for (String s : strArr) {
                System.out.println(s);
            }
        }*/
    }

    /**
     * 如果是清除的行数，则重新计算excel总行数
     * @param row
     * @return
     */
    public static boolean isBlankRow(HSSFRow row) {
        if (row == null)
            return true;
        boolean result = true;
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            HSSFCell cell = row.getCell(i, HSSFRow.RETURN_BLANK_AS_NULL);
            String value = "";
            if (cell != null) {
                switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value = String.valueOf((int) cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    value = String.valueOf(cell.getCellFormula());
                    break;
                default:
                    break;
                }

                if (!value.trim().equals("")) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    /**
    * 方法描述
    * 验证单元格数据
    * @param curRow
    * @param curCol
    * @param colCell
    * @param entityName
    * @param headName
    * @param curColType
    * @创建日期 2016年6月29日
    */
    public void validateCellData(int curRow, int curCol, HSSFCell colCell, String entityName, String headName,
            String curColType) {
        List rulList = (List) parseXmlUtil.getColumnRulesMap().get(entityName + "_" + headName);
        if (rulList != null && rulList.size() > 0) {
            for (int i = 0; i < rulList.size(); i++) {
                Map rulM = (Map) rulList.get(i);
                String rulName = String.valueOf(rulM.get("name"));
                String rulMsg = String.valueOf(rulM.get("message"));
                String cellValue = this.getStringCellValue(colCell);
                if (rulName.equals(ParseConstans.RULE_NAME_NULLABLE)) {
                    if (cellValue == null || "".equals(cellValue)) {
                        errorString.append("第" + curRow + "行,第" + curCol + "列:" + rulMsg + "<br>");
                    }
                }
            }
        }
    }

    /**
     * 方法描述
     * 获得单元格字符串
     * @param cell
     * @return
     * @创建日期 2016年6月29日
     */
    public String getStringCellValue(HSSFCell cell) {
        if (cell == null) {
            return null;
        }
        String result = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_BOOLEAN:
            result = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                java.text.SimpleDateFormat TIME_FORMATTER = new java.text.SimpleDateFormat("yyyy-MM-dd");
                result = TIME_FORMATTER.format(cell.getDateCellValue());
            } else {
                double doubleValue = cell.getNumericCellValue();
                result = "" + doubleValue;
            }
            break;
        case HSSFCell.CELL_TYPE_STRING:
            if (cell.getRichStringCellValue() == null) {
                result = null;
            } else {
                result = cell.getRichStringCellValue().getString();
            }
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            result = null;
            break;
        case HSSFCell.CELL_TYPE_FORMULA:
            try {
                result = String.valueOf(cell.getNumericCellValue());
            } catch (Exception e) {
                result = cell.getRichStringCellValue().getString();
            }
            break;
        default:
            result = "";
        }

        return result;
    }

    public String getCurEntityCode() {
        return curEntityCode;
    }

    public void setCurEntityCode(String curEntityCode) {
        this.curEntityCode = curEntityCode;
    }

    public Map getCurEntityHeadMap() {
        return curEntityHeadMap;
    }

    public void setCurEntityHeadMap(Map curEntityHeadMap) {
        this.curEntityHeadMap = curEntityHeadMap;
    }

    public ParseXMLUtil getParseXmlUtil() {
        return parseXmlUtil;
    }

    public void setParseXmlUtil(ParseXMLUtil parseXmlUtil) {
        this.parseXmlUtil = parseXmlUtil;
    }

    public Map getCurEntityColRequired() {
        return curEntityColRequired;
    }

    public void setCurEntityColRequired(Map curEntityColRequired) {
        this.curEntityColRequired = curEntityColRequired;
    }

    public List<Map<String, Object>> getListDatas() {
        return listDatas;
    }

    public void setListDatas(List<Map<String, Object>> listDatas) {
        this.listDatas = listDatas;
    }

    public StringBuffer getErrorString() {
        return errorString;
    }

    public void setErrorString(StringBuffer errorString) {
        this.errorString = errorString;
    }

}
