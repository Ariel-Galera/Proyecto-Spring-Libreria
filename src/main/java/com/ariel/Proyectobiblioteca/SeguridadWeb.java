package com.ariel.Proyectobiblioteca;

import com.ariel.Proyectobiblioteca.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)


public class SeguridadWeb {

    @Autowired
    public UsuarioServicio usuarioServicio;



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());

    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
     return (web -> web.ignoring()
             .requestMatchers("/css/*","/js/*","/img/*","/**"));
    }

}
