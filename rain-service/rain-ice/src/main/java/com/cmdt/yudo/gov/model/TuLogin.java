package com.cmdt.yudo.gov.model;

import java.util.Date;

public class TuLogin {
    private String vin;

    private Integer battCnt;

    private Integer battCodeLen;

    private String battCodes;

    private Integer battId;

    private Date createTime;

    private String gbHex;

    private String hexStr;

    private String iccid;

    private String loginSn;

    private Integer loginStatus;

    private String mcuactutem;

    private String simPhoneNum;

    private String plateNum;

    private String protVer;

    private String protocolId;

    private Date traceTime;

    private String tspSn;

    private String tuSupplierCode;

    private String tuSerialCode;

    private Date updateTime;

    private String vehType;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin == null ? null : vin.trim();
    }

    public Integer getBattCnt() {
        return battCnt;
    }

    public void setBattCnt(Integer battCnt) {
        this.battCnt = battCnt;
    }

    public Integer getBattCodeLen() {
        return battCodeLen;
    }

    public void setBattCodeLen(Integer battCodeLen) {
        this.battCodeLen = battCodeLen;
    }

    public String getBattCodes() {
        return battCodes;
    }

    public void setBattCodes(String battCodes) {
        this.battCodes = battCodes == null ? null : battCodes.trim();
    }

    public Integer getBattId() {
        return battId;
    }

    public void setBattId(Integer battId) {
        this.battId = battId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGbHex() {
        return gbHex;
    }

    public void setGbHex(String gbHex) {
        this.gbHex = gbHex == null ? null : gbHex.trim();
    }

    public String getHexStr() {
        return hexStr;
    }

    public void setHexStr(String hexStr) {
        this.hexStr = hexStr == null ? null : hexStr.trim();
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getLoginSn() {
        return loginSn;
    }

    public void setLoginSn(String loginSn) {
        this.loginSn = loginSn == null ? null : loginSn.trim();
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getMcuactutem() {
        return mcuactutem;
    }

    public void setMcuactutem(String mcuactutem) {
        this.mcuactutem = mcuactutem == null ? null : mcuactutem.trim();
    }

    public String getSimPhoneNum() {
        return simPhoneNum;
    }

    public void setSimPhoneNum(String simPhoneNum) {
        this.simPhoneNum = simPhoneNum == null ? null : simPhoneNum.trim();
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum == null ? null : plateNum.trim();
    }

    public String getProtVer() {
        return protVer;
    }

    public void setProtVer(String protVer) {
        this.protVer = protVer == null ? null : protVer.trim();
    }

    public String getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId == null ? null : protocolId.trim();
    }

    public Date getTraceTime() {
        return traceTime;
    }

    public void setTraceTime(Date traceTime) {
        this.traceTime = traceTime;
    }

    public String getTspSn() {
        return tspSn;
    }

    public void setTspSn(String tspSn) {
        this.tspSn = tspSn == null ? null : tspSn.trim();
    }

    public String getTuSupplierCode() {
        return tuSupplierCode;
    }

    public void setTuSupplierCode(String tuSupplierCode) {
        this.tuSupplierCode = tuSupplierCode == null ? null : tuSupplierCode.trim();
    }

    public String getTuSerialCode() {
        return tuSerialCode;
    }

    public void setTuSerialCode(String tuSerialCode) {
        this.tuSerialCode = tuSerialCode == null ? null : tuSerialCode.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVehType() {
        return vehType;
    }

    public void setVehType(String vehType) {
        this.vehType = vehType == null ? null : vehType.trim();
    }
}