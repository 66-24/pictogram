package com.tutorial.spring.pictogram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;
import org.springframework.integration.annotation.Role;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class Greeter implements Runnable, SmartLifecycle {

    private final ExecutorService executorService;
    private BlockingDeque<Future> tasksInProgress = new LinkedBlockingDeque<>();
    AtomicBoolean isRunning = new AtomicBoolean(true);

    Greeter(ExecutorService leaderElectedExecutor) {
        executorService = leaderElectedExecutor;
    }

    @Role(SpringConfig.ROLE_LEADER)
    private void sendGreeting() throws InterruptedException {
        while (true) {
            log.info("Leader says Hi {}", this);
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
    }

    @Override
    public void start() {
//        Future<?> future = executorService.submit(this);
        new Thread(this).start();
        isRunning.set(true);
//        tasksInProgress.add(future);
    }

    @Override
    public void stop() {
        log.info("Stop Called");
        tasksInProgress.stream().forEach(t -> t.cancel(true));
        tasksInProgress.clear();
    }

    @Override
    public boolean isRunning() {
        return isRunning.get();
    }

    @Override
    public int getPhase() {
        return 0;
    }

}

