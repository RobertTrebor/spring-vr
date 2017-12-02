package de.lengsfeld.apps.vr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(JpaConfiguration.class)
@SpringBootApplication
public class VrApplication {

    public static void main(String[] args) {
        SpringApplication.run(VrApplication.class, args);
    }

    @Bean
    public StartupRunner schedulerRunner() {
        return new StartupRunner();
    }
}
