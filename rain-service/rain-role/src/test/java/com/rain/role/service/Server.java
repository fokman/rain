package com.rain.role.service;


import com.rain.ice.utils.StartServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Server {

    public static void main(String[] args) {
        StartServer.startServer(args);
    }

}