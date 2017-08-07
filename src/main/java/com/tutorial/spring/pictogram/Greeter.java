package com.tutorial.spring.pictogram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@Component
public class Greeter extends BasicSmartLifeCycleWrapper implements Runnable {

    private final ExecutorService executorService;
    private Future<?> greeterFuture = null;

    @Value("${pictogram.greeter.version:VERSION_NOT_SET}")
    private String version;
    public Greeter(ExecutorService leaderElectedExecutor) {
        executorService = leaderElectedExecutor;
    }

    private void sendGreeting() throws InterruptedException {
        while (super.getIsRunning().get()) {
            log.info("Greeter says Hi {}", this);
            Thread.sleep(2000);
        }
    }

    @Override
    public void run() {
        try {
            sendGreeting();
        } catch (Exception e) {
            log.warn("Greeter interrupted, stopping.");
            super.getIsRunning().set(false);
        }
    }

    @Override
    public void start() {
        super.start();
        greeterFuture = executorService.submit(this);

    }

    @Override
    public void stop() {
        super.stop();
        log.info("Stop Called");
        greeterFuture.cancel(true);
    }

    @Override
    public String toString() {
        String msg = String.format("%s - version: %s", Instant.now(), version);
        return String.valueOf(msg);
    }
}

