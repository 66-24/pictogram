package com.tutorial.spring.pictogram;

import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PictogramApplicationTests.TestBeans.class})
@DirtiesContext
@SpringBootTest
@Ignore
public class PictogramApplicationTests {
	@Configuration
	public static class TestBeans {
		@Bean
		TestingServer testingServer() throws Exception {
			return new TestingServer(2181);
		}
	}
    @Autowired
    TestingServer zookeeperServer;



	@After
	public void tearDown() throws IOException {
        CloseableUtils.closeQuietly(zookeeperServer);
    }


	@Test
	public void should_send_greeting_only_if_leader() throws Exception {

	}

}
