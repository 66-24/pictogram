package com.tutorial.spring.pictogram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author 66-24
 */
// TODO Shutdown hooks to clean up resources on shutdown
@Slf4j
@SpringBootApplication
public class PictogramApplication implements CommandLineRunner {

    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder()
                .addCommandLineProperties(true)
                .bannerMode(Banner.Mode.OFF)
                .sources(PictogramApplication.class)
                .logStartupInfo(true)
                .registerShutdownHook(true)
                .run(args);
    }


    @Override
    public void run(String... strings) throws Exception {
        log.info("Joining thread, you can press Ctrl+C to shutdown application");
        Thread.currentThread().join();
    }

}

