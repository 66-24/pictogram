package com.tutorial.spring.pictogram.property_sources;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.XMLConfiguration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.StandardEnvironment;

import java.util.ArrayList;

@Slf4j
public class PictogramXmlConfigurationSource extends EnumerablePropertySource<XMLConfiguration> {

    private PictogramXmlConfigurationSource(String name, XMLConfiguration source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        ArrayList<String> keys = Lists.newArrayList(this.source.getKeys());
        return keys.toArray(new String[keys.size()]);    }

    @Override
    public Object getProperty(String name) {
        return this.source.getProperty(name);
    }

    static void addToEnvironment(ConfigurableEnvironment environment, XMLConfiguration xmlConfiguration) {
        environment.getPropertySources().addAfter(
                StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME,
                new PictogramXmlConfigurationSource("PictogramXmlConfiguration", xmlConfiguration));
        log.info("PictogramXmlConfigurationSource added to Environment");
    }
}
