package com.estore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EStoreApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(EStoreApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("I am live at 8081...");
    }
}
