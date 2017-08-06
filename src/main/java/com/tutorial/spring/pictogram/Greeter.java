package com.tutorial.spring.pictogram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class Greeter implements Runnable, SmartLifecycle {

    private final ExecutorService executorService;
    private Future<?> greeterFuture = null;
    private AtomicBoolean isRunning = new AtomicBoolean(true);

    @Value("${pictogram.greeter.version:VERSION_NOT_SET}")
    private String version;
    public Greeter(ExecutorService leaderElectedExecutor) {
        executorService = leaderElectedExecutor;
    }

    private void sendGreeting() throws InterruptedException {
        while (isRunning.get()) {
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
            isRunning.set(false);
        }
    }

    @Override
    public boolean isAutoStartup() {
        return false;
    }

    @Override
    public void stop(Runnable runnable) {
        log.info("Stop with Runnable Called");
        isRunning.set(false);
        runnable.run();
    }

    @Override
    public void start() {
        greeterFuture = executorService.submit(this);
        isRunning.set(true);
    }

    @Override
    public void stop() {
        log.info("Stop Called");
        isRunning.set(false);
        greeterFuture.cancel(true);
    }

    @Override
    public boolean isRunning() {
        return isRunning.get();
    }

    @Override
    public int getPhase() {
        return 0;
    }

    @Override
    public String toString() {
        String msg = String.format("%s - version: %s", Instant.now(), version);
        return String.valueOf(msg);
    }
}

