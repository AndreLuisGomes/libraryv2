package com.grupo9.libraryapi.config;

import com.grupo9.libraryapi.security.CustomUserDetailsService;
import com.grupo9.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

                // Desativa a proteção contra ataques CSRF (Cross-Site Request Forgery).
                // Habilita o suporte ao formulário de login com as configurações padrão.
                // Habilita a autenticação básica HTTP com as configurações padrão.
                // Define as regras de autorização para as requisições HTTP.
                // Especifica que qualquer requisição deve estar autenticada.
                // Finaliza a configuração e retorna a instância de SecurityFilterChain.

                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(configurer -> { configurer.loginPage("/login").permitAll();})
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();

                    authorize.anyRequest().authenticated();
                })
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService){

        return new CustomUserDetailsService(usuarioService);


        //        UserDetails user1 = User.builder()
//                .username("username")
//                .password(encoder.encode("username"))
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("admin")
//                .password(encoder.encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
    }

}
