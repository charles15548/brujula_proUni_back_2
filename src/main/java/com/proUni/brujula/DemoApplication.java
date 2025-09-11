package com.proUni.brujula;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
    	TimeZone.setDefault(TimeZone.getTimeZone("America/Lima"));
    	
        SpringApplication.run(DemoApplication.class, args);
    }
}

