package com.tutorial.spring.pictogram.spring_bean_definitions;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.tutorial.spring.pictogram.metrics.DiskSpaceUsageChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
public class MetricBeans {
    @Bean
    public ScheduledExecutorService scheduledExecutorService(ThreadFactory leaderThreadFactory) {
        return Executors.newScheduledThreadPool(2,leaderThreadFactory);
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public ConsoleReporter consoleReporter(MetricRegistry metricRegistry, ScheduledExecutorService executorService) {
        return ConsoleReporter
                .forRegistry(metricRegistry)
                .shutdownExecutorOnStop(false)
                .scheduleOn(executorService)
                .build();
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public Slf4jReporter slf4jReporter(MetricRegistry metricRegistry, ScheduledExecutorService executorService) {
        return Slf4jReporter
                .forRegistry(metricRegistry)
                .withLoggingLevel(Slf4jReporter.LoggingLevel.INFO)
                .shutdownExecutorOnStop(false)
                .scheduleOn(executorService)
                .build();
    }

    @Bean
    public DiskSpaceUsageChecker diskSpaceUsageChecker(MetricRegistry metricRegistry) {
        return new DiskSpaceUsageChecker(metricRegistry);
    }
}
