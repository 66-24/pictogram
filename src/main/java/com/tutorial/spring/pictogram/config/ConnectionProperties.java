package com.tutorial.spring.pictogram.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties("impala.connection")
@Data
public class ConnectionProperties {
    /**
     * The Impala JDBC driver url
     */
    private String driverUrl;
    private int port;
    private String username;
    private String password;
}
