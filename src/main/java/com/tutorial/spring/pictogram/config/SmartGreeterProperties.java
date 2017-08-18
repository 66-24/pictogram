package com.tutorial.spring.pictogram.config;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix="smart")
@Component
@Data
@Validated
public class SmartGreeterProperties {
    @NotEmpty
    private Map<String,String> sparkProperties;

    @NotEmpty
    private String mainGreeting;

    @NotNull
    @Valid
    private Greeter greeter;

    @NotNull
    @Valid
    private Greetings greetings;

    @Data
    public static class Greetings {
        private List<String> greeting;
    }

    @Data
    public static class Greeter {
//        @NotEmpty
//        private List<String> greetings;


        /**
         * Greeter will repeat greeting with this delayInMillis
         */
        @Range(min=2000L,max=10_000L)
        private long delayInMillis=10_000L;
    }


//    public enum GreetingType {
//        MORNING("morning"), EVENING("evening");
//
//        private String type;
//
//        GreetingType(String type) {
//
//            this.type = type;
//        }
//    }

}
