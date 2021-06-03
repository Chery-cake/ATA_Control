package com.control.ata.security.config;

import com.control.ata.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();

//        http.headers().disable();

        http.authorizeRequests()
                .antMatchers("/", "/home", "**.js", "/js/**", "**.css", "/css/**", "/img/**", "/webjars/**", "/assets", "/assets/**", "/save", "/save/**", "/register/pessoa", "/academia")
                .permitAll();

        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll();

        http.authorizeRequests().antMatchers("/planilha", "/placar").access("hasRole('ROLE_PLANILHA')");

        http.authorizeRequests().antMatchers("/perfil", "/criar/**", "/cadastrar/**", "/relatorio/**", "/novo/administrador").access("hasRole('ROLE_ADMIN')");

        http.authorizeRequests().antMatchers("/register/competidor", "/register/juiz", "/ajustar/pessoa", "/ajustar/alunos").access("hasRole('ROLE_USER')");

        http.authorizeRequests().anyRequest().permitAll();

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .deleteCookies("dummyCookie")
        );

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService)
                .passwordEncoder(bCryptPasswordEncoder());
    }
}
