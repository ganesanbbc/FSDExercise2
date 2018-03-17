package com.workout.util;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@ComponentScan
public class SpringSecurity extends WebSecurityConfigurerAdapter {
    public static final String USER = "/user/**";
    public static final String WORKOUT = "/workout/**";
    public static final String WORKOUT_TXN = "/transaction/**";
    public static final String USER_LOGIN_ERROR_TRUE = "/user/login?error=true";
    public static final String USER_LOGIN = "/user/login";
    public static final String USER_REGIST = "/user/regist";
    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";
    public static final String USER_LOGOUT = "/user/logout";
    public static final String ACCESS_DENIED = "/access-denied";
    public static final String LOGOUT_SUCCESS_URL = "/";
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;


    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers(USER).permitAll()
                .antMatchers(WORKOUT).permitAll()
                .antMatchers(WORKOUT_TXN).permitAll().anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage(USER_LOGIN).failureUrl(USER_LOGIN_ERROR_TRUE)
                .defaultSuccessUrl(USER_REGIST)
                .usernameParameter(USER_NAME)
                .passwordParameter(PASSWORD)
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(USER_LOGOUT))
                .logoutSuccessUrl(LOGOUT_SUCCESS_URL).and().exceptionHandling()
                .accessDeniedPage(ACCESS_DENIED);
    }

}