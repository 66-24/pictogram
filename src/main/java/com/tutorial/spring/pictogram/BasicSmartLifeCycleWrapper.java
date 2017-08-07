package com.tutorial.spring.pictogram;

import lombok.Getter;
import org.springframework.context.SmartLifecycle;

import java.util.concurrent.atomic.AtomicBoolean;


public abstract class BasicSmartLifeCycleWrapper implements SmartLifecycle {
    @Getter
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    @Override
    public boolean isAutoStartup() {
        return false;
    }

    @Override
    public void stop(Runnable callback) {
        isRunning.set(false);
        callback.run();
    }

    @Override
    public void start() {
        isRunning.set(true);
    }

    @Override
    public void stop() {
        isRunning.set(false);
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
