package com.tutorial.spring.pictogram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class PictogramApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PictogramApplication.class, args);
    }


    @Override
//    @Role("cluster")
    public void run(String... strings) throws Exception {
        log.info("run called");
        Thread.sleep(Long.MAX_VALUE);
    }

}
