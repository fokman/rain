package com.rain.ice.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Server {

    public static void main(String[] args) {
        log.info("start server....");
        StartServer.startServer(args);
    }

}