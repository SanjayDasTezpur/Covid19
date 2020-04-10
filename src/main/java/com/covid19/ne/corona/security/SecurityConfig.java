package com.covid19.ne.corona.security;

import com.covid19.ne.corona.persistency.FilePersistency;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.PostConstruct;

/**
 * Created by sanjayda on 9/25/2018 at 1:09 PM
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Getter
    private String user;
    @Getter
    private String password;
    @Getter
    private String adminUser;
    @Getter
    private String adminPassword;

    @PostConstruct
    public void init() {

        String sec = new FilePersistency<>("C:\\Users\\sanjayda\\").getTextFromSimpleFile("sec.txt");
        this.user = sec.split(":")[0];
        this.password = sec.split(":")[1];
        this.adminUser = sec.split(":")[2];
        this.adminPassword = sec.split(":")[3];
    }

    // Authentication : User --> Roles
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance()).withUser(user).password(password)
                .roles("USER").and().withUser(adminUser).password(adminPassword)
                .roles("USER", "ADMIN");
    }

    // Authorization : Role -> Access
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests().antMatchers("/auth/**")
                .hasRole("USER").antMatchers("/auth**").hasRole("ADMIN").and()
                .csrf().disable().headers().frameOptions().disable();
    }


}
