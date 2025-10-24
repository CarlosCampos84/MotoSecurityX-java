-- Adiciona a capacidade do pátio
ALTER TABLE patio
    ADD COLUMN capacidade INT NOT NULL DEFAULT 50;

-- Por segurança, garante valor para registros existentes
UPDATE patio SET capacidade = 50 WHERE capacidade IS NULL;
