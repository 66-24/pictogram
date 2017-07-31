package com.tutorial.spring.pictogram;

import org.apache.curator.test.TestingServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringTestConfig {
    @Bean
    public TestingServer zookeeper() throws Exception {
        return new TestingServer(2182);
    }

    @Bean
    PictogramApplication pictogramApplication1() {
        return new PictogramApplication();
    }

    @Bean
    PictogramApplication pictogramApplication2() {
        return  new PictogramApplication();
    }

}
