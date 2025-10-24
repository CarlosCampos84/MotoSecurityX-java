package br.com.motosecurityx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager jdbc = new JdbcUserDetailsManager(dataSource);
        jdbc.setUsersByUsernameQuery(
                "select username, password, enabled from usuario where username=?"
        );
        jdbc.setAuthoritiesByUsernameQuery(
                "select u.username as username, r.name as authority " +
                        "from usuario u " +
                        "join usuario_role ur on ur.usuario_id = u.id " +
                        "join role r on r.id = ur.role_id " +
                        "where u.username=?"
        );
        return jdbc;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // ✅ PasswordEncoder que remove automaticamente o {bcrypt}
        return new PasswordEncoder() {
            private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            
            @Override
            public String encode(CharSequence rawPassword) {
                return "{bcrypt}" + bcrypt.encode(rawPassword);
            }
            
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                // Remove o prefixo {bcrypt} se existir
                if (encodedPassword.startsWith("{bcrypt}")) {
                    encodedPassword = encodedPassword.substring(8);
                }
                return bcrypt.matches(rawPassword, encodedPassword);
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/css/**", "/js/**", "/images/**", "/webjars/**",
                                "/favicon.ico", "/error", "/h2-console/**", "/login"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/motos/**", "/patios/**").hasAnyRole("ADMIN","OPERADOR")
                        .requestMatchers(HttpMethod.POST, "/motos/**", "/patios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/motos/**", "/patios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/motos/**", "/patios/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
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
                .exceptionHandling(e -> e.accessDeniedPage("/login?denied"))
                .headers(h -> h.frameOptions(f -> f.sameOrigin()))
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));

        return http.build();
    }
}