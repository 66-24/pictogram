package com.tutorial.spring.pictogram;

import com.tutorial.spring.pictogram.spring_bean_definitions.SpringConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

@Slf4j
public class PictogramApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringConfig.class, args);
    }


    @Override
    public void run(String... strings) throws Exception {
        log.info("run called");
        Thread.sleep(Long.MAX_VALUE);
    }

}
