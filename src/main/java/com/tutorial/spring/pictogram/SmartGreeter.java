package com.tutorial.spring.pictogram;

import com.tutorial.spring.pictogram.config.ConnectionProperties;
import com.tutorial.spring.pictogram.config.SmartGreeterProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@Component

public class SmartGreeter extends BasicSmartLifeCycleWrapper {

    private ConnectionProperties connectionProperties;
    private SmartGreeterProperties smartGreeterProperties;
    private ExecutorService executorService;
    private Future future;

    public SmartGreeter(
            ConnectionProperties connectionProperties,
            SmartGreeterProperties smartGreeterProperties,
            ExecutorService executorService) {
        this.connectionProperties = connectionProperties;
        this.smartGreeterProperties = smartGreeterProperties;
        this.executorService = executorService;
    }

    @Scheduled(fixedDelayString = "${smart.greeter.delayInMillis}")
    public void greet() {
        log.info(
                "Connection Properties: {}, \n" +
                "App Properties: {}", connectionProperties, smartGreeterProperties);
    }

    @Override
    public void start() {
        super.start();
        future = executorService.submit(this::greet);
    }

    @Override
    public void stop() {
        super.stop();
        future.cancel(true);
    }
}
