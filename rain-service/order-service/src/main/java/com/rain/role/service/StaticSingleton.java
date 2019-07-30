package com.rain.role.service;

import java.io.Serializable;

/**
 * Created by fokman on 18/7/6.
 */
public class StaticSingleton implements Serializable {

    private StaticSingleton() {

    }

    private static class SingletonHolder {
        private static StaticSingleton instance = new StaticSingleton();
    }

    public static StaticSingleton getInstance() {
        return SingletonHolder.instance;
    }

    private StaticSingleton readResolve() {
        return SingletonHolder.instance;
    }

}
