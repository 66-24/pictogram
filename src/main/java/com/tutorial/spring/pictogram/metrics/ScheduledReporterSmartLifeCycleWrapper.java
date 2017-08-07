package com.tutorial.spring.pictogram.metrics;

import com.codahale.metrics.ScheduledReporter;
import com.tutorial.spring.pictogram.BasicSmartLifeCycleWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.SmartLifecycle;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ScheduledReporterSmartLifeCycleWrapper<T extends ScheduledReporter>
        extends BasicSmartLifeCycleWrapper implements SmartLifecycle{
    private ApplicationContext applicationContext;
    private Class<T> reporter;
    private Duration duration;
    private T currentReporter;

    public ScheduledReporterSmartLifeCycleWrapper(ApplicationContext applicationContext,
                                                  Class<T> reporter,
                                                  java.time.Duration duration) {
        this.applicationContext = applicationContext;
        this.reporter = reporter;
        this.duration = duration;
    }

    @Override
    public void start() {
        super.start();
        currentReporter = applicationContext.getBean(reporter);
        currentReporter.start(duration.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void stop() {
        super.stop();
        currentReporter.stop();
    }
}
