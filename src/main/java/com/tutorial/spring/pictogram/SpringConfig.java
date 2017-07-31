package com.tutorial.spring.pictogram;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.retry.RetryOneTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.leader.event.AbstractLeaderEvent;
import org.springframework.integration.leader.event.DefaultLeaderEventPublisher;
import org.springframework.integration.support.SmartLifecycleRoleController;
import org.springframework.integration.zookeeper.config.CuratorFrameworkFactoryBean;
import org.springframework.integration.zookeeper.config.LeaderInitiatorFactoryBean;
import org.springframework.util.LinkedMultiValueMap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
@EnableIntegration
@SpringBootApplication
public class SpringConfig {

    @Value("${zookeeper.port:2181}")
    private  String ZK_PORT;
    static final String ROLE_LEADER = "leader";

    @Bean
    public ThreadFactory threadFactory() {
        return new ThreadFactoryBuilder()
                .setNameFormat("LeaderElection")
                .build();
    }


    @Bean
    public ExecutorService leaderElectedExecutor() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor(threadFactory());
        executorService.awaitTermination(0, TimeUnit.SECONDS);
        return executorService;
    }

    @Bean
    public CuratorFramework curatorFramework() throws Exception {
        CuratorFrameworkFactoryBean curatorFrameworkFactoryBean =
                new CuratorFrameworkFactoryBean(
                        "localhost:" + ZK_PORT,
                        new RetryOneTime(2000));
        curatorFrameworkFactoryBean.setAutoStartup(false);
        return curatorFrameworkFactoryBean.getObject();
    }

    @Bean
    public SmartLifecycleRoleController smartLifecycleRoleController(Greeter greeter) {
        LinkedMultiValueMap<String, SmartLifecycle> rolesToLifeCycle = new LinkedMultiValueMap<>();
        rolesToLifeCycle.add(ROLE_LEADER, greeter);
        return new SmartLifecycleRoleController(rolesToLifeCycle);
    }

    @Bean
    public ApplicationEventPublisher applicationEventPublisher(SmartLifecycleRoleController smartLifecycleRoleController) {
        return new ApplicationEventPublisher() {
            @Override
            public void publishEvent(ApplicationEvent applicationEvent) {
                log.info("Got application event: {}", applicationEvent);
                smartLifecycleRoleController.onApplicationEvent((AbstractLeaderEvent) applicationEvent);
            }

            @Override
            public void publishEvent(Object o) {
                log.info("Got object: {}", o);
                smartLifecycleRoleController.onApplicationEvent((AbstractLeaderEvent) o);
            }
        };
    }


    @Bean
    public LeaderInitiatorFactoryBean leaderInitiator(CuratorFramework client, ApplicationEventPublisher applicationEventPublisher) throws Exception {
        LeaderInitiatorFactoryBean leaderInitiatorFactoryBean = new LeaderInitiatorFactoryBean();
        leaderInitiatorFactoryBean.setClient(client);
        leaderInitiatorFactoryBean.setPath("/leader_election/pictogram");
        leaderInitiatorFactoryBean.setRole(ROLE_LEADER);
        leaderInitiatorFactoryBean.setLeaderEventPublisher(new DefaultLeaderEventPublisher(applicationEventPublisher));
        return leaderInitiatorFactoryBean;
    }

    @Bean
    public Greeter greeter(ExecutorService leaderElectedExecutor ) {
        return new Greeter(leaderElectedExecutor);
    }

}
