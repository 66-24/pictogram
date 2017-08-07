package com.tutorial.spring.pictogram.metrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Random;
@Slf4j
public class DiskSpaceUsageChecker {
    //Used to simulate a time costly operation
    private Duration duration = Duration.ofSeconds(10);
    public DiskSpaceUsageChecker(MetricRegistry metricRegistry) {
        Gauge<Long> metric = this::diskSpaceUsage;
        metricRegistry.register("pictogram.diskspace", metric);
    }

    //TODO Use `@Cacheable` if this is a costly operation
    public long diskSpaceUsage() {
        Random random = new Random();
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            log.info("Interrupted while sleeping");
            Thread.currentThread().interrupt();
        }
        return random.nextLong();
    }
}
