package com.example.il_platform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/home", "/users/login", "/users/reg","/courses").permitAll()
                .antMatchers("/account").authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/users/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .passwordParameter("confirmPassword")
                .successHandler(((request, response, authentication) -> {
                    String password = request.getParameter("password");
                    String confirmPassword = request.getParameter("confirmPassword");
                    if (password.equals(confirmPassword)) {
                        response.sendRedirect("account");
                    }
                }))
                .failureHandler(((request, response, exception) -> {
                    response.sendRedirect("/users/login");
                })).permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(((request, response, authentication) -> {
                    request.getSession().invalidate();
                    response.sendRedirect("home");
                }))
                .and()
                .cors().disable()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
