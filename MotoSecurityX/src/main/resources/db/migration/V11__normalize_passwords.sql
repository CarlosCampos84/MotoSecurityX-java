-- V11__normalize_passwords.sql
-- 1) Prefixa {bcrypt} onde faltar
UPDATE usuario
SET password = '{bcrypt}' || password
WHERE password LIKE '$2a$%' AND password NOT LIKE '{bcrypt}%';

-- 2) Reaplica as senhas padrão conhecidas (com prefixo)
UPDATE usuario
SET password = '{bcrypt}$2a$10$XG2dX7Sx5y3S0sB8Qm7b.u2T.5K7Oa7mXa9jXH3Y2a3Z8vB4Zz4vS'
WHERE username = 'admin';

UPDATE usuario
SET password = '{bcrypt}$2a$10$kVbS0q1c0c7eJfPzq6r3Nulxv0bY9XrQ7v7mJkE7o54S2k6x5m2mi'
WHERE username = 'operador';
