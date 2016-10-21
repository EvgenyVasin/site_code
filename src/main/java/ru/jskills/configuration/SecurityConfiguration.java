package ru.jskills.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Created by safin.v on 17.10.2016.
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/").permitAll().and()
                .authorizeRequests().antMatchers("/console/**").permitAll()
                .anyRequest().authenticated();
        http
                .csrf()
                .disable();
        http
                .headers()
                .frameOptions()
                .disable();


        http
                .formLogin()
                .loginPage("/login").permitAll().usernameParameter("j_username")
                .passwordParameter("j_password").loginProcessingUrl("/j_spring_security_check")
                .and()
                .authorizeRequests().antMatchers("/user/**").hasRole("USER")
                .and()
                .logout().logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/")
                .and()
                .rememberMe().key("myKey").tokenValiditySeconds(300)
                .and()
                .csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT USERNAME, PASSWORD, IDROLE FROM USER WHERE USERNAME=?")
                .authoritiesByUsernameQuery("SELECT U.USERNAME, R.NAMEROLE  FROM USER U, ROLE R WHERE R.IDROLE = U.IDROLE AND U.USERNAME = ?");
    }



}