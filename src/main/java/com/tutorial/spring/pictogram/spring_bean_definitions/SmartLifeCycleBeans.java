package com.tutorial.spring.pictogram.spring_bean_definitions;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Slf4jReporter;
import com.tutorial.spring.pictogram.Greeter;
import com.tutorial.spring.pictogram.metrics.ScheduledReporterSmartLifeCycleWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.ExecutorService;

/**
 * Add these beans to the LeaderElectionBeans.SmartLifeCycleController
 */
@Configuration
public class SmartLifeCycleBeans {
    @Bean
    public Greeter greeter(ExecutorService leaderElectedExecutor ) {
        return new Greeter(leaderElectedExecutor);
    }

    @Bean
    public ScheduledReporterSmartLifeCycleWrapper consoleReportersSmartLifeCycleWrapper(ApplicationContext applicationContext) {
        return new ScheduledReporterSmartLifeCycleWrapper<>(applicationContext, ConsoleReporter.class, Duration.ofSeconds(2));
    }

    @Bean
    public ScheduledReporterSmartLifeCycleWrapper slf4jReporterSmartLifeCycleWrapper(ApplicationContext applicationContext) {
        return new ScheduledReporterSmartLifeCycleWrapper<>(applicationContext, Slf4jReporter.class, Duration.ofSeconds(2));
    }
}
