package com.rain.common.uitls;

public enum TempleteSchool {
    INIT("886"), TOTAL("887");
    private String value;

    private TempleteSchool(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
