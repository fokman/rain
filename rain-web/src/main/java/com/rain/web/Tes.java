package com.rain.web;

public class Tes {
    public static void main(String[] args) {
        Tes apiGatewayTest = new Tes();
        apiGatewayTest.replaceSpace(new StringBuffer("We Are Happy"));
    }

    public String replaceSpace(StringBuffer str) {
        String newSb = str.toString().replace(" ", "%20");
        System.out.println(newSb);
        return newSb.toString();
    }
}
