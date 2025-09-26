INSERT INTO usuario (username, password, enabled) VALUES
                                                      ('admin',    '{noop}admin123',    TRUE),
                                                      ('operador', '{noop}oper123',     TRUE);

-- relaciona usuários às roles
INSERT INTO usuario_role (usuario_id, role_id)
SELECT u.id, r.id FROM usuario u, role r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';

INSERT INTO usuario_role (usuario_id, role_id)
SELECT u.id, r.id FROM usuario u, role r
WHERE u.username = 'operador' AND r.name = 'ROLE_OPERADOR';
