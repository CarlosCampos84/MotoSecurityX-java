-- Adiciona flag de disponibilidade para a moto
ALTER TABLE moto
    ADD COLUMN disponivel BOOLEAN NOT NULL DEFAULT TRUE;

-- (Opcional, para garantir valor consistente em registros já existentes)
UPDATE moto SET disponivel = TRUE WHERE disponivel IS NULL;
