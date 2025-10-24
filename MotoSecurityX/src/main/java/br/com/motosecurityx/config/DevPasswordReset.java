package br.com.motosecurityx.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Em DEV (profile "dev"):
 *  - Garante, SEMPRE, que:
 *      admin    -> admin123
 *      operador -> oper123
 *  - Grava usando o PasswordEncoder configurado (delegating -> {bcrypt}).
 */
@Component
@Profile("dev")
public class DevPasswordReset implements CommandLineRunner {

    private final JdbcTemplate jdbc;
    private final PasswordEncoder encoder;

    public DevPasswordReset(JdbcTemplate jdbc, PasswordEncoder encoder) {
        this.jdbc = jdbc;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        reset("admin", "admin123");
        reset("operador", "oper123");
    }

    private void reset(String username, String raw) {
        String encoded = encoder.encode(raw); // vai gerar {bcrypt}...
        int updated = jdbc.update(
                "update usuario set password = ? where username = ?",
                encoded, username
        );
        if (updated > 0) {
            System.out.printf("[DevPasswordReset] Senha de '%s' ajustada para {bcrypt}.%n", username);
        }
    }
}
