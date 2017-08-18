package com.tutorial.spring.pictogram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@Component
public class Greeter extends BasicSmartLifeCycleWrapper  {

    private final ExecutorService executorService;
    private Future<?> greeterFuture = null;

    @Value("${pictogram.greeter.greeting:GREETING_NOT_SET}")
    private String greeting;
    public Greeter(ExecutorService leaderElectedExecutor) {
        executorService = leaderElectedExecutor;
    }

    public void sendGreeting() {
        try {
            while (super.getIsRunning().get()) {
                log.info("Greeter says {}", greeting);
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            log.warn("Greeter interrupted, stopping.");
            super.getIsRunning().set(false);
        }
    }


    @Override
    public void start() {
        super.start();
        greeterFuture = executorService.submit(this::sendGreeting);

    }

    @Override
    public void stop() {
        super.stop();
        log.info("Stop Called");
        greeterFuture.cancel(true);
    }

}

