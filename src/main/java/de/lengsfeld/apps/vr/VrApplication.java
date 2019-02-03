package de.lengsfeld.apps.vr;

import com.zaxxer.hikari.HikariDataSource;
import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
//@EnableScheduling
public class VrApplication {

    protected final Log log = LogFactory.getLog(getClass());

    @Autowired
    private CemeteryRepository cemeteryRepository;

    @Scheduled(initialDelay = 1000, fixedRate = 5000)
    public void run() {
        log.info("Number of cemeteries: " + cemeteryRepository.count());
    }

    public static void main(String[] args) {
        SpringApplication.run(VrApplication.class, args);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("b2bce111577fff");
        dataSource.setPassword("201c397b");
        dataSource.setUrl("jdbc:mysql://eu-cdbr-west-02.cleardb.net");
        dataSource.setSchema("heroku_e5f435f142d9681");

        return dataSource;
    }

/*    @Bean
    public StartupRunner schedulerRunner() {
        return new StartupRunner();
    }*/
}
