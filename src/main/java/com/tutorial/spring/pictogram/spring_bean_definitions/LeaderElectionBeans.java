package com.tutorial.spring.pictogram.spring_bean_definitions;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tutorial.spring.pictogram.Greeter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.retry.RetryOneTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.leader.event.AbstractLeaderEvent;
import org.springframework.integration.leader.event.DefaultLeaderEventPublisher;
import org.springframework.integration.support.SmartLifecycleRoleController;
import org.springframework.integration.zookeeper.config.CuratorFrameworkFactoryBean;
import org.springframework.integration.zookeeper.config.LeaderInitiatorFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.LinkedMultiValueMap;

import java.util.concurrent.ThreadFactory;

@Configuration
@Slf4j
public class LeaderElectionBeans {
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
    @Value("${zookeeper.port:2181}")
    private  String ZK_PORT;
    private static final String ROLE_LEADER = "leader";

    @Bean
    public ThreadFactory threadFactory() {
        return new ThreadFactoryBuilder()
                .setNameFormat(ROLE_LEADER)
                .build();
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

}
