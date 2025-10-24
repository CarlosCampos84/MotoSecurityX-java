package br.com.motosecurityx.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
public class DevPasswordNormalizer implements CommandLineRunner {

    private final JdbcTemplate jdbc;
    private final PasswordEncoder encoder;

    public DevPasswordNormalizer(JdbcTemplate jdbc, PasswordEncoder encoder) {
        this.jdbc = jdbc;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        normalize("admin", "admin123");
        normalize("operador", "oper123");
    }

    private void normalize(String username, String rawPassword) {
        try {
            String current = jdbc.queryForObject(
                    "select password from usuario where username = ?",
                    String.class, username
            );

            // Se não existe, ou não está com prefixo {bcrypt}, grava como {bcrypt}
            if (current == null || !current.startsWith("{bcrypt}")) {
                String encoded = encoder.encode(rawPassword); // delegating -> {bcrypt}...
                jdbc.update("update usuario set password = ? where username = ?", encoded, username);
                System.out.printf("[PasswordNormalizer] Ajustei senha de %s para {bcrypt}.%n", username);
            }
        } catch (Exception ex) {
            System.out.printf("[PasswordNormalizer] Aviso: não consegui verificar/ajustar %s. %s%n",
                    username, ex.getMessage());
        }
    }
}
