package com.tutorial.spring.pictogram.converters;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;

import java.time.Duration;

@ConfigurationPropertiesBinding
public class StringToDurationConverter implements Converter<String, Duration> {
    @Override
    public Duration convert(String s) {
        return  Duration.ofMillis(Integer.parseInt(s));
    }
}
