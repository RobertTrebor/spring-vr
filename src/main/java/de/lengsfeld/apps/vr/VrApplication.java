package de.lengsfeld.apps.vr;

import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/*
    mvn spring-boot:run -Dspring.profiles.active=dev
 */

@SpringBootApplication
@EnableScheduling
public class VrApplication {

    protected final Log log = LogFactory.getLog(getClass());

    private final CemeteryRepository cemeteryRepository;


    @Autowired
    public VrApplication(CemeteryRepository cemeteryRepository) {
        this.cemeteryRepository = cemeteryRepository;
    }

    @Scheduled(initialDelay = 1000, fixedRate = 15000)
    public void run() {
        log.info("Number of cemeteries: " + cemeteryRepository.count());
    }

    public static void main(String[] args) {
        SpringApplication.run(VrApplication.class, args);
    }

    @Bean
    public StartupRunner schedulerRunner() {
        return new StartupRunner();
    }

    @Value("${server.port}")
    String serverPort;

    @Bean
    CommandLineRunner values(){
        return args -> {
            log.info("Server Port is: " + serverPort);
        };
    }

}

