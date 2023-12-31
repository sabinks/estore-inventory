package com.sendmail;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SendMailConsumer implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(SendMailConsumer.class);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Send mail running at 8082...");
    }
}
