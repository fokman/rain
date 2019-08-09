package com.rain.common.servcie;

public class SetupClass implements Comparable<SetupClass> {

    private int sort;
    private Class<?> clas;

    public SetupClass() {
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Class<?> getClas() {
        return clas;
    }

    public void setClas(Class<?> clas) {
        this.clas = clas;
    }

    public int compareTo(SetupClass o) {
        if (o.sort > this.sort) {
            return 1;
        } else if (o.sort == this.sort) {
            return 0;
        } else {
            return -1;
        }
    }
}
