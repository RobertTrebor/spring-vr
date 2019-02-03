package de.lengsfeld.apps.vr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class VrApplication {
/*
    protected final Log log = LogFactory.getLog(getClass());

    @Autowired
    private CemeteryRepository cemeteryRepository;



    @Scheduled(initialDelay = 1000, fixedRate = 5000)
    public void run() {
        log.info("Number of cemeteries: " + cemeteryRepository.count());
    }
*/

    public static void main(String[] args) {
        SpringApplication.run(VrApplication.class, args);
    }

/*    @Bean
    public StartupRunner schedulerRunner() {
        return new StartupRunner();
    }*/
}
