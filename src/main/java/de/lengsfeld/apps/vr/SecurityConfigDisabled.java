package de.lengsfeld.apps.vr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//@Profile("dev")
@Order(200)
@ConditionalOnProperty(name = "app.security.basic.enabled", havingValue = "false")
public class SecurityConfigDisabled extends WebSecurityConfigurerAdapter {

    protected final Log log = LogFactory.getLog(getClass());

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().permitAll();
    }


}

