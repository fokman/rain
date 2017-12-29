package com.rain.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RainWebApplication {
	private static Logger logger = LoggerFactory.getLogger(RainWebApplication.class);

	public static void main(String[] args) {
		logger.info("start Rain web application.");
		SpringApplication.run(RainWebApplication.class, args);

	}
}
