package com.hahaha.fileparsingsystem.security.config;


import com.hahaha.fileparsingsystem.security.entity.User;
import com.hahaha.fileparsingsystem.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Autowired
            UserService userService;
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userService.getUserByName(username);
                if (user == null)
                    throw new UsernameNotFoundException("用户不存在！");
                ArrayList<GrantedAuthority> list = new ArrayList<>();
                for (String role : user.getRoles()) {
                    list.add(new SimpleGrantedAuthority(role));
                }
                System.out.println(list);
                return new org.springframework.security.core.userdetails.User(
                        user.getUserName(),
                        user.getPassWord(),
                        list);
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                .and()
                .authorizeRequests()
                .antMatchers("/file/**").hasAnyAuthority("vip", "normal")
//                .antMatchers("/parsing/**").hasAuthority("vip")
                .anyRequest().permitAll();
        return http.build();
    }

}
