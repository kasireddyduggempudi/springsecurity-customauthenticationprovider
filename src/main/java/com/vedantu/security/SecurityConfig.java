package com.vedantu.security;

import com.vedantu.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public SecurityConfig(){
        System.out.println("in clas");
    }

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private  JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // this will set the authentication manager to use this data to authenticate
        // even we can use UserDetailsService which will provide data
        // this is like authentication provider
        // which will be called by the authentication manager
        /*auth.inMemoryAuthentication()
                .withUser("kasi")
                .password("kasi")
                .roles("USER");*/
        //auth.userDetailsService(myUserDetailsService); // this is DaoAuthenticationProvider
        auth.authenticationProvider(customAuthenticationProvider);

/*        auth.inMemoryAuthentication().withUser("kasi").password("kasi").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("reddy").password("kasi").roles("USER");*/
        // this takes the data from the authentication manager and validates it

        System.out.println("authentication");
    }

    protected void configure(HttpSecurity http) throws Exception {
/*        http
            .authorizeRequests()
            .antMatchers("/resources/**", "/signup", "/about", "/login", "/register", "/loginPage").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/db/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_DBA')")
            .anyRequest().authenticated()
            .and()
            // ...
            .formLogin()
            .loginPage("/login");*/


        http.csrf().disable();  // if not post not enabled
        // http.cors().disable();   // if disable not working :::))))

        http
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                /*.antMatchers("/**").permitAll()*/
                .antMatchers("/user").hasAnyRole("USER" , "ADMIN")
                .antMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        System.out.println("authorization");
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    // password encoder
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
        // means no encoding technique here.
        // later we can change
    }

}
