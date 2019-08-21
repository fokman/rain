package com.rain.common.uitls;

public enum SourceType {
    TEACHER("教师"), PARENT("家长"), ADMIN("学校管理员"), SUPER_ADMIN("超级管理员");
    private String value;

    private SourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
