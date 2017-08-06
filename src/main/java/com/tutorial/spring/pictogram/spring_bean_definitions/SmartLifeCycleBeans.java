package com.tutorial.spring.pictogram.spring_bean_definitions;

import com.tutorial.spring.pictogram.Greeter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Add these beans to the LeaderElectionBeans.SmartLifeCycleController
 */
@Configuration
public class SmartLifeCycleBeans {
    @Bean
    public ExecutorService leaderElectedExecutor(ThreadFactory threadFactory) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadScheduledExecutor(threadFactory);
        executorService.awaitTermination(0, TimeUnit.SECONDS);
        return executorService;
    }

    @Bean
    public Greeter greeter(ExecutorService leaderElectedExecutor ) {
        return new Greeter(leaderElectedExecutor);
    }
}
