package com.tutorial.spring.pictogram.property_sources;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@Slf4j
public class PictogramSpringApplicationListener implements SpringApplicationRunListener {
    public PictogramSpringApplicationListener(SpringApplication application, String[] args) {
    }

    @Override
    public void starting() {

    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        String filename = environment.getProperty("pictogram.configuration");
        try {
            XMLConfiguration xmlConfiguration = new XMLConfiguration(filename);
            PictogramXmlConfigurationSource.addToEnvironment(environment,xmlConfiguration);
        } catch (ConfigurationException e) {
            log.error("Unable to load configuration from file: {}", filename, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {

    }

    @Override
    public void finished(ConfigurableApplicationContext context, Throwable exception) {

    }
}
