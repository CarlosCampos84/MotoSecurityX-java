package br.com.motosecurityx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    // Autenticação via JDBC usando as tabelas criadas pelo Flyway
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager jdbc = new JdbcUserDetailsManager(dataSource);
        jdbc.setUsersByUsernameQuery(
                "select username, password, enabled from usuario where username=?");
        jdbc.setAuthoritiesByUsernameQuery(
                "select u.username, r.name from role r " +
                        "join usuario_role ur on ur.role_id = r.id " +
                        "join usuario u on u.id = ur.usuario_id " +
                        "where u.username=?");
        return jdbc;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        // Públicos
                        .requestMatchers(
                                "/css/**", "/js/**", "/images/**", "/webjars/**",
                                "/favicon.ico", "/error", "/h2-console/**",
                                "/login"
                        ).permitAll()

                        // UI (Thymeleaf) - leitura para ADMIN/OPERADOR
                        .requestMatchers(HttpMethod.GET, "/motos/**", "/patios/**", "/fluxos/**")
                        .hasAnyRole("ADMIN","OPERADOR")
                        // UI - escrita só ADMIN
                        .requestMatchers(HttpMethod.POST, "/motos/**", "/patios/**", "/fluxos/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/motos/**", "/patios/**", "/fluxos/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/motos/**", "/patios/**", "/fluxos/**")
                        .hasRole("ADMIN")

                        // API REST - leitura para ADMIN/OPERADOR
                        .requestMatchers(HttpMethod.GET, "/api/**")
                        .hasAnyRole("ADMIN","OPERADOR")
                        // API REST - escrita só ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")

                        // Outras áreas administrativas (se houver)
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Demais rotas: autenticado
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .exceptionHandling(e -> e
                        .accessDeniedPage("/login?denied")
                )
                // H2 Console
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                // CSRF: ignorar H2 e API (facilita testes de endpoints REST)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/api/**"));

        return http.build();
    }
}
