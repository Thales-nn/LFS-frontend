package com.locadorafilmes.locadora.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Permite acesso público às rotas principais, estáticos e de login/registro
                .requestMatchers("/", "/index", "/locacoes/**", 
                               "/filmes/**", "/clientes/**",
                               "/pagamentos/**","/relatorios/**",
                               "/dashboard","/login","/registrar" , "/css/**", "/js/**").permitAll()
                // Qualquer outra requisição precisa de autenticação
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // Define a página de login customizada
                .loginPage("/login")
                // Redireciona para "/home" após o login bem-sucedido
                .defaultSuccessUrl("/home",true)
                .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                );   
        return http.build();
    }
}
