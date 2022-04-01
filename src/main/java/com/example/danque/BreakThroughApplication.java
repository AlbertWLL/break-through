package com.example.danque;

import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootApplication
@SpringBootTest
@RunWith(SpringRunner.class)
public class BreakThroughApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreakThroughApplication.class, args);
    }

}
